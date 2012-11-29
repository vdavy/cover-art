package com.stationmillenium.coverart.domain.statuses;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.stationmillenium.coverart.domain.statuses.abstracts.AbstractStatus;

/**
 * Entity of the fm states changes
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class FMStatus extends AbstractStatus {
	 
	/**
	 * Boolean to note if fm is up or down
	 */
	private boolean fmUp;
		
}
