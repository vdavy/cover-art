/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Place to configure alert
 * @author vincent
 *
 */
public class ConfigureAlertsPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<ConfigureAlertsPlace> {

		@Override
		public ConfigureAlertsPlace getPlace(String token) {
			return new ConfigureAlertsPlace();
		}

		@Override
		public String getToken(ConfigureAlertsPlace place) {
			return null;
		}
		
	}
	
}
