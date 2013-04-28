/**
 * 
 */
package com.stationmillenium.coverart.services.covergraber;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.beans.utils.GeneralPropertiesBean;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.dto.services.images.SongImageDTO;
import com.stationmillenium.coverart.dto.services.images.SongImageDTO.Provider;
import com.stationmillenium.coverart.exceptions.CustomImageException;
import com.stationmillenium.coverart.repositories.SongImageRepository;
import com.stationmillenium.coverart.repositories.SongItemRepository;

/**
 * Service to add a custom image into the image directory
 * @author vincent
 *
 */
@Service
public class CustomImageService {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomImageService.class);
	
	//configuration
	@Autowired
	private GeneralPropertiesBean config;
	
	//cover root service
	@Autowired
	private CoverGraberRootService coverGraberRootService;
	
	//song item repository
	@Autowired
	private SongItemRepository songItemRepository;
	
	//song image repository
	@Autowired 
	private SongImageRepository songImageRepository;
	
	/**
	 * Add a custom image to the directory 
	 * @param imageInputStream the input stream
	 * @return the image in a {@link SongImageDTO}
	 * @throws CustomImageException if any error occurs
	 */
	public SongImageDTO addCustomImageToDirectory(InputStream imageInputStream) throws CustomImageException {
		try {
			//prepare image
			BufferedImage image = ImageIO.read(imageInputStream); //get the image as buffered image
			BufferedImage imageResized = Scalr.resize(image, config.getCustomImageMaxSize()); //resize image to final size
			LOGGER.debug("New image size : " + imageResized.getWidth() + "x" + imageResized.getHeight());
			
			//write to disk
			String imageFileName = coverGraberRootService.generateMd5Time() + "." + config.getCoverImagesExtension(); //make file name
			File imageFile = new File(coverGraberRootService.getCoverImagesDirectory() + "/" + imageFileName); //init file
			ImageIO.write(imageResized, config.getCoverImagesExtension(), imageFile); //save image
			LOGGER.debug("Image written : " + imageFile.getAbsolutePath());		
			
			//return DTO
			SongImageDTO songDTO = new SongImageDTO();
			songDTO.setFileName(imageFileName);
			songDTO.setHeight(imageResized.getHeight());
			songDTO.setWidth(imageResized.getWidth());
			songDTO.setProvider(Provider.CUSTOM);
			LOGGER.debug("Returned song image DTO : " + songDTO);
			
			return songDTO;
			
		} catch (IOException e) { //catch errors
			LOGGER.error("Error during add of custom image", e);
			throw new CustomImageException(e);
		}
	}
	
	/**
	 * Link image to song
	 * @param song the song
	 * @param image the image to link
	 */
	public void linkCustomImageToFile(SongHistoryItemDTO song, SongImageDTO image) {
		LOGGER.debug("Update song : " + song + " - with image : " + image);
		
		//delete file
		if (songImageRepository.isSongHasImage(song)) {
			File imageFile = new File(coverGraberRootService.getCoverImagesDirectory() + "/" +songImageRepository.getImageFileNameOfSong(song));
			boolean fileDeleted = imageFile.delete();
			if (fileDeleted) 
				LOGGER.debug("File properly deleted : " + imageFile.getAbsolutePath());
			else
				LOGGER.warn("Image file not existing : " + song);
		} else
			LOGGER.debug("Song has no custom image");
				
		if (songImageRepository.isSongHasImage(song)) { //update
			LOGGER.debug("Song has already an image, so update"); 
			songImageRepository.updateCustomImage(song, image.getFileName(), image.getWidth(), image.getHeight());
			
		} else { //insert
			LOGGER.debug("Song has no image, so insert");
			songItemRepository.addCustomImageToSong(song, image.getFileName(), image.getWidth(), image.getHeight());
		}
	}
	
}
