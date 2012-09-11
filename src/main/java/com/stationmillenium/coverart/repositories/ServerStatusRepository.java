/**
 * 
 */
package com.stationmillenium.coverart.repositories;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.stationmillenium.coverart.domain.statuses.PlaylistStatus;
import com.stationmillenium.coverart.domain.statuses.ServerStatus;

/**
 * Repository for {@link ServerStatus}}
 * @author vincent
 *
 */
@Repository
public class ServerStatusRepository {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerStatusRepository.class);
	
	//entity manager to access db
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Get the last server status as boolean
	 * @return <code>true</code> if server up, <code>false</code> if server down
	 */
	public boolean getLastServerStatus() {
		try {
			Query query = entityManager.createNamedQuery("getLastServerStatusBoolean");
			return (boolean) query.getSingleResult();
		} catch (EmptyResultDataAccessException e) { //if not found
			LOGGER.warn("No entity found", e);
			return false;
		}
		
	}
	
	/**
	 * Record in database that server is up
	 */
	public void recordServerUp() {
		recordServerStatus(true);
	}
	
	/**
	 * Record in database that server is down
	 */
	public void recordServerDown() {
		recordServerStatus(false);
	}
	
	/**
	 * Record server status
	 * @param status the status
	 */
	private void recordServerStatus(boolean status) {
		ServerStatus serverStatus = new ServerStatus();
		serverStatus.setServerUp(status);
		serverStatus.setDateOfChange(Calendar.getInstance());
		serverStatus.persist();
	}
	
	/**
	 * Record playlist update timeout
	 */
	public void recordPlaylistUpdateTimeout() {
		recordPlaylist(false);
	}
	
	/**
	 * Record playlist updated
	 */
	public void recordPlaylistUpdated() {
		recordPlaylist(true);
	}
	
	/**
	 * Update playlist status if needed
	 * @param updated the status to record
	 */
	private void recordPlaylist(boolean updated) {
		//get previous update status
		boolean previousUpdate = false;
		try {
			Query query = entityManager.createNamedQuery("getLastPlaylistBoolean");
			previousUpdate = (boolean) query.getSingleResult();
		} catch (EmptyResultDataAccessException e) { //if not found
			LOGGER.warn("No entity found", e);
		}
		
		//insert update if needed
		if (updated != previousUpdate) {
			PlaylistStatus playlistStatus = new PlaylistStatus();
			playlistStatus.setDateOfChange(Calendar.getInstance());
			playlistStatus.setPlaylistUpdated(updated);
			playlistStatus.persist();
		}
	}
	
}
