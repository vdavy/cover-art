/**
 * 
 */
package com.stationmillenium.coverart.domain.aspects.queries;

import com.stationmillenium.coverart.domain.history.SongItem;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Aspect listing named queries for {@link ServerStatus}
 * @author vincent
 *
 */
public aspect SongItemNamedQueriesAspect {

	declare @type : SongItem : @NamedQueries({
		
		/**
		 * Get the last song history
		 */
		@NamedQuery( 
				name = "getLastSong", 
				query = "SELECT item FROM SongItem AS item " +
						"JOIN FETCH item.playedTimes AS history " +
						"WHERE history.playedDate = (SELECT max(playedDate) FROM SongHistory) " +
						"ORDER BY history.playedDate DESC"),
		
		/**
		 * Check if song exists
		 */
		@NamedQuery( 
				name = "checkExistingSong", 
				query = "SELECT count(item) FROM SongItem AS item WHERE artist = :artist AND title = :title"),
		
		/**
		 * Load existing song
		 */
		@NamedQuery( 
				name = "loadExistingSong", 
				query = "FROM SongItem WHERE artist = :artist AND title = :title"),
		
		/**
		 * Get the last songs ordered by played time
		 */
		@NamedQuery( 
				name = "getSongsOrderedByPlayedTime", 
				query = "SELECT item FROM SongItem AS item " +
						"JOIN FETCH item.playedTimes AS history " +
						"ORDER BY history.playedDate DESC")
						
	});
	
}
