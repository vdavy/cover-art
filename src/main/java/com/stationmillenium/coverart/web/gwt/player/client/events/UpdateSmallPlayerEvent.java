/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdateSmallPlayerEvent.UpdateSmallPlayerEventHandler;

/**
 * Event to fire when time is up to update the small player
 * @author vincent
 *
 */
public class UpdateSmallPlayerEvent extends GwtEvent<UpdateSmallPlayerEventHandler> {
	
	/**
	 * Event type 
	 */
	public static final Type<UpdateSmallPlayerEventHandler> TYPE = new Type<UpdateSmallPlayerEventHandler>();

	/**
	 * Event {@link UpdateSmallPlayerEvent} handler 
	 * @author vincent
	 *
	 */
	public static interface UpdateSmallPlayerEventHandler extends EventHandler {
		public void onUpdateSmallPlayer(UpdateSmallPlayerEvent event);
	}
	
	/**
	 * Event handler dispatcher
	 * @param handler the handler
	 */
	@Override
	protected void dispatch(UpdateSmallPlayerEventHandler handler) {
		handler.onUpdateSmallPlayer(this);
	}

	/**
	 * Get event type
	 * @return the {@link Type}
	 */
	@Override
	public Type<UpdateSmallPlayerEventHandler> getAssociatedType() {
		return TYPE;
	}
}
