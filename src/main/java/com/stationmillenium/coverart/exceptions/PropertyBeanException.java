/**
 * 
 */
package com.stationmillenium.coverart.exceptions;

/**
 * Property error exception
 * @author vincent
 *
 */
public class PropertyBeanException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * New property bean exception if property not properly set
	 * @param propertyName the name of the property
	 * @param propertyError the error occured on this property
	 */
	public PropertyBeanException(String propertyName, String propertyError) {
		super(propertyName + " : " + propertyError);
	}
	
	/**
	 * New property bean exception - other error type
	 * @param propertyName the name of the property
	 * @param e the exception
	 */
	public PropertyBeanException(String propertyName, Exception e) {
		super(propertyName + " : other error", e);
	}
}
