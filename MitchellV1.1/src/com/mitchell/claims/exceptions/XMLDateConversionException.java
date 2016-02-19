package com.mitchell.claims.exceptions;

@SuppressWarnings("serial")
public class XMLDateConversionException extends Exception {
	private String errorDetails;

	public XMLDateConversionException() {
		super("XML Date Conversion");
		errorDetails = "Error while converting date";
	}

	public String getFaultInfo() {
		return errorDetails;
	}
}
