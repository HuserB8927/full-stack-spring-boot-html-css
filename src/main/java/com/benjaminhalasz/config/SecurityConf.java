package com.benjaminhalasz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
				.roles("USER");
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
				.antMatchers("/log").permitAll()
				.antMatchers("/addUser").permitAll()
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