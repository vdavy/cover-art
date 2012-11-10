/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.history.client.view;

import java.util.Date;
import java.util.List;

import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.stationmillenium.coverart.web.gwt.history.shared.HistoryGWTDTO;

/**
 * Main player view interface
 * @author vincent
 *
 */
public interface HistoryViewInterface extends IsWidget {
	
	/**
	 * Set the presenter
	 * @param presenter the presenter
	 */
	public void setPresenter(HistoryViewPresenter presenter);
	
	/**
	 * Set the song history
	 * @param songHistory the list of song history
	 */
	public void setSongHistoryList(List<HistoryGWTDTO> songHistory);

	/**
	 * Set the song image
	 * @param uri the uri of image
	 * @param width the width
	 * @param height the height
	 */
	public void setImage(SafeUri uri, String width, String height);
	/**
	 * History view presenter interface
	 * @author vincent
	 *
	 */
	public static interface HistoryViewPresenter {
		
		/**
		 * Available search types
		 * @author vincent
		 *
		 */
		public enum SearchFieldType {
			ALL,
			ARTIST,
			TITLE;
		}
		
		/**
		 * Format a date
		 * @param dateToFormat the date to format
		 * @return le formatted date
		 */
		public String formatDate(Date dateToFormat);
		
		/**
		 * Refresh the history
		 */
		public void refreshHistory();
		
		/**
		 * Get the suggestions for the search
		 * @param request the oracle request
		 * @param callback the oracle callback
		 */
		public void getSearchSuggestions(Request request, Callback callback);
		
		/**
		 * Launch search from field 
		 * @param query the query 
		 * @param searchType the search type
		 */
		public void launchSearchFields(String query, SearchFieldType searchType);
		
		/**
		 * Launch search by date
		 * @param date the date 
		 * @param hours the hours
		 * @param minutes the minutes
		 */
		public void launchSearchDates(Date date, String hours, String minutes);
		
		/**
		 * Deal with song selected
		 * @param selectedSong the selected song
		 */
		public void songSelected(HistoryGWTDTO selectedSong);
	}
	
}
