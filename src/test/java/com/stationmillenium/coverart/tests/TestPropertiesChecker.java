/**
 * 
 */
package com.stationmillenium.coverart.tests;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.stationmillenium.coverart.beans.ShoutcastServerPropertiesBean;

/**
 * Test property loading and validation 
 * @author vincent
 *
 */
public class TestPropertiesChecker {

	//set up logger
	private static final Logger logger = LoggerFactory.getLogger(TestPropertiesChecker.class);
	
	//DI : the configuration for the shouctast server
	@Autowired
	private ShoutcastServerPropertiesBean shoutcastPropertiesBean;
	
	/**
	 * Test Shouctast server properties
	 */
	@Test
	public void testShoucastServerProperties() {
		logger.debug(shoutcastPropertiesBean.toString());
	}
}
