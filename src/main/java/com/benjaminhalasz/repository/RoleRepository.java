package com.benjaminhalasz.repository;

import org.springframework.data.repository.CrudRepository;

import com.benjaminhalasz.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRole(String role);
	
	

}
