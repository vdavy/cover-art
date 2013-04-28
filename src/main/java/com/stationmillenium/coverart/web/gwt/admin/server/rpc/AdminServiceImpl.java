/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.server.rpc;

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
import com.stationmillenium.coverart.dto.hybrid.SongHistoryItemImageDTO;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.SongItemRepository;
import com.stationmillenium.coverart.repositories.SongSearchRepository;
import com.stationmillenium.coverart.services.PollingService;
import com.stationmillenium.coverart.services.cron.CronTimer;
import com.stationmillenium.coverart.services.cron.CronType;
import com.stationmillenium.coverart.services.recover.RecoverDeletedImagesService;
import com.stationmillenium.coverart.services.recover.RemoveCustomImageService;
import com.stationmillenium.coverart.web.gwt.admin.client.server.rpc.AdminService;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.ServicesStatuses;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.SongGWT;

/**
 * Service impl for the admin GWT module
 * @author vincent
 *
 */
@SuppressWarnings("serial")
@Configurable
@WebServlet("/admin/service")
public class AdminServiceImpl extends RemoteServiceServlet implements AdminService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	//the cron service
	@Autowired
	private CronTimer cronTimer;
	
	//the polling service
	@Autowired
	private PollingService pollingService;
	
	//repository for indexing
	@Autowired
	private SongSearchRepository songSearchRepository;
	
	//repository for images
	@Autowired
	private SongItemRepository songItemRepository;
	
	//service for missing images recovery
	@Autowired
	private RecoverDeletedImagesService recoverDeletedImagesService;
	
	//service to remove custom image
	@Autowired
	private RemoveCustomImageService removeCustomImageService;
	
	//config
	@Autowired
	private GeneralPropertiesBean config;
		
	//dozer for bean conversion
	@Autowired
	private Mapper mapper;
		
	@Override
	public ServicesStatuses getServicesStatuses() {
		LOGGER.debug("Get the services statuses");
		ServicesStatuses statuses = new ServicesStatuses();
		statuses.setFmAlertStatus(cronTimer.isEnable(CronType.FM_ALERT));
		statuses.setPollingServiceStatus(cronTimer.isEnable(CronType.POLLING_SERVICE));
		return statuses;
	}
	
	@Override
	public void setFMAlertStatus(boolean enabled) {
		LOGGER.debug("Set the FM alert status : " + enabled);
		cronTimer.setEnable(CronType.FM_ALERT, enabled);
	}
	
	@Override
	public void setPollingServiceStatus(boolean enabled) {
		LOGGER.debug("Set the polling service status : " + enabled);
		cronTimer.setEnable(CronType.POLLING_SERVICE, enabled);		
	}
	
	@Override
	public String getCurrentTitle() {
		LOGGER.debug("Get the current title");
		SongHistoryItemDTO currentSong = pollingService.getCurrentSong();
		if (currentSong != null)
			return currentSong.getArtist() + " - " + currentSong.getTitle();
		else
			return "";
	}

	@Override
	public boolean isIndexingActive() {
		LOGGER.debug("Is indexing still running ?");
		return (!(songSearchRepository.isIndexingFinished()));
	}
	
	@Override
	public void launchIndexing() {
		LOGGER.debug("Launch indexing");
		songSearchRepository.indexAsync();
	}

	@Override
	public void launchMissingImagesRecovery() {
		LOGGER.debug("Launch missing images recovery");
		recoverDeletedImagesService.recoverLostImages();
	}

	@Override
	public boolean isRecoveryFinished() {
		LOGGER.debug("Is the recover finished ?");
		return recoverDeletedImagesService.isFinished();
	}

	@Override
	public List<SongGWT> getRecoveredSongs() {
		LOGGER.debug("Get recovered songs");
		List<SongHistoryItemDTO> customImageSongHistoryList = recoverDeletedImagesService.getMissingCustomImageSongsList(); //get the list of custom image songs
		List<SongHistoryItemDTO> songHistoryList = recoverDeletedImagesService.getMissingImageSongsList(); //get the list
		if ((songHistoryList != null) || (customImageSongHistoryList != null)) { //if not null, process
			
			List<SongGWT> songGWTList = new ArrayList<>();
			for (SongHistoryItemDTO songHistoryDTO : customImageSongHistoryList) { //for each song dto, convert - custom image song list
				SongGWT songGWT = mapper.map(songHistoryDTO, SongGWT.class);
				songGWTList.add(songGWT);
			}
			
			for (SongHistoryItemDTO songHistoryDTO : songHistoryList) { //for each song dto, convert - auto image song list
				SongGWT songGWT = mapper.map(songHistoryDTO, SongGWT.class);
				songGWTList.add(songGWT);
			}
			return songGWTList; 
			
		} else //if null return empty list
			return new ArrayList<>();
	}
	
	@Override
	public List<SongGWT> searchSongsWithoutCustomImage(String keywords, int limit) {
		LOGGER.debug("Search songs without custom images - keywords : " + keywords + " - limit : "  + limit);
		List<SongHistoryItemImageDTO> foundSongsList = songSearchRepository.searchSongsForCustomImage(keywords, limit, false);
		return mapSongGWTDTO(foundSongsList);
	}

	/**
	 * Map the {@link SongHistoryItemImageDTO} to {@link SongGWT}
	 * @param foundSongsList the list of {@link SongHistoryItemImageDTO}
	 * @return the return list of {@link SongGWT}
	 */
	private List<SongGWT> mapSongGWTDTO(List<SongHistoryItemImageDTO> foundSongsList) {
		if (foundSongsList != null) { //if data, process
			List<SongGWT> songGWTList = new ArrayList<>();
			for (SongHistoryItemImageDTO songHistoryImageDTO : foundSongsList) { //for each song dto, convert - auto image song list
				SongGWT songGWT = mapper.map(songHistoryImageDTO, SongGWT.class);
				if (songGWT.getImagePath() != null) 
					songGWT.setImagePath(config.getCoverImagesPath() + "/" + songGWT.getImagePath()); //adjust path
				songGWTList.add(songGWT);
			}
			return songGWTList;
			
		} else
			return new ArrayList<>(); //otherwise return an empty list
	}
	
	@Override
	public List<SongGWT> searchSongsWithoutCustomImage(String keywords) {
		LOGGER.debug("Search songs without custom images - keywords : " + keywords);
		return searchSongsWithoutCustomImage(keywords, 0);
	}
	
	@Override
	public List<SongGWT> searchSongsWithCustomImage(String keywords) {
		LOGGER.debug("Search songs with custom images - keywords : " + keywords);
		return searchSongsWithCustomImage(keywords, 0);
	}
	
	@Override
	public List<SongGWT> searchSongsWithCustomImage(String keywords, int limit) {
		LOGGER.debug("Search songs with custom images - keywords : " + keywords + " - limit : "  + limit);
		List<SongHistoryItemImageDTO> foundSongsList = songSearchRepository.searchSongsForCustomImage(keywords, limit, true);
		return mapSongGWTDTO(foundSongsList);
	}
	
	@Override
	public List<SongGWT> getAllCustomImageSong() {
		LOGGER.debug("Get all custom image songs");
		List<SongHistoryItemImageDTO> customImageSongsList = songItemRepository.getSongsWithCustomImages();
		return mapSongGWTDTO(customImageSongsList);
	}

	@Override
	public void setSongAsSongWithCustomImage(SongGWT songToSet) {
		LOGGER.debug("Set song as custom image song : " + songToSet);
		songItemRepository.setSongAsCustomImageSong(songToSet.getArtist(), songToSet.getTitle());
	}
	
	@Override
	public void removeCustomImageOnSong(SongGWT songToRemoveCustomImage) {
		LOGGER.debug("Remove custom image on song : " + songToRemoveCustomImage);
		
		//map the song to remove custom image
		SongHistoryItemDTO songItem = mapper.map(songToRemoveCustomImage, SongHistoryItemDTO.class);
		
		//remove custom image
		removeCustomImageService.removeImageOnSong(songItem);
	}
	
}
