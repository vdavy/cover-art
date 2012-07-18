/**
 * 
 */
package com.stationmillenium.coverart.repositories;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.stationmillenium.coverart.domain.ServerStatus;

/**
 * Repository for {@link ServerStatus}}
 * @author vincent
 *
 */
@Repository
public class ServerStatusRepository {

	//entity manager to access db
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Get the last server status as boolean
	 * @return <code>true</code> if server up, <code>false</code> if server down
	 */
	public boolean getLastServerStatus() {
		Query query = entityManager.createNamedQuery("getLastServerStatusBoolean");
		return (boolean) query.getSingleResult();
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
}
