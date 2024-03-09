package com.suraj.user.service.controller;

import com.suraj.user.service.entities.User;
import com.suraj.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User newUser = userService.saveUser(user);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
//		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
		User user = userService.getUser(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUser();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PostMapping("/update/{userId}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String userId) {
		User newUser = userService.updateUser(user, userId);
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}
}
