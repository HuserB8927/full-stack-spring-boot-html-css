package com.benjaminhalasz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled=true)
@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter {
	
	String[] staticResources  =  {
	        "/css/**",
	        "/images/**"
	        
	    };
	
	@Bean
	public UserDetailsService userDetailsService() {
		return super.userDetailsService();	
	}
	
	
	private UserDetailsService userService;
	
	@Autowired
	public void setUserService(UserDetailsService userService) {
		this.userService = userService;
	}
	@Autowired
	public void configureAuth(AuthenticationManagerBuilder auth) {
		
		try {
			auth
			.inMemoryAuthentication()
				.withUser("user")
				.password("{noop}user") //Add password storage format, for plain text, add {noop} avoiding the error: java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
				.roles("USER")
			.and()
				.withUser("admin")
				.password("{noop}admin")
				.roles("ADMIN");
		} catch (Exception e) {
			 System.out.println(" " + e.getMessage());
		}
	}
	@Override
    public void configure(WebSecurity web) {
        web
            .ignoring()
            .antMatchers(staticResources);
   
	        
	}
	@Override
	public void configure(HttpSecurity httpSec) {
		
	
		try {
			httpSec.authorizeRequests()
				.antMatchers(staticResources).permitAll()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/registration").permitAll()
				.antMatchers("/reg").permitAll()
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
		.logout()
			.logoutSuccessUrl("/login?logout")
			.permitAll();
		} catch (Exception ex) {
			System.out.println(" " + ex.getMessage());
		}
	
	
	}
}