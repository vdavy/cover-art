/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.server;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.stationmillenium.coverart.web.gwt.player.shared.SongGWTDTO;

/**
 * Service for the player GWT module
 * @author vincent
 *
 */
@RemoteServiceRelativePath("service")
public interface PlayerService extends RemoteService {

	/**
	 * Get the last played song
	 * @return the {@link SongGWTDTO} last played
	 */
	public SongGWTDTO getLastSong();
	
	/**
	 * Get the last 5 previous song
	 * @param displayLastSong display the last song
	 * @return list of last 5 {@link SongGWTDTO} played
	 */
	public List<SongGWTDTO> getLast5PreviousSongs(boolean displayLastSong);
	
}
