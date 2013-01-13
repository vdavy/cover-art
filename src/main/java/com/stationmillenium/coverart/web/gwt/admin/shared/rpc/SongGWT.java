/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.shared.rpc;

import java.io.Serializable;

/**
 * DTO for a song (artist & title)
 * @author vincent
 *
 */
@SuppressWarnings("serial")
public class SongGWT implements Serializable {

	private String artist;
	private String title;
	
	/**
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}
	
	/**
	 * @param artist the artist to set
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Song [artist=" + artist + ", title=" + title + "]";
	}
	
}
