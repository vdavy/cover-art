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

import com.stationmillenium.coverart.beans.history.ShoutcastServerPropertiesBean;
import com.stationmillenium.coverart.configuration.beans.AbstractPropertiesBeanConfiguration;
import com.stationmillenium.coverart.exceptions.PropertyBeanException;

/**
 * Shoutcast server properties bean configuration
 * @author vincent
 *
 */
@Configuration
public class ShoutcastServerPropertiesBeanConfiguration extends AbstractPropertiesBeanConfiguration<ShoutcastServerPropertiesBean> {

	//property list
	@NotNull
	@Size(min = 3)
	private @Value("${shoutcast.fdqn}") String shoutcastServer;
	
	@NotNull
	@Pattern(regexp = "[\\d]+")
	private @Value("${shoutcast.port}") String shoutcastPort;
	
	@NotNull
	@Size(min = 1)
	@Pattern(regexp = "^/.*")
	private @Value("${shoutcast.songHistoryPage}") String shoutcastSongHistoryPage;
	
	@NotNull
	@Size(min = 1)
	@Pattern(regexp = "^/.*")
	private @Value("${shoutcast.statusPage}") String shoutcastStatusPage;
	
	@NotNull
	@Size(min = 10)
	private @Value("${shoutcast.userAgent}") String userAgent;
	
	@NotNull
	@Size(min = 10)
	private @Value("${shoutcast.statusPageSelect}") String statusPageSelect;
	
	@NotNull
	@Size(min = 10)
	private @Value("${shoutcast.statusPageSelectText}") String statusPageSelectText;
	
	@NotNull
	@Size(min = 10)
	private @Value("${shoutcast.songHistoryPageSelect}") String songHistoryPageSelect;

	@NotNull
	@Size(min = 1)
	private @Value("${shoutcast.songHistorySongSeparator}") String songHistorySongSeparator;
	
	@NotNull
	@Size(min = 1)
	private @Value("${shoutcast.songHistoryDateSeparator}") String songHistoryDateSeparator;
	
	@Override
	protected ShoutcastServerPropertiesBean buildBean() {
		ShoutcastServerPropertiesBean propertiesBean = new ShoutcastServerPropertiesBean();
		propertiesBean.setShoutcastServerSongHistoryPage("http://" + shoutcastServer + ":" + shoutcastPort + shoutcastSongHistoryPage );
		propertiesBean.setShoutcastServerStatusPage("http://" + shoutcastServer + ":" + shoutcastPort + shoutcastStatusPage );
		propertiesBean.setUserAgent(userAgent);
		propertiesBean.setStatusPageSelect(statusPageSelect);
		propertiesBean.setStatusPageSelectText(statusPageSelectText);
		propertiesBean.setSongHistoryPageSelect(songHistoryPageSelect);
		propertiesBean.setSongHistorySongSeparator(songHistorySongSeparator);
		propertiesBean.setSongHistoryDateSeparator(songHistoryDateSeparator);
		return propertiesBean;
	}
	
	/**
	 * Provide the produced bean
	 * @return the <code>ShoutcastServerPropertiesBean</code>
	 */
	@Bean
	@Qualifier("shoutcastServerPropertiesBean")
	public ShoutcastServerPropertiesBean getShoutcastServerPropertiesBean() {
		ShoutcastServerPropertiesBean bean = assembleBean();
		return bean;
	}

	@Override
	protected void propertyCustomChecker() throws PropertyBeanException {
		//nothing to do		
	}

}
