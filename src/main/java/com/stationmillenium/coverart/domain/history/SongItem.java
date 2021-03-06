package com.stationmillenium.coverart.domain.history;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Entity representing a song in the song history
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@Indexed
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SongItem {

	//artist name
	@NotNull
	@Size(min = 1, max = 200)
	@Field
	private String artist;
	
	//song title
	@NotNull
	@Size(min = 1, max = 200)
	@Field
	private String title;
	
	//if custom image set
	@Field
	private boolean customImage;
	
	//associated image
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private SongHistoryImage image;
	
	//associated playing times
	@OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
	@OrderBy("playedDate DESC")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<SongHistory> playedTimes;

}
