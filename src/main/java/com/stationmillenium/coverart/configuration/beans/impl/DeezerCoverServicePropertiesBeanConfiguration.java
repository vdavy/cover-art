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

import com.stationmillenium.coverart.beans.covergraber.DeezerCoverServicePropertiesBean;
import com.stationmillenium.coverart.configuration.beans.AbstractPropertiesBeanConfiguration;
import com.stationmillenium.coverart.exceptions.PropertyBeanException;

/**
 * Deezer cover service properties bean configuration
 * @author vincent
 *
 */
@Configuration
public class DeezerCoverServicePropertiesBeanConfiguration extends AbstractPropertiesBeanConfiguration<DeezerCoverServicePropertiesBean> {

	//property list
	@NotNull
	@Size(min = 5)
	@Pattern(regexp = "^http://.+")
	private @Value("${deezerCover.url}") String url;
	
	@NotNull
	@Pattern(regexp = "[a-z]{3,4}")
	private @Value("${deezerCover.outputFormat}") String outputFormat;
		
	@Override
	protected DeezerCoverServicePropertiesBean buildBean() {
		DeezerCoverServicePropertiesBean propertiesBean = new DeezerCoverServicePropertiesBean();
		propertiesBean.setUrl(url);
		propertiesBean.setOutputFormat(outputFormat);
		return propertiesBean;
	}
	
	@Override
	protected void propertyCustomChecker() throws PropertyBeanException {
		//nothing to do
	}
	
	/**
	 * Provide the produced bean
	 * @return the <code>DeezerCoverServicePropertiesBean</code>
	 */
	@Bean
	@Qualifier("deezerCoverServicePropertiesBean")
	public DeezerCoverServicePropertiesBean getDeezerCoverServicePropertiesBean() {
		return assembleBean();
	}
	
}
