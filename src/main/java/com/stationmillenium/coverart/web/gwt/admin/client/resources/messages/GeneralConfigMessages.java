/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.resources.messages;

import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.LocalizableResource.GenerateKeys;
import com.google.gwt.i18n.client.Messages;

/**
 * Messages for admin GWT module general config
 * @author vincent
 *
 */
@GenerateKeys
@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
public interface GeneralConfigMessages extends Messages {

	@DefaultMessage("Images récupérées : {0}")
	String getRecoveredSongsImagesCount(int count);
	
}
