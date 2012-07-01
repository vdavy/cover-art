/**
 * 
 */
package com.stationmillenium.coverart.configuration.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.stationmillenium.coverart.beans.WhoisServerPropertiesBean;

/**
 * Classe de configuration du bean des propriétés
 * @author vincent
 *
 */
@Configuration
public class WhoisServerPropertiesBeanConfiguration {

	//liste des propriétés
	private @Value("${whoisServer.fdqn}") String operatorServiceWhoisServer;
	private @Value("${whoisServer.port}") String operatorServiceWhoisPort;
	private @Value("${whoisServer.options}") String operatorServiceWoisOption;
	
	/**
	 * Définir le bean des propriétés du serveur Whois
	 * @return le PropertiesBean
	 */
	@Bean
	@Scope("singleton")
	public WhoisServerPropertiesBean getWhoisServerPropertiesBean() {
		WhoisServerPropertiesBean propertiesBean = new WhoisServerPropertiesBean();
		propertiesBean.setOperatorServiceWhoisServer(operatorServiceWhoisServer);
		propertiesBean.setOperatorServiceWhoisPort(operatorServiceWhoisPort);
		propertiesBean.setOperatorServiceWoisOption(operatorServiceWoisOption);
		return propertiesBean;
	}
}
