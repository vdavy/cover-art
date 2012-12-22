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
import com.stationmillenium.coverart.web.gwt.admin.client.resources.constants.PlaylistExtractConstants;
import com.stationmillenium.coverart.web.gwt.admin.client.resources.constants.StatusReportConstants;
import com.stationmillenium.coverart.web.gwt.admin.client.server.autobean.AdminAutobeanFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requests.AdminRequestFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.view.MainView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.PlaylistExtractView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.StatusReportView;
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
	private AdminRequestFactory adminRequestFactory = GWT.create(AdminRequestFactory.class);
	private StatusReportConstants statusReportConstants = GWT.create(StatusReportConstants.class);
	private StatusReportView statusReportView = new StatusReportViewImpl(this);
	private AdminBundle adminBundle = GWT.create(AdminBundle.class);
	private AdminAutobeanFactory adminAutobeanFactory = GWT.create(AdminAutobeanFactory.class);
	private PlaylistExtractConstants playlistExtractConstants = GWT.create(PlaylistExtractConstants.class);
	private PlaylistExtractView playlistExtractView = new PlaylistExtractViewImpl(this);
	
	public ClientFactoryImpl() {
		adminRequestFactory.initialize(eventBus);
	}
	
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
	
}
