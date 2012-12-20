/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.AddTrackerEvent.AddTrackerEventHandler;

/**
 * Event to fire when adding trakcer
 * @author vincent
 *
 */
public class AddTrackerEvent extends GwtEvent<AddTrackerEventHandler> {
	
	/**
	 * Event type 
	 */
	public static final Type<AddTrackerEventHandler> TYPE = new Type<AddTrackerEventHandler>();

	/**
	 * Event {@link AddTrackerEvent} handler 
	 * @author vincent
	 *
	 */
	public static interface AddTrackerEventHandler extends EventHandler {
		public void onAddTimer(AddTrackerEvent event);
	}
	
	/**
	 * Event handler dispatcher
	 * @param handler the handler
	 */
	@Override
	protected void dispatch(AddTrackerEventHandler handler) {
		handler.onAddTimer(this);
	}

	/**
	 * Get event type
	 * @return the {@link Type}
	 */
	@Override
	public Type<AddTrackerEventHandler> getAssociatedType() {
		return TYPE;
	}
}
