package com.stationmillenium.coverart.domain.statuses;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.stationmillenium.coverart.domain.statuses.abstracts.AbstractServerStatus;

/**
 * Entity of the server states changes
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class ServerStatus extends AbstractServerStatus {
	 
	/**
	 * Boolean to note if server is up or down
	 */
	private boolean serverUp;
		
}
