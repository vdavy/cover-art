/**
 * 
 */
package com.stationmillenium.coverart.domain.aspects.queries;

import com.stationmillenium.coverart.domain.alert.AlertEmail;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Aspect listing named queries for {@link AlertEmail}
 * @author vincent
 *
 */
public aspect AlertNamedQueriesAspect {

	declare @type : AlertEmail : @NamedQueries({
		
		/**
		 * Get the associated emails for an alert type
		 */
		@NamedQuery( 
			name = "getEmailFromAlertType", 
			query = "FROM AlertEmail AS email " +
					"JOIN email.alertType AS alert " +
					"WHERE alert = :alert"),
		
		/**
		 * Get the associated alert types for an email
		 */
		@NamedQuery( 
			name = "getAlertTypeFromEmail", 
			query = "SELECT type " +
					"FROM AlertEmail AS emailAlert " +
					"JOIN emailAlert.alertType AS type " +
					"WHERE emailAlert.email = :email"),
		/**
		 * Get the activation for an alert type
		 */
		@NamedQuery( 
				name = "getAlertEnabledForAlertType", 
				query = "FROM AlertActivation " +
						"WHERE alertType = :type")
		
	});
	
}
