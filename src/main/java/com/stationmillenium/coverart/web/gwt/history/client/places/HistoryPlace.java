/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.history.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Main place of GWT player module
 * @author vincent
 *
 */
public class HistoryPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<HistoryPlace> {
		
		@Override
		public HistoryPlace getPlace(String token) {
			return new HistoryPlace();
		};
		
		@Override
		public String getToken(HistoryPlace place) {			
			return null;
		}
	}
}
