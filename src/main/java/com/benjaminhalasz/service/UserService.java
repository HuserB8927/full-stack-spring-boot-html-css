package com.benjaminhalasz.service;

import java.util.Date;

import com.benjaminhalasz.model.User;


public interface UserService {

	public String registerUser(User user);

	public User findByEmail(String email);

	public String userActivation(String code);

	public String userIpAddress();

	
	



	

}
