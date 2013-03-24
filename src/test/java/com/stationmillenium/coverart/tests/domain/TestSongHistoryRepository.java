package com.stationmillenium.coverart.tests.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.domain.history.SongHistory;
import com.stationmillenium.coverart.domain.history.SongItem;
import com.stationmillenium.coverart.dto.hybrid.SongHistoryItemImageDTO;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.SongItemRepository;

/**
 * Tests of the song history repository
 * @author vincent
 *
 */
public class TestSongHistoryRepository {
	
	//the repository
	@Autowired
	private SongItemRepository  repository;
		
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
	 * Test the {@link SongHistoryRepository#deleteLastRecordedSong()}
	 */
	@Test
	public void testDeleteLastRecordedSong() {
		//insert test data into db
		SongHistory songHistory = new SongHistory();
		songHistory.setPlayedDate(Calendar.getInstance());		
		SongItem songItem = new SongItem();
		songItem.setArtist("foo");
		songItem.setTitle("bar");
		songItem.setPlayedTimes(new HashSet<SongHistory>());
		songItem.getPlayedTimes().add(songHistory);
		songItem.persist();
					
		//process
		repository.deleteLastRecordedSong();
		
		//assert
		SongHistoryItemDTO lastSongItemFromDB = repository.getLastSongHistoryItem();
		SongHistoryItemDTO returnedDTO = mapper.map(songItem, SongHistoryItemDTO.class);
		returnedDTO.setPlayedDate(((SongHistory) songItem.getPlayedTimes().toArray()[0]).getPlayedDate());
		assertFalse(returnedDTO.equals(lastSongItemFromDB));
	}
	
	/**
	 * Test the {@link SongHistoryRepository#insertSongHistoryList()}
	 */
	@Test
	public void testInsertSongHistoryList() {
		//mock data
		songHistoryList.add(mockSongItem("foo1", "bar1"));
		songHistoryList.add(mockSongItem("foo2", "bar2"));
		songHistoryList.add(mockSongItem("foo3", "bar3"));
		
		//process
		for (SongHistoryItemDTO song : songHistoryList)
			repository.insertSongHistory(song);
		
		//assert
		for (int i = songHistoryList.size() - 1; i >= 0; i--) {
			SongHistoryItemDTO lastSongItemFromDB = repository.getLastSongHistoryItem();
			assertTrue(lastSongItemFromDB.equals(songHistoryList.get(i)));
			repository.deleteLastRecordedSong();
		}	
	}

	/**
	 * Test the {@link SongHistoryRepository#sExistingSong()}
	 */
	@Test
	public void testIsExistingSong() {
		//mock data
		songHistoryList.add(mockSongItem("foo1", "bar1"));
		repository.insertSongHistory(songHistoryList.get(0));

		//process 
		boolean existingSong = repository.isExistingSong(songHistoryList.get(0));
		
		//assert
		assertTrue(existingSong);
		
		//process
		repository.deleteLastRecordedSong();
		existingSong = repository.isExistingSong(songHistoryList.get(0));
		
		//assert
		assertFalse(existingSong);		
	}
	
	/**
	 * Test the {@link SongHistoryRepository#addTimeToExistingSong()}
	 */
	@Test
	public void testAddTimeToExistingSong() {
		//mock data
		songHistoryList.add(mockSongItem("foo1", "bar1"));
		repository.insertSongHistory(songHistoryList.get(0));

		//process 
		repository.addTimeToExistingSong(songHistoryList.get(0));
		
		//assert
		List<SongItem> songList = SongItem.findAllSongItems();
		Collections.reverse(songList);		
		assertEquals(songList.get(0).getPlayedTimes().size(), 2);
		
		//process
		repository.deleteLastRecordedSong();
	}
	
	/**
	 * Test the {@link SongHistoryRepository#getLast5PlayedSongs()}
	 */
	@Test
	public void testGetLast5PlayedSongs() {
		//process
		List<SongHistoryItemDTO> lastSongsList = repository.getLast5PlayedSongs(true);
		
		//assert
		assertEquals(lastSongsList.size(), 5);
		
		//process 
		lastSongsList = repository.getLast5PlayedSongs(false);
		
		//assert
		assertEquals(lastSongsList.size(), 5);		
	}
	
	/**
	 * Test the {@link SongHistoryRepository#getLastSongsFromMinDate()}
	 */
	@Test
	public void testGetLastSongsFromMinDate() {
		//set up param
		Calendar minDate = Calendar.getInstance();
		minDate.add(Calendar.DAY_OF_YEAR, -100);
		
		//process
		List<SongHistoryItemImageDTO> songList = repository.getLastSongsFromMinDate(minDate);
		
		//assert
		assertTrue(songList.size() > 0);
		for (SongHistoryItemImageDTO song : songList) {
			assertNotNull(song.getSongHistoryItemDTO().getArtist());
			assertNotNull(song.getSongHistoryItemDTO().getTitle());
			assertNotNull(song.getSongHistoryItemDTO().getPlayedDate());
			assertTrue(minDate.before(song.getSongHistoryItemDTO().getPlayedDate()));
			
			if ((song.getSongImageDTO().getHeight() != 0)
					|| (song.getSongImageDTO().getWidth() != 0)){
				assertNotNull(song.getSongImageDTO().getFileName());
				assertTrue(song.getSongImageDTO().getHeight() != 0);
				assertTrue(song.getSongImageDTO().	getWidth() != 0);
			}
		}
	}
	
	/**
	 * Test the {@link SongItemRepository#getSongsPlayedBetween2Dates(Calendar, Calendar)}
	 */
	@Test
	public void testGetSongsPlayedBetween2Dates() {
		//set up param
		Calendar minDate = Calendar.getInstance();
		minDate.add(Calendar.DAY_OF_YEAR, -100);
		Calendar maxDate = Calendar.getInstance();
		
		//process
		List<SongHistoryItemDTO> songList = repository.getSongsPlayedBetween2Dates(minDate, maxDate);
		
		//assert
		assertTrue(songList.size() > 0);
		for (SongHistoryItemDTO song : songList) {
			assertNotNull(song.getPlayedDate());
			assertNotNull(song.getArtist());
			assertNotNull(song.getTitle());
		}
	}
	
	/**
	 * Test the {@link SongItemRepository#getSongsPlayedBetween2DatesWithImages(Calendar, Calendar)}
	 */
	@Test
	public void testGetSongsPlayedBetween2DatesWithImages() {
		//set up param
		Calendar minDate = Calendar.getInstance();
		minDate.add(Calendar.DAY_OF_YEAR, -100);
		Calendar maxDate = Calendar.getInstance();
		
		//process
		List<SongHistoryItemImageDTO> songList = repository.getSongsPlayedBetween2DatesWithImages(minDate, maxDate);
		
		//assert
		assertTrue(songList.size() > 0);
		for (SongHistoryItemImageDTO song : songList) {
			assertNotNull(song.getSongHistoryItemDTO().getArtist());
			assertNotNull(song.getSongHistoryItemDTO().getTitle());
			assertNotNull(song.getSongHistoryItemDTO().getPlayedDate());
			assertTrue(minDate.before(song.getSongHistoryItemDTO().getPlayedDate()));
			
			if ((song.getSongImageDTO().getHeight() != 0)
					|| (song.getSongImageDTO().getWidth() != 0)){
				assertNotNull(song.getSongImageDTO().getFileName());
				assertTrue(song.getSongImageDTO().getHeight() != 0);
				assertTrue(song.getSongImageDTO().	getWidth() != 0);
			}
		}
	}
	
}
