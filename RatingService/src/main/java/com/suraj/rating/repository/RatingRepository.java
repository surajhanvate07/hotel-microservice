package com.suraj.rating.repository;

import com.suraj.rating.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, String> {

	// Custom finder methods
	List<Rating> findByUserId(String userId);

	List<Rating> findByHotelId(String hotelId);
}
