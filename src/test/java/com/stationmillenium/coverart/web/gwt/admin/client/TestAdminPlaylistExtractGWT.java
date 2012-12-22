/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.stationmillenium.coverart.web.gwt.admin.client.activities.PlaylistExtractActivity;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;

/**
 * Test case for admin GWT module - activity : playlist extract
 * @author vincent
 *
 */
public class TestAdminPlaylistExtractGWT extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "com.stationmillenium.coverart.web.gwt.admin.Admin";
	}

	/**
	 * Test playlist extract
	 */
	public void testPlaylistExtract() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		PlaylistExtractActivity activity =  new PlaylistExtractActivity(clientFactory);
		activity.launchPlaylistExtract(new Date(), "00", "00", new Date(), "23", "59"); 
		activity.launchPlaylistExtract(new Date(), "23", "59", new Date(), "00", "00"); 
		activity.launchPlaylistExtract(new Date(), "abc", "00", new Date(), "23", "59");
		activity.launchPlaylistExtract(new Date(), "00", "abc", new Date(), "abc", "59");
		activity.launchPlaylistExtract(new Date(), "00", "00", new Date(), "23", "abc");
	}
	
}
