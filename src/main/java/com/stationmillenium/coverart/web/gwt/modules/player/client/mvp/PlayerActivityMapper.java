package com.stationmillenium.coverart.web.gwt.modules.player.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.stationmillenium.coverart.web.gwt.modules.player.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.modules.player.client.places.PlayerPlace;

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
		else
			return null;
	}

}
