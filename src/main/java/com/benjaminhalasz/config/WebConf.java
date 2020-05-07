package com.benjaminhalasz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConf implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	//	registry.addViewController("/").setViewName("auth/login");
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
	}
	
}
