/**
 * 
 */
package com.stationmillenium.coverart.tests.services;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.services.covergraber.CoverGraberRootService;
import com.stationmillenium.coverart.services.covergraber.services.impl.LastFmCoverGraberService;

/**
 * Test class for the {@link CoverGraberRootService}
 * @author vincent
 *
 */
public class TestCoverGraberRootService {

	//mock variable
	@Spy
	private  List<SongHistoryItemDTO> songHistoryList;
		
	//service to test
	@Autowired
	private CoverGraberRootService coverGraberRootService;
	
	/**
	 * Init mockito
	 */
	@Before
	public void initMockito() {
		songHistoryList = new ArrayList<>();
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test the {@link LastFmCoverGraberService}
	 */
	@Test
	public void testGrabImageForSongs() {
		//mock data
		songHistoryList.add(mockSongItem("Cher", "Believe"));
		songHistoryList.add(mockSongItem("Cher", "Believe"));
		
		//process assert
		coverGraberRootService.grabImageForSongs(songHistoryList);
	}
	
	/**
	 * Create a mock of a <code>SongHistoryItemDTO</code>
	 * @param artist artist to mock
	 * @param title title to mock
	 * @return the mocket <code>SongHistoryItemDTO</code>
	 */
	private SongHistoryItemDTO mockSongItem(String artist, String title) {
		SongHistoryItemDTO mockDTO = mock(SongHistoryItemDTO.class);
		doReturn(title).when(mockDTO).getTitle();
		doReturn(artist).when(mockDTO).getArtist();
		
		Calendar playedDate = Calendar.getInstance();
		doReturn(playedDate).when(mockDTO).getPlayedDate();
		
		return mockDTO;
	}
}
