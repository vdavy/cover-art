/**
 * 
 */
package com.stationmillenium.coverart.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.ServerStatusRepository;
import com.stationmillenium.coverart.repositories.SongHistoryRepository;
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
	private SongHistoryRepository songHistoryRepository;
	
	/**
	 * Method to do server polling :
	 * -query shoutcast server
	 * -filter song
	 * -record in DB if needed
	 * -launch cover search
	 */
	public void doServerPolling() {
		List<SongHistoryItemDTO> songHistoryList = queryShoutcastServer();
		LOGGER.info("Gathered song list : " + songHistoryList);
		
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
			List<SongHistoryItemDTO> filteredSongHistoryFiltersList = songFilter.filterSongHistory(songHistoryList); //process filter
			
			return filteredSongHistoryFiltersList;
			
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
}
