/**
 * 
 */
package com.stationmillenium.coverart.tests.services.covergraber;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.services.covergraber.impl.LastFmCoverGraberService;

/**
 * Test class for the {@link LastFmCoverGraberService}
 * @author vincent
 *
 */
public class TestLastFMCoverGraberService {

	//service to test
	@Autowired
	private LastFmCoverGraberService lastFmCoverGraberService;
	
	/**
	 * Test the {@link LastFmCoverGraberService}
	 */
	@Test
	public void testLastFmCoverGraberService() {
		lastFmCoverGraberService.grabCover("Cher", "Believe");
	}
}
