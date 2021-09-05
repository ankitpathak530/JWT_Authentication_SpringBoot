package com.jwtauthentication.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home implements ErrorController{

	
	  @RequestMapping("/welcome")
	  public String welcome() {
		  String text = "this is private page! not allowed for unauthenticate user";
		  return text;  
	  }
	  
	  @RequestMapping("/error")
	    public String error() {
	        return "I Handle Error !";
	    }

	  
}
