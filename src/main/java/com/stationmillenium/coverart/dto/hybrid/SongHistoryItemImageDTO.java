/**
 * 
 */
package com.stationmillenium.coverart.dto.hybrid;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.dto.services.images.SongImageDTO;

/**
 * DTO containing {@link SongHistoryItemDTO} a	nd {@link SongImageDTO}
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
public class SongHistoryItemImageDTO {

	//song item
	private SongHistoryItemDTO songHistoryItemDTO = new SongHistoryItemDTO();
	
	//image of song
	private SongImageDTO songImageDTO = new SongImageDTO();

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SongHistoryItemImageDTO [songHistoryItemDTO="
				+ songHistoryItemDTO + ", songImageDTO=" + songImageDTO + "]";
	}
	
}
