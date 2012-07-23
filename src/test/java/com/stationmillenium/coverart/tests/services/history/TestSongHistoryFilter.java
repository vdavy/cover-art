/**
 * 
 */
package com.stationmillenium.coverart.tests.services.history;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.domain.SongHistory;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.SongHistoryRepository;
import com.stationmillenium.coverart.services.history.SongHistoryFilter;

	 
	
/**
 * Test part for the song history filter
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
	
	//song history repository
	@Autowired
	private SongHistoryRepository songHistoryRepository;
	
	//reference calendar needed for duration computation
	private Calendar referenceCalendar = Calendar.getInstance();
	
	//dozer mapper
	@Autowired
	private Mapper mapper;
	
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
	 * @param calendarOffsetSecond negative offset to apply to current time calendar
	 * @return the mocket <code>SongHistoryItemDTO</code>
	 */
	private SongHistoryItemDTO mockSongItem(String artist, String title, int calendarOffsetSecond) {
		SongHistoryItemDTO mockDTO = mock(SongHistoryItemDTO.class);
		doReturn(title).when(mockDTO).getTitle();
		doReturn(artist).when(mockDTO).getArtist();
		
		Calendar playedDate = Calendar.getInstance();
		playedDate.setTimeInMillis(referenceCalendar.getTimeInMillis());
		playedDate.add(Calendar.SECOND, -calendarOffsetSecond);
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
		songHistoryList.add(mockSongItem("foo", "bar", 60));
		songHistoryList.add(mockSongItem("foo", "Jingle", 60));				
		songHistoryList.add(mockSongItem("", "", 60));
		
		//filter
		List<SongHistoryItemDTO> filteredList = songHistoryFilter.filterSongHistory(songHistoryList);
		
		//verify
		verify(songHistoryList, atLeastOnce()).get(0);
		verify(songHistoryList, atLeastOnce()).get(1);
		verify(songHistoryList, atLeastOnce()).get(2);
		verify(songHistoryList, atLeastOnce()).get(4);
		assertEquals(filteredList.size(), 1);
	}
	
	/**
	 * Test song filtering against played date
	 */
	@Test
	public void testSongHistoryFilterPlayedDate() {
		//mock the list
		songHistoryList.add(mockSongItem("foo", "bar", 0));
		songHistoryList.add(mockSongItem("foo", "bar", 10));
		songHistoryList.add(mockSongItem("foo", "bar", 5));				
		songHistoryList.add(mockSongItem("foo", "bar", 60));			
		songHistoryList.add(mockSongItem("foo", "bar", 5));	
		
		//filter
		List<SongHistoryItemDTO> filteredList = songHistoryFilter.filterSongHistory(songHistoryList);
		
		//verify
		verify(songHistoryList, atLeastOnce()).get(0);
		verify(songHistoryList, atLeastOnce()).get(1);
		verify(songHistoryList, atLeastOnce()).get(2);
		verify(songHistoryList, atLeastOnce()).get(3);
		verify(songHistoryList, atLeastOnce()).get(4);
		assertEquals(filteredList.size(), 2);
	}
	
	/**
	 * Test for {@link SongHistoryFilter#filterLastRecordedSong(List)}
	 * Last song is the las recorded
	 */
	@Test
	public void testLastRecordedSongFilterLastSongIsLastRecorded() {
		SongHistory songHistory = generateTestEntity();
		
		//mock data
		songHistoryList.add(mockSongItem("foo", "bar", 0));

		//test 
		songHistoryFilter.filterLastRecordedSong(songHistoryList);
		
		//assert
		verify(songHistoryList, atLeastOnce()).get(0);
		SongHistoryItemDTO lastSongItemFromDB = songHistoryRepository.getLastSongHistoryItem();
		assertTrue(mapper.map(songHistory, SongHistoryItemDTO.class).equals(lastSongItemFromDB));
		
		//delete database entry
		songHistory.remove();
	}
	
	
	/**
	 * Test for {@link SongHistoryFilter#filterLastRecordedSong(List)}
	 * Song to remove in position 2
	 */
	@Test
	public void testLastRecordedSongFilterRemoveSongTwo() {
		SongHistory songHistory = generateTestEntity();
		
		//mock data
		songHistoryList.add(mockSongItem("foo2", "bar2", -15));
		songHistoryList.add(mockSongItem("foo", "bar", 0));
		songHistoryList.add(mockSongItem("foo1", "bar1", 10));
		
		//test 
		songHistoryFilter.filterLastRecordedSong(songHistoryList);
		
		//assert
		verify(songHistoryList, atLeastOnce()).get(0);
		verify(songHistoryList, atLeastOnce()).get(1);
		verify(songHistoryList, never()).get(2);
		SongHistoryItemDTO lastSongItemFromDB = songHistoryRepository.getLastSongHistoryItem();
		assertFalse(mapper.map(songHistory, SongHistoryItemDTO.class).equals(lastSongItemFromDB));
	}
	
	
	/**
	 * Test for {@link SongHistoryFilter#filterLastRecordedSong(List)}
	 * Song to remove in last position in list
	 */
	@Test
	public void testLastRecordedSongFilterRemoveSongEndingList() {
		SongHistory songHistory = generateTestEntity();
		
		//mock data
		songHistoryList.add(mockSongItem("foo2", "bar2", -15));
		songHistoryList.add(mockSongItem("foo3", "bar3", -10));
		songHistoryList.add(mockSongItem("foo", "bar", 0));
		
		//test 
		songHistoryFilter.filterLastRecordedSong(songHistoryList);
		
		//assert
		verify(songHistoryList, atLeastOnce()).get(0);
		verify(songHistoryList, atLeastOnce()).get(1);
		verify(songHistoryList, atLeastOnce()).get(2);
		SongHistoryItemDTO lastSongItemFromDB = songHistoryRepository.getLastSongHistoryItem();
		assertFalse(mapper.map(songHistory, SongHistoryItemDTO.class).equals(lastSongItemFromDB));
	}
	
	/**
	 * Test for {@link SongHistoryFilter#filterLastRecordedSong(List)}
	 * Song to remove not in in list
	 */
	@Test
	public void testLastRecordedSongFilterRemoveSongNotInList() {
		SongHistory songHistory = generateTestEntity();
		
		//mock data
		songHistoryList.add(mockSongItem("foo2", "bar2", -15));
		songHistoryList.add(mockSongItem("foo3", "bar3", -10));
		songHistoryList.add(mockSongItem("foo1", "bar1", -5));
		
		//test 
		songHistoryFilter.filterLastRecordedSong(songHistoryList);
		
		//assert
		verify(songHistoryList, atLeastOnce()).get(0);
		verify(songHistoryList, atLeastOnce()).get(1);
		verify(songHistoryList, atLeastOnce()).get(2);
		SongHistoryItemDTO lastSongItemFromDB = songHistoryRepository.getLastSongHistoryItem();
		assertFalse(mapper.map(songHistory, SongHistoryItemDTO.class).equals(lastSongItemFromDB));
	}

	/**
	 * Test for {@link SongHistoryFilter#filterLastRecordedSong(List)}
	 * Nothing to remove
	 */
	@Test
	public void testLastRecordedSongFilterNotRemoving() {
		SongHistory songHistory = generateTestEntity();
		
		//mock data
		songHistoryList.add(mockSongItem("foo2", "bar2", -510));
		songHistoryList.add(mockSongItem("foo3", "bar3", -500));
		songHistoryList.add(mockSongItem("foo1", "bar1", -300));
		
		//test 
		songHistoryFilter.filterLastRecordedSong(songHistoryList);
		
		//assert
		verify(songHistoryList, atLeastOnce()).get(0);
		verify(songHistoryList, atLeastOnce()).get(1);
		verify(songHistoryList, atLeastOnce()).get(2);
		SongHistoryItemDTO lastSongItemFromDB = songHistoryRepository.getLastSongHistoryItem();
		assertTrue(mapper.map(songHistory, SongHistoryItemDTO.class).equals(lastSongItemFromDB));
		
		//delete database entry
		songHistory.remove();
	}

	/**
	 * Generate the test entity
	 * @return entity
	 */
	private SongHistory generateTestEntity() {
		//insert test data into db
		SongHistory songHistory = new SongHistory();
		songHistory.setArtist("foo");
		songHistory.setTitle("bar");
		songHistory.setPlayedDate(referenceCalendar);
		songHistory.persist();
		return songHistory;
	}
	
}
