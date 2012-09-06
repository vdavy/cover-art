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

	/**
	 * Equals only if title, artist and played date are not null and equal each other.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) //if null
			return false;
		else if (!(obj instanceof SongHistoryItemDTO)) //if not good instance
			return false;
		else {
			SongHistoryItemDTO objToCompare = (SongHistoryItemDTO) obj;
			if ((objToCompare.getArtist() == null)  //if anything null
					|| (objToCompare.getTitle() == null)
					|| (objToCompare.getPlayedDate() == null)
					|| (playedDate == null))
				return false;
			else {
				long timeDelta = objToCompare.getPlayedDate().getTimeInMillis() - playedDate.getTimeInMillis();
				if ((objToCompare.getArtist().equals(artist)) //if all are equals
						&& (objToCompare.getTitle().equals(title))
						&& (timeDelta >= -3000)
						&& (timeDelta <= 3000))
					return true;
				else 
					return false;				
			}
		}
	}
}
