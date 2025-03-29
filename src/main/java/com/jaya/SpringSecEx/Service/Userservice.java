package com.jaya.SpringSecEx.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jaya.SpringSecEx.Model.UserPrinciple;
import com.jaya.SpringSecEx.Model.Users;
import com.jaya.SpringSecEx.Repo.UserRepo;
@Service
public class Userservice implements UserDetailsService
{//myuserdetailservice
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user=repo.findByUsername(username);
		if(user==null) {
			System.out.println("user not found");
			throw new UsernameNotFoundException("not found kelambu");
		}
		
		return new UserPrinciple(user);
	}

}
