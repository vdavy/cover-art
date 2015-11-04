/**
 * 
 */
package com.stationmillenium.coverart.tests;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.beans.covergraber.LastFMCoverServicePropertiesBean;
import com.stationmillenium.coverart.beans.history.IcecastServerPropertiesBean;
import com.stationmillenium.coverart.beans.history.SongHistoryFilterPropertiesBean;
import com.stationmillenium.coverart.beans.utils.GeneralPropertiesBean;

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
	private IcecastServerPropertiesBean shoutcastPropertiesBean;
	
	//the configuration for the song history filter
	@Autowired
	private SongHistoryFilterPropertiesBean songHistoryPropertiesBean;
	
	//the configuration for the last fm cover service
	@Autowired
	private LastFMCoverServicePropertiesBean lastFMCoverServicePropertiesBean;
	
	//the configuration for the deezer cover service
	@Autowired
	private LastFMCoverServicePropertiesBean deezerCoverServicePropertiesBean;
		
	//the general configuration
	@Autowired
	private GeneralPropertiesBean generalPropertiesBean;
	
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
	
	/**
	 * Test Deezer cover service properties
	 */
	@Test
	public void testDeezerCoverServiceProperties() {
		logger.debug(deezerCoverServicePropertiesBean.toString());
	}
	
	/**
	 * Test general properties
	 */
	@Test
	public void testGeneralPropertiesBean() {
		logger.debug(generalPropertiesBean.toString());
	}
}
