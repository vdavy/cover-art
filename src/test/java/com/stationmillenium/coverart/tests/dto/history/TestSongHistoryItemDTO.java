/**
 * 
 */
package com.stationmillenium.coverart.tests.dto.history;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;

/**
 * Test the {@link SongHistoryItemDTO} 
 * @author vincent
 *
 */
public class TestSongHistoryItemDTO {

	private SongHistoryItemDTO dtoToTest = new SongHistoryItemDTO();
	
	/**
	 * DTO initialization
	 */
	@Before
	public void initDTOToTest() {
		dtoToTest.setArtist("foo");
		dtoToTest.setTitle("bar");
		dtoToTest.setPlayedDate(Calendar.getInstance());
	}
	
	/**
	 * Test equals with null
	 */
	@Test
	public void testEqualsNull() {
		assertFalse(dtoToTest.equals(null));		
	}
	
	/**
	 * Test equals with other instance
	 */
	@Test
	public void testEqualsNotInstance() {
		assertFalse(dtoToTest.equals(new String()));		
	}
	
	/**
	 * Test equals with other instance
	 */
	@Test
	public void testEqualsWithEmtpyInstance() {
		assertFalse(dtoToTest.equals(new SongHistoryItemDTO()));		
	}
	
	/**
	 * Test equals with instance not equal
	 */
	@Test
	public void testEqualsWithInstanceNotEqual() {
		SongHistoryItemDTO item = new SongHistoryItemDTO();
		item.setArtist("bar");
		item.setTitle("foo");
		item.setPlayedDate(Calendar.getInstance());		
		assertFalse(dtoToTest.equals(item));		
	}
	
	/**
	 * Test equals with same instance
	 */
	@Test
	public void testEqualsWithSameInstance() {
		assertTrue(dtoToTest.equals(dtoToTest));		
	}
	
}
