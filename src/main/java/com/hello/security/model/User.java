package com.hello.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "USERS")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="first_name")
	@NotBlank(message = "Please provide First Name")
	private String firstName;
	
	@Column(name="last_name")
	@NotBlank(message = "Please provide Last Name")
	private String lastName;
	
	@Column(name="email")
	@NotBlank(message = "Please provide Email")
	@Email(message = "Provide valid email address")
	private String email;
	
	@Column(name="phone")
	@NotBlank(message = "Please provide Phone")
	@Size(min=10,max=10,message = "Phone number should not 10 digits")
	@Pattern(regexp = "^[0-9]+$",message = "Phone number is not valid")
	private String phone;
	
	@Column(name="password")
	@NotBlank(message = "Please provide Password")
	private String password;
	
}