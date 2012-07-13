/**
 * 
 */
package com.stationmillenium.coverart.dto.utils;

import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.stationmillenium.coverart.beans.utils.PaginationPropertiesBean.PaginationList;
;

/**
 * Les données à mettre en session pour la pagination
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
public class PaginationDTO {

	private int currentPage; //page courante
	private int totalPage; //nombre total de pages
	private int itemsNumber; //nombre total d'éléments
	private int firstElement; //premier élément
	private List<Integer> listOfOptions; //liste d'options possibles pour affichage
	private int selectedOption; //option d'affichage sélectionnée
	private int defaultOption; //option à afficher par défaut
	private PaginationList paginationList; //liste en question
	
}
