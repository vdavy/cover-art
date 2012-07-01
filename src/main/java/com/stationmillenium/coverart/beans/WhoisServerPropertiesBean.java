package com.stationmillenium.coverart.beans;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * Classe pour les propriétés
 * @author vincent
 *
 */
@RooJavaBean
public class WhoisServerPropertiesBean implements ApplicationContextAware {

	//service de recherche opérateur
	private String operatorServiceWhoisServer;
	private String operatorServiceWhoisPort;
	private String operatorServiceWoisOption;
	
	//context
	private transient ApplicationContext context;
	
	/**
	 * Obtention du contexte
	 */
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
	
}
