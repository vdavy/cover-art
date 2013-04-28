/**
 * 
 */
package com.stationmillenium.coverart.domain.aspects.queries;

import com.stationmillenium.coverart.domain.history.SongItem;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;

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
				query = "SELECT count(item) FROM SongItem AS item WHERE artist = :artist AND title = :title",
				hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
		
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
						"LEFT JOIN FETCH history.song.image " +						
						"WHERE history.playedDate > :minDate " +
						"ORDER BY history.playedDate DESC",
						hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
		
		/**
		 * Get histories with song fetched where played date is between
		 */
		@NamedQuery( 
				name = "getSongsFetchedOrderedByPlayedTimeBetween2Dates", 
				query = "FROM SongHistory AS history " +
						"JOIN FETCH history.song " +
						"WHERE history.playedDate " +
						"BETWEEN :minDate AND :maxDate " +
						"ORDER BY history.playedDate DESC",
						hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
		
		/**
		 * Get the last histories ordered by played time, with song and image fetched between 2 dates
		 * Set limit min date
		 */
		@NamedQuery( 
				name = "getSongsFetchedOrderedWithImageByPlayedTimeBetween2Dates", 
				query = "FROM SongHistory AS history " +
						"JOIN FETCH history.song " +
						"LEFT JOIN FETCH history.song.image " +		
						"WHERE history.playedDate " +
						"BETWEEN :minDate AND :maxDate " +
						"ORDER BY history.playedDate DESC",
						hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
						
		/**
		 * Get the songs with custom images - fetch images
		 */
		@NamedQuery( 
				name = "getSongsWithCustomFetchedImage", 
				query = "FROM SongItem AS song " +
						"LEFT JOIN FETCH song.image " +		
						"WHERE song.customImage = true " +
						"ORDER BY song.artist")
					
	});
	
}
