package com.benjaminhalasz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@EnableGlobalMethodSecurity(securedEnabled=true)
@EnableWebSecurity
@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter {
	
	String[] staticResources  =  {
	        "/css/**",
	        "/layouts/main"
	        
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
	public void configureAuth(AuthenticationManagerBuilder auth) { //doesn't seem to be working, investigate!
		
		try {
			auth
			.inMemoryAuthentication()
				.withUser("user")
				.password("user")
				.roles("USER");
		} catch (Exception e) {
			 System.out.println(" " + e.getMessage());
		}
	}
	
	@Override
    public void configure(WebSecurity web) { //doesn't seem to be working, investigate!
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
				.antMatchers("/main").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/addUser").permitAll()
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/login").permitAll() // for now I just want to expose only my main page without a login page. For the login page I would use: /login
				.and().rememberMe().tokenValiditySeconds(60*60*7).key("message")
				.and()
		.logout()
			.logoutSuccessUrl("/login?logout")
			.permitAll();
		} catch (Exception ex) {
			System.out.println(" " + ex.getMessage());
		}
	}
	@Bean
    public BCryptPasswordEncoder passwordEncoder() { //https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
        auth.authenticationProvider(authenticationProvider());
    }
    
    
    
}