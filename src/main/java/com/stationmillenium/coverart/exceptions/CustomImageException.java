/**
 * 
 */
package com.stationmillenium.coverart.exceptions;

import com.stationmillenium.coverart.services.covergraber.CustomImageService;

/**
 * Exception for the {@link CustomImageService}
 * @author vincent
 *
 */
public class CustomImageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new {@link CustomImageException} with an inside exception
	 * @param e the embedded exception
	 */
	public CustomImageException(Exception e) {
		super(e);
	}
	
}
