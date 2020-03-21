package com.benjaminhalasz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled=true)
@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter {
	
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
	protected void configure(HttpSecurity httpSec) {
	
		try {
			httpSec.authorizeRequests()
			//	.antMatchers(HttpMethod.GET, "/").permitAll()
			//	.antMatchers("/delete").hasRole("ADMIN)")
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

/*
 * 
 *@Override
	protected void configure(HttpSecurity http) {
		
		try {
			http
			.authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/registration").permitAll()
			.antMatchers("/reg").permitAll()
			.antMatchers("/activation/**").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.usernameParameter("admin")
			.passwordParameter("admin")
			.permitAll()
			.and()
		.logout()
			.logoutSuccessUrl("/login?logout")
			.permitAll();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
}*/
