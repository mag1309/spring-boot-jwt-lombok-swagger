package com.hello.security.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hello.security.model.User;
import com.hello.security.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	  @Autowired
	  private UserRepository userRepository;

	  @Override
	  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found: " + email));
	    
	    return org.springframework.security.core.userdetails.User//
	        .withUsername(email)//
	        .password(user.getPassword())//
	        .authorities(Collections.emptyList())//
	        .accountExpired(false)//
	        .accountLocked(false)//
	        .credentialsExpired(false)//
	        .disabled(false)//
	        .build();
	  }
	  
}