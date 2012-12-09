/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.modules.player.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Main place of GWT player module
 * @author vincent
 *
 */
public class PlayerPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<PlayerPlace> {
		
		@Override
		public PlayerPlace getPlace(String token) {
			return new PlayerPlace();
		};
		
		@Override
		public String getToken(PlayerPlace place) {			
			return null;
		}
	}
}
