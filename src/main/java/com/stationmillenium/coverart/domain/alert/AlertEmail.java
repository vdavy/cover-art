/**
 * 
 */
package com.stationmillenium.coverart.domain.alert;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Email to notify on alert
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class AlertEmail {

	@NotNull
	@Pattern(flags = Flag.CASE_INSENSITIVE, regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$")
	private String email;
	
	@NotNull	
	@ElementCollection
	private Set<AlertType> alertType;
	
}
