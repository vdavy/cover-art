/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.stationmillenium.coverart.web.gwt.player.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.player.client.events.AddTrackerEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.InitPlayerEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdatePlayerEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdateSmallPlayerEvent;
import com.stationmillenium.coverart.web.gwt.player.client.mvp.PlayerActivityMapper;
import com.stationmillenium.coverart.web.gwt.player.client.mvp.PlayerEventsHandler;
import com.stationmillenium.coverart.web.gwt.player.client.mvp.PlayerPlaceHistoryMapper;
import com.stationmillenium.coverart.web.gwt.player.client.places.PlayerPlace;

/**
 * Player GWT module entry point
 * @author vincent
 *
 */
public class Player implements EntryPoint {

	private Place defaultPlace;
	ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private SimplePanel appWidget = new SimplePanel();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	@Override
	public void onModuleLoad() {
		//initialization
		defaultPlace = new PlayerPlace();
		EventBus eventBus = clientFactory.getEventBus();
        PlaceController placeController = clientFactory.getPlaceController();

	    // Start ActivityManager for the main widget with our ActivityMapper
	    ActivityMapper activityMapper = new PlayerActivityMapper(clientFactory);
	    ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
	    activityManager.setDisplay(appWidget);

	    // Start PlaceHistoryHandler with our PlaceHistoryMapper
	    PlayerPlaceHistoryMapper historyMapper = GWT.create(PlayerPlaceHistoryMapper.class);
	    PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
	    historyHandler.register(placeController, eventBus, defaultPlace);

	    // Start the events handler
	    new PlayerEventsHandler(clientFactory);

	    // build UI
	    RootPanel.get().add(appWidget);
	   
	   	// Goes to the place represented on URL else default place
	    historyHandler.handleCurrentHistory();
	    
	    if (placeController.getWhere() instanceof PlayerPlace) {
	    	//timer for player update
		    setupScheduledTimer();

		    //timer for player start
		    setupStartTimer();
		    
		    //timer for tracking
		    setupTrackingTimer();
	    } else {
	    	clientFactory.getEventBus().fireEvent(new UpdateSmallPlayerEvent());			
	    	setupSmallPlayerScheduledTimer();
	    }
	    
	    //init the player
	    Scheduler.get().scheduleDeferred(new Command() {
	        public void execute() {
	        	clientFactory.getEventBus().fireEvent(new InitPlayerEvent());			
	        }
	    });
	}

	/**
	 * Set up timer for player update
	 */
	private void setupSmallPlayerScheduledTimer() {
		Timer timer = new Timer() {			
			@Override
			public void run() {
				clientFactory.getEventBus().fireEvent(new UpdateSmallPlayerEvent());				
			}
		};
		
		timer.scheduleRepeating(10000); //update each 10s
	}
	
	/**
	 * Set up timer for player update
	 */
	private void setupScheduledTimer() {
		Timer timer = new Timer() {			
			@Override
			public void run() {
				clientFactory.getEventBus().fireEvent(new UpdatePlayerEvent());				
			}
		};
		
		timer.scheduleRepeating(10000); //update each 10s
	}
	
	/**
	 * Set up timer for player start
	 */
	private void setupStartTimer() {
		Timer timer = new Timer() {			
			@Override
			public void run() {
				clientFactory.getEventBus().fireEvent(new UpdatePlayerEvent());			
			}
		};
		
		timer.schedule(2000); //first update 5s		
	}

	/**
	 * Setup timer for stracking
	 */
	private void setupTrackingTimer() {
		Timer timer = new Timer() {			
			@Override
			public void run() {
				clientFactory.getEventBus().fireEvent(new AddTrackerEvent());			
			}
		};
		
		timer.schedule(10000); 
	}
}
