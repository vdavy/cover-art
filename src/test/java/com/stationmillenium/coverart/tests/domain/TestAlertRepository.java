package com.stationmillenium.coverart.tests.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.domain.alert.AlertActivation;
import com.stationmillenium.coverart.domain.alert.AlertEmail;
import com.stationmillenium.coverart.domain.alert.AlertType;
import com.stationmillenium.coverart.repositories.AlertRepository;

/**
 * Tests of the alert repository
 * @author vincent
 *
 */
public class TestAlertRepository {
	
	//the repository
	@Autowired
	private AlertRepository alertRepository;

	/**
	 * Init database for tests
	 */
	@Before
	public void initTest() {
		//alert init for fm
		AlertActivation alertActivation = new AlertActivation();
		alertActivation.setAlertType(AlertType.FM);
		alertActivation.setEnableAlert(true);
		alertActivation.persist();
		
		//alert init for playlist
		alertActivation = new AlertActivation();
		alertActivation.setAlertType(AlertType.PLAYLIST);
		alertActivation.setEnableAlert(false);
		alertActivation.persist();
		
		//alert init for shoutcast
		alertActivation = new AlertActivation();
		alertActivation.setAlertType(AlertType.SHOUTCAST);
		alertActivation.setEnableAlert(true);
		alertActivation.persist();
		
		//email insertion
		AlertEmail email = new AlertEmail();
		email.setEmail("1@toto.com");
		email.setAlertType(new HashSet<AlertType>());
		email.getAlertType().add(AlertType.FM);
		email.getAlertType().add(AlertType.SHOUTCAST);
		email.persist();
		
		email = new AlertEmail();
		email.setEmail("2@toto.com");
		email.setAlertType(new HashSet<AlertType>());
		email.getAlertType().add(AlertType.PLAYLIST);
		email.persist();
	}
	
	/**
	 * Test method {@link AlertRepository#getEmailFromAlertType(AlertType)}
	 */
	@Test
	public void testGetEmailFromAlertType() {
		//test fm alert type
		List<String>  emailList = alertRepository.getEmailFromAlertType(AlertType.FM);
		assertEquals(emailList.size(), 1);
		assertEquals(emailList.get(0), "1@toto.com");
		
		//test fm playlist type
		emailList = alertRepository.getEmailFromAlertType(AlertType.PLAYLIST);
		assertEquals(emailList.size(), 1);
		assertEquals(emailList.get(0), "2@toto.com");
		
		//test fm shoutcast type
		emailList = alertRepository.getEmailFromAlertType(AlertType.SHOUTCAST);
		assertEquals(emailList.size(), 1);
		assertEquals(emailList.get(0), "1@toto.com");
	}
	
	/**
	 * Test method {@link AlertRepository#getAlertTypeForEmail(String)}
	 */
	@Test
	public void testGetAlertTypeForEmail() {
		//test no alert
		List<AlertType> alertTypeList = alertRepository.getAlertTypeForEmail("3@toto.com");
		assertEquals(alertTypeList.size(), 0);
		
		//test mail 1
		alertTypeList = alertRepository.getAlertTypeForEmail("1@toto.com");
		assertEquals(alertTypeList.size(), 2);
		assertEquals(alertTypeList.get(0), AlertType.SHOUTCAST);
		assertEquals(alertTypeList.get(1), AlertType.FM);
		
		//test mail 2
		alertTypeList = alertRepository.getAlertTypeForEmail("2@toto.com");
		assertEquals(alertTypeList.size(), 1);
		assertEquals(alertTypeList.get(0), AlertType.PLAYLIST);
	}

	/**
	 * Test method {@link AlertRepository#getAlertEnabledForType(AlertType)}
	 */
	@Test
	public void testGetAlertEnabledForType() {
		assertTrue(alertRepository.getAlertEnabledForType(AlertType.FM));
		assertFalse(alertRepository.getAlertEnabledForType(AlertType.PLAYLIST));
		assertTrue(alertRepository.getAlertEnabledForType(AlertType.SHOUTCAST));
	}
	
	/**
	 * Clean database after test
	 */
	@After
	public void clearDatabase() {
		for (AlertEmail email : AlertEmail.findAllAlertEmails())
			email.remove();
		
		for (AlertActivation activation : AlertActivation.findAllAlertActivations())
			activation.remove();
	}
	
}
