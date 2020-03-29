package com.benjaminhalasz.repository;

import org.springframework.data.repository.CrudRepository;

import com.benjaminhalasz.model.FormUser;
import com.benjaminhalasz.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);

	User findByActivation(String code);

	
}
