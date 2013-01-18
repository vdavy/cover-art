/**
 * 
 */
package com.stationmillenium.coverart.domain.history;

import javax.persistence.Cacheable;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SongHistoryImage {

	public enum Provider {
		LAST_FM,
		DEEZER
	}
	
	//image file name
	@NotNull
	private String fileName;
	
	//image width
	@Min(10)
	private int width;
	
	//image height
	@Min(10)
	private int height;
	
	//image provider
	@NotNull
	private Provider provider;
	
	//reverse link to mapped song item
	@OneToOne(mappedBy = "image")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private SongItem songHistory;

}
