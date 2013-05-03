package com.stationmillenium.coverart.web.gwt.player.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.stationmillenium.coverart.web.gwt.player.client.places.PlayerPlace;
import com.stationmillenium.coverart.web.gwt.player.client.places.SmallPlayerPlace;

/**
 * Player GWT module place history mapper
 * @author vincent
 *
 */
@WithTokenizers({
	PlayerPlace.Tokenizer.class,
	SmallPlayerPlace.Tokenizer.class
})
public interface PlayerPlaceHistoryMapper extends PlaceHistoryMapper {
	//nothing to add
}
