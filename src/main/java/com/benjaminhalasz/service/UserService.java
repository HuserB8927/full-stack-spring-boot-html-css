package com.benjaminhalasz.service;

import com.benjaminhalasz.model.User;


public interface UserService {

	public String registerUser(User user);

	public User findByEmail(String email);

	public String userActivation(String code);

}
