package com.benjaminhalasz.repository;

import org.springframework.data.repository.CrudRepository;

import com.benjaminhalasz.model.FormUser;

public interface FormUserRepository extends CrudRepository<FormUser, Long> {
	
	FormUser findByEmail(String email);

}
