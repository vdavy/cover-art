/**
 * 
 */
package com.stationmillenium.coverart.dto.services.covergraber;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Image as array
 * @author vincent
 *
 */
@RooToString
@RooJavaBean
public class ImageArray {
 
	private byte[] imageArray;
	private String fileExtension;
	
}
