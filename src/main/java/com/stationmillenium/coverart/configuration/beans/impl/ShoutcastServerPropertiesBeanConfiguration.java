/**
 * 
 */
package com.stationmillenium.coverart.configuration.beans.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.stationmillenium.coverart.beans.history.ShoutcastServerPropertiesBean;
import com.stationmillenium.coverart.configuration.beans.AbstractPropertiesBeanConfiguration;
import com.stationmillenium.coverart.exceptions.PropertyBeanException;

/**
 * Shoutcast server properties bean configuration
 * @author vincent
 *
 */
@Configuration
@RooJavaBean
public class ShoutcastServerPropertiesBeanConfiguration extends AbstractPropertiesBeanConfiguration<ShoutcastServerPropertiesBean> {

	//property list
	private @Value("${shoutcast.fdqn}") String shoutcastServer;
	private @Value("${shoutcast.port}") String shoutcastPort;
	private @Value("${shoutcast.songHistoryPage}") String shoutcastSongHistoryPage;
	private @Value("${shoutcast.statusPage}") String shoutcastStatusPage;
	private @Value("${shoutcast.userAgent}") String userAgent;
	private @Value("${shoutcast.statusPageSelect}") String statusPageSelect;
	private @Value("${shoutcast.statusPageSelectText}") String statusPageSelectText;
	private @Value("${shoutcast.songHistoryPageSelect}") String songHistoryPageSelect;
	private @Value("${shoutcast.songHistorySongSeparator}") String songHistorySongSeparator;
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
	
	@Override
	protected void propertyCustomChecker() throws PropertyBeanException {
		checkValueAgainstExpress("(T(java.lang.Integer).parseInt(#property) >= 1) and (T(java.lang.Integer).parseInt(#property) < 65535)", "shoutcastPort"); //check port range
	}

	/**
	 * Provide the produced bean
	 * @return the <code>ShoutcastServerPropertiesBean</code>
	 */
	@Bean
	@Qualifier("shoutcastServerPropertiesBean")
	public ShoutcastServerPropertiesBean getShoutcastServerPropertiesBean() {
		return assembleBean();
	}
	
}
