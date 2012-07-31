/**
 * 
 */
package com.stationmillenium.coverart.domain;

import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Entity for image for song history
 * @author vincent
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class SongHistoryImage {

	//image file name
	@NotNull
	private String fileName;
	
	//image width
	@Min(10)
	private int width;
	
	//image height
	@Min(10)
	private int height;
	
	//reverse link to mapped song item
	@OneToOne(mappedBy = "image")
	private SongItem songHistory;
	
}
