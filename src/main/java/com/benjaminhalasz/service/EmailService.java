package com.benjaminhalasz.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private final Log log = LogFactory.getLog(this.getClass());
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String MESSAGE_FROM;
	private String MESSAGE_TO = "h.benya@gmail.com";
	
	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	public void sendMessage(String email, String fullName) {
		SimpleMailMessage message = null;
		
		try {
			message = new SimpleMailMessage();
			message.setFrom(MESSAGE_FROM);
			message.setTo(MESSAGE_TO, email); //originally the argument is `email`
			message.setSubject("Registration successful");
			message.setText("Dear " + fullName + ", thanks for register on my website.");
			javaMailSender.send(message);
		} catch (Exception ex) {
			log.error("Email was not send for: " + email + " " + ex.getMessage());
			
		}
		
	}
}
