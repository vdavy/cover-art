/**
 * 
 */
package com.stationmillenium.coverart.tests.services;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.services.recover.RecoverDeletedImagesService;

/**
 * Test class for the {@link RecoverDeletedImagesService}
 * @author vincent
 *
 */
public class TestRecoverDeletedImagesService {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(TestRecoverDeletedImagesService.class);
	
	//service to test
	@Autowired
	private RecoverDeletedImagesService recoverDeletedImagesService;

	/**
	 * Test the service
	 */
	@Test
	public void testService() {
		//launch recover
		recoverDeletedImagesService.recoverLostImages();
		
		//wait
		do {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				LOGGER.warn("Error during pause", e);
			}
		} while (!recoverDeletedImagesService.isFinished());

		//display results
		List<SongHistoryItemDTO> recoveredSongsList = recoverDeletedImagesService.getMissingImageSongsList();
		LOGGER.info("Recevored songs : " + recoveredSongsList);		
	}

}
