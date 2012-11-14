package com.stationmillenium.coverart.web.gwt.history.client.clientfactory;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.stationmillenium.coverart.web.gwt.history.client.activities.HistoryActivity;
import com.stationmillenium.coverart.web.gwt.history.client.resources.HistoryConstants;
import com.stationmillenium.coverart.web.gwt.history.client.resources.HistoryResources;
import com.stationmillenium.coverart.web.gwt.history.client.server.HistoryServiceAsync;
import com.stationmillenium.coverart.web.gwt.history.client.view.HistoryViewInterface;

/**
 * Client factory interface
 * @author vincent
 *
 */
public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();
	HistoryConstants getConstants();
	HistoryServiceAsync getService();
	HistoryViewInterface getHistoryView();
	HistoryResources getResources();
	HistoryActivity getHistoryActivity();
}
