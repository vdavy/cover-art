/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.history.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.stationmillenium.coverart.beans.utils.GeneralPropertiesBean;
import com.stationmillenium.coverart.dto.hybrid.SongHistoryItemImageDTO;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.SongItemRepository;
import com.stationmillenium.coverart.repositories.SongSearchRepository;
import com.stationmillenium.coverart.web.gwt.history.client.server.HistoryService;
import com.stationmillenium.coverart.web.gwt.history.shared.HistoryGWTDTO;

/**
 * Service impl for the history GWT module
 * @author vincent
 *
 */
@Configurable
@WebServlet("/history/service")
public class HistoryServiceImpl extends RemoteServiceServlet implements HistoryService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(HistoryServiceImpl.class);
	
	//song repository
	@Autowired
	private SongItemRepository songItemRepository;
	
	//song search repository
	@Autowired
	private SongSearchRepository songSearchRepository;
	
	//dozer
	@Autowired
	private Mapper mapper;
	
	//config
	@Autowired
	private GeneralPropertiesBean config;
	
	@Override
	public List<HistoryGWTDTO> getSongHistory() {
		LOGGER.debug("Get song history");
		//setup min date
		int historyDelayMinutes =  config.getHistoryDisplayMinMinutes();
		Calendar minDate = Calendar.getInstance();
		minDate.add(Calendar.MINUTE, -historyDelayMinutes);
		
		//get data
		List<SongHistoryItemImageDTO>  songList =  songItemRepository.getLastSongsFromMinDate(minDate);
		
		return convertToGWTList(songList);
	}

	/**
	 * Convert to GWT list
	 * @param songList the list of song
	 * @return the list of {@link HistoryGWTDTO}
	 */
	private <T> List<HistoryGWTDTO> convertToGWTList(List<T> songList) {
		//convert
		List<HistoryGWTDTO> historyList = new ArrayList<HistoryGWTDTO>();
		for (T song : songList) {
			HistoryGWTDTO historySong = mapper.map(song, HistoryGWTDTO.class);
			if (historySong.getImagePath() != null) 
				historySong.setImagePath(config.getCoverImagesPath() + "/" + historySong.getImagePath()); //adjust path
			
			historyList.add(historySong);
		}
		
		return historyList;
	}
	
	@Override
	public List<HistoryGWTDTO> getSearchSuggestions(String query, int limit) {
		LOGGER.debug("Get search suggestions - keywords : " + query);
		List<SongHistoryItemDTO> suggestionList = songSearchRepository.searchSongsForSuggest(query, limit);
		return convertToGWTList(suggestionList);
	}
	
	@Override
	public List<HistoryGWTDTO> searchAll(String query) {
		LOGGER.debug("Search all songs - keywords : " + query);
		List<SongHistoryItemImageDTO> songList = songSearchRepository.searchSongs(query, config.getHistorySearchMaxResults());
		return convertToGWTList(songList);
	}
	
	@Override
	public List<HistoryGWTDTO> searchByArtist(String query) {
		LOGGER.debug("Search songs by artist - keywords : " + query);
		List<SongHistoryItemImageDTO> songList = songSearchRepository.searchSongsByArtist(query, config.getHistorySearchMaxResults());
		return convertToGWTList(songList);
	}
	
	@Override
	public List<HistoryGWTDTO> searchByTitle(String query) {
		LOGGER.debug("Search songs by title - keywords : " + query);
		List<SongHistoryItemImageDTO> songList = songSearchRepository.searchSongsByTitle(query, config.getHistorySearchMaxResults());
		return convertToGWTList(songList);
	}
	
	@Override
	public List<HistoryGWTDTO> searchByDate(Date searchDate) {
		LOGGER.debug("Search songs by date - date : " + searchDate);
		//set up date
		int searchDateDelta =  config.getDateSearchDelta();
		Calendar minDate = Calendar.getInstance();
		minDate.setTime(searchDate);
		minDate.add(Calendar.MINUTE, -searchDateDelta);
		Calendar maxDate = Calendar.getInstance();
		maxDate.setTime(searchDate);
		maxDate.add(Calendar.MINUTE, searchDateDelta);
		
		//search
		List<SongHistoryItemImageDTO> songList = songItemRepository.getSongsPlayedBetween2DatesWithImages(minDate, maxDate);
		return convertToGWTList(songList);
	}
	
}
