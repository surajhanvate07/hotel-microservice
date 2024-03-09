package com.suraj.user.service.services.impl;

import com.suraj.user.service.entities.User;
import com.suraj.user.service.exceptions.ResourceNotFoundException;
import com.suraj.user.service.repository.UserRepository;
import com.suraj.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

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
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Given user is not present!"));
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
