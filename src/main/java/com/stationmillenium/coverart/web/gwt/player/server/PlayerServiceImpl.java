/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.stationmillenium.coverart.beans.utils.GeneralPropertiesBean;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.dto.services.images.SongImageDTO;
import com.stationmillenium.coverart.repositories.SongImageRepository;
import com.stationmillenium.coverart.repositories.SongItemRepository;
import com.stationmillenium.coverart.services.PollingService;
import com.stationmillenium.coverart.web.gwt.player.client.server.PlayerService;
import com.stationmillenium.coverart.web.gwt.player.shared.SongGWTDTO;

/**
 * Service impl for the player GWT module
 * @author vincent
 *
 */
@Configurable
@WebServlet("/player/service")
public class PlayerServiceImpl extends RemoteServiceServlet implements PlayerService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerServiceImpl.class);
	
	//polling service
	@Autowired
	private PollingService pollingService;
	
	//song image service
	@Autowired
	private SongImageRepository songImageRepository;
	
	//song repository
	@Autowired
	private SongItemRepository songItemRepository;
	
	//dozer
	@Autowired
	private Mapper mapper;
	
	//config
	@Autowired
	private GeneralPropertiesBean config;
	
	/* (non-Javadoc)
	 * @see com.stationmillenium.coverart.web.gwt.player.client.server.PlayerService#getLastSong()
	 */
	@Override
	public SongGWTDTO getLastSong() {
		LOGGER.debug("Get last song");
		//get song
		SongHistoryItemDTO songItem = pollingService.getCurrentSong(); //get current song
		SongImageDTO image = null;
		if (songItem != null)
			image = songImageRepository.getImageForSong(songItem); //get image
		
		if (songItem != null) { //if song found
			SongGWTDTO songDTO = mapper.map(songItem, SongGWTDTO.class); //map to gwt bean
			if (image != null) {
				image.setFileName(config.getCoverImagesPath() + "/" + image.getFileName()); //adjust path
				mapper.map(image, songDTO); //map image
			}
			return songDTO;
			
		} else //if not found
			return null;
	}

	@Override
	public List<SongGWTDTO> getLast5PreviousSongs(boolean displayLastSong) {
		LOGGER.debug("Get last 5 previous songs");
		List<SongHistoryItemDTO> lastSongsList = songItemRepository.getLast5PlayedSongs(displayLastSong); //last 5 songs
		List<SongGWTDTO> lastSongsGWTList = new ArrayList<SongGWTDTO>(); //gwt list
		
		for (SongHistoryItemDTO song : lastSongsList) { //for each song
			SongGWTDTO songDTO = mapper.map(song, SongGWTDTO.class); //map to gwt bean
			lastSongsGWTList.add(songDTO);			
		}
		
		return lastSongsGWTList;		
	}
	
}
