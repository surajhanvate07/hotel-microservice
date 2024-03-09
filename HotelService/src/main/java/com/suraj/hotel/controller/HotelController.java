package com.suraj.hotel.controller;

import com.suraj.hotel.entities.Hotel;
import com.suraj.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;

	@PostMapping
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
		return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.createHotel(hotel));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Hotel> getHotel(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotelById(id));
	}

	@GetMapping
	public ResponseEntity<List<Hotel>> getHotels() {
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAllHotels());
	}
}
