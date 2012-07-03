/**
 * 
 */
package com.stationmillenium.coverart.configuration.beans.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.stationmillenium.coverart.beans.ShoutcastServerPropertiesBean;
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
	
	@Override
	protected ShoutcastServerPropertiesBean buildBean() {
		ShoutcastServerPropertiesBean propertiesBean = new ShoutcastServerPropertiesBean();
		propertiesBean.setShoutcastServerSongHistoryPage("http://" + shoutcastServer + ":" + shoutcastPort + shoutcastSongHistoryPage );
		propertiesBean.setShoutcastServerStatusPage("http://" + shoutcastServer + ":" + shoutcastPort + shoutcastStatusPage );
		return propertiesBean;
	}
	
	@Override
	protected void propertyCustomChecker() throws PropertyBeanException {
		checkValueAgainstExpress("(T(java.lang.Integer).parseInt(#property) >= 1) and (T(java.lang.Integer).parseInt(#property) < 65535)", "shoutcastPort"); //check port range
	}
}
