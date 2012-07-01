package com.stationmillenium.coverart.aspects;

import java.beans.PropertyDescriptor;

import javax.validation.constraints.Size;

/**
 * Aspect pour obtenir la longueur sur un champ sur une classe
 * @author vincent
 *
 */
privileged aspect TruncateFieldAspect {

	//déclarer sur les aspects
	declare parents : com.stationmillenium.coverart.domain..* implements TruncateField;

	//interface à implémenter
	private interface TruncateField {		
		public String truncateField(String fieldName);
	}
	
	//implementation de l'interface
	public String TruncateField.truncateField(String fieldName) {
		String value = getFieldValue(fieldName, this); //valeur du champ		
		int fieldMaxLength = getFieldLength(fieldName, this); //longeur max du champ
		if ((value != null) && (value.length() > fieldMaxLength))  //test si trop long
			value = value.substring(0, fieldMaxLength); //couper
		
		return value; //retour
	}
	
	
	/**
	 * Donne la valeur max d'un champ via l'annotation size
	 * @param fieldName le nom du champ
	 * @param instance l'instance
	 * @return la valeur du max de size si ok, 0 sinon
	 */
	private static int getFieldLength(String fieldName, Object instance) {		
		try {
			Size sizeAnnotation = instance.getClass().getDeclaredField(fieldName).getAnnotation(Size.class);
			if (sizeAnnotation != null) 
				return sizeAnnotation.max();
			else 
				return 0;
		} catch (SecurityException e) {
			e.printStackTrace();
			return 0;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	/**
	 * Obtenir la valeur du champ
	 * @param fieldName le nom du champ
	 * @param instance l'instance
	 * @return la valeur
	 */
	private static String getFieldValue(String fieldName, Object instance) {
		try {
			PropertyDescriptor propDesc = new PropertyDescriptor(fieldName, instance.getClass()); //property access
			String value = (String) propDesc.getReadMethod().invoke(instance, (Object[]) null); //valeur du champ
			return value;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;			
		} 
	}
}
