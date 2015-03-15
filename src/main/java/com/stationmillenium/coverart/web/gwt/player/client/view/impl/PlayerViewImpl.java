/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.view.impl;

import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellList.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.stationmillenium.coverart.web.gwt.player.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.player.client.view.PlayerViewInterface;

/**
 * Implementation for {@link PlayerViewInterface}
 * @author vincent
 *
 */
public class PlayerViewImpl extends Composite implements PlayerViewInterface {

	//log
	private static final Logger LOGGER = Logger.getLogger(PlayerViewImpl.class.getName());
	
	//ui binder
	private static PlayerViewImplUiBinder uiBinder = GWT.create(PlayerViewImplUiBinder.class);

	interface PlayerViewImplUiBinder extends UiBinder<Widget, PlayerViewImpl> {
	}
	
	//ui fields
	@UiField Label currentSong;
	@UiField Image songImage;
	@UiField(provided = true) CellList<String> songList;
	@UiField FlowPanel player;

	//data provider
	ListDataProvider<String> dataProvider = new ListDataProvider<String>();

	private ClientFactory clientFactory;
	
	/**
	 * Create a new {@link PlayerViewImpl}
	 * @param clientFactory the client factory
	 */
	public PlayerViewImpl(final ClientFactory clientFactory) {		
		this.clientFactory = clientFactory;
		
		//init celle list
		songList = new CellList<String>(new TextCell(), new CellList.Resources() {
			@Override
			public ImageResource cellListSelectedBackground() {
				return null;
			}			
			@Override
			public Style cellListStyle() {
				return (Style) clientFactory.getResources().emptyCellList();
			}
		});
		final SingleSelectionModel<String> ssm =  new SingleSelectionModel<String>();
		ssm.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				String selected  = ssm.getSelectedObject();
				LOGGER.fine("Selection " + selected);
			}
		});
		songList.setSelectionModel(ssm);
		dataProvider.addDataDisplay(songList);

		//init ui binder
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void insertPlayer() {
		//add piwik tracking code
		String jsCode = clientFactory.getResources().playerMusesJS().getText();
		ScriptInjector.fromString(jsCode).inject();
		insertPlayerJavascript();
	}
	
	/**
	 * Insert player - javascript part
	 */
	private native void insertPlayerJavascript() /*-{
		$wnd.insertPlayer();
	}-*/;

	@Override
	public void setCurrentSong(String currentSong) {
		this.currentSong.setText(currentSong);
	}
	
	@Override
	public void setImage(SafeUri uri, String width, String height) {
		songImage.setUrl(uri);
		songImage.setWidth(width);
		songImage.setHeight(height);
	}
	
	@Override
	public void setSongHistoryList(List<String> historyList) {
		dataProvider.setList(historyList);
	}
	
	@UiHandler("lastTitleLabel")
	public void onClickLastSong(ClickEvent event) {
		LOGGER.fine("Open title history page");
		Window.open(clientFactory.getConstants().lastSongsURL(), null, null);
	}
	
	@UiHandler("programScheduleLabel")
	public void onClickProgramSchedule(ClickEvent event) {
		LOGGER.fine("Open title history page");
		Window.open(clientFactory.getConstants().programScheduleURL(), null, null);
	}
	
	@UiHandler("contactLabel")
	public void onClickContact(ClickEvent event) {
		LOGGER.fine("Open title history page");
		Window.open(clientFactory.getConstants().contactURL(), null, null);
	}
	
}
