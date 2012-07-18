package com.stationmillenium.coverart.domain;

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
@RooJpaActiveRecord
public class ServerStatus {
	
	/**
	 * Boolean to note if server is up or down
	 */
	private boolean serverUp;
	
	/**
	 * Date of the change
	 */
	@NotNull
	private Calendar dateOfChange;
	
}
