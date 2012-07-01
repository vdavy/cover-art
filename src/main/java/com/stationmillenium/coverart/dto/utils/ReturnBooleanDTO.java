/**
 * 
 */
package com.stationmillenium.coverart.dto.utils;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * DTO de retour avec booléen et message texte
 * @author vincent
 *
 */
@RooJavaBean
@RooJson
@RooToString
public class ReturnBooleanDTO {

	/**
	 * Valeur de retour boolénne
	 */
	private boolean returnValue;
	
	/**
	 * Message éventuel
	 */
	private String message;
}
