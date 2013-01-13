/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.resources.constants;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.LocalizableResource.GenerateKeys;

/**
 * Constants for admin GWT module general config
 * @author vincent
 *
 */
@GenerateKeys
@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
public interface GeneralConfigConstants extends Constants {

	@DefaultStringValue("Impossible de récupérer le statut des services")
	String getServicesStatusesError();
	
	@DefaultStringValue("Service actif")
	String getServiceEnabled();
	
	@DefaultStringValue("Service désactivé")
	String getServiceDisabled();
	
	@DefaultStringValue("Mise à jour en cours du service...")
	String getServiceUpdate();
	
	@DefaultStringValue("Impossible de récupérer le titre en cours")
	String getCurrentTitleError();
	
	@DefaultStringValue("Impossible de récupérer le statut d'indéxation")
	String getIndexingStatusError();
	
	@DefaultStringValue("Indexation terminée")
	String getIndexingStatusFinished();
	
	@DefaultStringValue("Indexation en cours")
	String getIndexingStatusRunning();
	
	@DefaultStringValue("Indexation terminée")
	String getIndexingStatusEnded();
	
	@DefaultStringValue("Impossible de lancer l'indexation")
	String getLaunchIndexingError();
	
	@DefaultStringValue("Indexation en cours...")
	String getIndexingLaunched();
	
	@DefaultStringValue("Erreur durant le changement de statut")
	String getServiceStatusChangeError();
	
	@DefaultStringValue("Statut du service changé")
	String getServiceStatusChangeOK();
	
	@DefaultStringValue("Interface prête - en attente...")
	String getReadyState();
	
	@DefaultStringValue("Récupération des images en cours")
	String getRecoveryStatusRunning();

	@DefaultStringValue("Récupération en cours...")
	String getRecoveryLaunched();
	
	@DefaultStringValue("Impossible de lancer la récupération des images")
	String getLaunchRecoveryError();
	
	@DefaultStringValue("Impossible de récupérer le statut de la récupération")
	String getRecoveryStatusError();
	
	@DefaultStringValue("Récupération des images terminée")
	String getRecoveryStatusEnded();
	
	@DefaultStringValue("Récupération terminée")
	String getRecoveryStatusFinished();
	
	@DefaultStringValue("Impossible de récupérer la liste des images récupérées")
	String getRecoveredSongsListError();
	
}
