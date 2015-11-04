/**
 * 
 */
package com.stationmillenium.coverart.services.history;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.stationmillenium.coverart.beans.history.IcecastServerPropertiesBean;
import com.stationmillenium.coverart.dto.json.IcecastRoot;
import com.stationmillenium.coverart.dto.json.Icestats;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;

/**
 * Icecast parser to get song history
 * @author vincent
 *
 */
@Service
public class IcecastParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(IcecastParser.class);
	
	//server configuration
	@Autowired
	private IcecastServerPropertiesBean config;
	
	/**
	 * Check the Icecast server status :
	 * -<code>true</code> server alive
	 * -<code>false</code> server dead
	 * @return server status
	 */
	public boolean checkIcecastStatus() {
		try {
			IcecastRoot icecastRoot = queryIcecast();
			
			if (icecastRoot != null) {
				Icestats icestats = icecastRoot.getIcestats();
								
				if ((icestats != null) && (icestats.getSource() != null)) {
					LOGGER.debug("Icecast server up");
					return true;
				} else {
					LOGGER.debug("Icecast server down");
					return false;
				}
				
			} else {
				LOGGER.warn("No data found when parsing Icecast status... assuming server down");
				return false;
			}
		} catch (RestClientException e) {
			LOGGER.error("Error during Icecast server status parsing", e);
			return false;
		}		
	}
	
	/**
	 * Parse the Shoutcast song history list and return the list
	 * @return the list, never null
	 */
	public SongHistoryItemDTO getCurrentSong() {		
		try {
			IcecastRoot icecastRoot = queryIcecast();
			
			if ((icecastRoot != null) && (icecastRoot.getIcestats() != null) 
					&& (icecastRoot.getIcestats().getSource() != null)
					&& (icecastRoot.getIcestats().getSource().getTitle() != null)) {
				
				String song = icecastRoot.getIcestats().getSource().getTitle();				
				SongHistoryItemDTO item = processSongData(song);				
				item.setPlayedDate(Calendar.getInstance());
				
				LOGGER.debug("Song found : " + item);
				return item;
				
			} else {
				LOGGER.warn("No data found when parsing Icecast song data");
				return null;
			}
		} catch (RestClientException e) {
			LOGGER.error("Error during Icecast server song parsing", e);
			return null;
		}		
	}

	/**
	 * PArse the song data
	 * @param song the data as string 
	 * @return the data as {@link SongHistoryItemDTO}
	 */
	public SongHistoryItemDTO processSongData(String song) {
		SongHistoryItemDTO item = new SongHistoryItemDTO();				
		//process song name
		String[] splittedSong = song.split(config.getSongHistorySongSeparator());
		if (splittedSong.length == 2) {
			item.setArtist(splittedSong[0]);
			item.setTitle(splittedSong[1]);
		}
		
		if (item.getArtist() != null)
			item.setArtist(item.getArtist().trim());
		else {
			LOGGER.debug("Song with null artist : " + item);
			item.setArtist("");
		}
		
		//format title
		if (item.getTitle() != null) 
			item.setTitle(item.getTitle().trim());
		else {
			LOGGER.debug("Song with null title : " + item);
			item.setTitle("");
		}
		return item;
	}

	/**
	 * Query the Icecast server
	 * @return parsed data
	 */
	private IcecastRoot queryIcecast() throws RestClientException {
		return new RestTemplate().getForObject(config.getIcecastURL(), IcecastRoot.class, new Object[0]);
	}
}
 