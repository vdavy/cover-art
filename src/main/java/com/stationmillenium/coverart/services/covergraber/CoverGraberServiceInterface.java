/**
 * 
 */
package com.stationmillenium.coverart.services.covergraber;

import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.dto.services.covergraber.ImageArray;

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
	 * @return the found image as {@link ImageArray}
	 */
	public ImageArray grabCover(String artist, String title);
}
