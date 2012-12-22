/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.stationmillenium.coverart.web.gwt.admin.client.activities.StatusReportActivity;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.places.StatusReportPlace.ReportType;

/**
 * Test case for admin GWT module - activity : Status report
 * @author vincent
 *
 */
public class TestAdminStatusReportGWT extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "com.stationmillenium.coverart.web.gwt.admin.Admin";
	}

	/**
	 * Test FM report
	 */
	public void testStatusReportFMType() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		new StatusReportActivity(clientFactory, ReportType.FM).start(clientFactory.getMainView().getContentPanel(), null);
	}
	
	/**
	 * Test playlist report
	 */
	public void testStatusReportPlaylistType() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		new StatusReportActivity(clientFactory, ReportType.PLAYLIST).start(clientFactory.getMainView().getContentPanel(), null);
	}
	
	/**
	 * Test shoutcast report
	 */
	public void testStatusReportShoutcastType() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		new StatusReportActivity(clientFactory, ReportType.SHOUTCAST).start(clientFactory.getMainView().getContentPanel(), null);
	}
	
}
