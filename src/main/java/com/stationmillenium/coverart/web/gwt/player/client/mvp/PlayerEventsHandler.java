/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.mvp;

import java.util.logging.Logger;

import com.stationmillenium.coverart.web.gwt.player.client.activities.PlayerActivity;
import com.stationmillenium.coverart.web.gwt.player.client.activities.SmallPlayerActivity;
import com.stationmillenium.coverart.web.gwt.player.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.player.client.events.AddTrackerEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.AddTrackerEvent.AddTrackerEventHandler;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdateHistoryListEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdateHistoryListEvent.UpdateHistoryListEventHandler;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdatePlayerEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdatePlayerEvent.UpdatePlayerEventHandler;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdateSmallPlayerEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdateSmallPlayerEvent.UpdateSmallPlayerEventHandler;

/**
 * Events handler for player GWT module
 * @author vincent
 *
 */
public class PlayerEventsHandler implements UpdatePlayerEventHandler, UpdateHistoryListEventHandler, AddTrackerEventHandler, UpdateSmallPlayerEventHandler {

	//log
	private static final Logger LOGGER = Logger.getLogger(PlayerEventsHandler.class.getName());
	
	//presenter
	private PlayerActivity playerPresenter;
	private SmallPlayerActivity smallPlayerPresenter;
	
	/**
	 * Create the player events handler
	 * @param clientFactory client factory to register event
	 */
	public PlayerEventsHandler(ClientFactory clientFactory) {
		//initialization
		playerPresenter = clientFactory.getPlayerActivity();
		smallPlayerPresenter = clientFactory.getSmallPlayerActivity();
		
		//register event
		clientFactory.getEventBus().addHandler(UpdatePlayerEvent.TYPE, this);
		clientFactory.getEventBus().addHandler(UpdateHistoryListEvent.TYPE, this);
		clientFactory.getEventBus().addHandler(AddTrackerEvent.TYPE, this);
		clientFactory.getEventBus().addHandler(UpdateSmallPlayerEvent.TYPE, this);
	}
	
	@Override
	public void onUpdatePlayer(UpdatePlayerEvent event) {
		LOGGER.fine("Update player event received");
		playerPresenter.updatePlayer();
	}

	@Override
	public void onUpdateHistoryList(UpdateHistoryListEvent event) {
		LOGGER.fine("Update history list event received");
		playerPresenter.updateHistoryList(event.isDisplayLastSong());
	}
	
	@Override
	public void onAddTimer(AddTrackerEvent event) {
		LOGGER.fine("Add tracker event received");
		playerPresenter.addTrackerCode();
	}
	
	@Override
	public void onUpdateSmallPlayer(UpdateSmallPlayerEvent event) {
		LOGGER.fine("Update small player event received");
		smallPlayerPresenter.updateSmallPlayer();
	}
	
}
