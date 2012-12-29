/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Place to general configuration
 * @author vincent
 *
 */
public class GeneralConfigPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<GeneralConfigPlace> {

		@Override
		public GeneralConfigPlace getPlace(String token) {
			return new GeneralConfigPlace();
		}

		@Override
		public String getToken(GeneralConfigPlace place) {
			return null;
		}
		
	}
	
}
