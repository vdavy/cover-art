package com.stationmillenium.coverart.web.gwt.modules.player.client.clientfactory;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.stationmillenium.coverart.web.gwt.modules.player.client.activities.PlayerActivity;
import com.stationmillenium.coverart.web.gwt.modules.player.client.resources.PlayerConstants;
import com.stationmillenium.coverart.web.gwt.modules.player.client.resources.PlayerMessages;
import com.stationmillenium.coverart.web.gwt.modules.player.client.resources.PlayerResources;
import com.stationmillenium.coverart.web.gwt.modules.player.client.server.PlayerServiceAsync;
import com.stationmillenium.coverart.web.gwt.modules.player.client.view.PlayerViewInterface;

/**
 * Client factory interface
 * @author vincent
 *
 */
public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();
	PlayerConstants getConstants();
	PlayerMessages getMessages();
	PlayerServiceAsync getService();
	PlayerViewInterface getPlayerView();
	PlayerResources getResources();
	PlayerActivity getPlayerActivity();
}
