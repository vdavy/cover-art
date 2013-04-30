/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.resources.messages;

import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.LocalizableResource.GenerateKeys;
import com.google.gwt.i18n.client.Messages;

/**
 * Messages for admin GWT module custom images config
 * @author vincent
 *
 */
@GenerateKeys
@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
public interface ConfigureCustomImagesMessages extends Messages {

	@DefaultMessage("Titre sélectionné : {0} - {1}") String getSelectedSong(String artist, String title);
	@DefaultMessage("Titre avec image sélectionné : {0} - {1}") String getSelectedCustomSong(String artist, String title);
	
}
