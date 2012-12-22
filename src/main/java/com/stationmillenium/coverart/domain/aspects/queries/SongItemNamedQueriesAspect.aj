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
				query = "SELECT history.song FROM SongHistory AS history " +
						"ORDER BY history.playedDate DESC"),
		
		/**
		 * Get the last histories ordered by played time, with song and image fetched
		 * Set limit min date
		 */
		@NamedQuery( 
				name = "getSongsFetchedOrderedByPlayedTimeWithMinDate", 
				query = "FROM SongHistory AS history " +
						"JOIN FETCH history.song " +
						"JOIN FETCH history.song.image " +						
						"WHERE history.playedDate > :minDate " +
						"ORDER BY history.playedDate DESC"),
		
		/**
		 * Get histories with song fetched where played date is beitwe
		 */
		@NamedQuery( 
				name = "getSongsFetchedOrderedByPlayedTimeBetween2Dates", 
				query = "FROM SongHistory AS history " +
						"JOIN FETCH history.song " +
						"WHERE history.playedDate " +
						"BETWEEN :minDate AND :maxDate " +
						"ORDER BY history.playedDate DESC")
					
	});
	
}
