/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.view;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Main player view interface
 * @author vincent
 *
 */
public interface SmallPlayerViewInterface extends IsWidget {
	
	/**
	 * Set the current song
	 * @param currentSong current song as string
	 */
	public void setCurrentSong(String currentSong);
			
	/**
	 * Set the presenter
	 * @param presenter the presenter
	 */
	void sePresenter(SmallPlayerViewPresenter presenter);
	
	/**
	 * Player view presenter interface
	 * @author vincent
	 *
	 */
	public static interface SmallPlayerViewPresenter {
		
		/**
		 * Open the main player
		 */
		public void openMainPlayer();
			
		/**
		 * Update the small player
		 */
		void updateSmallPlayer();

	}
	
}
