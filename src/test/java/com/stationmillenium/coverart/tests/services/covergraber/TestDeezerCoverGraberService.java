/**
 * 
 */
package com.stationmillenium.coverart.tests.services.covergraber;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.services.covergraber.services.impl.DeezerCoverGraberService;

/**
 * Test class for the {@link DeezerCoverGraberService}
 * @author vincent
 *
 */
public class TestDeezerCoverGraberService {

	//service to test
	@Autowired
	private DeezerCoverGraberService deezerCoverGraberService;
	
	/**
	 * Test the {@link DeezerCoverGraberService}
	 */
	@Test
	public void testDeezerCoverGraberService() {
		assertNotNull(deezerCoverGraberService.grabCover("David Guetta", "Metropolis"));
	}
	
	/**
	 * Test the {@link DeezerCoverGraberService} - not found case
	 */
	@Test
	public void testDeezerCoverGraberServiceNullTest() {
		assertNull(deezerCoverGraberService.grabCover("azerty", "ytreza"));
	}
	
}
