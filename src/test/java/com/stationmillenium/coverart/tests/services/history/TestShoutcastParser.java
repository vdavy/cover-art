/**
 * 
 */
package com.stationmillenium.coverart.tests.services.history;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.beans.history.ShoutcastServerPropertiesBean;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.services.history.ShoutcastParser;

/**
 * Test part for the Shoutcast server parser through Jsoup
 * @author vincent
 *
 */
public class TestShoutcastParser {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(TestShoutcastParser.class);
	
	//server configuration
	@Autowired
	private ShoutcastServerPropertiesBean config;
	
	//shoutcast parser
	@Autowired
	private ShoutcastParser shoutcastParser;
		
	/**
	 * Check regular status check
	 */
	@Test
	public void testRegularStatus() {
		Assert.assertTrue("Server status down - up expected", shoutcastParser.checkShoutcastStatus());
	}
	
	/**
	 * Check regular down status check
	 */
	@Test
	public void testDownStatus() {
		config.setStatusPageSelectText("table");
		Assert.assertFalse("Server status up", shoutcastParser.checkShoutcastStatus());
	}
	
	/**
	 * Check wrong path test
	 */
	@Test
	public void testWrongPathTest() {
		config.setStatusPageSelect("foo");
		Assert.assertFalse("Server status up", shoutcastParser.checkShoutcastStatus());
	}
	
	/**
	 * Check wrong path test
	 */
	@Test
	public void testServerDown() {
		config.setShoutcastServerStatusPage("http://foo");
		Assert.assertFalse("Server status up", shoutcastParser.checkShoutcastStatus());
	}
	
	/**
	 * Check song history parsing
	 */
	@Test
	public void testRegularSongHistoryParsing() {
		List<SongHistoryItemDTO> songList = shoutcastParser.getSongHistoryList();
		checkList(songList);
	}
	
	/**
	 * Test song history parsing when server seems down
	 */
	@Test
	public void testSongHistoryServerDown() {
		config.setShoutcastServerSongHistoryPage("http://foo");
		checkErrorCases();
	}
	
	/**
	 * Test song history parsing with bad select text : nothing can be found
	 */
	@Test
	public void testSongHistoryBadSelectString() {
		config.setSongHistoryPageSelect("foo");
		checkErrorCases();
	}
	
	/**
	 * Test song history parsing with bad song separator : song cannot be splitted
	 */
	@Test
	public void testSongHistoryBadSongSeparator() {
		config.setSongHistorySongSeparator("foo");
		checkErrorCases();
	}
	
	/**
	 * Test song history parsing with bad date separator : date cannot be splitted
	 */
	@Test
	public void testSongHistoryBadDateSeparator() {
		config.setSongHistoryDateSeparator("foo");
		checkErrorCases();
	}
	
	/**
	 * Do test cases for errors
	 */
	private void checkErrorCases() {
		List<SongHistoryItemDTO> songList = shoutcastParser.getSongHistoryList();
		Assert.assertNotNull(songList);
		Assert.assertTrue(songList.size() == 0);
	}
	
	/**
	 * Check the list not contains null value and songs properly ordered
	 * @param list
	 */
	private void checkList(List<SongHistoryItemDTO> list) {
		LOGGER.info("List gathered : " + list);
		LOGGER.info("List gathered size : " + list.size());
		
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
		
		Calendar previousCalendar = null;
		for (SongHistoryItemDTO item : list) {
			Assert.assertNotNull("Artist null", item.getArtist());
			Assert.assertNotNull("Title null", item.getTitle());
			Assert.assertNotNull("Date null", item.getPlayedDate());
			
			if (previousCalendar != null) {
				Assert.assertTrue(previousCalendar.after(item.getPlayedDate()));
			}
			
			previousCalendar = item.getPlayedDate();
		}
	}
}
