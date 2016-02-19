package com.mitchell.claims.business;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import com.mitchell.claims.entity.MitchellClaimType;
import com.mitchell.claims.entity.MitchellClaimsListType;
import com.mitchell.claims.entity.VehicleInfoType;
import com.mitchell.claims.exceptions.InsertionException;
import com.mitchell.claims.exceptions.InvalidXMLException;
import com.mitchell.claims.exceptions.NoRecordsException;
import com.mitchell.claims.exceptions.NullPrimaryKeyException;
import com.mitchell.claims.exceptions.XMLDateConversionException;
import com.mitchell.claims.utilities.SQLResult;
import com.mitchell.claims.utilities.Utility;

public class MitchellClaimPersistence {
	private static final String PERSISTENCE_UNIT_NAME = "MitchellV1.1";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);;

	/**
	 * createClaim converts the xml instance to database ready instance and persists into database
	 * @param claimType
	 * @return SUCCESS
	 * @throws InvalidXMLException
	 * @throws NullPrimaryKeyException
	 * @throws InsertionException
	 */
	public SQLResult createClaim(MitchellClaimType claimType)
			throws InvalidXMLException, NullPrimaryKeyException, InsertionException {
		if (claimType != null) {
			claimType = Utility.toDatabaseInstance(claimType);

			EntityManager manager = factory.createEntityManager();

			try {
				if (claimType.getClaimNumber() == null || claimType.getClaimNumber().equals("")) {
					throw new NullPrimaryKeyException();
				} else {
					manager.getTransaction().begin();
					manager.persist(claimType);
					manager.getTransaction().commit();
				}
			} catch (Exception exception) {
				if (exception.getMessage().contains("Error Code: 1062")) {
					throw new InsertionException();
				} else {
					throw exception;
				}
			}

			manager.close();
			return SQLResult.SUCCESS;
		} else {
			throw new InvalidXMLException();
		}

	}

	/**
	 * retrieveClaim uses the claimNumber to retrieve a claim, converts the database object to xml ready instance
	 * @param claimNumber
	 * @return MitchellClaimType
	 * @throws XMLDateConversionException
	 * @throws DatatypeConfigurationException
	 * @throws NoRecordException
	 */
	public MitchellClaimType retrieveClaim(String claimNumber) throws NoRecordsException, XMLDateConversionException {

		EntityManager manager = factory.createEntityManager();

		manager.getTransaction().begin();
		MitchellClaimType claimType = manager.find(MitchellClaimType.class, claimNumber);
		manager.getTransaction().commit();

		if (claimType != null) {
			try {
				claimType = Utility.toXMLInstance(claimType);
				return claimType;
			} catch (DatatypeConfigurationException e) {
				throw new XMLDateConversionException();
			}

		} else {
			throw new NoRecordsException();
		}

	}

	/**
	 * deleteClaim finds a claim using claimNumber, deletes it from database
	 * @param claimNumber
	 * @return SUCCESS
	 * @throws NoRecordsException
	 */
	public SQLResult deleteClaim(String claimNumber) throws NoRecordsException {

		EntityManager manager = factory.createEntityManager();

		MitchellClaimType claimType = manager.find(MitchellClaimType.class, claimNumber);

		if (claimType != null) {
			manager.getTransaction().begin();
			manager.remove(claimType);
			manager.getTransaction().commit();
			return SQLResult.SUCCESS;
		} else {
			throw new NoRecordsException();
		}
	}

	/**
	 * claimsBetweenDates finds claims between two dates, returns them in MitchellClaimsType object
	 * @param fromDate
	 * @param toDate
	 * @return List of MitchellClaimType
	 * @throws DatatypeConfigurationException
	 * @throws NoRecordsException
	 * @throws XMLDateConversionException
	 */
	@SuppressWarnings("unchecked")
	public MitchellClaimsListType claimsBetweenDates(XMLGregorianCalendar fromDate, XMLGregorianCalendar toDate)
			throws NoRecordsException, XMLDateConversionException {

		Date fromDateDate = Utility.toDate(fromDate);
		Date toDateDate = Utility.toDate(toDate);

		EntityManager manager = factory.createEntityManager();

		String queryString = "SELECT m from MitchellClaimType m WHERE m.lossDateDate BETWEEN :fromDate AND :toDate";
		Query query = manager.createQuery(queryString);

		query.setParameter("fromDate", fromDateDate, TemporalType.TIMESTAMP);
		query.setParameter("toDate", toDateDate, TemporalType.TIMESTAMP);

		ArrayList<MitchellClaimType> list = new ArrayList<MitchellClaimType>();

		list.addAll(query.getResultList());
		if (list.size() > 0) {

			for (MitchellClaimType mitchellClaimType : list) {
				try {
					mitchellClaimType = Utility.toXMLInstance(mitchellClaimType);
				} catch (DatatypeConfigurationException e) {
					throw new XMLDateConversionException();
				}
			}

			MitchellClaimsListType listType = new MitchellClaimsListType();
			listType.setClaimDetails(list);
			return listType;
		} else {
			throw new NoRecordsException();
		}
	}

	/**
	 * retrieveVehicle finds a vehicle with claimNumber, Vin, converts the database object to xml ready instance and returns
	 * @param claimNumber
	 * @param vin
	 * @return VehicleInfoType
	 * @throws DatatypeConfigurationException
	 * @throws NoRecordsException
	 * @throws XMLDateConversionException
	 */
	public VehicleInfoType retrieveVehicle(String claimNumber, String vin)
			throws NoRecordsException, XMLDateConversionException {
		EntityManager manager = factory.createEntityManager();
		String queryString = "SELECT v from VehicleInfoType v WHERE v.key.claimNumber=:claimNumber AND v.key.vin=:vin";

		Query query = manager.createQuery(queryString);

		query.setParameter("claimNumber", claimNumber);
		query.setParameter("vin", vin);

		try {
			VehicleInfoType vehicleInfoType = (VehicleInfoType) query.getSingleResult();
			vehicleInfoType
					.setLicPlateExpDate(Utility.toXMLGregorianCalendar(vehicleInfoType.getLicPlateExpDateDate()));
			vehicleInfoType.setVin(vehicleInfoType.getKey().getVin());
			return vehicleInfoType;
		} catch (NoResultException e) {
			throw new NoRecordsException();
		} catch (DatatypeConfigurationException e) {
			throw new XMLDateConversionException();
		}

	}

	/**
	 * 
	 * @param claimType
	 * @return
	 */
	/*
	 * public SQLResult updateClaim(MitchellClaimType claimType) { EntityManager
	 * manager = factory.createEntityManager();
	 * 
	 * MitchellClaimType foundClaimType = manager.find(MitchellClaimType.class,
	 * claimType.getClaimNumber());
	 * 
	 * manager.getTransaction().begin();
	 * 
	 * if (claimType.getClaimantFirstName() != null) {
	 * foundClaimType.setClaimantFirstName(claimType.getClaimantFirstName()); }
	 * if (claimType.getClaimantLastName() != null) {
	 * foundClaimType.setClaimantFirstName(claimType.getClaimantLastName()); }
	 * if (claimType.getStatus() != null) {
	 * foundClaimType.setStatus(claimType.getStatus()); } if
	 * (claimType.getLossDate() != null) {
	 * foundClaimType.setLossDateDate(Utility.toDate(claimType.getLossDate()));
	 * } if (claimType.getLossInfo() != null) { if
	 * (claimType.getLossInfo().getCauseOfLoss() != null) {
	 * foundClaimType.getLossInfo().setCauseOfLoss(claimType.getLossInfo().
	 * getCauseOfLoss()); } if(claimType.getLossInfo().getLossDescription() !=
	 * null) {
	 * foundClaimType.getLossInfo().setLossDescription(claimType.getLossInfo().
	 * getLossDescription()); } if(claimType.getLossInfo().getReportedDate() !=
	 * null) {
	 * foundClaimType.getLossInfo().setReportedDateDate(Utility.toDate(claimType
	 * .getLossInfo().getReportedDate())); } } if
	 * (claimType.getAssignedAdjusterID() != null) {
	 * foundClaimType.setAssignedAdjusterID(claimType.getAssignedAdjusterID());
	 * } if (claimType.getVehicles() != null) { List<VehicleInfoType>
	 * vehicleInfoTypes = claimType.getVehicles().getVehicleDetails();
	 * 
	 * for (VehicleInfoType vehicleInfoType : vehicleInfoTypes) {
	 * 
	 * } } manager.getTransaction().commit();
	 * 
	 * return SQLResult.SUCCESS; }
	 */

	/*
	 * public int updateClaim(MitchellClaimType claimType) { EntityManager
	 * manager = factory.createEntityManager();
	 * 
	 * MitchellClaimType foundClaimType = manager.find(MitchellClaimType.class,
	 * claimType.getClaimNumber()); if (foundClaimType != null) {
	 * manager.getTransaction().begin(); manager.merge(claimType);
	 * manager.getTransaction().commit(); } return 0; }
	 */

}
