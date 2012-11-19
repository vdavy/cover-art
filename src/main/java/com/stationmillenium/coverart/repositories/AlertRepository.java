/**
 * 
 */
package com.stationmillenium.coverart.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.stationmillenium.coverart.domain.alert.AlertActivation;
import com.stationmillenium.coverart.domain.alert.AlertEmail;
import com.stationmillenium.coverart.domain.alert.AlertType;

/**
 * Repository for alerts
 * @author vincent
 *
 */
@Repository
public class AlertRepository {
	
	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(AlertRepository.class);
	
	//entity manager to access db
	@PersistenceContext
	private EntityManager entityManager;
		
	/**
	 * Get email form alert type
	 * @param alertType the alert type 
	 * @return the list of email as string
	 */
	public List<String> getEmailFromAlertType(AlertType alertType) {
		//process query
		Query query = entityManager.createNamedQuery("getEmailFromAlertType", AlertEmail.class);
		query.setParameter("alert", alertType);
		List<AlertEmail> emailList = query.getResultList();
		
		//manage result
		List<String> returnList = new ArrayList<>();
		for (AlertEmail email : emailList) {
			returnList.add(email.getEmail());
		}
		return returnList;
	}
	
	/**
	 * Get the alert type for an email
	 * @param email the email
	 * @return the list of {@link AlertType} or empty list if not found
	 */
	public List<AlertType> getAlertTypeForEmail(String email) {
		//process query
		Query query = entityManager.createNamedQuery("getAlertTypeFromEmail", AlertType.class);
		query.setParameter("email", email);
		try {
			List<AlertType> alertTypeList = query.getResultList();			
			return alertTypeList;				
			
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("No entity found", e);
			return new ArrayList<>();
		}		
	}
	
	/**
	 * Get if an alert is enabled or not 
	 * @param alertType the alert type
	 * @return true if alert enbaled, false if not
	 */
	public boolean getAlertEnabledForType(AlertType alertType) {
		//process query
		Query query = entityManager.createNamedQuery("getAlertEnabledForAlertType", AlertActivation.class);
		query.setParameter("type", alertType);
		try {
			AlertActivation alertActivation = (AlertActivation) query.getSingleResult();
			//manage result
			return alertActivation.isEnableAlert();	
			
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("No entity found", e);
			return false;
		}		
	}
	
}
