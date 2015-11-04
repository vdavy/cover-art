/**
 * 
 */
package com.stationmillenium.coverart.services.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.beans.history.SongHistoryFilterPropertiesBean;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.SongItemRepository;

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
	
	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(SongHistoryFilter.class);

	//configuration bean
	@Autowired
	private SongHistoryFilterPropertiesBean config;
	
	//song history repository
	@Autowired
	private SongItemRepository songHistoryRepository;
	
	/**
	 * Check if a song does not contains forbidden keywords
	 * @param song the song as {@link SongHistoryItemDTO}
	 * @return <code>true</code> if song is OK, <code>false</code> if not
	 */
	public boolean checkNoForbiddenKeywords(SongHistoryItemDTO song) {
		boolean canBeAdded = true; //we suppose item can be added
		for (String keyword : config.getForbiddenKeywords()) { //for each forbidden kewyord
			if ((song.getTitle().equals("")) //if title empty
					|| (song.getArtist().equals("")) //if artist empty
					|| (song.getTitle().toLowerCase().contains(keyword.toLowerCase())) //if title contains forbidden keyword
					|| (song.getArtist().toLowerCase().contains(keyword.toLowerCase()))) { //or artist name
				canBeAdded = false; //rejected
				LOGGER.debug("Song rejected due to forbidden keywords : " + song);
				break;
			}
		}
		return canBeAdded;
	}
	
}
