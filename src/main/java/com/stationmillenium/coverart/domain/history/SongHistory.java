package com.stationmillenium.coverart.domain.history;

import java.util.Calendar;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
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
@Indexed
public class SongHistory {

	//played date on shoutcast server
	@NotNull
	@Field
	private Calendar playedDate;
	
	//associated song
	@ManyToOne
	private SongItem song;
	
}
