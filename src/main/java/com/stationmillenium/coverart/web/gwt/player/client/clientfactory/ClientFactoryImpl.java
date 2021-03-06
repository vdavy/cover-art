/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.clientfactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.stationmillenium.coverart.web.gwt.player.client.activities.PlayerActivity;
import com.stationmillenium.coverart.web.gwt.player.client.activities.SmallPlayerActivity;
import com.stationmillenium.coverart.web.gwt.player.client.resources.PlayerConstants;
import com.stationmillenium.coverart.web.gwt.player.client.resources.PlayerMessages;
import com.stationmillenium.coverart.web.gwt.player.client.resources.PlayerResources;
import com.stationmillenium.coverart.web.gwt.player.client.server.PlayerService;
import com.stationmillenium.coverart.web.gwt.player.client.server.PlayerServiceAsync;
import com.stationmillenium.coverart.web.gwt.player.client.view.PlayerViewInterface;
import com.stationmillenium.coverart.web.gwt.player.client.view.SmallPlayerViewInterface;
import com.stationmillenium.coverart.web.gwt.player.client.view.impl.PlayerViewImpl;
import com.stationmillenium.coverart.web.gwt.player.client.view.impl.SmallPlayerViewImpl;

/**
 * Client factory of player GWT module
 * @author vincent
 *
 */
public class ClientFactoryImpl implements ClientFactory {

	private EventBus eventBus = new SimpleEventBus();
	private PlaceController placeController = new PlaceController(eventBus);
	private PlayerConstants constants = GWT.create(PlayerConstants.class);
	private PlayerMessages messages = GWT.create(PlayerMessages.class);
	private PlayerServiceAsync playerService = GWT.create(PlayerService.class);	
	private PlayerResources resources = GWT.create(PlayerResources.class);
	private PlayerActivity playerActivity = new PlayerActivity(this);
	private PlayerViewInterface playerView = null;
	private SmallPlayerActivity smallPlayerActivity = new SmallPlayerActivity(this); 
	private SmallPlayerViewInterface smallPlayerView = new SmallPlayerViewImpl(this);
	
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public PlayerConstants getConstants() {
		return constants;
	}

	@Override
	public PlayerMessages getMessages() {
		return messages;
	}
	
	@Override
	public PlayerServiceAsync getService() {
		return playerService;
	}
	
	@Override
	public PlayerViewInterface getPlayerView() {
		if (playerView == null) //init player view only on demand
			playerView = new PlayerViewImpl(this);
		
		return playerView;
	}
	
	@Override
	public PlayerResources getResources() {
		return resources;
	}
	
	@Override
	public PlayerActivity getPlayerActivity() {
		return playerActivity;
	}
	
	@Override
	public SmallPlayerViewInterface getSmallPlayer() {
		return smallPlayerView;
	}
	
	@Override
	public SmallPlayerActivity getSmallPlayerActivity() {
		return smallPlayerActivity;
	}
	
}
