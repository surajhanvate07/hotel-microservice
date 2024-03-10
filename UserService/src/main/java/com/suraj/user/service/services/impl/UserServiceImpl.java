package com.suraj.user.service.services.impl;

import com.suraj.user.service.entities.Hotel;
import com.suraj.user.service.entities.Rating;
import com.suraj.user.service.entities.User;
import com.suraj.user.service.exceptions.ResourceNotFoundException;
import com.suraj.user.service.repository.UserRepository;
import com.suraj.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		String id = UUID.randomUUID().toString();
		user.setUserId(id);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Given user is not present!"));

		// Fetch rating of the user from RATING_SERVICE
		// API - http://localhost:8083/ratings/users/6db383b8-87c9-4fcb-a237-8cc3b808d8ef

		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
		logger.info("{} ", ratingsOfUser);

		List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

		List<Rating> ratingList = ratings.stream().map(rating -> {
			// API call to hotel service to get the hotel
			// http://localhost:8082/hotels/2dcc06ac-1b4b-43e5-801c-2d7e7f8dfa00
			ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
			Hotel hotel = forEntity.getBody();
			logger.info("Response status code {}", forEntity.getStatusCode());

			// set the hotel to rating
			rating.setHotel(hotel);

			// return the rating
			return rating;
		}).collect(Collectors.toList());

		user.setRatings(ratingList);

		return user;
	}

	@Override
	public User updateUser(User user, String userId) {
		Optional<User> oldUser = userRepository.findById(userId);
		if (oldUser.isEmpty()) {
			throw new ResourceNotFoundException("User not exists with given Id" + userId);
		}
		User newUser = oldUser.get();
		if (user.getName() != null) {
			newUser.setName(user.getName());
		}
		if (user.getEmail() != null) {
			newUser.setEmail(user.getEmail());
		}
		if (user.getAbout() != null) {
			newUser.setAbout(user.getAbout());
		}
		return userRepository.save(newUser);
	}
}
