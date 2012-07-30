/**
 * 
 */
package com.stationmillenium.coverart.configuration.beans.impl;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stationmillenium.coverart.beans.covergraber.LastFMCoverServicePropertiesBean;
import com.stationmillenium.coverart.configuration.beans.AbstractPropertiesBeanConfiguration;
import com.stationmillenium.coverart.exceptions.PropertyBeanException;

/**
 * Last FM cover service properties bean configuration
 * @author vincent
 *
 */
@Configuration
public class LastFMCoverServicePropertiesBeanConfiguration extends AbstractPropertiesBeanConfiguration<LastFMCoverServicePropertiesBean> {

	//property list
	@NotNull
	@Size(min = 5)
	@Pattern(regexp = "^http://.+")
	private @Value("${lastFMCover.url}") String url;
	
	@NotNull
	@Size(min = 32, max = 32)
	private @Value("${lastFMCover.apiKey}") String apiKey;

	@NotNull
	@Size(min = 1)
	private @Value("${lastFMCover.textToRemoveInXML}") String textToRemoveInXML;
	
	@NotNull
	@Size(min = 1)
	private @Value("${lastFMCover.imageSize}") String imageSize;
		
	@Override
	protected LastFMCoverServicePropertiesBean buildBean() {
		LastFMCoverServicePropertiesBean propertiesBean = new LastFMCoverServicePropertiesBean();
		propertiesBean.setUrl(url);
		propertiesBean.setApiKey(apiKey);
		propertiesBean.setTextToRemoveInXML(textToRemoveInXML);
		propertiesBean.setImageSize(imageSize);
		return propertiesBean;
	}
	
	@Override
	protected void propertyCustomChecker() throws PropertyBeanException {
		//nothing to do
	}
	
	/**
	 * Provide the produced bean
	 * @return the <code>LastFMCoverServicePropertiesBean</code>
	 */
	@Bean
	@Qualifier("lastFMCoverServicePropertiesBean")
	public LastFMCoverServicePropertiesBean getLastFMCoverServicePropertiesBean() {
		return assembleBean();
	}
	
}
