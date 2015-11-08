/**
 * 
 */
package com.stationmillenium.coverart.configuration.beans.impl;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stationmillenium.coverart.beans.history.IcecastServerPropertiesBean;
import com.stationmillenium.coverart.configuration.beans.AbstractPropertiesBeanConfiguration;
import com.stationmillenium.coverart.exceptions.PropertyBeanException;

/**
 * Icecast server properties bean configuration
 * @author vincent
 *
 */
@Configuration
public class IcecastServerPropertiesBeanConfiguration extends AbstractPropertiesBeanConfiguration<IcecastServerPropertiesBean> {

	//property list
	@NotNull
	@Size(min = 3)
	private @Value("${icecast.url}") String icecastURL;

	@NotNull
	@Size(min = 1)
	private @Value("${icecast.songHistorySongSeparator}") String songHistorySongSeparator;
	
	@Override
	protected IcecastServerPropertiesBean buildBean() {
		IcecastServerPropertiesBean propertiesBean = new IcecastServerPropertiesBean();
		propertiesBean.setIcecastURL(icecastURL);
		propertiesBean.setSongHistorySongSeparator(songHistorySongSeparator);
		return propertiesBean;
	}
	
	/**
	 * Provide the produced bean
	 * @return the <code>IcecastServerPropertiesBean</code>
	 */
	@Bean
	@Qualifier("icecastServerPropertiesBean")
	public IcecastServerPropertiesBean getIcecastServerPropertiesBean() {
		IcecastServerPropertiesBean bean = assembleBean();
		return bean;
	}

	@Override
	protected void propertyCustomChecker() throws PropertyBeanException {
		//nothing to do		
	}

}
