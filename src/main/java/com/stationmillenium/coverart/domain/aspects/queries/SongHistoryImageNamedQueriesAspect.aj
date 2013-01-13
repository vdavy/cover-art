/**
 * 
 */
package com.stationmillenium.coverart.domain.aspects.queries;

import com.stationmillenium.coverart.domain.history.SongHistoryImage;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

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
				name = "getImageForSongWithTime", 
				query = "SELECT image FROM SongHistoryImage AS image " +
						"JOIN image.songHistory.playedTimes AS times " +
						"WHERE image.songHistory.artist = :artist " +
						"AND image.songHistory.title = :title " +
						"AND times = (" +
							"FROM SongHistory WHERE playedDate = :calendar" +
						")"),
		
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
						"AND song.title = :title ")
		
	});
	
}
