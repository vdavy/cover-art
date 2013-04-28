package com.stationmillenium.coverart.services.recover;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.SongImageRepository;
import com.stationmillenium.coverart.services.covergraber.CoverGraberRootService;

/**
 * Service to remove custom image on songs
 * @author vincent
 *
 */
@Service
public class RemoveCustomImageService {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoveCustomImageService.class);

	//image database repository
	@Autowired
	private SongImageRepository songImageRepository;
	
	//recover previsous image service
	@Autowired
	private CoverGraberRootService coverGraberRootService;
	
	/**
	 * Remove the custom image of a song
	 * Restore the previous one
	 * @param songToRemoveCustomImage the song with custom image to remove
	 */
	public void removeImageOnSong(SongHistoryItemDTO songToRemoveCustomImage) {
		LOGGER.debug("Song to remove custom image : " + songToRemoveCustomImage);
		
		//delete file
		if (songImageRepository.isSongHasImage(songToRemoveCustomImage)) {
			File imageFile = new File(coverGraberRootService.getCoverImagesDirectory() + "/" +songImageRepository.getImageFileNameOfSong(songToRemoveCustomImage));
			boolean fileDeleted = imageFile.delete();
			if (fileDeleted) 
				LOGGER.debug("File properly deleted : " + imageFile.getAbsolutePath());
			else
				LOGGER.warn("Image file not existing : " + songToRemoveCustomImage);
		} else
			LOGGER.debug("Song has no custom image");
		
		//remove custom image song attribute
		LOGGER.debug("Remove custom image of song...");
		songImageRepository.cancelCustomImage(songToRemoveCustomImage);
		
		//recover the previsous image
		LOGGER.debug("Recover the previous image...");
		List<SongHistoryItemDTO> songList = Arrays.asList(songToRemoveCustomImage);
		coverGraberRootService.grabImageForSongs(songList);
		
		//end of process
		LOGGER.debug("Custom image fully removed");
	}
	
}
