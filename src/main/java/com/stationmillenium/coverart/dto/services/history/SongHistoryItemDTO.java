/**
 * 
 */
package com.stationmillenium.coverart.dto.services.history;

import java.util.Calendar;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Item of song history, contains :
 * -Date
 * -Title
 * 
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
public class SongHistoryItemDTO {

	private Calendar playedDate;
	private String artist;
	private String title;
	
}
