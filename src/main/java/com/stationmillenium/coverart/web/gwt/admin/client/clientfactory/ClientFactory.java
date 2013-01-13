/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.clientfactory;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.AdminBundle;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.constants.ConfigureAlertsConstants;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.constants.GeneralConfigConstants;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.constants.PlaylistExtractConstants;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.constants.StatusReportConstants;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.messages.ConfigureAlertsMessages;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.messages.GeneralConfigMessages;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.autobean.AdminAutobeanFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.AdminRequestFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.server.rpc.AdminServiceAsync;
import com.stationmillenium.coverart.web.gwt.admin.client.view.ConfigureAlertView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.GeneralConfigView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.MainView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.PlaylistExtractView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.StatusReportView;

/**
 * Client factory interface
 * @author vincent
 *
 */
public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();
	MainView getMainView();
	Widget getMainViewAsWidget();
	AdminRequestFactory getAdminRequestFactory();
	StatusReportConstants getConstants();
	StatusReportView getStatusReportView();
	AdminBundle getAdminBundle();
	AdminAutobeanFactory getAutobeanFactory();
	PlaylistExtractConstants getPlaylistExtractConstants();
	PlaylistExtractView getPlaylistExtractView();
	GeneralConfigView getGeneralConfigView();	
	AdminServiceAsync getAdminService();
	GeneralConfigConstants getGeneralConfigConstants();
	ConfigureAlertView getConfigureAlertView();
	ConfigureAlertsConstants getConfigureAlertsConstants();
	ConfigureAlertsMessages getConfigureAlertsMessages();
	GeneralConfigMessages getGeneralConfigMessages();
}
