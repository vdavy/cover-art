package com.stationmillenium.coverart.web.gwt.player.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.stationmillenium.coverart.web.gwt.player.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.player.client.places.PlayerPlace;
import com.stationmillenium.coverart.web.gwt.player.client.places.SmallPlayerPlace;

/**
 * Player GWT module activity mapper
 * @author vincent
 *
 */
public class PlayerActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;
	
	/**
	 * Instanciate activity mapper 
	 * @param clientFactory client factory
	 */
	public PlayerActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public Activity getActivity(Place place) {
		if (place instanceof PlayerPlace) 
			return clientFactory.getPlayerActivity();
		else if (place instanceof SmallPlayerPlace)
			return clientFactory.getSmallPlayerActivity();
		else
			return null;
	}

}
