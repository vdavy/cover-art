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
 * Test 
 * @author vincent
 *
 */
public class TestPropertyChecker {

	private static final Logger logger = LoggerFactory.getLogger(TestPropertyChecker.class);
	
	@Autowired
	private ShoutcastServerPropertiesBean shoutcastPropertiesBean;
	
	@Test
	public void testPropertyChecker() {
		logger.debug(shoutcastPropertiesBean.toString());
	}
}
