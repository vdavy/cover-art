/**
 * 
 */
package com.stationmillenium.coverart.configuration.general;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Dozer mapping
 * @author vincent
 *
 */
@Configuration
public class DozerConfiguration {

	/**
	 * Get the {@link DozerBeanMapper}
	 * @return the <code>DozerBeanMapper</code>
	 */
	@Bean
	public DozerBeanMapper getDozerBeanMapper() {
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		return dozerBeanMapper;
	}
}
