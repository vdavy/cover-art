package com.stationmillenium.coverart.beans;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Shoutcast server properties bean
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
public class ShoutcastServerPropertiesBean {

	//properties
	private String shoutcastServerStatusPage;
	private String shoutcastServerSongHistoryPage;
		
}
