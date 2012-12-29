/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.server.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.ServicesStatuses;

/**
 * Service for the admin GWT module
 * @author vincent
 *
 */
@RemoteServiceRelativePath("service")
public interface AdminService extends RemoteService {

	/**
	 * Get the services statuses as bean
	 * @return the {@link ServicesStatuses} containing statuses
	 */
	ServicesStatuses getServicesStatuses();
	
	/**
	 * Set the polling service status
	 * @param enabled <code>true</code> to enable, <code>false</code> to disable
	 */
	void setPollingServiceStatus(boolean enabled);
	
	/**
	 * Set the FM alert status
	 * @param enabled <code>true</code> to enable, <code>false</code> to disable
	 */
	void setFMAlertStatus(boolean enabled);
	
	/**
	 * Get the current title 
	 * @return the current title as {@link String} 
	 */
	String getCurrentTitle();

	/**
	 * Launch the indexing
	 */
	void launchIndexing();
	
	/**
	 * Is the indexing is still alive ?
	 * @return <code>true</code> if active, <code>false</code> if not
	 */
	boolean isIndexingActive();
		
}
