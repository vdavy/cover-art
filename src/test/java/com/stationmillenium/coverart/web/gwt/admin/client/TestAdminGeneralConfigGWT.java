/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.stationmillenium.coverart.web.gwt.admin.client.activities.GeneralConfigActivity;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;

/**
 * Test case for admin GWT module - activity : General config
 * @author vincent
 *
 */
public class TestAdminGeneralConfigGWT extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "com.stationmillenium.coverart.web.gwt.admin.Admin";
	}

	/**
	 * Test FM alert checkbox click
	 */
	public void testGeneralConfigFMAlertCheckbox() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		GeneralConfigActivity activity = new GeneralConfigActivity(clientFactory);
		activity.start(clientFactory.getMainView().getContentPanel(), null);
		activity.onClickFMAlertCheckbox(true);
		activity.onClickFMAlertCheckbox(false);
	}
	
	/**
	 * Test polling service checkbox click
	 */
	public void testGeneralConfigPollingServiceCheckbox() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		GeneralConfigActivity activity = new GeneralConfigActivity(clientFactory);
		activity.start(clientFactory.getMainView().getContentPanel(), null);
		activity.onClickPollingServiceCheckbox(true);
		activity.onClickPollingServiceCheckbox(false);
	}
	
	/**
	 * Test index button
	 */
	public void testGeneralConfigIndexButton() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		GeneralConfigActivity activity = new GeneralConfigActivity(clientFactory);
		activity.start(clientFactory.getMainView().getContentPanel(), null);
		activity.onClickIndexButton();
	}
	
}
