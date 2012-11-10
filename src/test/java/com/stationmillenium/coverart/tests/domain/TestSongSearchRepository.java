/**
 * 
 */
package com.stationmillenium.coverart.tests.domain;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.dto.hybrid.SongHistoryItemImageDTO;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.SongSearchRepository;

/**
 * Test the {@link SongSearchRepository} with Hibernate Search
 * @author vincent
 *
 */
public class TestSongSearchRepository {

	@Autowired
	private SongSearchRepository repository;
	
	/**
	 * Test indexing
	 * @throws InterruptedException 
	 */
	@Test
	public void testIndex() throws InterruptedException {
		repository.index();
	}
	
	/**
	 * Test song search
	 */
	@Test
	public void testSearchSongs() {
		List<SongHistoryItemImageDTO> songList = repository.searchSongs("David guetta");
		assertTrue(songList.size() > 0);
	}
	
	/**
	 * Test song search for suggest
	 */
	@Test
	public void testSearchSongsForSuggest() {
		List<SongHistoryItemDTO> songList = repository.searchSongsForSuggest("David guetta", 3);
		assertTrue(songList.size() > 0);
		assertTrue(songList.size() <= 3);
	}
	
	
	/**
	 * Test song search by artist
	 */
	@Test
	public void testSearchSongsByArtist() {
		List<SongHistoryItemImageDTO> songList = repository.searchSongsByArtist("David");
		assertTrue(songList.size() > 0);
		
		songList = repository.searchSongsByArtist("Gueta");
		assertTrue(songList.size() > 0);
	}
	
	/**
	 * Test song search by title
	 */
	@Test
	public void testSearchSongsByTitle() {
		List<SongHistoryItemImageDTO> songList = repository.searchSongsByTitle("she wolf");
		assertTrue(songList.size() > 0);
		
		songList = repository.searchSongsByTitle("shie wolh");
		assertTrue(songList.size() > 0);
	}
	
	/**
	 * Test song search by times
	 */
	@Test
	public void testSearchByTimes() {
		//set up calendars
		Calendar beginning = Calendar.getInstance();
		beginning.set(2012, 8, 14, 0, 0, 0);
		
		Calendar ending = Calendar.getInstance();
		ending.set(2012, 8, 15, 0, 0, 0);
		
		//test
		List<SongHistoryItemImageDTO> songList = repository.searchSongsByTime(beginning, ending);
		assertTrue(songList.size() > 0);
	}
	
}
