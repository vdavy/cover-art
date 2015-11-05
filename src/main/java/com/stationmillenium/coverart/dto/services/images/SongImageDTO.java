/**
 * 
 */
package com.stationmillenium.coverart.dto.services.images;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Entity for image for song history
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
public class SongImageDTO {

	/**
	 * Available images providers
	 * @author vincent
	 *
	 */
	public enum Provider {
		LAST_FM,
		DEEZER,
		CUSTOM
	}
	
	//image file name
	private String fileName;
	
	//image width
	private int width;
	
	//image height
	private int height;
	
	//image provider
	private Provider provider;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SongImageDTO [fileName=" + fileName + ", width=" + width
				+ ", height=" + height + ", provider=" + provider + "]";
	}
	
	
	
}
