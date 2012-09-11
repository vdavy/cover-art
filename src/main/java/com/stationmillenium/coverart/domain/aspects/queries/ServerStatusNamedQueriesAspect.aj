/**
 * 
 */
package com.stationmillenium.coverart.domain.aspects.queries;

import com.stationmillenium.coverart.domain.statuses.ServerStatus;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Aspect listing named queries for {@link ServerStatus}
 * @author vincent
 *
 */
public aspect ServerStatusNamedQueriesAspect {

	declare @type : ServerStatus : @NamedQueries({
		
		/**
		 * Get the last server status as boolean
		 */
		@NamedQuery( 
			name = "getLastServerStatusBoolean", 
			query = "SELECT serverUp FROM ServerStatus WHERE id = (SELECT max(id) FROM ServerStatus)"),
		
		/**
		 * Get the last server status as boolean
		 */
		@NamedQuery( 
			name = "getLastPlaylistBoolean", 
			query = "SELECT playlistUpdated FROM PlaylistStatus WHERE id = (SELECT max(id) FROM PlaylistStatus)")
		
	});
	
}
