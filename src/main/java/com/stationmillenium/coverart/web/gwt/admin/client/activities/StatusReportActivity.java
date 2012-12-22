/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.activities;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.places.StatusReportPlace.ReportType;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requests.AdminRequestFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.view.StatusReportView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.StatusReportView.Presenter;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses.FMStatusProxy;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses.PlaylistStatusProxy;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses.ServerStatusProxy;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses.StatusProxy;

/**
 * Activity for the status report activity
 * @author vincent
 *
 */
public class StatusReportActivity extends AbstractActivity implements Presenter {

	//logger
	private static final Logger LOGGER = Logger.getLogger(StatusReportActivity.class.getName());
	private ClientFactory clientFactory;
	private ReportType type;
	private final Receiver<List<? extends StatusProxy>> receiver = new Receiver<List<? extends StatusProxy>>() {

		@Override
		public void onSuccess(List<? extends StatusProxy> response) {
			LOGGER.fine("Gathered statuses : " + response);
			StatusProxy lastIndexObject = response.get(response.size() - 1);
			if (lastIndexObject instanceof FMStatusProxy) //fm case
				clientFactory.getStatusReportView().setAlertActive(((FMStatusProxy) lastIndexObject).isFmUp());
			else if (lastIndexObject instanceof ServerStatusProxy) //server case
				clientFactory.getStatusReportView().setAlertActive(((ServerStatusProxy) lastIndexObject).isServerUp());
			else if (lastIndexObject instanceof PlaylistStatusProxy) //playlist case
				clientFactory.getStatusReportView().setAlertActive(((PlaylistStatusProxy) lastIndexObject).isPlaylistUpdated());
			
			clientFactory.getStatusReportView().setStatusesList(response);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void onFailure(ServerFailure error) {
			LOGGER.warning("Error during getting statuses : " + error.getMessage());
			LOGGER.warning(error.getStackTraceString());
			clientFactory.getStatusReportView().setStatusesList(Collections.EMPTY_LIST);
			Window.alert(clientFactory.getConstants().getStatusesLoadingError());
		}
	};
	
	
	/**
	 * Create {@link StatusReportActivity}
	 * @param clientFactory the client factory
	 */
	public StatusReportActivity(ClientFactory clientFactory, ReportType type) {
		this.clientFactory = clientFactory;
		this.type= type; 
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget, com.google.gwt.event.shared.EventBus)
	 */
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		StatusReportView view = clientFactory.getStatusReportView();
		panel.setWidget(view);
		view.setAlertType(type); //display the alert type	
		displayStatuses();		
	}

	/**
	 * Display the statuses according the requested type
	 */
	private void displayStatuses() {
		LOGGER.fine("Get the statuses for type : " + type);
		AdminRequestFactory adminRequestFactory = clientFactory.getAdminRequestFactory();
		switch (type) {
		case FM:
			adminRequestFactory.fmStatusRequest().findAllFMStatuses().fire(receiver);
			break;

		case PLAYLIST:
			adminRequestFactory.playlistStatusRequest().findAllPlaylistStatuses().fire(receiver);
			break;
			
		case SHOUTCAST:
			adminRequestFactory.serverStatusRequest().findAllServerStatuses().fire(receiver);
			break;
		}
	}
}
