/**
 * 
 */
package com.stationmillenium.coverart.tests.services.alerts;

import java.util.HashSet;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.domain.alert.AlertActivation;
import com.stationmillenium.coverart.domain.alert.AlertEmail;
import com.stationmillenium.coverart.services.alerts.AlertService;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertType;

/**
 * Test for the {@link AlertService}
 * @author vincent
 *
 */
public class TestAlertService {

	@Autowired
	private AlertService alertService;
	
	@Test
	public void init() {
		//email insertion
		AlertEmail email = new AlertEmail();
		email.setEmail("address@mail.com");
		email.setAlertType(new HashSet<AlertType>());
		email.getAlertType().add(AlertType.FM);
		email.getAlertType().add(AlertType.ICECAST);
		email.getAlertType().add(AlertType.PLAYLIST);
		email.persist();
		
		email = new AlertEmail();
		email.setEmail("address@mail.com");
		email.setAlertType(new HashSet<AlertType>());
		email.getAlertType().add(AlertType.ICECAST);
		email.getAlertType().add(AlertType.PLAYLIST);
		email.persist();
		
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
		alertActivation.setAlertType(AlertType.ICECAST);
		alertActivation.setEnableAlert(true);
		alertActivation.persist();
	}
	
	/**
	 * Test alert activation on FM
	 */
	@Test
	public void testActiveAlertFM() {
		alertService.sendActiveAlert(AlertType.FM);
	}
	
	/**
	 * Test alert activation on playlist
	 */
	@Test
	public void testActiveAlertPlaylist() {
		alertService.sendActiveAlert(AlertType.PLAYLIST);
	}
	
	/**
	 * Test alert activation on shoutcast
	 */
	@Test
	public void testActiveAlertShoutcast() {
		alertService.sendActiveAlert(AlertType.ICECAST);
	}
	
	/**
	 * Test ended alert on FM
	 */
	@Test
	public void testDisabledAlertFM() {
		alertService.sendEndedAlert(AlertType.FM);
	}
	
	/**
	 * Test ended alert on playlist
	 */
	@Test
	public void testDisabledAlertPlaylist() {
		alertService.sendEndedAlert(AlertType.PLAYLIST);
	}
	
	/**
	 * Test ended alert on shoutcast
	 */
	@Test
	public void testDisabledAlertShoutcast() {
		alertService.sendEndedAlert(AlertType.ICECAST);
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
