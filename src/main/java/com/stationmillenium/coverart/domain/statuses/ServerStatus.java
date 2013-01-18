package com.stationmillenium.coverart.domain.statuses;

import javax.persistence.Cacheable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.stationmillenium.coverart.domain.statuses.abstracts.AbstractStatus;

/**
 * Entity of the server states changes
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServerStatus extends AbstractStatus {
	 
	/**
	 * Boolean to note if server is up or down
	 */
	private boolean serverUp;
		
}
