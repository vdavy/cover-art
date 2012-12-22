/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.resources.constants;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.LocalizableResource.GenerateKeys;

/**
 * Constants for admin GWT module playlist extract
 * @author vincent
 *
 */
@GenerateKeys
@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
public interface PlaylistExtractConstants extends Constants {

	@DefaultStringValue("Entrer les dates pour extraction de playlist")
	String getDefaultMessage();
	
	@DefaultStringValue("Date de début invalide")
	String getInvalidStartDate(); 
	
	@DefaultStringValue("Date de fin invalide")
	String getInvalidEndDate(); 
	
	@DefaultStringValue("Date de début et de fin invalides")
	String getInvalidStartEndDate(); 
	
	@DefaultStringValue("Extraction en cours")
	String getExtract(); 
	
	@DefaultStringValue("La date de début est après la date de fin")
	String getEndDateBeforeStartDate();
}
