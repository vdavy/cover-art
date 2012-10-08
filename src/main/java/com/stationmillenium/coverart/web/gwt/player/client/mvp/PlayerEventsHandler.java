/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.mvp;

import com.google.gwt.core.client.GWT;
import com.stationmillenium.coverart.web.gwt.player.client.activities.PlayerActivity;
import com.stationmillenium.coverart.web.gwt.player.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdateHistoryListEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdateHistoryListEvent.UpdateHistoryListEventHandler;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdatePlayerEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdatePlayerEvent.UpdatePlayerEventHandler;

/**
 * Events handler for player GWT module
 * @author vincent
 *
 */
public class PlayerEventsHandler implements UpdatePlayerEventHandler, UpdateHistoryListEventHandler {

	//presenter
	private PlayerActivity presenter;
	
	/**
	 * Create the player events handler
	 * @param clientFactory client factory to register event
	 */
	public PlayerEventsHandler(ClientFactory clientFactory) {
		//initialization
		presenter =  clientFactory.getPlayerActivity();
		
		//register event
		clientFactory.getEventBus().addHandler(UpdatePlayerEvent.TYPE, this);
		clientFactory.getEventBus().addHandler(UpdateHistoryListEvent.TYPE, this);
	}
	
	@Override
	public void onUpdatePlayer(UpdatePlayerEvent event) {
		GWT.log("Update player event received");
		presenter.updatePlayer();
	}

	@Override
	public void onUpdateHistoryList(UpdateHistoryListEvent event) {
		GWT.log("Update history list event received");
		presenter.updateHistoryList(event.isDisplayLastSong());
	}
	
}
