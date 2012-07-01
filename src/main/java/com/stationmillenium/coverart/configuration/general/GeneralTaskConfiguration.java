/**
 * 
 */
package com.stationmillenium.coverart.configuration.general;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration générale des taches asynchrones du projet
 * @author vincent
 *
 */
@Configuration
@EnableAsync
@EnableScheduling
public class GeneralTaskConfiguration {

}
