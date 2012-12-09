/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.modules.player.client.server;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.stationmillenium.coverart.web.gwt.modules.player.shared.SongGWTDTO;

/**
 * @author vincent
 *
 */
public interface PlayerServiceAsync {

	/**
	 * Get the last played song
	 * @return the {@link SongGWTDTO} last played
	 */
	void getLastSong(AsyncCallback<SongGWTDTO> callback);

	/**
	 * Get the last 5 previous song
	 * @param displayLastSong display the last song
	 * @return list of last 5 {@link SongGWTDTO} played
	 */
	public void getLast5PreviousSongs(boolean displayLastSong, AsyncCallback<List<SongGWTDTO>> callback);
	
}
