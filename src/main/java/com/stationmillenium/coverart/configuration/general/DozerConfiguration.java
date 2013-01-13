/**
 * 
 */
package com.stationmillenium.coverart.configuration.general;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stationmillenium.coverart.domain.history.SongHistory;
import com.stationmillenium.coverart.domain.history.SongItem;
import com.stationmillenium.coverart.dto.hybrid.SongHistoryItemImageDTO;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.dto.services.images.SongImageDTO;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.SongGWT;
import com.stationmillenium.coverart.web.gwt.history.shared.HistoryGWTDTO;
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
				
				mapping(SongHistory.class, SongHistoryItemImageDTO.class, TypeMappingOptions.mapNull(),
						TypeMappingOptions.mapEmptyString())
						.fields("playedDate", "songHistoryItemDTO.playedDate")
						.fields("song.artist", "songHistoryItemDTO.artist")
						.fields("song.title", "songHistoryItemDTO.title")			
						.fields("song.image.fileName", "songImageDTO.fileName")
						.fields("song.image.width", "songImageDTO.width")
						.fields("song.image.height", "songImageDTO.height");
				
				mapping(SongHistoryItemImageDTO.class, HistoryGWTDTO.class, TypeMappingOptions.mapNull(),
						TypeMappingOptions.mapEmptyString())
						.fields("songHistoryItemDTO.playedDate", "playedDate")
						.fields("songHistoryItemDTO.artist", "artist")
						.fields("songHistoryItemDTO.title", "title")			
						.fields("songImageDTO.fileName", "imagePath")
						.fields("songImageDTO.width", "imageWidth")
						.fields("songImageDTO.height", "imageHeight");
				
				mapping(SongHistory.class, SongHistoryItemDTO.class, TypeMappingOptions.mapNull(),
						TypeMappingOptions.mapEmptyString())
						.fields("playedDate", "playedDate")
						.fields("song.artist", "artist")
						.fields("song.title", "title");			
				
				mapping(SongItem.class, SongHistoryItemImageDTO.class, TypeMappingOptions.mapNull(),
						TypeMappingOptions.mapEmptyString())
						.fields("artist", "songHistoryItemDTO.artist")
						.fields("title", "songHistoryItemDTO.title")			
						.fields("image.fileName", "songImageDTO.fileName")
						.fields("image.width", "songImageDTO.width")
						.fields("image.height", "songImageDTO.height");
				
				mapping(SongHistoryItemDTO.class, SongGWT.class, TypeMappingOptions.mapNull(),
						TypeMappingOptions.mapEmptyString()).exclude("playedDate")
						.fields("artist", "artist")
						.fields("title", "title");
				
			}
		};
		
		return bmb;
	}
}
