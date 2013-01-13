/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.server.rpc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.SongSearchRepository;
import com.stationmillenium.coverart.services.PollingService;
import com.stationmillenium.coverart.services.cron.CronTimer;
import com.stationmillenium.coverart.services.cron.CronType;
import com.stationmillenium.coverart.services.recover.RecoverDeletedImagesService;
import com.stationmillenium.coverart.web.gwt.admin.client.server.rpc.AdminService;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.ServicesStatuses;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.SongGWT;

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
	
	//service for missing images recovery
	@Autowired
	private RecoverDeletedImagesService recoverDeletedImagesService;
	
	//dozer for bean conversion
	@Autowired
	private Mapper mapper;
		
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

	@Override
	public void launchMissingImagesRecovery() {
		recoverDeletedImagesService.recoverLostImages();
	}

	@Override
	public boolean isRecoveryFinished() {
		return recoverDeletedImagesService.isFinished();
	}

	@Override
	public List<SongGWT> getRecoveredSongs() {
		List<SongHistoryItemDTO> songHistoryList = recoverDeletedImagesService.getMissingImageSongsList(); //get the list
		if (songHistoryList != null) { //if not null, process
			
			List<SongGWT> songGWTList = new ArrayList<>();
			for (SongHistoryItemDTO songHistoryDTO : songHistoryList) { //for each song dto, convert
				SongGWT songGWT = mapper.map(songHistoryDTO, SongGWT.class);
				songGWTList.add(songGWT);
			}
			return songGWTList; 
			
		} else //if null return empty list
			return new ArrayList<>();
	}
	
}
