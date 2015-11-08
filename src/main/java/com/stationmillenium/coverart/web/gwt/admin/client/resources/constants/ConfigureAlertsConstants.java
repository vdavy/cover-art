/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.resources.constants;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.LocalizableResource.GenerateKeys;

/**
 * Constants for admin GWT module alert config
 * @author vincent
 *
 */
@GenerateKeys
@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
public interface ConfigureAlertsConstants extends Constants {

	@DefaultStringValue("Impossible de charger la liste des alertes")
	String getLoadAlertActivationError();
	
	@DefaultStringValue("Impossible de charger l'alerte sélectionnée")
	String getLoadSelectedAlertActivationError();
	
	@DefaultStringValue("Impossible de savegarder l'alerte sélectionnée")
	String getSaveSelectedAlertActivationError();
	
	@DefaultStringValue("Alerte sauvegardée")
	String getSavedSelectedAlert();

	@DefaultStringValue("Sauvegarde en cours...")
	String getSavingSelectedAlert();
	
	@DefaultStringValue("Interface prête - en attente...")
	String getReadyState();
	
	@DefaultStringValue("Impossible de charger la liste des emails pour les alertes")
	String getLoadAlertEmailError();
	
	@DefaultStringValue("Impossible de charger l'adresse mail sélectionnée")
	String getLoadSelectedAlertEmailError();
	
	@DefaultStringValue("Impossible de savegarder l'email sélectionné")
	String getSaveSelectedAlertEmailError(); 
	
	@DefaultStringValue("Des erreurs ont été détectées...")
	String getSaveSelectedAlertEmailValidationErrors(); 
	
	@DefaultStringValue("Nouvelle adresse mail d'alerte")
	String getNewAlertEmail();
	
	@DefaultStringValue("Impossible de supprimer l'email sélectionné")
	String getDeleteSelectedAlertEmailError();
	
	@DefaultStringValue("Email sélectionné supprimé")
	String getDeleteSelectedAlertEmail();
	
	@DefaultStringValue("Suppresson en cours...")
	String getDeletingSelectedAlertEmail();
	
	@DefaultStringValue("Confirmer la suppression de l'email ?")
	String getConfirmAlertEmailDeletion();
	
	//alertes names
	@DefaultStringValue("Icecast") String getIcecastAlertName();
	@DefaultStringValue("FM") String getFMAlertName();
	@DefaultStringValue("Playlist") String getPlaylistAlertName();
	@DefaultStringValue("Images perso") String getCustomImagesAlertName();
	
}
