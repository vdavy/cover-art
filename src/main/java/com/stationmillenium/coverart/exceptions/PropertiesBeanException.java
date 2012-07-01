/**
 * 
 */
package com.stationmillenium.coverart.exceptions;

/**
 * Classe abstraite pour les erreurs de propriétés
 * @author vincent
 *
 */
public abstract class PropertiesBeanException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropertiesBeanException(String message) {
		super(message);
	}
}
