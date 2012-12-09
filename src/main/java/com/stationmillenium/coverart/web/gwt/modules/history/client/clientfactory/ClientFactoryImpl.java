/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.modules.history.client.clientfactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.stationmillenium.coverart.web.gwt.modules.history.client.activities.HistoryActivity;
import com.stationmillenium.coverart.web.gwt.modules.history.client.resources.HistoryConstants;
import com.stationmillenium.coverart.web.gwt.modules.history.client.resources.HistoryResources;
import com.stationmillenium.coverart.web.gwt.modules.history.client.server.HistoryService;
import com.stationmillenium.coverart.web.gwt.modules.history.client.server.HistoryServiceAsync;
import com.stationmillenium.coverart.web.gwt.modules.history.client.view.HistoryViewInterface;
import com.stationmillenium.coverart.web.gwt.modules.history.client.view.impl.HistoryViewImpl;

/**
 * Client factory of player GWT module
 * @author vincent
 *
 */
public class ClientFactoryImpl implements ClientFactory {

	private EventBus eventBus = new SimpleEventBus();
	private PlaceController placeController = new PlaceController(eventBus);
	private HistoryConstants constants = GWT.create(HistoryConstants.class);
	private HistoryServiceAsync historyService = GWT.create(HistoryService.class);
	private HistoryViewInterface historyView = new HistoryViewImpl(this);
	private HistoryResources resources = GWT.create(HistoryResources.class);
	private HistoryActivity activity = new HistoryActivity(this);
	
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public HistoryConstants getConstants() {
		return constants;
	}
	
	@Override
	public HistoryServiceAsync getService() {
		return historyService;
	}
	
	@Override
	public HistoryViewInterface getHistoryView() {
		return historyView;
	}
	
	@Override
	public HistoryResources getResources() {
		return resources;
	}
	
	@Override
	public HistoryActivity getHistoryActivity() {
		return activity;
	}
	
}
