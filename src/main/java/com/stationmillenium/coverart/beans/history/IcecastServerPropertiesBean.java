package com.stationmillenium.coverart.beans.history;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.stationmillenium.coverart.beans.interfaces.PropertyBeanInterface;

/**
 * Icecast server properties bean
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
public class IcecastServerPropertiesBean implements PropertyBeanInterface {

	//properties
	private String icecastURL;	
	private String songHistorySongSeparator;
	
}
