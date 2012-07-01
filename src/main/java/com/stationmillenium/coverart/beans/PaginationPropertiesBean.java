/**
 * 
 */
package com.stationmillenium.coverart.beans;

import java.util.List;
import java.util.Map;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Bean contenant les propriétés pour la pagination
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
public class PaginationPropertiesBean {

	/**
	 * Listes où il y a de la pagination
	 * @author vincent
	 *
	 */
	public enum PaginationList {
		USER,
		STREAM,
		VIP_VISITOR;
	}
	
	//liste des options de paginations possibles
	private List<Integer> paginationOptions;
	
	//pagination par défaut
	private Map<PaginationList, Integer> defaultPagination;
}
