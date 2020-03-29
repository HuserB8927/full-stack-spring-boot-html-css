package com.benjaminhalasz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.benjaminhalasz.model.FormUser;
import com.benjaminhalasz.model.User;
import com.benjaminhalasz.repository.FormUserRepository;
import com.benjaminhalasz.repository.RoleRepository;
import com.benjaminhalasz.repository.UserRepository;

@Service
public class FormUserServiceImpl implements UserDetailsService, FormUserService{
	
	private FormUserRepository formUserRepository;
	
	@Autowired
	public void FormUserRepository(FormUserRepository formUserRepository) {
		
		this.formUserRepository = formUserRepository;
	}

	@Override
	public String registerUser(FormUser newUser) {
		// TODO Auto-generated method stub
		formUserRepository.save(newUser);
		
		return "ok";
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		FormUser user = findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new FormUserDetailsImpl(user);
	
	}

	@Override
	public FormUser findByEmail(String email) {
		return formUserRepository.findByEmail(email);
	}

}
