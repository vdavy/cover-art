/**
 * 
 */
package com.stationmillenium.coverart.tests.services.history;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
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
	private  List<SongHistoryItemDTO> songHistoryList;
	
	/**
	 * Init mockito
	 */
	@Before
	public void initMockito() {
		songHistoryList = new ArrayList<>();
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
		songHistoryList.add(mockSongItem("foo", "bar", 1000));
		songHistoryList.add(mockSongItem("foo", "Jingle", 0));				
		
		//filter
		List<SongHistoryItemDTO> filteredList = songHistoryFilter.filterSongHistory(songHistoryList, mockSongItem("", "", 0));
		
		//verify
		verify(songHistoryList, atLeastOnce()).get(0);
		verify(songHistoryList, atLeastOnce()).get(1);
		verify(songHistoryList, atLeastOnce()).get(2);
		assertEquals(filteredList.size(), 1);
	}
	
	/**
	 * Test song filtering against played date
	 */
	@Test
	public void testSongHistoryFilterPlayedDate() {
		//mock the list
		songHistoryList.add(mockSongItem("foo", "bar", 60));
		songHistoryList.add(mockSongItem("foo", "bar", 10));
		songHistoryList.add(mockSongItem("foo", "bar", 5));				
		
		//filter
		List<SongHistoryItemDTO> filteredList = songHistoryFilter.filterSongHistory(songHistoryList, mockSongItem("", "", 0));
		
		//verify
		verify(songHistoryList, atLeastOnce()).get(0);
		verify(songHistoryList, atLeastOnce()).get(1);
		verify(songHistoryList, atLeastOnce()).get(2);
		assertEquals(filteredList.size(), 1);
	}
	
}
