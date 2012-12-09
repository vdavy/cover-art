/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.modules.history.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * DTO for the song
 * @author vincent
 *
 */
//@SuppressWarnings("serial")
public class HistoryGWTDTO implements IsSerializable {

	//fields
	private String artist;
	private String title;
	private Date playedDate;
	private String imagePath;
	private String imageWidth;
	private String imageHeight;
	
	//to string
	@Override
	public String toString() {
		return "SongDTO [artist=" + artist + ", title=" + title
				+ ", playedDate=" + playedDate + ", imagePath=" + imagePath
				+ ", imageWidth=" + imageWidth + ", imageHeight=" + imageHeight
				+ "]";
	}
	
	/**
	 * Equals only if artist and title equal 
	 * @param obj object to compare
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HistoryGWTDTO) {
			if ((artist != null) && (title != null)) {
				HistoryGWTDTO objToCompare = (HistoryGWTDTO) obj;
				return artist.equals(objToCompare.getArtist()) && title.equals(objToCompare.getTitle());
			} else
				return false;				
		} else 
			return false;
	}
	
	//getters - setters
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPlayedDate() {
		return playedDate;
	}

	public void setPlayedDate(Date playedDate) {
		this.playedDate = playedDate;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}
	
}
