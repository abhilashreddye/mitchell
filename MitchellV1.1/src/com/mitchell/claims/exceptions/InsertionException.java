package com.mitchell.claims.exceptions;

@SuppressWarnings("serial")
public class InsertionException extends Exception {
	private String errorDetails;

	public InsertionException() {
		super("Insertion");
		errorDetails = "Error while inserting claim into database.";
	}

	public String getFaultInfo() {
		return errorDetails;
	}
}
