package com.hello.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hello.security.model.User;
import com.hello.security.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/v1/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable Integer id) {	
		return new ResponseEntity<User>(service.retrieve(id), HttpStatus.OK);
	}
	
	@PostMapping("/v1/user")
	public ResponseEntity<Integer> saveUser(@Validated @RequestBody User user) {
		return new ResponseEntity<Integer>(service.create(user), HttpStatus.OK);
	}
	
	@PutMapping("/v1/user/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Integer id) {
		return new ResponseEntity<User>(service.update(id, user), HttpStatus.OK);
	}
		
	@DeleteMapping("/v1/user/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/v1/user")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<List<User>>(service.findAll(), HttpStatus.OK);
	}
}