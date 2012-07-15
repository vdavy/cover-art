/**
 * 
 */
package com.stationmillenium.coverart.tests;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.services.history.SongHistoryFilter;

	 
	
/**
 * Test part for the song history parser
 * @author vincent
 *
 */
public class TestSongHistoryFilter {

	//service
	@Autowired
	private SongHistoryFilter songHistoryFilter;
	
	//mock variable
	@Spy
	private  List<SongHistoryItemDTO> songHistoryList = new ArrayList<>();
	
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
	 * @param calendarOffsetSecond offset to apply to current time calendar
	 * @return the mocket <code>SongHistoryItemDTO</code>
	 */
	private SongHistoryItemDTO mockSongItem(String artist, String title, int calendarOffsetSecond) {
		SongHistoryItemDTO mockDTO = mock(SongHistoryItemDTO.class);
		doReturn(title).when(mockDTO).getTitle();
		doReturn(artist).when(mockDTO).getArtist();
		
		Calendar playedDate = Calendar.getInstance();
		playedDate.add(Calendar.SECOND, calendarOffsetSecond);
		doReturn(playedDate).when(mockDTO).getPlayedDate();
		
		return mockDTO;
	}
	
	/**
	 * Test song filtering against name and title
	 */
	@Test
	public void testSongHistoryFilterSongNameAndTitle() {
		//mock the list
		songHistoryList.add(mockSongItem("Millenium", "foo", 0));
		songHistoryList.add(mockSongItem("foor", "bar", 1000));
		songHistoryList.add(mockSongItem("foor", "Jingle", 0));				
		
		//filter
		List<SongHistoryItemDTO> filteredList = songHistoryFilter.filterSongHistory(songHistoryList, mockSongItem("", "", 0));
		
		//verify
		Mockito.verify(songHistoryList, VerificationModeFactory.atLeastOnce()).get(0);
		Mockito.verify(songHistoryList, VerificationModeFactory.atLeastOnce()).get(1);
		Mockito.verify(songHistoryList, VerificationModeFactory.atLeastOnce()).get(2);
		Assert.assertEquals(filteredList.size(), 1);
	}
	
}
