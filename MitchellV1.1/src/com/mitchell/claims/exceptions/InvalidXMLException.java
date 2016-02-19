package com.mitchell.claims.exceptions;

@SuppressWarnings("serial")
public class InvalidXMLException extends Exception {
	private String errorDetails;
	public InvalidXMLException() {
		super("Invalid XML");
		errorDetails = "Invalid XML, please make sure all required fields are entered";
	}
	
	public String getFaultInfo() {
		return errorDetails;
	}
}
