/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.modules.history.client.server;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.stationmillenium.coverart.web.gwt.modules.history.shared.HistoryGWTDTO;

/**
 * @author vincent
 *
 */
public interface HistoryServiceAsync {

	/**
	 * Get the song history
	 */
	public void getSongHistory(AsyncCallback<List<HistoryGWTDTO>> callback);

	/**
	 * Get the search suggestions 
	 */
	void getSearchSuggestions(String query, int limit, AsyncCallback<List<HistoryGWTDTO>> callback);

	/**
	 * Search in all fields 
	 */
	void searchAll(String query, AsyncCallback<List<HistoryGWTDTO>> callback);

	/**
	 * Search in artist field
	 */
	void searchByArtist(String query, AsyncCallback<List<HistoryGWTDTO>> callback);

	/**
	 * Search in title field
	 */
	void searchByTitle(String query, AsyncCallback<List<HistoryGWTDTO>> callback);

	/**
	 * Search by date
	 */
	void searchByDate(Date searchDate, AsyncCallback<List<HistoryGWTDTO>> callback);
	
}
