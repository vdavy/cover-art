/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.stationmillenium.coverart.web.gwt.admin.client.activities.ConfigureAlertsActivity;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertActivationProxy;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertEmailProxy;

/**
 * Test case for admin GWT module - activity : Configure alerts
 * @author vincent
 *
 */
public class TestAdminConfigureAlertsGWT extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "com.stationmillenium.coverart.web.gwt.admin.Admin";
	}

	/**
	 * Test selected alert
	 */
	public void testSelectedAlert() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		ConfigureAlertsActivity activity = new ConfigureAlertsActivity(clientFactory);
		activity.start(clientFactory.getMainView().getContentPanel(), null);
		activity.onSelectedAlert((AlertActivationProxy) null);
		activity.onSelectedAlert((AlertEmailProxy) null);
	}
	
	/**
	 * Test add email
	 */
	public void testAddEmail() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		ConfigureAlertsActivity activity = new ConfigureAlertsActivity(clientFactory);
		activity.start(clientFactory.getMainView().getContentPanel(), null);
		activity.onClickAddEmail();
	}
	
}
