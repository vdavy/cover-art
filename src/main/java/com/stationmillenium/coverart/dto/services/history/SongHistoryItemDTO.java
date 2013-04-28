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
public class SongHistoryItemDTO implements Cloneable {

	private Calendar playedDate;
	private String artist;
	private String title;
	private boolean customImage;
	
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
				if ((objToCompare.getArtist().equalsIgnoreCase(artist)) //if all are equals
						&& (objToCompare.getTitle().equalsIgnoreCase(title))
						&& (timeDelta >= -3000)
						&& (timeDelta <= 3000))
					return true;
				else 
					return false;				
			}
		}
	}
	
	@Override
	public SongHistoryItemDTO clone() throws CloneNotSupportedException {
		SongHistoryItemDTO returnedDTO = new SongHistoryItemDTO();
		returnedDTO.setArtist(artist);
		returnedDTO.setTitle(title);
		returnedDTO.setCustomImage(customImage);
		
		//process calendar
		if (playedDate != null) {
			Calendar returnedTime = Calendar.getInstance();
			returnedTime.setTimeInMillis(playedDate.getTimeInMillis());
			returnedDTO.setPlayedDate(returnedTime);
		}
		
		return returnedDTO;
	}
}
