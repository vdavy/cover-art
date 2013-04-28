/**
 * 
 */
package com.stationmillenium.coverart.tests.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.dto.hybrid.SongHistoryItemImageDTO;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.dto.services.images.SongImageDTO.Provider;
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
	public void testIndexing() throws InterruptedException {
		repository.indexAsync();
		boolean indexingFinished = repository.isIndexingFinished();
		while (!indexingFinished) {
			indexingFinished = repository.isIndexingFinished();
		}
	}
	
	/**
	 * Test song search
	 */
	@Test
	public void testSearchSongs() {
		List<SongHistoryItemImageDTO> songList = repository.searchSongs("David guetta", 100);
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
		List<SongHistoryItemImageDTO> songList = repository.searchSongsByArtist("David", 100);
		assertTrue(songList.size() > 0);
		
		songList = repository.searchSongsByArtist("Gueta", 100);
		assertTrue(songList.size() > 0);
	}
	
	/**
	 * Test song search by title
	 */
	@Test
	public void testSearchSongsByTitle() {
		List<SongHistoryItemImageDTO> songList = repository.searchSongsByTitle("she wolf", 100);
		assertTrue(songList.size() > 0);
		
		songList = repository.searchSongsByTitle("shie wolh", 100);
		assertTrue(songList.size() > 0);
	}

	/**
	 * Test {@link SongSearchRepository#searchSongsForCustomImage(String, int)}
	 */
	@Test
	public void testSearchSongsForCustomImageWithImage() {
		//process
		List<SongHistoryItemImageDTO> songList = repository.searchSongsForCustomImage("alicia keys", 10, true);
		
		//assert
		assertTrue(songList.size() > 0);
		for (SongHistoryItemImageDTO song : songList) {
			assertNotNull(song.getSongHistoryItemDTO().getArtist());
			assertNotNull(song.getSongHistoryItemDTO().getTitle());
			assertTrue(song.getSongHistoryItemDTO().isCustomImage());
			
			if ((song.getSongImageDTO().getHeight() != 0)
					|| (song.getSongImageDTO().getWidth() != 0)){
				assertNotNull(song.getSongImageDTO().getFileName());
				assertTrue(song.getSongImageDTO().getHeight() != 0);
				assertTrue(song.getSongImageDTO().getWidth() != 0);
				assertFalse(song.getSongImageDTO().getProvider() == Provider.CUSTOM);
			}
		}
	}

	/**
	 * Test {@link SongSearchRepository#searchSongsForCustomImage(String, int)}
	 */
	@Test
	public void testSearchSongsForCustomImageWithoutImage() {
		//process
		List<SongHistoryItemImageDTO> songList = repository.searchSongsForCustomImage("alicia keys", 10, false);
		
		//assert
		assertTrue(songList.size() > 0);
		for (SongHistoryItemImageDTO song : songList) {
			assertNotNull(song.getSongHistoryItemDTO().getArtist());
			assertNotNull(song.getSongHistoryItemDTO().getTitle());
			assertFalse(song.getSongHistoryItemDTO().isCustomImage());
			
			if ((song.getSongImageDTO().getHeight() != 0)
					|| (song.getSongImageDTO().getWidth() != 0)){
				assertNotNull(song.getSongImageDTO().getFileName());
				assertTrue(song.getSongImageDTO().getHeight() != 0);
				assertTrue(song.getSongImageDTO().getWidth() != 0);
				assertFalse(song.getSongImageDTO().getProvider() != Provider.CUSTOM);
			}
		}
	}
	
}
