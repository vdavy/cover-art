/**
 * 
 */
package com.stationmillenium.coverart.tests;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.beans.covergraber.LastFMCoverServicePropertiesBean;
import com.stationmillenium.coverart.beans.history.ShoutcastServerPropertiesBean;
import com.stationmillenium.coverart.beans.history.SongHistoryFilterPropertiesBean;

/**
 * Test property loading and validation 
 * @author vincent
 *
 */
public class TestPropertiesChecker {

	//set up logger
	private static final Logger logger = LoggerFactory.getLogger(TestPropertiesChecker.class);
	
	//the configuration for the shoutast server
	@Autowired
	private ShoutcastServerPropertiesBean shoutcastPropertiesBean;
	
	//the configuration for the song history filter
	@Autowired
	private SongHistoryFilterPropertiesBean songHistoryPropertiesBean;
	
	//the configuration for the last fm cover service
	@Autowired
	private LastFMCoverServicePropertiesBean lastFMCoverServicePropertiesBean;
	
	/**
	 * Test Shouctast server properties
	 */
	@Test
	public void testShoucastServerProperties() {
		logger.debug(shoutcastPropertiesBean.toString());
	}
	
	/**
	 * Test song history filter properties
	 */
	@Test
	public void testSongHistoryFilterProperties() {
		logger.debug(songHistoryPropertiesBean.toString());
	}
	
	/**
	 * Test last FM cover service properties
	 */
	@Test
	public void testLastFMCoverServiceProperties() {
		logger.debug(lastFMCoverServicePropertiesBean.toString());
	}
}
