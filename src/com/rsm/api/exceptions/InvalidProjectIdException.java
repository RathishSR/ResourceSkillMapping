package com.rsm.api.exceptions;

public class InvalidProjectIdException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidProjectIdException(String exceptionMsg)
	{
		super(exceptionMsg);
	}
}