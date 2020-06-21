package com.benjaminhalasz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FormUser {
	@Id
	@GeneratedValue
	private Long id;
	private String email;
	private String name;
//	private List <FormUser> formUser = new ArrayList<FormUser>();
	
	
	
	public FormUser(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}


	public FormUser() {
		
	}
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}


//	public List<FormUser> getFormUser() {
//		return formUser;
//	}
//
//
//	public void setFormUser(List<FormUser> formUser) {
//		this.formUser = formUser;
//	}
//	

}
