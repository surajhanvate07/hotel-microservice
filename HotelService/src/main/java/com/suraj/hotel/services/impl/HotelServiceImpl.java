package com.suraj.hotel.services.impl;

import com.suraj.hotel.entities.Hotel;
import com.suraj.hotel.exceptions.ResourceNotFoundException;
import com.suraj.hotel.repository.HotelRepository;
import com.suraj.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	private HotelRepository hotelRepository;

	@Override
	public Hotel createHotel(Hotel hotel) {
		String hotelId = UUID.randomUUID().toString();
		hotel.setId(hotelId);
		return hotelRepository.save(hotel);
	}

	@Override
	public Hotel getHotelById(String id) {
		return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found!!"));
	}

	@Override
	public List<Hotel> getAllHotels() {
		return hotelRepository.findAll();
	}
}
