/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Place to extract data from playlist
 * @author vincent
 *
 */
public class PlaylistExtractPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<PlaylistExtractPlace> {

		@Override
		public PlaylistExtractPlace getPlace(String token) {
			return new PlaylistExtractPlace();
		}

		@Override
		public String getToken(PlaylistExtractPlace place) {
			return null;
		}
		
	}
	
}
