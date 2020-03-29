package com.benjaminhalasz.service;

import com.benjaminhalasz.model.FormUser;


public interface FormUserService {

	public String registerUser(FormUser newUser);
	public FormUser findByEmail(String email);

	

}
