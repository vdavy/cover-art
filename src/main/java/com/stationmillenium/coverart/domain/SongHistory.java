/**
 * 
 */
package com.stationmillenium.coverart.domain;

import java.util.Calendar;

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
public class SongHistory {

	//artist name
	@NotNull
	@Size(min = 1, max = 200)
	private String artist;
	
	//song title
	@NotNull
	@Size(min = 1, max = 200)
	private String title;
	
	//played date on shoutcast server
	@NotNull
	private Calendar playedDate;
	
}
