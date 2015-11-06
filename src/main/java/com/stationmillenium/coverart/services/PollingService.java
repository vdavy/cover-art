/**
 * 
 */
package com.stationmillenium.coverart.services;

import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.beans.utils.GeneralPropertiesBean;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.SongItemRepository;
import com.stationmillenium.coverart.repositories.SongSearchRepository;
import com.stationmillenium.coverart.repositories.StatusRepository;
import com.stationmillenium.coverart.services.alerts.AlertService;
import com.stationmillenium.coverart.services.covergraber.CoverGraberRootService;
import com.stationmillenium.coverart.services.history.IcecastParser;
import com.stationmillenium.coverart.services.history.SongHistoryFilter;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertType;

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
	
	//time for server status
	private Calendar serverStatusCalendar;
	
	//service injection
	//the shoutcast parser
	@Autowired
	private IcecastParser icecastParser;
	
	//the song history filter
	@Autowired
	private SongHistoryFilter songFilter;
	
	//the alert service to send alert mails
	@Autowired	
	private AlertService alertService;
	
	//repositories injection
	//the server status repository
	@Autowired
	private StatusRepository statusRepository;
	
	//the song history repository
	@Autowired
	private SongItemRepository songHistoryRepository;
	
	//the service to gather
	@Autowired
	private CoverGraberRootService coverGraberRootService;
	
	//song search repository for indexing
	@Autowired
	private SongSearchRepository songSearchRepository;
	
	//configuration
	@Autowired
	private GeneralPropertiesBean config;
	
	/**
	 * Index the database on startup
	 */
	@PostConstruct
	public void doIndexing() {
		songSearchRepository.indexAsync();
	}
	
	/**
	 * Method to do server polling :
	 * -query shoutcast server
	 * -filter song
	 * -record in DB if needed
	 * -launch cover search
	 */
	public void doServerPolling() {
		//query shoutcast
		SongHistoryItemDTO song = queryIcecastServer();
		LOGGER.debug("Gathered song : " + song);
		
		//insert song list
		SongHistoryItemDTO songImageMissing = insertSong(song);		
		
		//get the image
		if (songImageMissing != null) {
			LOGGER.debug("Song with missing image : " + songImageMissing);
			coverGraberRootService.grabImageForSongs(songImageMissing);
		}
	}
	
	/**
	 * Query Icecast server :
	 * -query if server is up
	 * -if server up, gather song list
	 * -filter song list
	 * @return filtered song list, or empty song list if nothing found
	 */
	private SongHistoryItemDTO queryIcecastServer() {
		if (icecastParser.checkIcecastStatus()) { //test if shoutcast parser up
			recordServerStatus(true); //deal with server up
			serverStatusCalendar = null; //reset calendar
			
			SongHistoryItemDTO songItem = icecastParser.getCurrentSong(); //get the list			
			
			//set current song
			if (songItem != null) {
				try {
					//set current song
					if (songFilter.checkNoForbiddenKeywords(songItem)) { //check if the song is allowed
						currentSong = songItem.clone(); //set current song					
						
						if (!isPlaylistAlert(songItem)) { //check playlist update
							//record playlist updated
							boolean sendAlert = statusRepository.recordPlaylistUpdated();
							if (sendAlert)
								alertService.sendEndedAlert(AlertType.PLAYLIST);
							LOGGER.debug("Playlist updated");

							return songItem;
						
						} else { //playlist not updated
							boolean sendAlert = statusRepository.recordPlaylistUpdateTimeout();
							if (sendAlert)
								alertService.sendActiveAlert(AlertType.PLAYLIST);
							LOGGER.warn("Playlist not updated in timeout");		
							currentSong = null; //no song to display
							return null; //return empty list
						}					
					} else {
						currentSong = null;
						return null;
					}
					
				} catch (CloneNotSupportedException e) { //error during cloning
					LOGGER.error("Error during current song notification", e);
					currentSong = null; //no song to display
					return null; //return empty list
				}						
			} else {
				LOGGER.warn("Playlist empty");		
				currentSong = null; //no song to display
				return null; //return empty list
			}
			
		} else { //if server down
			//deal with server down
			if (serverStatusCalendar == null) {
				serverStatusCalendar = Calendar.getInstance();
				LOGGER.debug("Server down at : " + serverStatusCalendar);
			} else {
				Calendar currentServerDownTime = Calendar.getInstance();
				currentServerDownTime.add(Calendar.SECOND, -config.getAlertTimeout()); //add alert delay time
				if (currentServerDownTime.after(serverStatusCalendar)) {
					recordServerStatus(false);
					LOGGER.debug("Record server down at : " + currentServerDownTime.getTime());
				}					
			}						

			currentSong = null; //no song to display
			return null; //return empty list
		}
	}
	
	/**
	 * Is the playlist alert is active ?
	 * @param songItem the gathered song
	 * @return true if alert active, false if not
	 */
	private boolean isPlaylistAlert(SongHistoryItemDTO songItem) {
		//check playlist update timeout
		Calendar timeoutCalendar = Calendar.getInstance();
		timeoutCalendar.add(Calendar.MINUTE, -config.getPlaylistUpdateTimeout());
		SongHistoryItemDTO lastRecordedSong = songHistoryRepository.getLastSongHistoryItem();
		
		return lastRecordedSong.getArtist().equals(songItem.getArtist())
				&& lastRecordedSong.getTitle().equals(songItem.getTitle())
				&& timeoutCalendar.after(songItem.getPlayedDate());
	}
	
	/**
	 * Record the server status : <code>true</code> if up, <code>false</code> if down
	 * Record only if it changes from previous status
	 * @param serverStatus <code>true</code> if server is up, <code>false</code> if server is down
	 */
	private void recordServerStatus(boolean serverStatus) {
		if (statusRepository.getLastServerStatus() != serverStatus) { //if server status changed from previous one
			if (serverStatus) {
				statusRepository.recordServerUp(); //record server up
				alertService.sendEndedAlert(AlertType.SHOUTCAST); //stop sending alert
			} else {
				statusRepository.recordServerDown(); //record server down
				alertService.sendActiveAlert(AlertType.SHOUTCAST); //send alert
			}
		}
	}
	
	/**
	 * Insert song list into DB
	 * Stop when last recorded song is encountered
	 * @param songList the song list to insert
	 */
	private SongHistoryItemDTO insertSong(SongHistoryItemDTO songToInsert) {
		//compute current date
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.MINUTE, 1);
		
		SongHistoryItemDTO lastSong = songHistoryRepository.getLastSongHistoryItem(); //get last recorded song

		if (!(lastSong.getArtist().equalsIgnoreCase(songToInsert.getArtist()) && lastSong.getTitle().equalsIgnoreCase(songToInsert.getTitle()))) { //if not equals
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Last song artist : '" + lastSong.getArtist() + "' - song to insert artist : '" + songToInsert.getArtist() + "' - Last song title : '" + lastSong.getTitle() + "' - song to insert title : '"+ songToInsert.getTitle() 
						+ "'\nlastSong.getArtist().equals(songToInsert.getArtist()) = " + (lastSong.getArtist().equals(songToInsert.getArtist())) 
						+ "\nlastSong.getTitle().equals(songToInsert.getTitle()) = " + (lastSong.getTitle().equals(songToInsert.getTitle()))) ;
			}
			
			if (((lastSong.getPlayedDate() == null) //if no entity found
					|| (lastSong.getPlayedDate().before(songToInsert.getPlayedDate()))) 
					&& (currentDate.after(songToInsert.getPlayedDate()))) { //past the last recorded song and not in the future
				
				if (songHistoryRepository.isExistingSong(songToInsert)) { //check if sonf already exists
					songHistoryRepository.addTimeToExistingSong(songToInsert); //just add time
					LOGGER.debug("Song already exists - insert time : " + songToInsert);
				} else {
					songHistoryRepository.insertSongHistory(songToInsert); //insert in db		
					LOGGER.debug("Song not existing : " + songToInsert);
					return songToInsert;
				}
			} else {
				LOGGER.warn("Song not in right time range : " + songToInsert);
			}
		} else {
			LOGGER.debug("Song already inserted : " + songToInsert);
		}

		return null;
	}
	
	/**
	 * Get the current song
	 * @return the {@code SongHistoryItemDTO
	 */
	public SongHistoryItemDTO getCurrentSong() {
		return currentSong;
	}
}
