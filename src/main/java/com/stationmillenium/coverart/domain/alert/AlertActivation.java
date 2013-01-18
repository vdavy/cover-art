/**
 * 
 */
package com.stationmillenium.coverart.domain.alert;

import javax.persistence.Cacheable;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertType;

/**
 * Class to enable of not all the alerts
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
