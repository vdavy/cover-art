/**
 * 
 */
package com.stationmillenium.coverart.services.covergraber.services;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import org.slf4j.Logger;

import com.stationmillenium.coverart.schema.lastfmtracksearch.Lfm;


/**
 * @author vincent
 *
 */
public abstract class AbstractCoverGraberService<T> implements CoverGraberServiceInterface {
	
	//logger
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AbstractCoverGraberService.class);
	
	@Override
	public BufferedImage grabCover(String artist, String title) {
		String xmlData = gatherXMLData(artist, title); //gather XML
		if (xmlData != null) {
			T xmlObject = unmarshalllData(xmlData); //unmarshall data
			
			if (xmlObject != null) {
				String imageURL = getImageURLFromXML(xmlObject); //get image url from xml
				
				if (imageURL != null) 
					return gatherImage(imageURL); //get the image
				else
					LOGGER.debug("Image URL null");		
				
			} else
				LOGGER.debug("XML object null");		
		} else 
			LOGGER.debug("XML data null");		
		
		return null;
	}
	
	/**
	 * Gather image container
	 * @param urlText the URL of the image
	 * @return the image 
	 */
	private BufferedImage gatherImage(String urlText)  {
		try {
			URL url = new URL(urlText);
			BufferedImage image = ImageIO.read(url);
			LOGGER.debug("Gathered image : " +  image);
			return image;
		} catch (Exception e) { //if error occurs
			LOGGER.warn("Error during gathering image", e);
			return null;
		}
	}
	
	/**
	 * Gather XML data for a song search
	 * @param artist the artist to search
	 * @param title the title to search
	 * @return the XML data as {@code String} or null if error
	 */
	protected abstract String gatherXMLData(String artist, String title);

	/**
	 * Unmarshall the xml data as  data 
	 * @param xmlData the xml data as string
	 * @return the {@link Lfm} or null if error occured
	 */
	protected abstract T unmarshalllData(String xmlData);
	
	/**
	 * Get the image URL from the XML object
	 * @param xmlObject the XML object unmarshalled
	 * @return the image URL as {@link String}
	 */
	protected abstract String getImageURLFromXML(T xmlObject);
}
