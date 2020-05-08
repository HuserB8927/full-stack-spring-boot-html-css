package com.benjaminhalasz.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.benjaminhalasz.model.FormUser;
import com.benjaminhalasz.model.User;
import com.benjaminhalasz.service.EmailService;
import com.benjaminhalasz.service.FormUserService;
import com.benjaminhalasz.service.UserService;



@Controller
public class ApiController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private EmailService emailService;
	private UserService userService;
	private FormUserService formUserService;
	
	@Autowired
	public void setFormUserService(FormUserService formUserService) {
		this.formUserService = formUserService;
	}

	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/")
	public String home(Model  model) {
		
		model.addAttribute("serverTime", new Date());
		model.addAttribute("developer", "Developer skills");
		model.addAttribute("itskills", "IT skills");
		model.addAttribute("references", "References");
		model.addAttribute("qualifications", "Qualifications");
		model.addAttribute("myproject", "My Project");
		model.addAttribute("it", "IT");
		model.addAttribute("vaadin", "VAADIN");
		model.addAttribute("java", "JAVA");
		model.addAttribute("resume", "RESUME");
		model.addAttribute("exam", "EXAM 🇭🇺");
		model.addAttribute("yourip", userService.userIpAddress());
		
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("serverTime", new Date());
		model.addAttribute("yourip", userService.userIpAddress());
		return "auth/login";
	}
	
	@RequestMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("serverTime", new Date());
		model.addAttribute("yourip", userService.userIpAddress());
		return "registration";
	}
	//@RequestMapping(value="/reg", method=RequestMethod.POST)
	@PostMapping("/reg")
	public String registration(@ModelAttribute User user) {
		System.out.println("NEW USER");
		emailService.sendMessage(user.getEmail(), user.getFullName());
		log.info("New User");
		userService.registerUser(user);
		return "auth/login";
	}
	
	@RequestMapping(path = "/activation/{code}", method = RequestMethod.GET)
	public String activation(@PathVariable("code") String code, HttpServletResponse response) {
		String result = userService.userActivation(code);
		return "auth/login?activationsuccess";
	}
	
	
	@RequestMapping("/index")
	public String addUser(Model model) {
		model.addAttribute("formuser", new FormUser());
		return "index";
	}
	
	@PostMapping("/addUser")
    public String addUser(@ModelAttribute FormUser newUser) {
		
		formUserService.registerUser(newUser);
		
        return "index";
    }
	@ModelAttribute(value = "formuser")
	public FormUser getFormUser()
	{
	    return new FormUser();
	
	}
	@RequestMapping(value = "/welcome") 
	public String wellcome() { 
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
	    System.out.println("username: " + auth.getName()); 
	    return "index"; 
	}
	
	}

	



	

