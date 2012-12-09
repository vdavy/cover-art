/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.modules.history.client.server;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.stationmillenium.coverart.web.gwt.modules.history.shared.HistoryGWTDTO;

/**
 * Service for the player GWT module
 * @author vincent
 *
 */
@RemoteServiceRelativePath("service")
public interface HistoryService extends RemoteService {

	/**
	 * Get the song history
	 */
	public List<HistoryGWTDTO> getSongHistory();

	/**
	 * Get the search suggestions 
	 * @param query the query
	 * @param limit the limit of result
	 * @return the list of suggestions
	 */
	public List<HistoryGWTDTO> getSearchSuggestions(String query, int limit);
	
	/**
	 * Search query in all fields (artist & title)
	 * @param query the query to search
	 * @return the list of found {@link HistoryGWTDTO}
	 */
	public List<HistoryGWTDTO> searchAll(String query);
	
	/**
	 * Search query in artist field
	 * @param query the query to search
	 * @return the list of found {@link HistoryGWTDTO}
	 */
	public List<HistoryGWTDTO> searchByArtist(String query);
	
	/**
	 * Search query in title field
	 * @param query the query to search
	 * @return the list of found {@link HistoryGWTDTO}
	 */
	public List<HistoryGWTDTO> searchByTitle(String query);
	
	/**
	 * Search by date
	 * @param searchDate the search date
	 * @return the list of found {@link HistoryGWTDTO}
	 */
	public List<HistoryGWTDTO> searchByDate(Date searchDate);
	
}
