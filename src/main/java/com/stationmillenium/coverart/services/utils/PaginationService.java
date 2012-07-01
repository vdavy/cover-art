/**
 * 
 */
package com.stationmillenium.coverart.services.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.beans.PaginationPropertiesBean;
import com.stationmillenium.coverart.beans.PaginationPropertiesBean.PaginationList;
import com.stationmillenium.coverart.dto.utils.PaginationDTO;

/**
 * Serivce pour gérer la pagination
 * @author vincent
 *
 */
@Service
public class PaginationService {

	//les propriétés de pagination
	@Autowired
	private PaginationPropertiesBean paginationBean;
	
	/**
	 * Obtenir un DTO initalisé
	 * @param list la liste en question de la page
	 * @return le DTO prérempli
	 */
	public PaginationDTO initPaginationDTO(PaginationList list) {
		PaginationDTO paginationDTO = new PaginationDTO();
		paginationDTO.setListOfOptions(paginationBean.getPaginationOptions()); //la liste des options possibles
		paginationDTO.setDefaultOption(paginationBean.getDefaultPagination().get(list)); //l'option par défaut pour la liste
		paginationDTO.setSelectedOption(paginationDTO.getDefaultOption()); //l'option courante
		paginationDTO.setPaginationList(list); //le type de pagination
		return paginationDTO;
	}
	
	/**
	 * Définir le nombre total de page
	 * @param dto le DTO pour définir et calculer
	 */
	public void setTotalPage(PaginationDTO dto) {
		dto.setTotalPage(dto.getItemsNumber() / dto.getSelectedOption() + 1);
	}
	
	/**
	 * Définir le nombre max d'item ainsi que le nombre total de page
	 * @param dto le DTO pour définir et calculer
	 * @param itemsNumber le nombre d'items max
	 */
	public void setItemsNumberAndTotalPages(PaginationDTO dto, int itemsNumber) {
		dto.setItemsNumber(itemsNumber); //definir le nombre max d'items
		setTotalPage(dto); //+ définir en même temps le nombre max de pages
	}
	
	/**
	 * Définir le premier élément de résultat en fonction de la page demandée et du nombre d'élement par page
	 * @param dto le DTO pour définir et calculer
	 */
	public void setFirstResultFollowingRequestedPage(PaginationDTO dto) {
		dto.setFirstElement(dto.getCurrentPage() * dto.getSelectedOption());
	}
	
	/**
	 * Définir le nombre d'élements par page et recalculer la pagination
	 * @param dto le DTO pour définir et calculer
	 * @param itemsNumberPerPage le nombre d'items par page
	 */
	public void setSelectedOption(PaginationDTO dto, int itemsNumberPerPage) {
		dto.setSelectedOption(itemsNumberPerPage); //definir le nombre d'élements par page
		setTotalPage(dto); //recalculer
	}
	
}
