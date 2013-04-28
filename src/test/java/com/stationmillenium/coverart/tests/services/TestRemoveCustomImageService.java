/**
 * 
 */
package com.stationmillenium.coverart.tests.services;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.services.recover.RemoveCustomImageService;

/**
 * Test for the {@link RemoveCustomImageService}
 * @author vincent
 *
 */
public class TestRemoveCustomImageService {

	//the service
	@Autowired
	private RemoveCustomImageService service;
	
	//mock song
	private SongHistoryItemDTO song;
	
	/**
	 * Init the mocks
	 */
	@Before
	public void initMocks() {
		song = mock(SongHistoryItemDTO.class);
		when(song.getArtist()).thenReturn("Alicia Keys");
		when(song.getTitle()).thenReturn("Doesn't Mean Anything");
	}
	
	/**
	 * Main test method
	 */
	@Test
	public void testRemoveCustomImageService() {
		//process
		service.removeImageOnSong(song);
		
		//assert
		verify(song, atLeastOnce()).getArtist();
		verify(song, atLeastOnce()).getTitle();
	}
	
}
