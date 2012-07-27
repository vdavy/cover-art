/**
 * 
 */
package com.stationmillenium.coverart.services.covergraber;

import org.springframework.stereotype.Service;

/**
 * Interface for all cover grabers
 * @author vincent
 *
 */
@Service
public interface CoverGraberServiceInterface {

	/**
	 * Get the cover and return the image as byte array
	 * Return null if not found
	 * @param artist the name of the artist to search
	 * @param title the title
	 * @return the found image as byte array
	 */
	public byte[] grabCover(String artist, String title);
}
