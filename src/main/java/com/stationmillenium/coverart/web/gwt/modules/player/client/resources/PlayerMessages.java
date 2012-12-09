/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.modules.player.client.resources;

import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.LocalizableResource.GenerateKeys;
import com.google.gwt.i18n.client.Messages;

/**
 * Messages for the player GWT module
 * @author vincent
 *
 */
@GenerateKeys
@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
public interface PlayerMessages extends Messages {

	@DefaultMessage("{0} - {1}")
	public String currentSongText(String artist, String title);
	
	@DefaultMessage("Player Millenium | {0} - {1}")
	public String windowTitlecurrentSong(String artist, String title);
	
}
