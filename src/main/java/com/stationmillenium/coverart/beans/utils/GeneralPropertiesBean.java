/**
 * 
 */
package com.stationmillenium.coverart.beans.utils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.stationmillenium.coverart.beans.interfaces.PropertyBeanInterface;

/**
 * Bean with main configuration
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
public class GeneralPropertiesBean implements PropertyBeanInterface {

	@NotNull
	@Size(min = 1)
	@Pattern(regexp = "^/\\w*")
	private String coverImagesPath;
	
	@NotNull
	@Size(min = 1)
	@Pattern(regexp = "^/\\w*")
	private String fallbackPath;
	
	@NotNull
	@Size(min = 3, max = 4)
	@Pattern(regexp = "[a-z]{3-4}")
	private String coverImagesExtension;
	
	@Min(1)
	private int playlistUpdateTimeout;
	
	@Min(1)
	@Max(120)
	private int alertTimeout;
	
	@Min(1)
	@Max(10080)
	private int historyDisplayMinMinutes;
	
	@Min(10)
	@Max(240)
	private int dateSearchDelta;
	
	@Min(10)
	@Max(120)
	private int fmAlertTimeout;
	
	@Min(1)
	@Max(1000)
	private int historySearchMaxResults;
		
}
