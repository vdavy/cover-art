/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Place to configure custom images
 * @author vincent
 *
 */
public class ConfigureCustomImagesPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<ConfigureCustomImagesPlace> {

		@Override
		public ConfigureCustomImagesPlace getPlace(String token) {
			return new ConfigureCustomImagesPlace();
		}

		@Override
		public String getToken(ConfigureCustomImagesPlace place) {
			return null;
		}
		
	}
	
}
