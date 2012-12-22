package com.stationmillenium.coverart.services.alerts;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.beans.utils.GeneralPropertiesBean;
import com.stationmillenium.coverart.domain.alert.AlertType;
import com.stationmillenium.coverart.repositories.StatusRepository;

/**
 * FM alert service to manage FM alert
 * @author vincent
 *
 */
@Service
public class FMAlertService {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(FMAlertService.class);
		
	//alert service to send alerts
	@Autowired
	private AlertService alertService;
	
	//status repository to record alerts
	@Autowired
	private StatusRepository statusRepository;
	
	//the general configuration
	@Autowired
	private GeneralPropertiesBean config;
	
	//local calendar to save when the fm last query
	private Calendar lastTimeQueried = Calendar.getInstance();
	
	/**
	 * Update last query date
	 */
	public void updateQueryDate() {
		synchronized (lastTimeQueried) {
			lastTimeQueried = Calendar.getInstance();
		}
	}
	
	/**
	 * Check if alert need to be sent
	 */
	public void checkAlert() {
		//get the alert calendar time
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, -config.getFmAlertTimeout());
		Calendar alertTime = Calendar.getInstance();
		synchronized (lastTimeQueried) {
			alertTime.setTimeInMillis(lastTimeQueried.getTimeInMillis());
		}
		
		if (now.after(alertTime)) { //alert time triggered
			LOGGER.warn("FM alert detected");
			recordFMStatus(false);
		} else {
			recordFMStatus(true);
		}
	}
	
	/**
	 * Record the fm status : <code>true</code> if up, <code>false</code> if down
	 * Record only if it changes from previous status
	 * @param fmStatus <code>true</code> if is up, <code>false</code> if is down
	 */
	private void recordFMStatus(boolean fmStatus) {
		if (statusRepository.getLastFMStatus() != fmStatus) { //if server status changed from previous one
			if (fmStatus) {
				statusRepository.recordFMUp(); //record FM up
				alertService.sendEndedAlert(AlertType.FM); //stop sending alert
			} else {
				statusRepository.recordFMDown(); //record FM down
				alertService.sendActiveAlert(AlertType.FM); //send alert
			}
		}
	}
	
}
