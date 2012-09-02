/**
 * 
 */
package com.stationmillenium.coverart.beans.covergraber;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.stationmillenium.coverart.beans.interfaces.PropertyBeanInterface;

/**
 * Deezer cover service properties bean
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
public class DeezerCoverServicePropertiesBean implements PropertyBeanInterface {

	//properties
	private String url;
	private String outputFormat;
	
}
