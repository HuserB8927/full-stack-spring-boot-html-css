package com.benjaminhalasz.controller;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.benjaminhalasz.model.User;
import com.benjaminhalasz.service.EmailService;
import com.benjaminhalasz.service.UserService;

@Controller
public class ApiController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private EmailService emailService;
	private UserService userService;
	
	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
//	// @PreAuthorize("hasAuthority('USER')") // we dont need to use it, see SecurityConf.java/configure()
//	@RequestMapping("/bloggers")
//	public String bloggers() {
//		return "bloggers";
//	}
//	// @PreAuthorize("hasAuthority('ADMIN')") // we dont need to use it, see SecurityConf.java/configure()
//	@RequestMapping("/stories")
//	public String stories() {
//		return "stories";
//	} 
	
	@RequestMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}
	//@RequestMapping(value="/reg", method=RequestMethod.POST)
	@PostMapping("/reg")
	public String registration(@ModelAttribute User user) {
		System.out.println("NEW USER");
		emailService.sendMessage(user.getEmail(), user.getFullName());
		log.info("New User");
//		log.debug(user.getPassword());
//		log.debug(user.getEmail());
		userService.registerUser(user);
		return "auth/login";
	}
	
	@RequestMapping(path = "/activation/{code}", method = RequestMethod.GET)
	public String activation(@PathVariable("code") String code, HttpServletResponse response) {
		String result = userService.userActivation(code);
		return "auth/login?activationsuccess";
	}
}


	

