/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.stationmillenium.coverart.web.gwt.modules.player.client.activities.PlayerActivity;
import com.stationmillenium.coverart.web.gwt.modules.player.client.clientfactory.ClientFactory;

/**
 * Test case for player
 * @author vincent
 *
 */
public class TestPlayerGWT extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "com.stationmillenium.coverart.web.gwt.player.Player";
	}

	/**
	 * Test player update
	 */
	public void testUpdatePlayer() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		new PlayerActivity(clientFactory).updatePlayer();
	}
	
	/**
	 * Test player history update
	 */
	public void testUpdateHistoryList() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		PlayerActivity playerActivity = new PlayerActivity(clientFactory);
		playerActivity.updateHistoryList(true);
		playerActivity.updateHistoryList(false);
	}
	
	/**
	 * Test player update
	 */
	public void testStreamURLs() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		String[] urls = new PlayerActivity(clientFactory).getStreamURLs();
		assertTrue(urls.length != 0);
	}
}
