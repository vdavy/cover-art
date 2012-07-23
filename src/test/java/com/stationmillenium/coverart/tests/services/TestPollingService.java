/**
 * 
 */
package com.stationmillenium.coverart.tests.services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.services.PollingService;

/**
 * Test for the {@link PollingService}
 * @author vincent
 *
 */
public class TestPollingService {

	//the polling service
	@Autowired
	private PollingService pollingService;
	
	/**
	 * Main test method
	 */
	@Test
	public void testPollingService() {
		pollingService.doServerPolling();
	}
}
