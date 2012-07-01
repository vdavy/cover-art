package com.stationmillenium.coverart.tests.aspects;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Configure les classes de tests de manières automatiques
 * @author vincent
 *
 */
public aspect CoverArtTestConfigurationAspect {
	 
	declare @type: com.stationmillenium.coverart.tests..Test* : @RunWith(SpringJUnit4ClassRunner.class);
	    
	declare @type: com.stationmillenium.coverart.tests..Test* : @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
	
	declare @type: com.stationmillenium.coverart.tests..Test* : @ActiveProfiles("dev");
}
