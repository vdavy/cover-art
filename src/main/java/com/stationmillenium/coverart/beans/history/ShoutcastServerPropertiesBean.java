package com.stationmillenium.coverart.beans.history;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.stationmillenium.coverart.beans.interfaces.PropertyBeanInterface;

/**
 * Shoutcast server properties bean
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
public class ShoutcastServerPropertiesBean implements PropertyBeanInterface {

	//properties
	private String shoutcastServerStatusPage;
	private String shoutcastServerSongHistoryPage;
	private String userAgent;
	private String statusPageSelect;
	private String statusPageSelectText;
	private String songHistoryPageSelect;	
	private String songHistorySongSeparator;
	private String songHistoryDateSeparator;
	
}
