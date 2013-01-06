/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.clientfactory;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.AdminBundle;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.constants.ConfigureAlertsConstants;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.constants.GeneralConfigConstants;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.constants.PlaylistExtractConstants;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.constants.StatusReportConstants;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.messages.ConfigureAlertsMessages;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.autobean.AdminAutobeanFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.AdminRequestFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.server.rpc.AdminService;
import com.stationmillenium.coverart.web.gwt.admin.client.server.rpc.AdminServiceAsync;
import com.stationmillenium.coverart.web.gwt.admin.client.view.ConfigureAlertView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.GeneralConfigView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.MainView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.PlaylistExtractView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.StatusReportView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.ConfigureAlertViewImpl;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.GeneralConfigViewImpl;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.MainViewImpl;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.PlaylistExtractViewImpl;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.StatusReportViewImpl;

/**
 * GWT admin module client factory impl
 * @author vincent
 *
 */
public class ClientFactoryImpl implements ClientFactory {

	private EventBus eventBus = new SimpleEventBus();
	private PlaceController placeController = new PlaceController(eventBus);
	private MainViewImpl mainView = new MainViewImpl(this);
	private StatusReportConstants statusReportConstants = GWT.create(StatusReportConstants.class);
	private StatusReportView statusReportView = new StatusReportViewImpl(this);
	private AdminBundle adminBundle = GWT.create(AdminBundle.class);
	private AdminAutobeanFactory adminAutobeanFactory = GWT.create(AdminAutobeanFactory.class);
	private PlaylistExtractConstants playlistExtractConstants = GWT.create(PlaylistExtractConstants.class);
	private GeneralConfigConstants generalConfigConstants = GWT.create(GeneralConfigConstants.class);
	private PlaylistExtractView playlistExtractView = new PlaylistExtractViewImpl(this);
	private GeneralConfigView generalConfigView = new GeneralConfigViewImpl(this);
	private AdminServiceAsync adminService = GWT.create(AdminService.class);
	private ConfigureAlertView configureAlertView = new ConfigureAlertViewImpl(this);
	private ConfigureAlertsConstants configureAlertsConstants = GWT.create(ConfigureAlertsConstants.class);
	private ConfigureAlertsMessages configureAlertsMessages = GWT.create(ConfigureAlertsMessages.class);
		
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}
	
	@Override
	public MainView getMainView() {
		return mainView;
	}
	
	@Override
	public Widget getMainViewAsWidget() {
		return mainView;
	}
	
	@Override
	public AdminRequestFactory getAdminRequestFactory() {
		AdminRequestFactory adminRequestFactory = GWT.create(AdminRequestFactory.class);
		adminRequestFactory.initialize(eventBus);
		return adminRequestFactory;
	}
	
	@Override
	public StatusReportConstants getConstants() {
		return statusReportConstants;
	}
	
	@Override
	public StatusReportView getStatusReportView() {
		return statusReportView;
	}
	
	@Override
	public AdminBundle getAdminBundle() {
		return adminBundle;
	}
	
	@Override
	public AdminAutobeanFactory getAutobeanFactory() {
		return adminAutobeanFactory;
	}
	
	@Override
	public PlaylistExtractView getPlaylistExtractView() {
		return playlistExtractView;
	}
	
	@Override
	public PlaylistExtractConstants getPlaylistExtractConstants() {
		return playlistExtractConstants;
	}

	@Override
	public GeneralConfigView getGeneralConfigView() {
		return generalConfigView;
	}
	
	@Override
	public AdminServiceAsync getAdminService() {
		return adminService;
	}
	
	@Override
	public GeneralConfigConstants getGeneralConfigConstants() {
		return generalConfigConstants;
	}
	
	@Override
	public ConfigureAlertView getConfigureAlertView() {
		return configureAlertView;
	}
	
	@Override
	public ConfigureAlertsConstants getConfigureAlertsConstants() {
		return configureAlertsConstants;
	}
	
	@Override
	public ConfigureAlertsMessages getConfigureAlertsMessages() {
		return configureAlertsMessages;
	}
	
}
