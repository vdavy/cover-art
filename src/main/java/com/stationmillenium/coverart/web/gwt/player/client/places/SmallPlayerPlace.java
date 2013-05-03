/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Small player place of GWT player module
 * @author vincent
 *
 */
public class SmallPlayerPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<SmallPlayerPlace> {
		
		@Override
		public SmallPlayerPlace getPlace(String token) {
			return new SmallPlayerPlace();
		};
		
		@Override
		public String getToken(SmallPlayerPlace place) {			
			return null;
		}
	}
}
