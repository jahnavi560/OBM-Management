package com.example.service;

import java.util.List;

import com.example.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
	public List<User> findAll();
	public void deleteUser(User user);
	
}
