package com.suraj.user.service.services;

import com.suraj.user.service.entities.User;

import java.util.List;

public interface UserService {

	User saveUser(User user);

	List<User> getAllUser();

	User getUser(String userId);

	User updateUser(User user, String userId);
}
