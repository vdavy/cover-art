/**
 * 
 */
package com.stationmillenium.coverart.services;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Timer for the polling service
 * @author vincent
 *
 */
@Service
public class PollingServiceTimer {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(PollingServiceTimer.class);
	
	//the polling service
	@Autowired
	private PollingService pollingService;
	
	//if enable
	private Boolean enable = false; 
	
	/**
	 * Run the polling service every 10s, if enable
	 */
	@Scheduled(fixedDelay = 10000)
	public void runPollingService() {
		boolean enableLocal = false;
		synchronized (enable) { //get enable state with local copy
			enableLocal = enable;
		}
		
		if (enableLocal) { //if enable
			LOGGER.debug("Run polling service");
			pollingService.doServerPolling();
		} else
			LOGGER.debug("Polling service not running");
	}

	/**
	 * Auto enable polling when server start
	 */
	@PostConstruct
	public void autoEnableOnStartup() {
		setEnable(true);		
	}
	
	/**
	 * Get enable state (sync'ed)
	 * @return <code>true</code> if active, <code>false</code> if not active
	 */
	public Boolean isEnable() {
		boolean enableLocal = false;
		synchronized (enable) { //get enable state with local copy
			enableLocal = enable;
		}
		return enableLocal;
	}

	/**
	 * Set enable state (sync'ed)
	 * @param enable <code>true</code> if active, <code>false</code> if not active
	 */
	public void setEnable(Boolean enable) {
		synchronized (enable) { //get enable state with local copy
			this.enable = enable;
		}
	}
		
}
