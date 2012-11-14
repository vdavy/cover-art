package com.stationmillenium.coverart.web.gwt.history.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.stationmillenium.coverart.web.gwt.history.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.history.client.places.HistoryPlace;

/**
 * Player GWT module activity mapper
 * @author vincent
 *
 */
public class HistoryActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;
	
	/**
	 * Instanciate activity mapper 
	 * @param clientFactory client factory
	 */
	public HistoryActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public Activity getActivity(Place place) {
		if (place instanceof HistoryPlace) 
			return clientFactory.getHistoryActivity();
		else
			return null;
	}

}
