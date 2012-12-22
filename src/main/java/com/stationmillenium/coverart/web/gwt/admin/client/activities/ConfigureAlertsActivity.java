/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.activities;

import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.view.ConfigureAlertView.Presenter;

/**
 * Activity for the configure alerts part
 * @author vincent
 *
 */
public class ConfigureAlertsActivity extends AbstractActivity implements Presenter {

	//logger
	private static final Logger LOGGER = Logger.getLogger(ConfigureAlertsActivity.class.getName());
	
	/**
	 * Create a new {@link ConfigureAlertsActivity}
	 * @param clientFactory the client factory
	 */
	public ConfigureAlertsActivity(ClientFactory clientFactory) {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget, com.google.gwt.event.shared.EventBus)
	 */
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		// TODO Auto-generated method stub

	}

}
