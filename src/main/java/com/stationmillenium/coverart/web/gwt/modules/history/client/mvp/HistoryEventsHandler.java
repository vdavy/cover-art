/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.modules.history.client.mvp;

import java.util.logging.Logger;

import com.stationmillenium.coverart.web.gwt.modules.history.client.activities.HistoryActivity;
import com.stationmillenium.coverart.web.gwt.modules.history.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.modules.history.client.events.RefreshHistoryEvent;
import com.stationmillenium.coverart.web.gwt.modules.history.client.events.RefreshHistoryEvent.RefreshHistoryEventHandler;

/**
 * Events handler for player GWT module
 * @author vincent
 *
 */
public class HistoryEventsHandler implements RefreshHistoryEventHandler {

	//log
	private static final Logger LOGGER = Logger.getLogger(HistoryEventsHandler.class.getName());
	
	//presenter
	private HistoryActivity presenter;
	
	/**
	 * Create the player events handler
	 * @param clientFactory client factory to register event
	 */
	public HistoryEventsHandler(ClientFactory clientFactory) {
		//initialization
		presenter =  clientFactory.getHistoryActivity();
		
		//register event
		clientFactory.getEventBus().addHandler(RefreshHistoryEvent.TYPE, this);
	}
	
	@Override
	public void onRefreshHistory(RefreshHistoryEvent event) {
		LOGGER.fine("Refresh history event received");
		presenter.refreshHistory();
	}
	
}
