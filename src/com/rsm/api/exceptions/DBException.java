package com.rsm.api.exceptions;

public class DBException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DBException(String exceptionMsg)
	{
		super(exceptionMsg);
	}
}
