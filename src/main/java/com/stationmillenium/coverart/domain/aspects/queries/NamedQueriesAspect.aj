/**
 * 
 */
package com.stationmillenium.coverart.domain.aspects.queries;


/**
 * Aspect listant toutes les named queries
 * @author vincent
 *
 */
public aspect NamedQueriesAspect {

	//syntax example
	//TODO : delete afterward
	/*
	declare @type : IPAddress : @NamedQueries({
		@NamedQuery( //vérifier si une adresse IP existe déjà
			name = "checkExistingIPAddress", 
			query = "SELECT count(ip) FROM IPAddress AS ip WHERE ipAddress = :ipAddress"),
		@NamedQuery( //charger une adresse ip existante
			name = "loadExistingIPAddress", 
			query = "FROM IPAddress WHERE ipAddress = :ipAddress")
	});
	*/
}
