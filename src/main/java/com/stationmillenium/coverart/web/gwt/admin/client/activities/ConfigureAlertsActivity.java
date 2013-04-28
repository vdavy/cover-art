/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.activities;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.alerts.AlertActivationRequest;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.alerts.AlertEmailRequest;
import com.stationmillenium.coverart.web.gwt.admin.client.utils.callbacks.AjaxLoaderReceiver;
import com.stationmillenium.coverart.web.gwt.admin.client.utils.editors.AlertEmailEditor;
import com.stationmillenium.coverart.web.gwt.admin.client.view.ConfigureAlertView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.ConfigureAlertView.Presenter;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.AbstractMessageView.MessageLabelStyle;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.ConfigureAlertViewImpl;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertActivationProxy;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertEmailProxy;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertType;

/**
 * Activity for the configure alerts part
 * @author vincent
 *
 */
public class ConfigureAlertsActivity extends AbstractActivity implements Presenter {

	//editor interfaces
	interface AlertActivationDriver extends RequestFactoryEditorDriver<AlertActivationProxy, ConfigureAlertViewImpl> {}
	interface AlertEmailDriver extends RequestFactoryEditorDriver<AlertEmailProxy, AlertEmailEditor> {}
	
	//logger
	private static final Logger LOGGER = Logger.getLogger(ConfigureAlertsActivity.class.getName());
	
	//instances
	private ClientFactory clientFactory;
	private AlertActivationDriver alertActivationDriver;
	private AlertEmailDriver alertEmailDriver;
	private boolean reloadEmailList;
	
	/**
	 * Create a new {@link ConfigureAlertsActivity}
	 * @param clientFactory the client factory
	 */
	public ConfigureAlertsActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget, com.google.gwt.event.shared.EventBus)
	 */
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		//init panel
		ConfigureAlertView view = clientFactory.getConfigureAlertView();
		view.setPresenter(this);
		clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getReadyState(), MessageLabelStyle.DEFAULT);
		panel.setWidget(view);
		view.hideAlertEmailEditor();
		
		//load data
		loadAlertActivationList();
		loadAlertEmailList();
	}

	/**
	 * Load the alert activation list for display
	 */
	private void loadAlertActivationList() {
		Request<List<AlertActivationProxy>> request = clientFactory.getAdminRequestFactory().alertActivationRequest().findAllAlertActivations();
		request.fire(new AjaxLoaderReceiver<List<AlertActivationProxy>>(clientFactory) {

			@Override
			public void onCustomSuccess(List<AlertActivationProxy> response) {
				LOGGER.fine("List of received alert activations : " + response);
				clientFactory.getConfigureAlertView().setAlertActivationList(response);
				clientFactory.getConfigureAlertView().clearAlertActivationListSelection();
			}
			
			@Override
			public void onCustomFailure(ServerFailure error) {
				LOGGER.warning("Error during loading list of alert activations : " + error.getMessage());
				clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getLoadAlertActivationError(), MessageLabelStyle.RED);
			}
		});
	}
	
	/**
	 * Load the alert email list for display
	 */
	private void loadAlertEmailList() {
		Request<List<AlertEmailProxy>> request = clientFactory.getAdminRequestFactory().alertEmailRequest().findAllAlertEmails();
		request.fire(new AjaxLoaderReceiver<List<AlertEmailProxy>>(clientFactory) {

			@Override
			public void onCustomSuccess(List<AlertEmailProxy> response) {
				LOGGER.fine("List of received alert emails : " + response);
				clientFactory.getConfigureAlertView().setAlertEmailList(response);
			}
			
			@Override
			public void onCustomFailure(ServerFailure error) {
				LOGGER.warning("Error during loading list of alert emails : " + error.getMessage());
				clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getLoadAlertEmailError(), MessageLabelStyle.RED);
			}
		});
	}
	
	@Override
	public void onSelectedAlert(AlertActivationProxy selectedAlert) {
		LOGGER.fine("Edit alert activation : " + selectedAlert);
		if (selectedAlert != null) {
			initializeAlertActivationDriver();		
			clientFactory.getAdminRequestFactory().alertActivationRequest().find(selectedAlert.stableId()).fire(new AjaxLoaderReceiver<AlertActivationProxy>(clientFactory) { //load the selected alert
				@Override
				public void onCustomSuccess(AlertActivationProxy response) {
					AlertActivationRequest request = clientFactory.getAdminRequestFactory().alertActivationRequest();
					clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsMessages().getEditSelectedAlertActivation(getAlertTranslation(response.getAlertType())), MessageLabelStyle.DEFAULT);
					clientFactory.getConfigureAlertView().setDisplayedImageIcon(response.isEnableAlert());
					alertActivationDriver.edit(response, request);
					request.merge().using(response);				
				}
				
				@Override
				public void onCustomFailure(ServerFailure error) {
					LOGGER.warning("Error during loading selected alert activation : " + error.getMessage());
					clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getLoadSelectedAlertActivationError(), MessageLabelStyle.RED);
				}
			});
		}
	}
	
	/**
	 * Get the translation for the alert
	 * @param type the alert type
	 * @return the matching translation
	 */
	private String getAlertTranslation(AlertType type) {
		String translation = "";
		switch (type) {
		case CUSTOM_IMAGE:
			translation = clientFactory.getConfigureAlertsConstants().getCustomImagesAlertName();
			break;

		case FM:
			translation = clientFactory.getConfigureAlertsConstants().getFMAlertName();
			break;
		
		case PLAYLIST:
			translation = clientFactory.getConfigureAlertsConstants().getPlaylistAlertName();
			break;

		case SHOUTCAST:
			translation = clientFactory.getConfigureAlertsConstants().getShoutcastAlertName();
			break;
		}
		
		return translation;
	}
	
	@Override
	public void onChangeAlertActivation() {
		clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getSavingSelectedAlert(), MessageLabelStyle.DEFAULT);
		AlertActivationRequest request = (AlertActivationRequest) alertActivationDriver.flush(); //flush changes
		request.fire(new AjaxLoaderReceiver<Void>(clientFactory) { //send changes
			@Override
			public void onCustomSuccess(Void response) {
				LOGGER.fine("Changes saved");
				clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getSavedSelectedAlert(), MessageLabelStyle.GREEN);
			}
			
			@Override
			public void onCustomFailure(ServerFailure error) {
				LOGGER.warning("Error during saving selected alert : " + error.getMessage());
				clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getSaveSelectedAlertActivationError(), MessageLabelStyle.RED);
			}
		});			
	}
	
	/**
	 * Initialize the {@link AlertActivationDriver}
	 */
	private void initializeAlertActivationDriver() {
		alertActivationDriver = GWT.create(AlertActivationDriver.class);
		alertActivationDriver.initialize(clientFactory.getAdminRequestFactory(), (ConfigureAlertViewImpl) clientFactory.getConfigureAlertView());
	}
	
	/**
	 * Initialize the {@link AlertEmailDriver}
	 */
	private void initializeAlertEmailDriver() {
		alertEmailDriver = GWT.create(AlertEmailDriver.class);
		alertEmailDriver.initialize(clientFactory.getAdminRequestFactory(), clientFactory.getConfigureAlertView().getAlertEmailEditor());
	}
	
	@Override
	public void onSelectedAlert(AlertEmailProxy selectedAlert) {
		//init request
		LOGGER.fine("Edit alert email : " + selectedAlert);
		if (selectedAlert != null) {
			initializeAlertEmailDriver();
			
			clientFactory.getAdminRequestFactory().alertEmailRequest().find(selectedAlert.stableId()).fire(new AjaxLoaderReceiver<AlertEmailProxy>(clientFactory) { //load the selected alert
				
				@Override
				public void onCustomSuccess(AlertEmailProxy response) {
					AlertEmailRequest request = clientFactory.getAdminRequestFactory().alertEmailRequest();
					clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsMessages().getEditSelectedAlertEmail(response.getEmail()), MessageLabelStyle.DEFAULT);
					alertEmailDriver.edit(response, request);
					request.merge().using(response); //next step : merging
				}
				
				@Override
				public void onCustomFailure(ServerFailure error) {
					LOGGER.warning("Error during loading selected alert email : " + error.getMessage());
					clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getLoadSelectedAlertEmailError(), MessageLabelStyle.RED);
				}
			});
		}
	}
	
	@Override
	public void onClickSaveAlertEmail() {
		//init request
		clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getSavingSelectedAlert(), MessageLabelStyle.DEFAULT);
		AlertEmailRequest request = (AlertEmailRequest) alertEmailDriver.flush(); //flush changes
		
		request.fire(new AjaxLoaderReceiver<Void>(clientFactory) { //send changes
			
			@Override
			public void onCustomSuccess(Void response) {
				LOGGER.fine("Changes saved");
				clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getSavedSelectedAlert(), MessageLabelStyle.GREEN);
				clientFactory.getConfigureAlertView().hideAlertEmailEditor();
				
				if (reloadEmailList) { //if reload image list requested
					loadAlertEmailList();
					reloadEmailList = false;
				}
			}
			
			@Override
			public void onCustomConstraintViolation(Set<ConstraintViolation<?>> violations) {
				alertEmailDriver.setConstraintViolations(violations);
				clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getSaveSelectedAlertEmailValidationErrors(), MessageLabelStyle.RED);
			}
			
			@Override
			public void onCustomFailure(ServerFailure error) {
				LOGGER.warning("Error during saving selected alert : " + error.getMessage());
				clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getSaveSelectedAlertEmailError(), MessageLabelStyle.RED);
			}			
		});			
	}
	
	@Override
	public void onClickAddEmail() {
		//create request
		LOGGER.fine("Add new email");
		initializeAlertEmailDriver();
		AlertEmailRequest request = clientFactory.getAdminRequestFactory().alertEmailRequest();
		AlertEmailProxy newEmailAlert = request.create(AlertEmailProxy.class);
		
		//finish display
		clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getNewAlertEmail(), MessageLabelStyle.DEFAULT);
		alertEmailDriver.edit(newEmailAlert, request);
		request.persist().using(newEmailAlert); //next step : save
		reloadEmailList = true; //request reload email list after
	}

	@Override
	public void onClickDeleteButton() {
		//get email to delete
		AlertEmailProxy emailToDelete = clientFactory.getConfigureAlertView().getSelectedAlertEmail();
		LOGGER.fine("Alert email requested to delete : " + emailToDelete);
		
		if ((emailToDelete != null) 
				&& (Window.confirm(clientFactory.getConfigureAlertsConstants().getConfirmAlertEmailDeletion()))){ //if not null : deletion
			clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getDeletingSelectedAlertEmail(), MessageLabelStyle.DEFAULT);
			clientFactory.getAdminRequestFactory().alertEmailRequest().remove().using(emailToDelete).fire(new AjaxLoaderReceiver<Void>(clientFactory) {

				@Override
				public void onCustomSuccess(Void response) {
					LOGGER.fine("Selected email deleted");
					clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getDeleteSelectedAlertEmail(), MessageLabelStyle.GREEN);
					clientFactory.getConfigureAlertView().hideAlertEmailEditor();
					loadAlertEmailList(); //reload list
				}
				
				@Override
				public void onCustomFailure(ServerFailure error) {
					LOGGER.warning("Error during deleting selected alert : " + error.getMessage());
					clientFactory.getConfigureAlertView().setMessageLabelTextAndStyle(clientFactory.getConfigureAlertsConstants().getDeleteSelectedAlertEmailError(), MessageLabelStyle.RED);
				}								
			});
			
		} else 
			LOGGER.warning("Selected email is null !");
	}
	
}
