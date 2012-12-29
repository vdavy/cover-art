/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.server.rpc;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.SongSearchRepository;
import com.stationmillenium.coverart.services.PollingService;
import com.stationmillenium.coverart.services.cron.CronTimer;
import com.stationmillenium.coverart.services.cron.CronType;
import com.stationmillenium.coverart.web.gwt.admin.client.server.rpc.AdminService;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.ServicesStatuses;

/**
 * Service impl for the admin GWT module
 * @author vincent
 *
 */
@SuppressWarnings("serial")
@Configurable
@WebServlet("/admin/service")
public class AdminServiceImpl extends RemoteServiceServlet implements AdminService {
	
	//the cron service
	@Autowired
	private CronTimer cronTimer;
	
	//the polling service
	@Autowired
	private PollingService pollingService;
	
	//repository for indexing
	@Autowired
	private SongSearchRepository repository;
		
	@Override
	public ServicesStatuses getServicesStatuses() {
		ServicesStatuses statuses = new ServicesStatuses();
		statuses.setFmAlertStatus(cronTimer.isEnable(CronType.FM_ALERT));
		statuses.setPollingServiceStatus(cronTimer.isEnable(CronType.POLLING_SERVICE));
		return statuses;
	}
	
	@Override
	public void setFMAlertStatus(boolean enabled) {
		cronTimer.setEnable(CronType.FM_ALERT, enabled);
	}
	
	@Override
	public void setPollingServiceStatus(boolean enabled) {
		cronTimer.setEnable(CronType.POLLING_SERVICE, enabled);		
	}
	
	@Override
	public String getCurrentTitle() {
		SongHistoryItemDTO currentSong = pollingService.getCurrentSong();
		if (currentSong != null)
			return currentSong.getArtist() + " - " + currentSong.getTitle();
		else
			return "";
	}

	@Override
	public boolean isIndexingActive() {
		return (!(repository.isIndexingFinished()));
	}
	
	@Override
	public void launchIndexing() {
		repository.indexAsync();
	}
	
}
