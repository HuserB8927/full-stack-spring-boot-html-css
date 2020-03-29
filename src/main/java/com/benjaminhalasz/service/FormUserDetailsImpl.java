package com.benjaminhalasz.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.benjaminhalasz.model.FormUser;

public class FormUserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -5782494056180107353L;
	
	private FormUser formUser;
	
	public FormUserDetailsImpl(FormUser formUser) {
		this.formUser = formUser;
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return formUser.getName();
	}

	
	public String getEmail() {
		return formUser.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
