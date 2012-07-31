package com.stationmillenium.coverart.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class SongItem {

	//artist name
	@NotNull
	@Size(min = 1, max = 200)
	private String artist;
	
	//song title
	@NotNull
	@Size(min = 1, max = 200)
	private String title;
	
	//associated image
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private SongHistoryImage image;
	
	//associated playing times
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<SongHistory> playedTimes;
	
}
