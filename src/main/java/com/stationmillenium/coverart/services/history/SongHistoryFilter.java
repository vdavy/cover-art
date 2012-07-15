/**
 * 
 */
package com.stationmillenium.coverart.services.history;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.beans.history.SongHistoryFilterPropertiesBean;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;

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
	
	/**
	 * Filter songs among history :
	 * -remove these with forbidden keywords in the title or artist name
	 * -songs with minimal length not permitted
	 * @param songHistoryList the list to filter
	 * @param lastSong the last recorded song to compare minimal length
	 * @return the filtered list - never null
	 */
	public List<SongHistoryItemDTO> filterSongHistory(List<SongHistoryItemDTO> songHistoryList, SongHistoryItemDTO lastSong) {
		List<SongHistoryItemDTO> filteredSongHistoryList = new ArrayList<>();
		
		//for each item of the list		
		for (int i = 0 ; i < songHistoryList.size(); i++) {
			boolean canBeAdded = true; //we suppose item can be added
			for (String keyword : config.getForbiddenKeywords()) { //for each forbidden kewyord
				if ((songHistoryList.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase())) //if title contains forbidden keyword
						|| songHistoryList.get(i).getArtist().toLowerCase().contains(keyword.toLowerCase())) { //or artist name
 					canBeAdded = false;		
 					break;
				}
			}
			
			//if not minimal length
			long timeDelta;
			if (i == songHistoryList.size() - 1)
				timeDelta = songHistoryList.get(i).getPlayedDate().getTimeInMillis() - lastSong.getPlayedDate().getTimeInMillis();
			else
				timeDelta = songHistoryList.get(i).getPlayedDate().getTimeInMillis() - songHistoryList.get(i + 1).getPlayedDate().getTimeInMillis();
			if (timeDelta < config.getMinimalLength() * 1_000) //if too short song
				canBeAdded = false;
			
			if (canBeAdded) //add to final list
				filteredSongHistoryList.add(songHistoryList.get(i));
		}
		
		return filteredSongHistoryList;
	}
	
}
