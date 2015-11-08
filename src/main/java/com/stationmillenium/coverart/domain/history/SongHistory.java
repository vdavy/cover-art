package com.stationmillenium.coverart.domain.history;

import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Entity representing a song history (played time) 
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SongHistory {

	//played date on server
	@NotNull
	private Calendar playedDate;
	
	//associated song
	@ManyToOne
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private SongItem song;
	
}
