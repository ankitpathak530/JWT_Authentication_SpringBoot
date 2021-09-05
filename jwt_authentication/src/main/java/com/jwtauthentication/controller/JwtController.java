package com.jwtauthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwtauthentication.helper.JwtUtilHelper;
import com.jwtauthentication.model.JwtRequest;
import com.jwtauthentication.model.JwtResponse;
import com.jwtauthentication.service.CustomUserDetailsService;

@RestController
public class JwtController {
  
	@Autowired
	private CustomUserDetailsService customUserDetailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtilHelper jwtUtilHelper;
	
	
	
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest)throws Exception {
		
		System.out.println("This is jwtRequest----->"+jwtRequest);
		
		try {
			
	 	 	this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
			
		
			
		}catch(UsernameNotFoundException e)
		{
			e.printStackTrace();
			throw new Exception("Bad credential");
		}
		catch(BadCredentialsException e) {
			e.printStackTrace();
		}
		
		//Authenticated user 
		UserDetails userDetail = this.customUserDetailService.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtUtilHelper.generateToken(userDetail);
		
		System.out.println("JWT "+token);
		
		//{"token":"value"}
		
		return ResponseEntity.ok(new JwtResponse(token));
		
	}
}
