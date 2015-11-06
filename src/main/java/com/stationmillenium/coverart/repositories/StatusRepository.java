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
import org.springframework.transaction.annotation.Transactional;

import com.stationmillenium.coverart.domain.statuses.FMStatus;
import com.stationmillenium.coverart.domain.statuses.PlaylistStatus;
import com.stationmillenium.coverart.domain.statuses.ServerStatus;

/**
 * Repository for {@link ServerStatus}}
 * @author vincent
 *
 */
@Repository
public class StatusRepository {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(StatusRepository.class);
	
	//entity manager to access db
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Get the last server status as boolean
	 * @return <code>true</code> if server up, <code>false</code> if server down
	 */
	@Transactional
	public boolean getLastServerStatus() {
		return getLastStatus("getLastServerStatusBoolean");		
	}
	
	/**
	 * Get the last FM status as boolean
	 * @return <code>true</code> if FM up, <code>false</code> if FM down
	 */
	@Transactional
	public boolean getLastFMStatus() {
		return getLastStatus("getLastFMStatusBoolean");		
	}
	
	/**
	 * Get the last status as boolean
	 * @param query the query to exec
	 * @return <code>true</code> if  up, <code>false</code> if  down
	 */
	private boolean getLastStatus(String query) {
		try {
			Query queryToExec = entityManager.createNamedQuery(query);
			return (boolean) queryToExec.getSingleResult();
		} catch (EmptyResultDataAccessException e) { //if not found
			LOGGER.warn("No entity found", e);
			return false;
		}		
	}
	
	/**
	 * Record in database that server is up
	 */
	@Transactional
	public void recordServerUp() {
		recordServerStatus(true);
	}
	
	/**
	 * Record in database that server is down
	 */
	@Transactional
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
		serverStatus.setDateOfChange(Calendar.getInstance().getTime());
		serverStatus.persist();
	}
	
	/**
	 * Record in database that fm is up
	 */
	@Transactional
	public void recordFMUp() {
		recordFMStatus(true);
	}
	
	/**
	 * Record in database that FM is down
	 */
	@Transactional
	public void recordFMDown() {
		recordFMStatus(false);
	}
	
	/**
	 * Record FM status
	 * @param status the status
	 */
	private void recordFMStatus(boolean status) {
		FMStatus  fmStatus = new FMStatus();
		fmStatus.setFmUp(status);
		fmStatus.setDateOfChange(Calendar.getInstance().getTime());
		fmStatus.persist();
	}
	
	/**
	 * Record playlist update timeout
	 * @return <code>true</code> if status updated in database, <code>false</code> otherwhise
	 */
	@Transactional
	public boolean recordPlaylistUpdateTimeout() {
		return recordPlaylist(false);
	}
	
	/**
	 * Record playlist updated
	 * @return <code>true</code> if status updated in database, <code>false</code> otherwhise
	 */
	@Transactional
	public boolean recordPlaylistUpdated() {
		return recordPlaylist(true);
	}
	
	/**
	 * Update playlist status if needed
	 * @param updated the status to record
	 * @return <code>true</code> if status updated in database, <code>false</code> otherwhise
	 */
	private boolean recordPlaylist(boolean updated) {
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
			playlistStatus.setDateOfChange(Calendar.getInstance().getTime());
			playlistStatus.setPlaylistUpdated(updated);
			playlistStatus.persist();
			return true;
		} else
			return false;
	}
	
}
