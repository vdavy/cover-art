package com.stationmillenium.coverart.web.gwt.history.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.stationmillenium.coverart.web.gwt.history.client.places.HistoryPlace;

/**
 * Player GWT module place history mapper
 * @author vincent
 *
 */
@WithTokenizers({
	HistoryPlace.Tokenizer.class
})
public interface HistoryPlaceHistoryMapper extends PlaceHistoryMapper {
	//nothing to add
}
