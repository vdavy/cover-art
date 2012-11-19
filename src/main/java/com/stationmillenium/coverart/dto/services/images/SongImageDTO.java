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

	//image file name
	private String fileName;
	
	//image width
	private int width;
	
	//image height
	private int height;
	
}
