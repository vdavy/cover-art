/**
 * 
 */
package com.stationmillenium.coverart.domain.aspects.queries;

import com.stationmillenium.coverart.domain.SongHistory;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Aspect listing named queries for {@link ServerStatus}
 * @author vincent
 *
 */
public aspect SongHistoryNamedQueriesAspect {

	declare @type : SongHistory : @NamedQueries({
		
		/**
		 * Get the last song history
		 */
		@NamedQuery( 
				name = "getLastSong", 
				query = "FROM SongHistory  WHERE id = (SELECT max(id) FROM SongHistory)")
		
	});
	
}
