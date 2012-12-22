/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.clientfactory;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.AdminBundle;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.constants.PlaylistExtractConstants;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.constants.StatusReportConstants;
import com.stationmillenium.coverart.web.gwt.admin.client.server.autobean.AdminAutobeanFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requests.AdminRequestFactory;
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
}
