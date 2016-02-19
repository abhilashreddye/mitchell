package com.mitchell.claims.exceptions;

@SuppressWarnings("serial")
public class UpdationException extends Exception {
	private String errorDetails;

	public UpdationException() {
		super("Updation");
		errorDetails = "Error while updating claim in database";
	}

	public String getFaultInfo() {
		return errorDetails;
	}
}
