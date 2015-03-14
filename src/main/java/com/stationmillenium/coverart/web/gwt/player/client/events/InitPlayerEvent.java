/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.stationmillenium.coverart.web.gwt.player.client.events.InitPlayerEvent.InitPlayerEventHandler;

/**
 * Event to fire when init player
 * @author vincent
 *
 */
public class InitPlayerEvent extends GwtEvent<InitPlayerEventHandler> {
	
	/**
	 * Event type 
	 */
	public static final Type<InitPlayerEventHandler> TYPE = new Type<InitPlayerEventHandler>();

	/**
	 * Event {@link InitPlayerEvent} handler 
	 * @author vincent
	 *
	 */
	public static interface InitPlayerEventHandler extends EventHandler {
		public void initPlayer();
	}
	
	/**
	 * Event handler dispatcher
	 * @param handler the handler
	 */
	@Override
	protected void dispatch(InitPlayerEventHandler handler) {
		handler.initPlayer();
	}

	/**
	 * Get event type
	 * @return the {@link Type}
	 */
	@Override
	public Type<InitPlayerEventHandler> getAssociatedType() {
		return TYPE;
	}
}
