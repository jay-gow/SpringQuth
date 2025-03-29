package com.jaya.SpringSecEx.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaya.SpringSecEx.Model.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer>{

	public abstract Users findByUsername(String username);



}
//Encryption
//plain->cipher text 
//instead of this
//hash ->hashing bsna md5
//plain to hash easily but not hash to plain 
//we can acheive by using sha 265 //run one time
//bcrypt libary is there inside spring security