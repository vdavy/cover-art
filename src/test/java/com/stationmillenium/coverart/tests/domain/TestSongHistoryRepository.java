package com.stationmillenium.coverart.tests.domain;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
		repository.deleteLastRecordedSong();
	}
}
