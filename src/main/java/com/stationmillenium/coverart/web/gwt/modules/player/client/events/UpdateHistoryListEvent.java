/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.modules.player.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.stationmillenium.coverart.web.gwt.modules.player.client.events.UpdateHistoryListEvent.UpdateHistoryListEventHandler;

/**
 * Event to fire when time is up to update history list
 * @author vincent
 *
 */
public class UpdateHistoryListEvent extends GwtEvent<UpdateHistoryListEventHandler> {
	
	private boolean displayLastSong;
	
	/**
	 * Event type 
	 */
	public static final Type<UpdateHistoryListEventHandler> TYPE = new Type<UpdateHistoryListEventHandler>();

	/**
	 * Event {@link UpdateHistoryListEvent} handler 
	 * @author vincent
	 *
	 */
	public static interface UpdateHistoryListEventHandler extends EventHandler {
		public void onUpdateHistoryList(UpdateHistoryListEvent event);
	}
	
	/**
	 * Event handler dispatcher
	 * @param handler the handler
	 */
	@Override
	protected void dispatch(UpdateHistoryListEventHandler handler) {
		handler.onUpdateHistoryList(this);		
	}

	/**
	 * Get event type
	 * @return the {@link Type}
	 */
	@Override
	public Type<UpdateHistoryListEventHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * @return the displayLastSong
	 */
	public boolean isDisplayLastSong() {
		return displayLastSong;
	}

	/**
	 * @param displayLastSong the displayLastSong to set
	 */
	public void setDisplayLastSong(boolean displayLastSong) {
		this.displayLastSong = displayLastSong;
	}	
	
}
