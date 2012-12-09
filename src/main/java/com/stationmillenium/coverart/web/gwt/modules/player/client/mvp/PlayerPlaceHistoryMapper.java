package com.stationmillenium.coverart.web.gwt.modules.player.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.stationmillenium.coverart.web.gwt.modules.player.client.places.PlayerPlace;

/**
 * Player GWT module place history mapper
 * @author vincent
 *
 */
@WithTokenizers({
	PlayerPlace.Tokenizer.class
})
public interface PlayerPlaceHistoryMapper extends PlaceHistoryMapper {
	//nothing to add
}
