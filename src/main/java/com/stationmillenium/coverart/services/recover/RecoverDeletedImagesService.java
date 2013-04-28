/**
 * 
 */
package com.stationmillenium.coverart.services.recover;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.dto.hybrid.SongHistoryItemImageDTO;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.dto.services.images.SongImageDTO.Provider;
import com.stationmillenium.coverart.repositories.SongImageRepository;
import com.stationmillenium.coverart.services.alerts.CustomImageMissingAlertService;
import com.stationmillenium.coverart.services.covergraber.CoverGraberRootService;

/**
 * Service to recover deleted cover art images
 * @author vincent
 *
 */
@Service
public class RecoverDeletedImagesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecoverDeletedImagesService.class);
	
	//song image repository injection
	@Autowired
	private SongImageRepository songImageRepository;
	
	//cover service to retrieve lost image
	@Autowired
	private CoverGraberRootService coverGraberRootService;
	
	//service to send missing images alert
	@Autowired
	private CustomImageMissingAlertService customImageMissingAlertService;
	
	//instances
	private Boolean finished = true;
	private List<SongHistoryItemDTO> missingImageSongsList = new ArrayList<>();
	private List<SongHistoryItemDTO> missingCustomImageSongsList = new ArrayList<>();
	
	/**
	 * Launch the recover of lost images
	 */
	@Async
	public void recoverLostImages() {
		LOGGER.debug("Start lost images recover");
		synchronized (finished) { //notice it is starting
			finished = false;
		}
		
		//load all songs with image
		List<SongHistoryItemImageDTO> allSongsList = songImageRepository.getAllSongsWithImage();
		LOGGER.debug("Retrieved all songs with image : " + allSongsList);
		
		missingImageSongsList = new ArrayList<>();
		missingCustomImageSongsList = new ArrayList<>();
		for (SongHistoryItemImageDTO song : allSongsList) { //check each song if image exists
			String fileName = coverGraberRootService.getCoverImagesDirectory() + "/" + song.getSongImageDTO().getFileName();
			File imageFile = new File(fileName); //init file
			
			if (!imageFile.exists()) { //test if file exists 
				LOGGER.debug("Image missing for song : " + song);				
				if ((song.getSongHistoryItemDTO().isCustomImage()) && (song.getSongImageDTO().getProvider() == Provider.CUSTOM)) //if custom image
					missingCustomImageSongsList.add(song.getSongHistoryItemDTO()); //add to custom image missing list
				else 
					missingImageSongsList.add(song.getSongHistoryItemDTO()); //add to list
				
				songImageRepository.deleteImageOfSong(song.getSongHistoryItemDTO()); //delete image
			}
		}
		
		//launch recover
		LOGGER.debug("List of songs to recover images : " + missingImageSongsList);
		coverGraberRootService.grabImageForSongs(missingImageSongsList);
		
		//send alert if needed
		LOGGER.info("Missing custom images list : " + missingCustomImageSongsList);
		if (missingCustomImageSongsList.size() > 0)
			customImageMissingAlertService.sendAlert(missingCustomImageSongsList);
			
		//finish
		LOGGER.info("Finished images recover : " + missingImageSongsList);
		synchronized (finished) { //notice it is finshed
			finished = true;
		}
	}
	
	/**
	 * Is finished ?
	 * @return <code>true</code> if finished, <code>false</code> if not
	 */
	public boolean isFinished() {
		synchronized (finished) { //notice it is starting
			return finished;
		}
	}
	
	/**
	 * Get the missing image songs list, if finished
	 * @return the list if finished, null else
	 */
	public List<SongHistoryItemDTO> getMissingImageSongsList() {
		synchronized (finished) { //notice it is starting
			if (finished)
				return missingImageSongsList;
			else
				return null;
		}
	}
	
	/**
	 * Get the custom missing image songs list, if finished
	 * @return the list if finished, null else
	 */
	public List<SongHistoryItemDTO> getMissingCustomImageSongsList() {
		synchronized (finished) { //notice it is starting
			if (finished)
				return missingCustomImageSongsList;
			else
				return null;
		}
	}
	
}
