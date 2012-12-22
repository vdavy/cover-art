/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.resources.constants;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.LocalizableResource.GenerateKeys;

/**
 * Constants for admin GWT module status report
 * @author vincent
 *
 */
@GenerateKeys
@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
public interface StatusReportConstants extends Constants {

	@DefaultStringValue("Date du changement")
	String getDateOfChange();
	
	@DefaultStringValue("Statut")
	String getStatus();
	
	@DefaultStringValue("FM up")
	String getFMUp();
	
	@DefaultStringValue("FM down")
	String getFMDown();
	
	@DefaultStringValue("Playlist up")
	String getPlaylistUp();
	
	@DefaultStringValue("Playlist down")
	String getPlaylistDown();
	
	@DefaultStringValue("Serveur up")
	String getServerUp();
	
	@DefaultStringValue("Serveur down")
	String getServerDown();
	
	@DefaultStringValue("Erreur lors du chargement des status")
	String getStatusesLoadingError();
	
	@DefaultStringValue("Alerte sélectionnée : FM")
	String getSelectedAlertFM();
	
	@DefaultStringValue("Alerte sélectionnée : playlist inactive")
	String getSelectedAlertPlaylist();
	
	@DefaultStringValue("Alerte sélectionnée : serveur Shoutcast")
	String getSelectedAlertServer();
	
	@DefaultStringValue("Alerte actuellement non active")
	String getSelectedAlertDisabled();
	
	@DefaultStringValue("Alerte actuellement active")
	String getSelectedAlertEnabled();
	
}
