package com.suraj.hotel.services;

import com.suraj.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

	Hotel createHotel(Hotel hotel);

	Hotel getHotelById(String id);

	List<Hotel> getAllHotels();
}
