/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.view.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.places.ConfigureAlertsPlace;
import com.stationmillenium.coverart.web.gwt.admin.client.places.ConfigureCustomImagesPlace;
import com.stationmillenium.coverart.web.gwt.admin.client.places.GeneralConfigPlace;
import com.stationmillenium.coverart.web.gwt.admin.client.places.PlaylistExtractPlace;
import com.stationmillenium.coverart.web.gwt.admin.client.places.StatusReportPlace;
import com.stationmillenium.coverart.web.gwt.admin.client.places.StatusReportPlace.ReportType;
import com.stationmillenium.coverart.web.gwt.admin.client.view.MainView;

/**
 * Main view of the GWT admin module
 * @author vincent
 *
 */
public class MainViewImpl extends Composite implements MainView {

	private static MainViewImplUiBinder uiBinder = GWT.create(MainViewImplUiBinder.class);

	interface MainViewImplUiBinder extends UiBinder<Widget, MainViewImpl> {
	}

	//content panel to display views
	@UiField SimplePanel contentPanel;
	
	//menus widget 
	@UiField MenuItem icecastStatusMenuItem;
	@UiField MenuItem playlistStatusMenuItem;
	@UiField MenuItem fmStatusMenuItem;
	@UiField MenuItem playlistExtractMenuItem;
	@UiField MenuItem generalConfigMenuItem;
	@UiField MenuItem alertsConfigMenuItem;
	@UiField MenuItem customImagesConfigMenuItem;
	
	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
	public MainViewImpl(final ClientFactory clientFactory) {
		initWidget(uiBinder.createAndBindUi(this));
		
		icecastStatusMenuItem.setScheduledCommand(new Command() {	 //set command for Icecast menu item
			@Override
			public void execute() {
				clientFactory.getPlaceController().goTo(new StatusReportPlace(ReportType.ICECAST));				
			}
		});
		
		playlistStatusMenuItem.setScheduledCommand(new Command() { //set command for playlist menu item
			@Override
			public void execute() {
				clientFactory.getPlaceController().goTo(new StatusReportPlace(ReportType.PLAYLIST));
			}
		});
		
		fmStatusMenuItem.setScheduledCommand(new Command() { //set command for fm menu item
			@Override
			public void execute() {
				clientFactory.getPlaceController().goTo(new StatusReportPlace(ReportType.FM));				
			}
		});
		
		playlistExtractMenuItem.setScheduledCommand(new Command() { //set command for playlist extract menu item
			@Override
			public void execute() {
				clientFactory.getPlaceController().goTo(new PlaylistExtractPlace());	
			}
		});
	
		generalConfigMenuItem.setScheduledCommand(new Command() { //set command for alerts configuration menu item
			@Override
			public void execute() {
				clientFactory.getPlaceController().goTo(new GeneralConfigPlace() );	
			}
		});
		
		alertsConfigMenuItem.setScheduledCommand(new Command() { //set command for alerts configuration menu item
			@Override
			public void execute() {
				clientFactory.getPlaceController().goTo(new ConfigureAlertsPlace());
			}
		});
		
		customImagesConfigMenuItem.setScheduledCommand(new Command() { //set command for custom images configuration menu item
			@Override
			public void execute() {
				clientFactory.getPlaceController().goTo(new ConfigureCustomImagesPlace());
			}
		});
		
	}
	
	@Override
	public SimplePanel getContentPanel() {
		return contentPanel;
	}

}
