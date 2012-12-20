/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.mvp;

import java.util.logging.Logger;

import com.stationmillenium.coverart.web.gwt.player.client.activities.PlayerActivity;
import com.stationmillenium.coverart.web.gwt.player.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.player.client.events.AddTrackerEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.AddTrackerEvent.AddTrackerEventHandler;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdateHistoryListEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdateHistoryListEvent.UpdateHistoryListEventHandler;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdatePlayerEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdatePlayerEvent.UpdatePlayerEventHandler;

/**
 * Events handler for player GWT module
 * @author vincent
 *
 */
public class PlayerEventsHandler implements UpdatePlayerEventHandler, UpdateHistoryListEventHandler, AddTrackerEventHandler {

	//log
	private static final Logger LOGGER = Logger.getLogger(PlayerEventsHandler.class.getName());
	
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
		clientFactory.getEventBus().addHandler(AddTrackerEvent.TYPE, this);
	}
	
	@Override
	public void onUpdatePlayer(UpdatePlayerEvent event) {
		LOGGER.fine("Update player event received");
		presenter.updatePlayer();
	}

	@Override
	public void onUpdateHistoryList(UpdateHistoryListEvent event) {
		LOGGER.fine("Update history list event received");
		presenter.updateHistoryList(event.isDisplayLastSong());
	}
	
	@Override
	public void onAddTimer(AddTrackerEvent event) {
		LOGGER.fine("Add tracker event received");
		presenter.addTrackerCode();
	}
}
