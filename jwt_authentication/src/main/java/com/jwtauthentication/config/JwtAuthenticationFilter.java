package com.jwtauthentication.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwtauthentication.helper.JwtUtilHelper;
import com.jwtauthentication.service.CustomUserDetailsService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	
	@Autowired
	private JwtUtilHelper jwtUtilHelper;
	
	@Autowired
	private CustomUserDetailsService customeUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 
		// get jwt
		// Bearer
		// validate
		String requestTokenHeader = request.getHeader("Authorization");
		
		String username = null;
		String jwtToken = null;
		
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			  
			 jwtToken = requestTokenHeader.substring(7);
			 
			 try {
                   this.jwtUtilHelper.extractUsername(jwtToken);
                   
                   
			 }catch(Exception e) {
				 e.printStackTrace();
			 }
			 
			 // this line will execute if 
			 
			 UserDetails userdetails = this.customeUserDetailsService.loadUserByUsername(username);
			
			 if(username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
				  
				 
				    //Error here please correct it
				    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
				 
				    
				    UsernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
				   
				    SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken);
			 }
			 
			 else {
				 System.out.println("Token is not validated");
			 }
			 
			 
			 
			 filterChain.doFilter(request, response);
			 
			 
			 
		}
		
		
	}
    
	  
}
