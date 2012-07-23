/**
 * 
 */
package com.stationmillenium.coverart.services.history;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.beans.history.SongHistoryFilterPropertiesBean;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.SongHistoryRepository;

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
	private SongHistoryRepository songHistoryRepository;
	
	/**
	 * Filter songs among history :Calendar.getInstance()
	 * -remove these with forbidden keywords in the title or artist name
	 * -songs with minimal length not permitted
	 * @param songHistoryList the list to filter
	 * @param lastSong the last recorded song to compare minimal length
	 * @return the filtered list - never null
	 */
	public List<SongHistoryItemDTO> filterSongHistory(List<SongHistoryItemDTO> songHistoryList) {
		LOGGER.debug("Initial list : " + songHistoryList);
		List<SongHistoryItemDTO> filteredSongHistoryList = new ArrayList<>();
		
		//for each item of the list		
		for (int i = 0 ; i < songHistoryList.size(); i++) {
			boolean canBeAdded = true; //we suppose item can be added
			for (String keyword : config.getForbiddenKeywords()) { //for each forbidden kewyord
				if ((songHistoryList.get(i).getTitle().equals("")) //if title empty
						|| (songHistoryList.get(i).getArtist().equals("")) //if artist empty
						|| (songHistoryList.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase())) //if title contains forbidden keyword
						|| (songHistoryList.get(i).getArtist().toLowerCase().contains(keyword.toLowerCase()))) { //or artist name
 					canBeAdded = false; //rejected
 					LOGGER.debug("Song rejected due to forbidden keywords : " + songHistoryList.get(i));
 					break;
				}
			}
			
			//if not minimal length
			long timeDelta = 0;
			if (canBeAdded) { //if test needed
				if (i == 0) { //if first
					timeDelta = config.getMinimalLength() * 1_000; //always add 
				} else
					timeDelta = songHistoryList.get(i - 1).getPlayedDate().getTimeInMillis() - songHistoryList.get(i).getPlayedDate().getTimeInMillis();
				
				if (timeDelta < config.getMinimalLength() * 1_000) { //if too short song
					canBeAdded = false;
					LOGGER.debug("Song rejected due to too short length : " + songHistoryList.get(i));
				}
			}
			
			if (canBeAdded) { //add to final list
				LOGGER.debug("Added song : " + songHistoryList.get(i));
				filteredSongHistoryList.add(songHistoryList.get(i));				
			}				
		}
		
		LOGGER.debug("Final list : " + filteredSongHistoryList);
		return filteredSongHistoryList;
	}
	
	/**
	 * Check if the last recorded song is not too short and must be removed.
	 * If it needs to be removed, it is done
	 * @param songHistoryList the song history list to compare with the last recorded song
	 */
	public void filterLastRecordedSong(List<SongHistoryItemDTO> songHistoryList) {
		if (songHistoryList.size() > 0) { //if not empty list
			SongHistoryItemDTO lastRecordedSong = songHistoryRepository.getLastSongHistoryItem(); //get the last song
			if (!lastRecordedSong.equals(songHistoryList.get(0))) { //if not the first object (no comparison can be done)
				
				SongHistoryItemDTO songToCompare = null;
				for (int i = 1; i < songHistoryList.size(); i++) { //for each song
					if (lastRecordedSong.equals(songHistoryList.get(i))) { //if song found 
						songToCompare = songHistoryList.get(i - 1);
						break;
					}
				}
				
				if (songToCompare == null) //if no song found
					songToCompare = songHistoryList.get(songHistoryList.size() - 1); //use last
				
				long timeDelta = songToCompare.getPlayedDate().getTimeInMillis() - lastRecordedSong.getPlayedDate().getTimeInMillis(); //do comparison
				if (timeDelta < config.getMinimalLength() * 1_000) { //if too short song
					songHistoryRepository.deleteLastRecordedSong(); //delete last entry
					LOGGER.debug("Last recorded song deleted due to too short length : " + lastRecordedSong);					
				}
			}			
		}
	}
	
}
