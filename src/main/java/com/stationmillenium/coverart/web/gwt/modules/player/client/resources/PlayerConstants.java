package com.stationmillenium.coverart.web.gwt.modules.player.client.resources;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.LocalizableResource.GenerateKeys;

/**
 * Constants for the player GWT module
 * @author vincent
 *
 */
@GenerateKeys
@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
public interface PlayerConstants extends Constants {

	@DefaultStringValue("Player Millenium")
	String getWindowTitle();
	
	@DefaultStringValue("Indisponible")
	String songUnaivalaible();
	
	@DefaultStringArrayValue({"http://www.station-millenium.com/millenium.mp3", "http://www.station-millenium.com/millenium.ogg"})
	String[] streamURLs();
	
}
