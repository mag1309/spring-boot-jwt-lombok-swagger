package com.hello.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hello.security.jwt.JwtTokenUtil;
import com.hello.security.model.JwtRequest;
import com.hello.security.model.JwtResponse;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping(path = "/v1/authenticate" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
	
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenUtil.generateJwtToken(authentication);
		
		final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return new ResponseEntity<JwtResponse>(new JwtResponse(token,userDetails.getUsername()), HttpStatus.OK);
		
	}

}