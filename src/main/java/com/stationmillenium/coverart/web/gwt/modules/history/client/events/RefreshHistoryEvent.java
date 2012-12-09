/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.modules.history.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.stationmillenium.coverart.web.gwt.modules.history.client.events.RefreshHistoryEvent.RefreshHistoryEventHandler;

/**
 * Event to fire to refresh history
 * @author vincent
 *
 */
public class RefreshHistoryEvent extends GwtEvent<RefreshHistoryEventHandler> {
	
	/**
	 * Event type 
	 */
	public static final Type<RefreshHistoryEventHandler> TYPE = new Type<RefreshHistoryEventHandler>();

	/**
	 * Event {@link RefreshHistoryEvent} handler 
	 * @author vincent
	 *
	 */
	public static interface RefreshHistoryEventHandler extends EventHandler {
		public void onRefreshHistory(RefreshHistoryEvent event);
	}
	
	/**
	 * Event handler dispatcher
	 * @param handler the handler
	 */
	@Override
	protected void dispatch(RefreshHistoryEventHandler handler) {
		handler.onRefreshHistory(this);
	}

	/**
	 * Get event type
	 * @return the {@link Type}
	 */
	@Override
	public Type<RefreshHistoryEventHandler> getAssociatedType() {
		return TYPE;
	}
}
