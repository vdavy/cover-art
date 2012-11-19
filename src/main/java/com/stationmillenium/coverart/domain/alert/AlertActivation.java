/**
 * 
 */
package com.stationmillenium.coverart.domain.alert;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Class to enable of not all the alerts
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class AlertActivation {

	/**
	 * Enable or not the alert
	 */
	private boolean enableAlert;
	
	/**
	 * Alert type
	 */
	@NotNull
	private AlertType alertType;
	
}
