package com.stationmillenium.coverart.beans.history;

import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.stationmillenium.coverart.beans.interfaces.PropertyBeanInterface;

/**
 * Song history filter properties bean
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
public class SongHistoryFilterPropertiesBean implements PropertyBeanInterface {

	//properties
	private List<String> forbiddenKeywords;
	private int minimalLength;
	
}
