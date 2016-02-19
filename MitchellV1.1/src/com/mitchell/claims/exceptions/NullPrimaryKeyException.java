package com.mitchell.claims.exceptions;

@SuppressWarnings("serial")
public class NullPrimaryKeyException extends Exception {
	private String errorDetails;
	public NullPrimaryKeyException() {
		super("Insertion");
		errorDetails = "Primary key cannot be null";
	}
	
	public String getFaultInfo() {
		return errorDetails;
	}
}
