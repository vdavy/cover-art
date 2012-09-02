/**
 * 
 */
package com.stationmillenium.coverart.beans.covergraber;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.stationmillenium.coverart.beans.interfaces.PropertyBeanInterface;

/**
 * Last FM cover service properties bean
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
public class LastFMCoverServicePropertiesBean implements PropertyBeanInterface {

	//properties
	private String url;
	private String apiKey;
	private String textToRemoveInXML;
	private String imageSize;
	
}
