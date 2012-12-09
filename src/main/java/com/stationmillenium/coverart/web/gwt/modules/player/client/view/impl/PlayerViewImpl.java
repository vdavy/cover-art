/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.modules.player.client.view.impl;

import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.stationmillenium.coverart.web.gwt.modules.player.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.modules.player.client.view.PlayerViewInterface;

/**
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

	public PlayerViewImpl(ClientFactory clientFactory) {		
		//init celle list
		songList = new CellList<String>(new TextCell());
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
		
		//add html5 player if supported
		if (Audio.isSupported()) {
			createAudioPlayer(clientFactory);
		}
	}

	/**
	 * Create the audio player
	 * @param clientFactory the client factory
	 */
	private void createAudioPlayer(final ClientFactory clientFactory) {
		LOGGER.info("Audio tag supported"); 
		
		//create it
		final Audio audioTag = Audio.createIfSupported();
		audioTag.setLoop(true);
		audioTag.setControls(true);
		audioTag.setAutoplay(true);
		audioTag.setWidth("280px");
		audioTag.setVolume(1);
		audioTag.addClickHandler(new ClickHandler() { //handle 	pause click
			@Override
			public void onClick(ClickEvent event) {
				Timer timer = new Timer() {						
					@Override
					public void run() {
						if (audioTag.isPaused()) { //if player pause
							String currentSrc = audioTag.getCurrentSrc(); //get current src
							audioTag.setSrc(""); //set empty src to stop player
							audioTag.setAutoplay(false); //disable autoplay		
							audioTag.setPreload("none"); //no buffering
							audioTag.setSrc(currentSrc); //reset src								
							LOGGER.fine("Player reset src");							
						}							
					}
				};
				
				timer.schedule(1000); //call 500ms later, for player to pause
			}
		});

		for (String url : clientFactory.getConstants().streamURLs()) { //add sources
			audioTag.addSource(url);
			LOGGER.fine("Source added : " + url);
		}
		
		//add to dom
		player.clear();
		player.add(audioTag);
	}

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
	
}
