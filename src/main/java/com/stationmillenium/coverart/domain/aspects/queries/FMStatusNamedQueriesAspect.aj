/**
 * 
 */
package com.stationmillenium.coverart.domain.aspects.queries;

import com.stationmillenium.coverart.domain.statuses.FMStatus;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Aspect listing named queries for {@link FMStatus}
 * @author vincent
 *
 */
public aspect FMStatusNamedQueriesAspect {

	declare @type : FMStatus : @NamedQueries({
		
		/**
		 * Get the last FM status as boolean
		 */
		@NamedQuery( 
			name = "getLastFMStatusBoolean", 
			query = "SELECT fmUp FROM FMStatus WHERE id = (SELECT max(id) FROM FMStatus)")
				
	});
	
}
