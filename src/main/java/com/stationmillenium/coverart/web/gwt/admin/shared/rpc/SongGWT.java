/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.shared.rpc;

import java.io.Serializable;

/**
 * DTO for a song (artist & title, custom image and image data)
 * @author vincent
 *
 */
@SuppressWarnings("serial")
public class SongGWT implements Serializable {

	//fields
	private String artist;
	private String title;
	private boolean customImage;
	private String imagePath;
	private String imageWidth;
	private String imageHeight;
	
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
	
	/**
	 * @return the customImage
	 */
	public boolean isCustomImage() {
		return customImage;
	}

	/**
	 * @param customImage the customImage to set
	 */
	public void setCustomImage(boolean customImage) {
		this.customImage = customImage;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the imageWidth
	 */
	public String getImageWidth() {
		return imageWidth;
	}

	/**
	 * @param imageWidth the imageWidth to set
	 */
	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
	 * @return the imageHeight
	 */
	public String getImageHeight() {
		return imageHeight;
	}

	/**
	 * @param imageHeight the imageHeight to set
	 */
	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SongGWT [artist=" + artist + ", title=" + title
				+ ", customImage=" + customImage + ", imagePath=" + imagePath
				+ ", imageWidth=" + imageWidth + ", imageHeight=" + imageHeight
				+ "]";
	}

}
