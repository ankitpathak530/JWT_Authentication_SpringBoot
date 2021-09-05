package com.jwtauthentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jwtauthentication.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class MySecurity  extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserdetailsService;
	
	@Autowired
    private JwtAuthenticationFilter jwtFilter;	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http 
		     .csrf()
		     .disable()
		     .cors()
		     .disable()
		     .authorizeRequests()
		     .antMatchers("/token")
		     .permitAll()
		     .anyRequest().authenticated()
		     .and()
		     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// corerect error in below line
		http.addFilterBefore(jwtFilter	, UsernamePasswordAuthenticatinFilter.class);
		
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(this.customUserdetailsService);
	} 
	
	
	@Bean
	public AuthenticationManager authenticationManagerBean() {
		try {
			return super.authenticationManager();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
       
	
}
