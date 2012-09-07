/**
 * 
 */
package com.stationmillenium.coverart.services.history;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.beans.history.ShoutcastServerPropertiesBean;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;

/**
 * Shoutcast parser to get song history
 * @author vincent
 *
 */
@Service
public class ShoutcastParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShoutcastParser.class);
	
	//server configuration
	@Autowired
	private ShoutcastServerPropertiesBean config;
	
	/**
	 * Check the Shoutcast server status :
	 * -<code>true</code> server alive
	 * -<code>false</code> server dead
	 * @return
	 */
	public boolean checkShoutcastStatus() {
		try {
			Document document = Jsoup.connect(config.getShoutcastServerStatusPage())
					.userAgent(config.getUserAgent())
					.get(); //get the document through jsoup
			Elements elements = document.select(config.getStatusPageSelect()); //get elements
			if (elements.size() > 0) {
				Element element = elements.get(0); //get first element
				
				if (config.getStatusPageSelectText().equals(element.text())) {
					LOGGER.debug("Shoutcast server up : " + config.getShoutcastServerStatusPage());
					return true;
				} else {
					LOGGER.debug("Shoutcast server down : " + config.getShoutcastServerStatusPage());
					return false;
				}
				
			} else {
				LOGGER.warn("No text found when parsing Shoucast status page... assuming server down");
				return false;
			}
		} catch (IOException e) {
			LOGGER.error("Error during Shoucast server status parsing", e);
			return false;
		}		
	}
	
	/**
	 * Parse the Shoutcast song history list and return the list
	 * @return the list, never null
	 */
	public List<SongHistoryItemDTO> getSongHistoryList() {
		List<SongHistoryItemDTO> songHistoryList = new ArrayList<>(); //return song hsitory list
		
		try {
			Document document = Jsoup.connect(config.getShoutcastServerSongHistoryPage())
					.userAgent(config.getUserAgent())
					.get(); //get the document through jsoup
			Elements elements = document.select(config.getSongHistoryPageSelect()); //get elements
			if (elements.size() > 0) {
				for (int i = 0; i < elements.size(); i = i + 2) {
					String date = elements.get(i).text();
					String song = elements.get(i + 1).text();
					addSongToHistoryList(date, song, songHistoryList);
				}
				
			} else {
				LOGGER.warn("No text found when parsing Shoucast song history list");
			}
		} catch (IOException e) {
			LOGGER.error("Error during Shoucast server song history list parsing", e);
		}		
		
		//day change
		handeDateChange(songHistoryList);
				
		//logging
		LOGGER.debug("Song history found : " + songHistoryList);
		
		return songHistoryList; //return the list
	}

	/**
	 * Handle the date change
	 * @param songHistoryList the list to process
	 */
	private void handeDateChange(List<SongHistoryItemDTO> songHistoryList) {
		//handle the case of day changing
		for (int i = 0; i < songHistoryList.size() - 1; i++) {
			//get songs
			SongHistoryItemDTO currentSong = songHistoryList.get(i); 
			SongHistoryItemDTO nextSong = songHistoryList.get(i + 1);
			
			//if current song before the next song
			if (currentSong.getPlayedDate().before(nextSong.getPlayedDate())) {
				nextSong.getPlayedDate().add(Calendar.DAY_OF_MONTH, -1); //song of the previous day
				LOGGER.debug("Changed the date of song : " + nextSong);
			}
		}
	}
	
	/**
	 * Add a song to the song history list
	 * @param date the date in {@link String} format
	 * @param song the song name
	 * @param songHistoryList the list
	 */
	private void addSongToHistoryList(String date, String song, List<SongHistoryItemDTO> songHistoryList) {
		SongHistoryItemDTO item = new SongHistoryItemDTO();

		//process song name
		String[] splittedSong = song.split(config.getSongHistorySongSeparator());
		if (splittedSong.length == 2) {
			item.setArtist(splittedSong[0]);
			item.setTitle(splittedSong[1]);
		}

		//process date
		Calendar playedDate = Calendar.getInstance();
		String[] splittedDate = date.split(config.getSongHistoryDateSeparator());
		if (splittedDate.length == 3) {			
			try {
				if (splittedDate[0] != null)
					playedDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splittedDate[0]));

				if (splittedDate[1] != null)
					playedDate.set(Calendar.MINUTE, Integer.parseInt(splittedDate[1]));

				if (splittedDate[2] != null)
					playedDate.set(Calendar.SECOND, Integer.parseInt(splittedDate[2]));

			} catch (NumberFormatException e) {
				LOGGER.warn("Error while parsing date of song in song history list", e);
				playedDate = null;
			}
		} else {
			LOGGER.warn("Error while plitting date in song history list");
			playedDate = null;
		}
				
		item.setPlayedDate(playedDate);  //set played date
		
		cleanSongHistoryItemAndAddIt(item, songHistoryList); //add item
	}
		
	
	/**
	 * Clean song history item :
	 * -trim title and name
	 * -correct song title and name if null
	 * Add it date is set 
	 * @param item item to clean
	 * @param list the list where to add item
	 */
	private void cleanSongHistoryItemAndAddIt(SongHistoryItemDTO item, List<SongHistoryItemDTO> list) {
		//format artist name
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
		
		if (item.getPlayedDate() != null) //if date not null
				list.add(item);
		else
			LOGGER.info("Song item not added to song history list - played date null : " + item);
	}
}
 