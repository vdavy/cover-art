/**
 * 
 */
package com.stationmillenium.coverart.controllers.interfaces;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.stationmillenium.coverart.dto.utils.PaginationDTO;

/**
 * Classe abstraite que les controleurs pour la pagination doivent implémenter
 * @author davyv
 *
 */
public interface PaginatedControllerInterface {

	/**
	 * Renvoyer un DTO de pagination initialisé
	 * @return le DTO de pagination
	 */
	@ModelAttribute("paginationDTO")
	public PaginationDTO getPaginationDTO(); 

}
