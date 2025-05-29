package com.proy.VIBA.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {

	/**
	 * MANEJO DE ERROR PARA EL LOGIN 
	 */

	public UserNotFoundException(String message) {
        super(message);
    }
}
