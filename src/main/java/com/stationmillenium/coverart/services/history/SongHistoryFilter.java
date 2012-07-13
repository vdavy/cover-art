/**
 * 
 */
package com.stationmillenium.coverart.services.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.beans.history.SongHistoryFilterPropertiesBean;

/**
 * Service to analyse the song history and to filter some song to exclude :
 * Filters based on :
 * -keywords in artist name or song title
 * -duration below a value (to remove song samples) 
 * @author vincent
 *
 */
@Service
public class SongHistoryFilter {

	//configuration bean
	@Autowired
	private SongHistoryFilterPropertiesBean config;
	
	public void toto() {
		config.toString();
	}
	
}
