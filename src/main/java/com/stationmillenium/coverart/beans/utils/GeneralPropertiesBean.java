/**
 * 
 */
package com.stationmillenium.coverart.beans.utils;

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
	
}
