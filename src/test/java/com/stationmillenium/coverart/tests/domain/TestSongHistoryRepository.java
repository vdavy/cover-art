package com.stationmillenium.coverart.tests.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

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

/**
 * Tests of the song history repository
 * @author vincent
 *
 */
public class TestSongHistoryRepository {
	
	//the repository
	@Autowired
	private SongHistoryRepository  repository;
		
	//dozer mapper
	@Autowired
	private Mapper mapper;
	
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
	 * @return the mocket <code>SongHistoryItemDTO</code>
	 */
	private SongHistoryItemDTO mockSongItem(String artist, String title) {
		SongHistoryItemDTO mockDTO = mock(SongHistoryItemDTO.class);
		doReturn(title).when(mockDTO).getTitle();
		doReturn(artist).when(mockDTO).getArtist();
		doReturn(Calendar.getInstance()).when(mockDTO).getPlayedDate();
		
		return mockDTO;
	}
	
	/**
	 * Test the {@link SongHistoryRepository#getLastSongHistoryItem()}
	 */
	@Test
	public void testLastSongHistoryItem() {
		SongHistoryItemDTO songItem = repository.getLastSongHistoryItem();
		assertNotNull(songItem);
		assertNotNull(songItem.getArtist());
		assertNotNull(songItem.getTitle());
		assertNotNull(songItem.getPlayedDate());
	}
	
	/**
	 * Test the { {@link SongHistoryRepository#deleteLastRecordedSong()}
	 */
	@Test
	public void testDeleteLastRecordedSong() {
		//insert test data into db
		SongHistory songHistory = new SongHistory();
		songHistory.setArtist("foo");
		songHistory.setTitle("bar");
		songHistory.setPlayedDate(Calendar.getInstance());
		songHistory.persist();
				
		//process
		repository.deleteLastRecordedSong();
		
		//assert
		SongHistoryItemDTO lastSongItemFromDB = repository.getLastSongHistoryItem();
		assertFalse(mapper.map(songHistory, SongHistoryItemDTO.class).equals(lastSongItemFromDB));
	}
	
	@Test
	public void testInsertSongHistoryList() {
		//mock data
		songHistoryList.add(mockSongItem("foo1", "bar1"));
		songHistoryList.add(mockSongItem("foo2", "bar2"));
		songHistoryList.add(mockSongItem("foo3", "bar3"));
		
		//process
		repository.insertSongHistoryList(songHistoryList);
		
		//assert
		for (int i = songHistoryList.size() - 1; i >= 0; i--) {
			SongHistoryItemDTO lastSongItemFromDB = repository.getLastSongHistoryItem();
			assertTrue(lastSongItemFromDB.equals(songHistoryList.get(i)));
			repository.deleteLastRecordedSong();
		}	
	}
}
