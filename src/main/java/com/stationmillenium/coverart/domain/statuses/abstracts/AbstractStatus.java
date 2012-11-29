package com.stationmillenium.coverart.domain.statuses.abstracts;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Entity of the server states changes
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord(inheritanceType = "SINGLE_TABLE")
public abstract class AbstractStatus {
	
	/**
	 * Date of the change
	 */
	@NotNull
	private Calendar dateOfChange;
	
}
