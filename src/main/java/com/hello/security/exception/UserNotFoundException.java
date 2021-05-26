package com.hello.security.exception;

public class UserNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(Integer id) {
        super("User id not found : " + id);
    }
	
	public UserNotFoundException(String email) {
        super("User id not found : " + email);
    }
}
