/**
 * 
 */
package com.stationmillenium.coverart.domain.alert;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertType;

/**
 * Email to notify on alert
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AlertEmail {

	@NotNull
	@Pattern(flags = Flag.CASE_INSENSITIVE, regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$")
	private String email;
	
	@NotNull	
	@ElementCollection(fetch = FetchType.EAGER)
	@Size(min = 1, max = 4)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<AlertType> alertType;
	
}
