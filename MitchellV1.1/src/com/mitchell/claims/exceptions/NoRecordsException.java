package com.mitchell.claims.exceptions;

@SuppressWarnings("serial")
public class NoRecordsException extends Exception {
	private String errorDetails;
	public NoRecordsException() {
		super("No Records");
		errorDetails = "No records exist in the database that match the criteria";
	}
	
	public String getFaultInfo() {
		return errorDetails;
	}
}
