package com.stationmillenium.coverart.tests.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.domain.history.SongHistoryImage;
import com.stationmillenium.coverart.domain.history.SongHistoryImage.Provider;
import com.stationmillenium.coverart.domain.history.SongItem;
import com.stationmillenium.coverart.dto.hybrid.SongHistoryItemImageDTO;
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
	
	/**
	 * Test the getAllSongsWithImage()
	 */
	@Test
	public void testGetAllSongsWithImage() {
		//load dtos
		List<SongHistoryItemImageDTO> listOfSongs = repository.getAllSongsWithImage();
		
		//asserts 
		assertNotNull(listOfSongs);
		assertNotEquals(listOfSongs.size(), 0);
		for (SongHistoryItemImageDTO song : listOfSongs) {
			assertNotNull(song);
			assertNotNull(song.getSongHistoryItemDTO());
			assertNotNull(song.getSongHistoryItemDTO().getArtist());
			assertNotNull(song.getSongHistoryItemDTO().getTitle());
			assertNotNull(song.getSongImageDTO());
			assertNotNull(song.getSongImageDTO().getFileName());
			assertNotEquals(song.getSongImageDTO().getHeight(), 0);
			assertNotEquals(song.getSongImageDTO().getWidth(), 0);
		}
	}

	/**
	 * Test the deleteImageOfSong()
	 */
	@Test
	public void testDeleteImageOfSong() {
		//insert of song to delete
		SongItem songToInsert = new SongItem();
		songToInsert.setArtist("toto");
		songToInsert.setTitle("titi");
		//songToInsert.setPlayedTimes(new HashSet<SongHistory>());
		SongHistoryImage image = new SongHistoryImage();
		image.setFileName("fileName");
		image.setHeight(100);
		image.setWidth(100);
		image.setProvider(Provider.LAST_FM);
		songToInsert.persist();
		image.persist();
		songToInsert.setImage(image);
		songToInsert.merge();
		
		
		//test
		SongHistoryItemDTO mockedDTO = mockSongItem("toto", "titi", null);
		repository.deleteImageOfSong(mockedDTO);
		
		//assert
		verify(mockedDTO).getArtist();
		verify(mockedDTO).getTitle();
		assertNull(SongHistoryImage.findSongHistoryImage(image.getId()));
		assertNull(SongItem.findSongItem(songToInsert.getId()).getImage());
	}

	/**
	 *  Test the {@link SongImageRepository#isSongHasImage(SongHistoryItemDTO)}
	 */
	@Test
	public void testIsSongHasImage() {
		//init
		SongHistoryItemDTO mockedDTO = mockSongItem("David guetta", "She Wolf (Falling To Pieces)", Calendar.getInstance());
		
		//process
		boolean result = repository.isSongHasImage(mockedDTO);
		
		//assert
		verify(mockedDTO).getArtist();
		verify(mockedDTO).getTitle();
		assertTrue(result);
		
		//init
		mockedDTO = mockSongItem("Inna", "Caliente", Calendar.getInstance());
		
		//process
		result = repository.isSongHasImage(mockedDTO);
		
		//assert
		verify(mockedDTO).getArtist();
		verify(mockedDTO).getTitle();
		assertFalse(result);
	}

	/**
	 *  Test the {@link SongImageRepository#getImageFileNameOfSong(SongHistoryItemDTO)}
	 */
	@Test
	public void testGetImageFileNameOfSong() {
		//init
		SongHistoryItemDTO mockedDTO = mockSongItem("David guetta", "She Wolf (Falling To Pieces)", Calendar.getInstance());
		
		//process
		String fileName = repository.getImageFileNameOfSong(mockedDTO);
		
		//assert
		verify(mockedDTO).getArtist();
		verify(mockedDTO).getTitle();
		assertNotNull(fileName);
	}
	
}
