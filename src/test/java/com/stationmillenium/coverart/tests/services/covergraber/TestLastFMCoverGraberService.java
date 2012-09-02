/**
 * 
 */
package com.stationmillenium.coverart.tests.services.covergraber;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.services.covergraber.services.impl.LastFmCoverGraberService;

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
		assertNotNull(lastFmCoverGraberService.grabCover("Cher", "Believe"));
	}
	
	/**
	 * Test the {@link LastFmCoverGraberService} - null test
	 */
	@Test
	public void testLastFmCoverGraberServiceNullTest() {
		assertNull(lastFmCoverGraberService.grabCover("azerty", "ytreza"));
	}
	
}
