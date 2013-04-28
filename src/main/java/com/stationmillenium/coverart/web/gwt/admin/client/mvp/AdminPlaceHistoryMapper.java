/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.stationmillenium.coverart.web.gwt.admin.client.places.ConfigureAlertsPlace;
import com.stationmillenium.coverart.web.gwt.admin.client.places.ConfigureCustomImagesPlace;
import com.stationmillenium.coverart.web.gwt.admin.client.places.GeneralConfigPlace;
import com.stationmillenium.coverart.web.gwt.admin.client.places.PlaylistExtractPlace;
import com.stationmillenium.coverart.web.gwt.admin.client.places.StatusReportPlace;

/**
 * Admin GWT module place history mapper
 * @author vincent
 *
 */
@WithTokenizers({
	ConfigureAlertsPlace.Tokenizer.class,
	PlaylistExtractPlace.Tokenizer.class,
	StatusReportPlace.Tokenizer.class,
	GeneralConfigPlace.Tokenizer.class,
	ConfigureCustomImagesPlace.Tokenizer.class
})
public interface AdminPlaceHistoryMapper extends PlaceHistoryMapper {
	//nothing to add
}
