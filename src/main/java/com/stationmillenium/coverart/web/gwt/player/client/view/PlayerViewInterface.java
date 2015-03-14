/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.view;

import java.util.List;

import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Main player view interface
 * @author vincent
 *
 */
public interface PlayerViewInterface extends IsWidget {
	
	/**
	 * Set the current song
	 * @param currentSong current song as string
	 */
	public void setCurrentSong(String currentSong);
	
	/**
	 * Set the song image
	 * @param uri the URI of song image as {@link SafeUri}
	 * @param width the width of image
	 * @param height the height of image
	 */
	public void setImage(SafeUri uri, String width, String height);
	
	/**
	 * Set the song history list for display
	 * @param historyList the history list
	 */
	public void setSongHistoryList(List<String> historyList);
	
	/**
	 * Insert the player
	 */
	public void insertPlayer();
	
	/**
	 * Player view presenter interface
	 * @author vincent
	 *
	 */
	public static interface PlayerViewPresenter {
		
		/**
		 * Update the player
		 */
		public void updatePlayer();
		
		/**
		 * Update history list
		 * @param displayLastSong if need to display last played song
		 */
		public void updateHistoryList(boolean displayLastSong);
			
		/**
		 * Add the tracker code
		 */
		public void addTrackerCode();
		
		/**
		 * Insert the JS player 
		 * 
		 */
		public void insertPlayer();
				
	}
	
}
