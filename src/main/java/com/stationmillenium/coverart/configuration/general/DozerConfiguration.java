/**
 * 
 */
package com.stationmillenium.coverart.configuration.general;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stationmillenium.coverart.domain.history.SongItem;
import com.stationmillenium.coverart.dto.services.SongImageDTO;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.web.gwt.player.shared.SongGWTDTO;

/**
 * Configuration class for Dozer mapping
 * @author vincent
 *
 */
@Configuration
public class DozerConfiguration {

	/**
	 * Get the {@link DozerBeanMapper}
	 * @return the <code>DozerBeanMapper</code>
	 */
	@Bean
	public DozerBeanMapper getDozerBeanMapper() {
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		dozerBeanMapper.addMapping(mapSongEntityToHistoryDTO());
		return dozerBeanMapper;
	}
	
	/**
	 * Map the {@link SongItem} artist and title to {@link SongHistoryItemDTO}
	 * Played date not mapped 
	 * @return the {@link BeanMappingBuilder}
	 */
	private BeanMappingBuilder mapSongEntityToHistoryDTO() {
		BeanMappingBuilder bmb = new BeanMappingBuilder() {						
			@Override
			protected void configure() {
				mapping(SongItem.class, SongHistoryItemDTO.class, TypeMappingOptions.mapNull(),
						TypeMappingOptions.mapEmptyString()).exclude("playedDate")
						.fields("artist", "artist")
						.fields("title", "title");
				
				mapping(SongHistoryItemDTO.class, SongGWTDTO.class, TypeMappingOptions.mapNull(),
						TypeMappingOptions.mapEmptyString())
						.fields("artist", "artist")
						.fields("title", "title")
						.fields("playedDate", "playedDate");
				
				mapping(SongImageDTO.class, SongGWTDTO.class, TypeMappingOptions.mapNull(),
						TypeMappingOptions.mapEmptyString())
						.fields("fileName", "imagePath")
						.fields("width", "imageWidth")
						.fields("height", "imageHeight");
			}
		};
		
		return bmb;
	}
}
