/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.stationmillenium.coverart.web.gwt.admin.client.activities.ConfigureAlertsActivity;
import com.stationmillenium.coverart.web.gwt.admin.client.activities.GeneralConfigActivity;
import com.stationmillenium.coverart.web.gwt.admin.client.activities.PlaylistExtractActivity;
import com.stationmillenium.coverart.web.gwt.admin.client.activities.StatusReportActivity;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.places.ConfigureAlertsPlace;
import com.stationmillenium.coverart.web.gwt.admin.client.places.GeneralConfigPlace;
import com.stationmillenium.coverart.web.gwt.admin.client.places.PlaylistExtractPlace;
import com.stationmillenium.coverart.web.gwt.admin.client.places.StatusReportPlace;

/**
 * Activity mapper for GWT admin module
 * @author vincent
 *
 */
public class AdminActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;
	
	/**
	 * Create a new {@link AdminActivityMapper}
	 * @param clientFactory
	 */
	public AdminActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;		
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.activity.shared.ActivityMapper#getActivity(com.google.gwt.place.shared.Place)
	 */
	@Override
	public Activity getActivity(Place place) {
		if (place instanceof ConfigureAlertsPlace)
			return new ConfigureAlertsActivity(clientFactory);
		else if (place instanceof GeneralConfigPlace)
			return new GeneralConfigActivity(clientFactory);
		else if (place instanceof PlaylistExtractPlace)
			return new PlaylistExtractActivity(clientFactory);
		else if (place instanceof StatusReportPlace)
			return new StatusReportActivity(clientFactory, ((StatusReportPlace) place).getReportType());
		else
			return null;
			
	}

}
