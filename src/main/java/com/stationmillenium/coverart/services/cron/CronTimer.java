/**
 * 
 */
package com.stationmillenium.coverart.services.cron;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.services.PollingService;
import com.stationmillenium.coverart.services.alerts.FMAlertService;

/**
 * Timer for the polling service
 * @author vincent
 *
 */
@Service
public class CronTimer {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(CronTimer.class);
	
	//the polling service
	@Autowired
	private PollingService pollingService;
	
	//the fm alert service
	@Autowired
	private FMAlertService fmAlertService;
	
	//if enable
	private Map<CronType, Boolean> enabledCronType = new HashMap<>();
	
	/**
	 * Get a new {@link CronTimer}
	 */
	public CronTimer() {
		enabledCronType.put(CronType.FM_ALERT, false);
		enabledCronType.put(CronType.POLLING_SERVICE, false);
	}
	
	/**
	 * Run services, if enable - every 10s
	 */
	@Scheduled(fixedDelay = 10000)
	public void runServices() {
		runPollingService();
		runFMAlert();
	}

	/**
	 * Run the polling service every 10s, if enable
	 */
	private void runPollingService() {
		boolean enableLocal = isEnable(CronType.POLLING_SERVICE);
		
		if (enableLocal) { //if enable
			LOGGER.debug("Run polling service");
			pollingService.doServerPolling();
		} else
			LOGGER.debug("Polling service not running");
	}
	
	/**
	 * Run the fm alert, if enable
	 */
	private void runFMAlert() {
		boolean enableLocal = isEnable(CronType.FM_ALERT);
		
		if (enableLocal) { //if enable
			LOGGER.debug("Run FM alert");
			fmAlertService.checkAlert();
		} else
			LOGGER.debug("FM alert not running");
	}

	/**
	 * Auto enable polling when server start
	 */
	@PostConstruct
	public void autoEnableOnStartup() {
		setEnable(CronType.FM_ALERT, true);
		setEnable(CronType.POLLING_SERVICE, true);
	}
	
	/**
	 * Get enable state (sync'ed)
	 * @param type the cron type to get
	 * @return <code>true</code> if active, <code>false</code> if not active
	 */
	public Boolean isEnable(CronType type) {
		boolean enableLocal = false;
		synchronized (enabledCronType) { //get enable state with local copy
			enableLocal = enabledCronType.get(type);
		}
		return enableLocal;
	}

	/**
	 * Set enable state (sync'ed)
	 * @param type the cron type to set
	 * @param enable <code>true</code> if active, <code>false</code> if not active
	 */
	public void setEnable(CronType type, boolean enable) {
		synchronized (enabledCronType) { //get enable state with local copy
			enabledCronType.put(type, enable);
		}
	}
		
}
