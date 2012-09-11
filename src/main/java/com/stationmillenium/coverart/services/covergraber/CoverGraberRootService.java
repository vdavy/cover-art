/**
 * 
 */
package com.stationmillenium.coverart.services.covergraber;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.stationmillenium.coverart.beans.utils.GeneralPropertiesBean;
import com.stationmillenium.coverart.domain.history.SongHistoryImage.Provider;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.SongItemRepository;
import com.stationmillenium.coverart.services.covergraber.services.CoverGraberServiceInterface;

/**
 * Root service for cover grabers :
 * -query cover graber service
 * -save image files 
 * @author vincent
 *
 */
@Service
public class CoverGraberRootService implements ServletContextAware {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(CoverGraberRootService.class);
		
	//servlet context to get path
	private ServletContext servletContext;
	
	//directory to store cover image
	private String coverImagesDirectory;
	
	//configuration
	@Autowired
	private GeneralPropertiesBean generalPropertiesBean;
	
	//song item repository
	@Autowired
	private SongItemRepository songItemRepository;
	
	//random for image name generation
	private MessageDigest md5; 
	
	//cover grabers
	//last fm cover graver
	@Autowired
	@Qualifier("lastFmCoverGraberService")
	private CoverGraberServiceInterface lastFmCoverGraberService;
	
	//deezer cover graver
	@Autowired
	@Qualifier("deezerCoverGraberService")
	private CoverGraberServiceInterface deezerCoverGraberService;
		
	
	public void grabImageForSongs(List<SongHistoryItemDTO> songList) {
		for (SongHistoryItemDTO song : songList) { //for each song 
			Provider provider = Provider.LAST_FM; //last fm default provider
			BufferedImage image = lastFmCoverGraberService.grabCover(song.getArtist(), song.getTitle()); //get the image
			
			if (image == null) { //if not found on lastfm, try deezer
				provider = Provider.DEEZER; //deezer provider
				image = deezerCoverGraberService.grabCover(song.getArtist(), song.getTitle()); //get the image
			}
			
			if (image != null) { //if image found				
				try {
					String imageFileName = generateMd5Time() + "." + generalPropertiesBean.getCoverImagesExtension(); //make file name
					File imageFile = new File(coverImagesDirectory + "/" + imageFileName); //init file
					ImageIO.write(image, generalPropertiesBean.getCoverImagesExtension(), imageFile); //save image
					LOGGER.debug("Image written : " + imageFile.getAbsolutePath());					
					
					//record in db
					songItemRepository.addImageToSong(song, imageFileName, image.getWidth(), image.getHeight(), provider); //add image to song
					
				} catch (IOException e) {
					LOGGER.warn("Error during writing image", e);
				}
			}
		}
	}

	/**
	 * Generate a random md5 based on current millisecond time
	 * @return a random md5
	 */
	private String generateMd5Time() {		
		try {
			String timeValue = String.valueOf(System.currentTimeMillis());
			byte[] timeValueInBytes = timeValue.getBytes("UTF-8");
			byte[] timeValueDigest = md5.digest(timeValueInBytes);
			
			StringBuilder hashString = new StringBuilder();
			for (int i = 0; i < timeValueDigest.length; i++) {
			        String hex = Integer.toHexString(timeValueDigest[i]);
			        if (hex.length() == 1) {
			                hashString.append('0');
			                hashString.append(hex.charAt(hex.length() - 1));
			        } else
			                hashString.append(hex.substring(hex.length() - 2));
			}
			
			return hashString.toString();
			
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Error during md5 generation", e);
			return null;
		}		
	}
	
	/**
	 * Get the servlet context
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;	
	}
	
	/**
	 * Set the cover images storage directory
	 * Init md5 algo
	 */
	@PostConstruct
	public void initCoverImagesDirectory() {
		if (servletContext != null) //if servlet context available
			coverImagesDirectory = servletContext.getRealPath(generalPropertiesBean.getCoverImagesPath());
		else //otherwise use fallback path
			coverImagesDirectory = generalPropertiesBean.getFallbackPath();
			
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Error in MD5 algoruymn initialization", e);
		}
	}
}
