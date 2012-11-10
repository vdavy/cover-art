/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.history.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.stationmillenium.coverart.web.gwt.history.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.history.client.events.RefreshHistoryEvent;
import com.stationmillenium.coverart.web.gwt.history.client.mvp.HistoryActivityMapper;
import com.stationmillenium.coverart.web.gwt.history.client.mvp.HistoryEventsHandler;
import com.stationmillenium.coverart.web.gwt.history.client.mvp.HistoryPlaceHistoryMapper;
import com.stationmillenium.coverart.web.gwt.history.client.places.HistoryPlace;

/**
 * Player GWT module entry point
 * @author vincent
 *
 */
public class History implements EntryPoint {

	private Place defaultPlace;
	ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private SimplePanel appWidget = new SimplePanel();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	@Override
	public void onModuleLoad() {
		//initialization
		defaultPlace = new HistoryPlace();
		EventBus eventBus = clientFactory.getEventBus();
        PlaceController placeController = clientFactory.getPlaceController();

	    // Start ActivityManager for the main widget with our ActivityMapper
	    ActivityMapper activityMapper = new HistoryActivityMapper(clientFactory);
	    ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
	    activityManager.setDisplay(appWidget);

	    // Start PlaceHistoryHandler with our PlaceHistoryMapper
	    HistoryPlaceHistoryMapper historyMapper = GWT.create(HistoryPlaceHistoryMapper.class);
	    PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
	    historyHandler.register(placeController, eventBus, defaultPlace);

	    // Start the events handler
	    new HistoryEventsHandler(clientFactory);

	    // build UI
	    RootPanel.get().add(appWidget);
	   
	   	// Goes to the place represented on URL else default place
	    historyHandler.handleCurrentHistory();
	    
	    //timer for player update
	    eventBus.fireEvent(new RefreshHistoryEvent());
	}

}
