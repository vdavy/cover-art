/**
 * 
 */
package com.stationmillenium.coverart.domain.aspects.queries;

import com.stationmillenium.coverart.domain.history.SongHistoryImage;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;

/**
 * Aspect listing named queries for {@link ServerStatus}
 * @author vincent
 *
 */
public aspect SongHistoryImageNamedQueriesAspect {

	declare @type : SongHistoryImage : @NamedQueries({
		
		/**
		 * Get the last song history
		 */
		@NamedQuery( 
				name = "getImageForSongWithCache", 
				query = "SELECT image FROM SongHistoryImage AS image " +
						"WHERE image.songHistory.artist = :artist " +
						"AND image.songHistory.title = :title "	,
						hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
		
		/**
		 * Get all songs with images fetched
		 */
		@NamedQuery( 
				name = "getAllSongsWithImage", 
				query = "FROM SongItem AS song " +
						"JOIN FETCH song.image " +
						"WHERE song.image IS NOT NULL"),
						
		/**
		 * Get the last song history
		 */
		@NamedQuery( 
				name = "getSongWithImage", 
				query = "FROM SongItem AS song " +
						"JOIN FETCH song.image " +
						"WHERE song.artist = :artist " +
						"AND song.title = :title "),
						
		/**
		 * Get the songs with custom images - fetch images
		 */
		@NamedQuery( 
				name = "getSpecificSongWithCustomFetchedImage", 
				query = "FROM SongItem AS song " +
						"LEFT JOIN FETCH song.image " +		
						"WHERE song.customImage = true " +
						"AND song.artist = :artist " +
						"AND song.title = :title"),
						
		/**
		 * Get song with image - if image exists
		 */
		@NamedQuery( 
				name = "getSongWithImageLeftJoinFetch", 
				query = "FROM SongItem AS song " +
						"LEFT JOIN FETCH song.image " +
						"WHERE song.artist = :artist " +
						"AND song.title = :title "),

		/**
		 * Getimage file name of song
		 */
		@NamedQuery( 
				name = "getImageFileNameOfSong", 
				query = "Select song.image.fileName FROM SongItem AS song " +
						"WHERE song.artist = :artist " +
						"AND song.title = :title ")
		
	});
	
}
