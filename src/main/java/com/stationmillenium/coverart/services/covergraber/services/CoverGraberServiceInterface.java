/**
 * 
 */
package com.stationmillenium.coverart.services.covergraber.services;

import java.awt.image.BufferedImage;

import org.springframework.stereotype.Service;

/**
 * Interface for all cover grabers
 * @author vincent
 *
 */
@Service
public interface CoverGraberServiceInterface {

	/**
	 * Get the cover and return the image as {@link ImageArray}
	 * Return null if not found
	 * @param artist the name of the artist to search
	 * @param title the title
	 * @return the found image as {@link BufferedImage}
	 */
	public BufferedImage grabCover(String artist, String title);
}
