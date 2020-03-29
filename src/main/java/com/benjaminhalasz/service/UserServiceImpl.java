package com.benjaminhalasz.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.benjaminhalasz.model.FormUser;
import com.benjaminhalasz.model.Role;
import com.benjaminhalasz.model.User;
import com.benjaminhalasz.repository.FormUserRepository;
import com.benjaminhalasz.repository.RoleRepository;
import com.benjaminhalasz.repository.UserRepository;
@Primary
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	private final String USER_ROLE = "USER";
	
	
//	@Autowired
//	public void setFormUserRepository(FormUserRepository formUserRepository) {
//		this.formUserRepository = formUserRepository;
//	}
	
	@Autowired
	public void UserRepository(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		
	}
	
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserDetailsImpl(user);
	}
	@Override
	public String registerUser(User userToRegister) {
		User userCheck = userRepository.findByEmail(userToRegister.getEmail());
		if (userCheck != null) {
			return "alreadyExist";
		}
		
		Role userRole = roleRepository.findByRole(USER_ROLE);
		if (userRole != null) {
			userToRegister.getRoles().add(userRole);
		} else {
			userToRegister.addRoles(USER_ROLE);
		}
		userToRegister.setActivation(generateKey());
		userToRegister.setEnabled(true);
		userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));
		userRepository.save(userToRegister);
		
		return "ok";
	}
	
	public String generateKey() {
		String key = "";
		Random random = new Random();
		char[] word = new char[16];
		for(int i = 0; i < word.length; i++) {
			word[i] = (char) ('a' + random.nextInt(26));
		}
		String toReturn = new String(word);
		
		return new String(word);
	}
	@Override
	public String userActivation(String code) {
		
		User user = userRepository.findByActivation(code);
		if (user == null) {
			return "no result";
		}
		
		user.setEnabled(true);
		user.setActivation("");
		userRepository.save(user);
		
		return "ok";
		
	}

}
