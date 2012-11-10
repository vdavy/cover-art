package com.stationmillenium.coverart.web.gwt.history.client.resources;

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
public interface HistoryConstants extends Constants {

	@DefaultStringValue("Artiste")
	String getSongArtistTitle();
	
	@DefaultStringValue("Titre")
	String getSongTitleTitle();
	
	@DefaultStringValue("Date")
	String getSongDateTitle();
	
	@DefaultStringValue("Erreur lors du chargement de la playlist")
	String historyLoadingError();
	
	@DefaultStringValue("Erreur durant la recherche de playlist")
	String songSearchError();
	
}
