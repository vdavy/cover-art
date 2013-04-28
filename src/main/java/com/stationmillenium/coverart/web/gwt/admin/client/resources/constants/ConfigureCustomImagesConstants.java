/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.resources.constants;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.LocalizableResource.GenerateKeys;

/**
 * Constants for admin GWT module custom images config
 * @author vincent
 *
 */
@GenerateKeys
@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
public interface ConfigureCustomImagesConstants extends Constants {

	@DefaultStringValue("Erreur durant la recherche de titres") String getSongsSearchError();
	@DefaultStringValue("Recherche de titre terminée") String getSongsSearchFinished();
	
	@DefaultStringValue("Erreur durant la recherche de titres avec image") String getSongsWithImageSearchError();
	@DefaultStringValue("Recherche de titre avec image terminée") String getSongsWithImageSearchFinished();
	
	@DefaultStringValue("Erreur lors de la recherche des titres avec image") String getAllSongsWithImageListingError();
	@DefaultStringValue("Liste des titres avec images à jour") String getAllSongsWithImageListingOK();
	
	@DefaultStringValue("Aucun titre sélectionné !") String getNoSongSelected();
	@DefaultStringValue("Erreur lors du passage du titre comme titre avec image personnalisé") String getCustomImageSetError();
	@DefaultStringValue("Passage du titre comme titre avec image personnalisé OK") String getCustomImageSetOK();
	
	@DefaultStringValue("Confirmer la suppression de l'image personnalisée ?") String getConfirmCustomImageRemoval();
	@DefaultStringValue("Suppression de l'image personnalisée OK") String getCustomImageRemovalOK();
	@DefaultStringValue("Erreur lors de la suppression de l'image personnalisée") String getCustomImageRemovalError();
	
	@DefaultStringValue("Aucun fichier sélectionné") String getSelectedFileNull();
	@DefaultStringValue("Seules les images PNG et JPEG sont acceptées") String getSelectedFileWrongExtension();
	@DefaultStringValue("L'image personnalisée a bien été ajoutée") String getCustomImageProperlyAdded();
	@DefaultStringValue("Une erreur est survenue durant l'ajout de l'image personnalisée") String getCustomImageAddError();
	
}
