package com.jaya.SpringSecEx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jaya.SpringSecEx.Model.Users;
import com.jaya.SpringSecEx.Service.MyUserregister;

@RestController
public class UserController {
	@Autowired
	private MyUserregister service;
	
	@PostMapping("/register")
	public Users register(@RequestBody Users user) {
		return service.register(user);
	}
	@PostMapping("/jwtlogin")
	public String login(@RequestBody Users user) {
		System.out.println(user);
		
		return service.verify(user);
	}

}
