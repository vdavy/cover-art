package com.stationmillenium.coverart.tests.domain;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.dto.services.images.SongImageDTO;
import com.stationmillenium.coverart.repositories.SongImageRepository;

/**
 * Tests of the song history image repository
 * @author vincent
 *
 */
public class TestSongHistoryImageRepository {
	
	//the repository
	@Autowired
	private SongImageRepository repository;
			
	/**
	 * Init mockito
	 */
	@Before
	public void initMockito() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Create a mock of a <code>SongHistoryItemDTO</code>
	 * @param artist artist to mock
	 * @param title title to mock
	 * @param calendar played date to mock
	 * @return the mocked <code>SongHistoryItemDTO</code>
	 */
	private SongHistoryItemDTO mockSongItem(String artist, String title, Calendar playedDate) {
		SongHistoryItemDTO mockDTO = mock(SongHistoryItemDTO.class);
		doReturn(title).when(mockDTO).getTitle();
		doReturn(artist).when(mockDTO).getArtist();
		doReturn(playedDate).when(mockDTO).getPlayedDate();
		
		return mockDTO;
	}
	
	/**
	 * Test the testGetImageForSong()
	 */
	@Test
	public void testGetImageForSong() {
		//mock song
		Calendar playedDate = Calendar.getInstance();
		playedDate.set(2012, 8, 14, 10, 2, 36);		
		SongHistoryItemDTO mockedDTO = mockSongItem("David guetta", "She Wolf (Falling To Pieces)", playedDate);
		
		//test
		SongImageDTO image = repository.getImageForSong(mockedDTO);
		
		//assert 
		assertNotNull(image);
		verify(mockedDTO).getArtist();
		verify(mockedDTO).getTitle();
		verify(mockedDTO).getPlayedDate();
	}
	
}
