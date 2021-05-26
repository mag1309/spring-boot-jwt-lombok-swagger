package com.hello.security.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hello.security.exception.UserNotFoundException;
import com.hello.security.model.User;
import com.hello.security.repository.UserRepository;

@Repository
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public Integer create(User user)
	{
		repository.save(user);
		return user.getId();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public User retrieve(Integer id)
	{
		return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
	}
	
	/**
	 * 
	 * @param id
	 * @param user
	 * @return
	 */
	public User update(Integer id, User user)
	{
		User userIn = retrieve(id);
		
		if(userIn != null)
		{
			userIn.setFirstName(user.getFirstName());
			userIn.setLastName(user.getLastName());
			userIn.setPhone(user.getPhone());
			repository.save(userIn);
		}
		else
		{
			user.setId(id);
            repository.save(user);
        }
		return user;
	}
		
	/**
	 * 
	 * @param id
	 */
	public void delete(Integer id)
	{
		User user = repository.findById(id)
        	.orElseThrow(() -> new UserNotFoundException(id));
		if(user != null)
			repository.deleteById(id);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<User> findAll()
	{
		return repository.findAll();
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public User loadUserByEmail(String email) {
		return repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
	}
}