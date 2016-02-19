package com.mitchell.claims.utilities;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.mitchell.claims.entity.MitchellClaimType;
import com.mitchell.claims.entity.VehicleInfoType;
import com.mitchell.claims.entity.VehicleKey;
import com.mitchell.claims.entity.VehicleListType;

public class Utility {
	
	/**
	 * Converts XMLGregorianCalendar type to Date
	 * @param calendar
	 * @return Date
	 */
	public static Date toDate(XMLGregorianCalendar calendar) {
		return calendar.toGregorianCalendar().getTime();
	}
	
	/**
	 * Converts Date type to XMLGregorianCalendar
	 * @param date
	 * @return
	 * @throws DatatypeConfigurationException 
	 */
	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		
		XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		
		return calendar;
	}
	
	public static  VehicleListType toVehicleList(List<VehicleInfoType> list) {
		VehicleListType listType = new VehicleListType();
		listType.setVehicleDetails(list);
		return listType;
	}
	
	public static MitchellClaimType toDatabaseInstance(MitchellClaimType claimType) {
		//Converting lossDate of XMLGregorianCalendar type to java.util.Date 
		if(claimType.getLossDate()!=null) {
			claimType.setLossDateDate(Utility.toDate(claimType.getLossDate()));
		}
		
		//Converting reportedDate of XMLGregorianCalendar type to java.util.Date
		if(claimType.getLossInfo()!=null) {
			if(claimType.getLossInfo().getReportedDate()!=null) {
				claimType.getLossInfo().setReportedDateDate(Utility.toDate(claimType.getLossInfo().getReportedDate()));
			}
		}
		
		//Converting VehicleListType to list of VehicleType to store in database
		if(claimType.getVehicles()!=null) {
			List<VehicleInfoType> list = claimType.getVehicles().getVehicleDetails();
			
			//For each VehicleInfoType
			//Setting claimNumber to store as foreign key
			//Converting licPlateExpDate of XMLGregorianCalendar type to java.util.Date
			
			for (VehicleInfoType vehicleInfoType : list) {
				VehicleKey key = new VehicleKey();
				key.setClaimNumber(claimType.getClaimNumber());
				key.setVin(vehicleInfoType.getVin());
				vehicleInfoType.setKey(key);
				vehicleInfoType.setLicPlateExpDateDate(Utility.toDate(vehicleInfoType.getLicPlateExpDate()));
			}
			claimType.setVehicleList(list);
		}
		
		return claimType;
	}
	
	public static MitchellClaimType toXMLInstance(MitchellClaimType claimType) throws DatatypeConfigurationException {
		
		//Converting lossDate of java.util.Date type to XMLGregorianCalendar
		if(claimType.getLossDateDate()!=null) {
			claimType.setLossDate(Utility.toXMLGregorianCalendar(claimType.getLossDateDate()));
		}
		
		//Converting reportedDate of java.util.Date type to XMLGregorianCalendar
		if(claimType.getLossInfo()!=null) {
			if(claimType.getLossInfo().getReportedDateDate()!=null) {
				claimType.getLossInfo().setReportedDate(Utility.toXMLGregorianCalendar(claimType.getLossInfo().getReportedDateDate()));
			}
		}
		
		//Converting list of VehicleType to VehicleListType
		if(claimType.getVehicleList()!=null) {
			
			//For each VehicleInfoType
			//Converting licPlateExpDate of java.util.Date type to XMLGregorianCalendar
			
			for (VehicleInfoType vehicleInfoType : claimType.getVehicleList()) {
				vehicleInfoType.setVin(vehicleInfoType.getKey().getVin());
				vehicleInfoType.setLicPlateExpDate(Utility.toXMLGregorianCalendar(vehicleInfoType.getLicPlateExpDateDate()));
			}
			//Converting list of VehicleType to VehicleListType
			claimType.setVehicles(new VehicleListType());
			claimType.getVehicles().setVehicleDetails(claimType.getVehicleList());;
		}
		return claimType;
	}
}
