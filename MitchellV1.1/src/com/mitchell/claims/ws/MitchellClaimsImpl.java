package com.mitchell.claims.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.datatype.XMLGregorianCalendar;

import com.mitchell.claims.business.MitchellClaimPersistence;
import com.mitchell.claims.entity.MitchellClaimType;
import com.mitchell.claims.entity.MitchellClaimsListType;
import com.mitchell.claims.entity.VehicleInfoType;
import com.mitchell.claims.exceptions.InsertionException;
import com.mitchell.claims.exceptions.InvalidXMLException;
import com.mitchell.claims.exceptions.NoRecordsException;
import com.mitchell.claims.exceptions.NullPrimaryKeyException;
import com.mitchell.claims.exceptions.XMLDateConversionException;
import com.mitchell.claims.utilities.SQLResult;

@WebService
@SOAPBinding(style = Style.RPC)
public class MitchellClaimsImpl {

	MitchellClaimPersistence mcp = new MitchellClaimPersistence();
	
	@WebMethod(operationName = "CreateClaim")
	@WebResult(name="Result")
	public SQLResult createClaim(@WebParam(name = "MitchellClaim")MitchellClaimType claimType) throws InvalidXMLException, NullPrimaryKeyException, InsertionException {
			try {
				return mcp.createClaim(claimType);
			} catch (InvalidXMLException | NullPrimaryKeyException | InsertionException e) {
				throw e;
			} 
	}
	
	@WebMethod(operationName = "RetrieveClaim")
	@WebResult(name="MitchellClaim")
	public MitchellClaimType retrieveClaim(@WebParam(name = "ClaimNumber")String claimNumber) throws NoRecordsException, XMLDateConversionException {
		try {
			return mcp.retrieveClaim(claimNumber);
		} catch (NoRecordsException | XMLDateConversionException e) {
			throw e;
		} 
	}
	
	@WebMethod(operationName = "DeleteClaim")
	@WebResult(name="Result")
	public SQLResult deleteClaim(@WebParam(name = "ClaimNumber")String claimNumber) throws NoRecordsException {
		try {
			return mcp.deleteClaim(claimNumber);
		} catch (NoRecordsException e) {
			throw e;
		}
	}
	
	@WebMethod(operationName = "ClaimsBetweenDates")
	@WebResult(name="MitchellClaims")
	public MitchellClaimsListType claimsBetweenDates(@WebParam(name = "FromDate")XMLGregorianCalendar fromDate, @WebParam(name = "ToDate")XMLGregorianCalendar toDate) throws  NoRecordsException, XMLDateConversionException {
		try {
			return mcp.claimsBetweenDates(fromDate, toDate);
		} catch (NoRecordsException | XMLDateConversionException e) {
			throw e;
		}
	}
	
	@WebMethod(operationName = "RetrieveVehicle")
	@WebResult(name = "VehicleInfo")
	public VehicleInfoType retrieveVehicle(@WebParam(name = "ClaimNumber")String claimNumber, @WebParam(name = "Vin")String vin) throws NoRecordsException, XMLDateConversionException {
		try {
			return mcp.retrieveVehicle(claimNumber, vin);
		} catch (NoRecordsException | XMLDateConversionException e) {
			throw e;
		}
	}
	
	/*@WebMethod(operationName = "UpdateClaim")
	@WebResult(name="Result")
	public SQLResult updateClaim(@WebParam(name = "MitchellClaim")MitchellClaimType claimType) {
		return mcp.updateClaim(claimType);
	}*/
}
