/**
 * 
 */
package com.stationmillenium.coverart.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.beans.utils.GeneralPropertiesBean;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.ServerStatusRepository;
import com.stationmillenium.coverart.repositories.SongItemRepository;
import com.stationmillenium.coverart.services.covergraber.CoverGraberRootService;
import com.stationmillenium.coverart.services.history.ShoutcastParser;
import com.stationmillenium.coverart.services.history.SongHistoryFilter;

/**
 * Service to poll the Shoutcast server periodically
 * Main service class
 * @author vincent
 *
 */
@Service
public class PollingService {
	
	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(PollingService.class);

	//current song
	private SongHistoryItemDTO currentSong;
	
	//service injection
	//the shoutcast parser
	@Autowired
	private ShoutcastParser shoutcastParser;
	
	//the song history filter
	@Autowired
	private SongHistoryFilter songFilter;
	
	//repositories injection
	//the server status repository
	@Autowired
	private ServerStatusRepository serverStatusRepository;
	
	//the song history repository
	@Autowired
	private SongItemRepository songHistoryRepository;
	
	//the service to gather
	@Autowired
	private CoverGraberRootService coverGraberRootService;
	
	//configuration
	@Autowired
	private GeneralPropertiesBean config;
		
	/**
	 * Method to do server polling :
	 * -query shoutcast server
	 * -filter song
	 * -record in DB if needed
	 * -launch cover search
	 */
	public void doServerPolling() {
		//query shoutcast
		List<SongHistoryItemDTO> songHistoryList = queryShoutcastServer();
		LOGGER.debug("Gathered song list : " + songHistoryList);
		
		//insert song list
		List<SongHistoryItemDTO> songImageMissingList = insertSongList(songHistoryList);
		LOGGER.debug("Songs with missing image list : " + songImageMissingList);
		
		//get the image
		coverGraberRootService.grabImageForSongs(songImageMissingList);
	}
	
	/**
	 * Query Shoutcast server :
	 * -query if server is up
	 * -if server up, gather song list
	 * -filter song list
	 * @return filtered song list, or empty song list if nothing found
	 */
	private List<SongHistoryItemDTO> queryShoutcastServer() {
		if (shoutcastParser.checkShoutcastStatus()) { //test if shoutcast parser up
			recordServerStatus(true);
			List<SongHistoryItemDTO> songHistoryList = shoutcastParser.getSongHistoryList(); //get the list			
						
			//set current song
			if ((songHistoryList.size() > 0) && (songHistoryList.get(0) != null)) {
				try {
					currentSong = songHistoryList.get(0).clone(); //set current song
					
					//check playlist update timeout
					Calendar timeoutCalendar = Calendar.getInstance();
					timeoutCalendar.add(Calendar.MINUTE, -config.getPlaylistUpdateTimeout());
					
					if (timeoutCalendar.before(currentSong.getPlayedDate())) { //check playlist update
						//record playlist updated
						serverStatusRepository.recordPlaylistUpdated();
						LOGGER.debug("Playlist updated");
						
						//playlist updated - process filtering
						List<SongHistoryItemDTO> filteredSongHistoryFiltersList = songFilter.filterSongHistory(songHistoryList); //process filter
						songFilter.filterLastRecordedSong(filteredSongHistoryFiltersList); //filter last recorded song
						return filteredSongHistoryFiltersList;			
						
					} else { //playlist not updated
						serverStatusRepository.recordPlaylistUpdateTimeout();
						LOGGER.warn("Playlist not updated in timeout");						
						return new ArrayList<>(); //return empty list
					}					
					
				} catch (CloneNotSupportedException e) { //error during cloning
					LOGGER.error("Error during current song notification", e);
					return new ArrayList<>(); //return empty list
				}		
				
			} else {
				LOGGER.warn("Playlist empty");		
				return new ArrayList<>(); //return empty list
			}
			
		} else { //if server down
			recordServerStatus(false);
			return new ArrayList<>(); //return empty list
		}
	}
	
	/**
	 * Record the server status : <code>true</code> if up, <code>false</code> if down
	 * Record only if it changes from previous status
	 * @param serverStatus <code>true</code> if server is up, <code>false</code> if server is down
	 */
	private void recordServerStatus(boolean serverStatus) {
		if (serverStatusRepository.getLastServerStatus() != serverStatus) { //if server status changed from previous one
			if (serverStatus)
				serverStatusRepository.recordServerUp(); //record server up
			else
				serverStatusRepository.recordServerDown(); //record server down
		}
	}
	
	/**
	 * Insert song list into DB
	 * Stop when last recorded song is encountered
	 * @param songList the song list to insert
	 */
	private List<SongHistoryItemDTO> insertSongList(List<SongHistoryItemDTO> songList) {
		//compute current date
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.MINUTE, 1);
		
		SongHistoryItemDTO lastSong = songHistoryRepository.getLastSongHistoryItem(); //get last recorded song
		List<SongHistoryItemDTO> listToInsert = new ArrayList<>(); //list of song to insert
		
		for (int i = 0; i < songList.size(); i++) { //for each song in the list
			if (!lastSong.equals(songList.get(i))) { //if not equals
				if (((lastSong.getPlayedDate() == null) //if no entity found
						|| (lastSong.getPlayedDate().before(songList.get(i).getPlayedDate()))) 
						&& (currentDate.after(songList.get(i).getPlayedDate()))) //past the last recorded song and not in the future
					listToInsert.add(songList.get(i)); //add to the list
				else
					LOGGER.warn("Song not in right time range : " + songList.get(i));
			} else
				break; //reach the same - do not add and quit
		}
		
		Collections.reverse(listToInsert);
		LOGGER.info("Song list inserted : " + listToInsert);
		List<SongHistoryItemDTO> songListToGatherImage = new ArrayList<>(); //list to put new song where image is missing
		for (SongHistoryItemDTO song : listToInsert) {
			if (songHistoryRepository.isExistingSong(song)) { //check if sonf already exists
				songHistoryRepository.addTimeToExistingSong(song); //just add time
				LOGGER.debug("Song already exists : " + song);
			} else {
				songHistoryRepository.insertSongHistory(song); //insert in db		
				LOGGER.debug("Song not existing : " + song);
				songListToGatherImage.add(song); //add it into the list to 
			}
		}
		
		return songListToGatherImage;
	}
	
	/**
	 * Get the current song
	 * @return the {@code SongHistoryItemDTO
	 */
	public SongHistoryItemDTO getCurrentSong() {
		return currentSong;
	}
}
