/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.activities;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.view.GeneralConfigView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.GeneralConfigView.Presenter;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.AbstractMessageView.MessageLabelStyle;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.ServicesStatuses;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.SongGWT;

/**
 * Activity for the main configuration
 * @author vincent
 *
 */
public class GeneralConfigActivity extends AbstractActivity implements Presenter {

	//logger
	private static final Logger LOGGER = Logger.getLogger(GeneralConfigActivity.class.getName());
	
	//local instances
	private ClientFactory clientFactory;
	private Timer timer;
	private boolean indexingRunning = true;
	private boolean recoveryRunning = true;
		
	/**
	 * Create a new {@link GeneralConfigActivity}
	 * @param clientFactory the client factory
	 */
	public GeneralConfigActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		
		//set up timer
		timer = new Timer() {			
			@Override
			public void run() {
				getCurrentTitle(); //get the current title
				if (indexingRunning) //update indexing status only if running
					getIndexingStatus();
				
				if (recoveryRunning) //update recovery status only if running
					getRecoveryStatus();
			}
		};
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget, com.google.gwt.event.shared.EventBus)
	 */
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		//set up display
		GeneralConfigView view = clientFactory.getGeneralConfigView();
		view.setPresenter(this);
		panel.setWidget(view);
		
		//set the display information
		getServicesStatuses();
		getCurrentTitle();
		getIndexingStatus();
		getRecoveryStatus();
		
		//start timer
		timer.scheduleRepeating(10000);
	}
	
	@Override
	public void onStop() {
		LOGGER.fine("Stop timer");
		timer.cancel();
	}

	@Override
	public void onClickPollingServiceCheckbox(boolean value) {
		LOGGER.fine("Change polling service status : " + value);
		clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getServiceUpdate(), MessageLabelStyle.DEFAULT);
		clientFactory.getGeneralConfigView().setPollingServiceCheckboxActivation(false);
		clientFactory.getAdminService().setPollingServiceStatus(value, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getServiceStatusChangeOK(), MessageLabelStyle.GREEN);
				clientFactory.getGeneralConfigView().setPollingServiceCheckboxActivation(true);				
				getServicesStatuses();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				LOGGER.log(Level.WARNING, "Error while changing polling service status", caught);
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getServiceStatusChangeError(), MessageLabelStyle.RED);
			}
		});
	}

	@Override
	public void onClickFMAlertCheckbox(boolean value) {
		LOGGER.fine("Change FM alert status : " + value);
		clientFactory.getGeneralConfigView().setFMAlertCheckboxActivation(false);
		clientFactory.getAdminService().setFMAlertStatus(value, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getServiceStatusChangeOK(), MessageLabelStyle.GREEN);
				clientFactory.getGeneralConfigView().setFMAlertCheckboxActivation(true);				
				getServicesStatuses();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				LOGGER.log(Level.WARNING, "Error while changing FM alert status", caught);
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getServiceStatusChangeError(), MessageLabelStyle.RED);
			}
		});		
	}

	@Override
	public void onClickIndexButton() {
		LOGGER.fine("Launch indexing...");
		clientFactory.getAdminService().launchIndexing(new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				LOGGER.fine("Indexing launched");
				indexingRunning = true;
				clientFactory.getGeneralConfigView().setHibernateSearchInformation(true, clientFactory.getGeneralConfigConstants().getIndexingStatusRunning() );
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getIndexingLaunched(), MessageLabelStyle.DEFAULT);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				LOGGER.log(Level.WARNING, "Error while launching indexing", caught);
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getLaunchIndexingError(), MessageLabelStyle.RED);
			}
			
		});
	}
	
	/**
	 * Get and set on view the services statuses
	 */
	private void getServicesStatuses() {
		LOGGER.fine("Get the services statuses");
		clientFactory.getGeneralConfigView().setPollingServiceCheckboxActivation(false);
		clientFactory.getGeneralConfigView().setFMAlertCheckboxActivation(false);
		clientFactory.getAdminService().getServicesStatuses(new AsyncCallback<ServicesStatuses>() {
			
			@Override
			public void onSuccess(ServicesStatuses result) {
				if (result != null) { 
					LOGGER.fine("Received statuses :" + result);
					
					//deal with polling status case
					clientFactory.getGeneralConfigView().setPollingServiceCheckboxActivation(true);
					clientFactory.getGeneralConfigView().setPollingServiceCheckboxChecked(result.isPollingServiceStatus());
					String pollingServiceStatusText = (result.isPollingServiceStatus()) 
							? clientFactory.getGeneralConfigConstants().getServiceEnabled() 
							: clientFactory.getGeneralConfigConstants().getServiceDisabled();
					clientFactory.getGeneralConfigView().setPollingServiceInformation(result.isPollingServiceStatus(), pollingServiceStatusText);
					
					//deal with fm alert case
					clientFactory.getGeneralConfigView().setFMAlertCheckboxActivation(true);
					clientFactory.getGeneralConfigView().setFMAlertCheckboxChecked(result.isFmAlertStatus());
					String fmAlertStatusText = (result.isFmAlertStatus()) 
							? clientFactory.getGeneralConfigConstants().getServiceEnabled() 
							: clientFactory.getGeneralConfigConstants().getServiceDisabled();
					clientFactory.getGeneralConfigView().setFMAlertInformation(result.isFmAlertStatus(), fmAlertStatusText);
					
				} else {
					LOGGER.warning("Services statuses DTO is null");
					clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getServicesStatusesError(), MessageLabelStyle.RED);
				}					
			}
			
			@Override
			public void onFailure(Throwable caught) {
				LOGGER.log(Level.WARNING, "Error while getting the service statuses", caught);
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getServicesStatusesError(), MessageLabelStyle.RED);
			}
		});
	}

	/**
	 * Get the current title
	 */
	private void getCurrentTitle() {
		LOGGER.fine("Get the current title");
		clientFactory.getAdminService().getCurrentTitle(new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				LOGGER.fine("Current title : " + result);
				clientFactory.getGeneralConfigView().setCurrentTitleText(result);
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getReadyState(), MessageLabelStyle.DEFAULT);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				LOGGER.log(Level.WARNING, "Error while getting the current title", caught);
				clientFactory.getGeneralConfigView().setCurrentTitleText("");
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getCurrentTitleError(), MessageLabelStyle.RED);
			}
		});
	}
	
	/**
	 * Get the indexing status
	 */
	private void getIndexingStatus() {
		LOGGER.fine("Get indexing status");
		clientFactory.getAdminService().isIndexingActive(new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				LOGGER.fine("Indexing status : " + result);
				String indexingStatusText = (result) 
						? clientFactory.getGeneralConfigConstants().getIndexingStatusRunning() 
						: clientFactory.getGeneralConfigConstants().getIndexingStatusEnded();
				clientFactory.getGeneralConfigView().setHibernateSearchInformation(result, indexingStatusText);
				if (!result) {
					clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getIndexingStatusFinished(), MessageLabelStyle.GREEN);
					indexingRunning = false;
				} else
					clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getIndexingLaunched(), MessageLabelStyle.DEFAULT);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				LOGGER.log(Level.WARNING, "Error while getting indexing status", caught);
				clientFactory.getGeneralConfigView().setHibernateSearchInformation(false, "");
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getIndexingStatusError(), MessageLabelStyle.RED);
			}
			
		});
	}
	
	@Override
	public void onClickRecoverButton() {
		LOGGER.fine("Launch recovery...");
		clientFactory.getAdminService().launchMissingImagesRecovery(new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				LOGGER.fine("Recovery launched");
				recoveryRunning = true;
				clientFactory.getGeneralConfigView().setMissingImagesRecoveryInformation(true, clientFactory.getGeneralConfigConstants().getRecoveryStatusRunning() );
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getRecoveryLaunched(), MessageLabelStyle.DEFAULT);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				LOGGER.log(Level.WARNING, "Error while launching recovery", caught);
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getLaunchRecoveryError(), MessageLabelStyle.RED);
			}
			
		});
	}
	
	/**
	 * Get the recovery status
	 */
	private void getRecoveryStatus() {
		LOGGER.fine("Get recovery status");
		clientFactory.getAdminService().isRecoveryFinished(new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				LOGGER.fine("Recovery finished : " + result);
				String recoveryStatusText = (result) 
						? clientFactory.getGeneralConfigConstants().getRecoveryStatusEnded() 
						: clientFactory.getGeneralConfigConstants().getRecoveryStatusRunning();						
				clientFactory.getGeneralConfigView().setMissingImagesRecoveryInformation(!result, recoveryStatusText);
				
				if (result) {
					clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getRecoveryStatusFinished(), MessageLabelStyle.GREEN);
					recoveryRunning = false;
					
					//display recovered songs
					displayRecoveredSongs();
				} else
					clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getRecoveryLaunched(), MessageLabelStyle.DEFAULT);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				LOGGER.log(Level.WARNING, "Error while getting recovery status", caught);
				clientFactory.getGeneralConfigView().setMissingImagesRecoveryInformation(false, "");
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getRecoveryStatusError(), MessageLabelStyle.RED);
			}
			
		});
	}
	
	/**
	 * Display the recovered songs
	 */
	private void displayRecoveredSongs() {
		LOGGER.fine("Display the recovered songs");
		clientFactory.getAdminService().getRecoveredSongs(new AsyncCallback<List<SongGWT>>() {
			
			@Override
			public void onSuccess(List<SongGWT> result) {
				LOGGER.fine("Recovered songs : " + result);
				if ((result != null) && (result.size() > 0)) {
					String text = clientFactory.getGeneralConfigMessages().getRecoveredSongsImagesCount(result.size());
					clientFactory.getGeneralConfigView().setRecoveredImagesLabelText(text);
					clientFactory.getGeneralConfigView().displayRecoveredSongsList(result);					
				}
			};
			
			@Override
			public void onFailure(Throwable caught) {
				LOGGER.log(Level.WARNING, "Error while getting recovered songs list", caught);
				clientFactory.getGeneralConfigView().setMessageLabelTextAndStyle(clientFactory.getGeneralConfigConstants().getRecoveredSongsListError(), MessageLabelStyle.RED);				
			}
		});
	}
	
}
