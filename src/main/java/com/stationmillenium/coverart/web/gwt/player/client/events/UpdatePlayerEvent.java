/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdatePlayerEvent.UpdatePlayerEventHandler;

/**
 * Event to fire when time is up to update player
 * @author vincent
 *
 */
public class UpdatePlayerEvent extends GwtEvent<UpdatePlayerEventHandler> {
	
	/**
	 * Event type 
	 */
	public static final Type<UpdatePlayerEventHandler> TYPE = new Type<UpdatePlayerEventHandler>();

	/**
	 * Event {@link UpdatePlayerEvent} handler 
	 * @author vincent
	 *
	 */
	public static interface UpdatePlayerEventHandler extends EventHandler {
		public void onUpdatePlayer(UpdatePlayerEvent event);
	}
	
	/**
	 * Event handler dispatcher
	 * @param handler the handler
	 */
	@Override
	protected void dispatch(UpdatePlayerEventHandler handler) {
		handler.onUpdatePlayer(this);
	}

	/**
	 * Get event type
	 * @return the {@link Type}
	 */
	@Override
	public Type<UpdatePlayerEventHandler> getAssociatedType() {
		return TYPE;
	}
}
