package com.jaya.SpringSecEx.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jaya.SpringSecEx.Model.Users;
import com.jaya.SpringSecEx.Repo.UserRepo;

@Service
public class MyUserregister {
	@Autowired
	UserRepo repo;
	@Autowired
	AuthenticationManager manager;
	
	@Autowired
	private JWTservice jwtservice;
	//rounds mention inside constructor
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

	// save encodedPassword to the database
	public Users register(Users user) {
        // Encode the plain text password before saving the user
      user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
		
	}

	public String verify(Users user) {
	Authentication auth=	
			manager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
	if(auth.isAuthenticated())return jwtservice.generateToken(user.getUsername());
		return "fail" ;
	}

}
