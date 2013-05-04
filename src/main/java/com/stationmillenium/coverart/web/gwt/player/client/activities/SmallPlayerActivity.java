/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.activities;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.stationmillenium.coverart.web.gwt.player.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.player.client.view.SmallPlayerViewInterface;
import com.stationmillenium.coverart.web.gwt.player.client.view.SmallPlayerViewInterface.SmallPlayerViewPresenter;
import com.stationmillenium.coverart.web.gwt.player.shared.SongGWTDTO;

/**
 * Player GWT module small player activity
 * @author vincent
 *
 */
public class SmallPlayerActivity extends AbstractActivity implements SmallPlayerViewPresenter {

	//log
	private static final Logger LOGGER = Logger.getLogger(SmallPlayerActivity.class.getName());	

	//local instances	
	private ClientFactory clientFactory;
	private SongGWTDTO currentSong;
	private boolean inError = false;
		
	/**
	 * Instanciate new activity
	 * @param clientFactory client factory
	 */
	public SmallPlayerActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		String jsCode = clientFactory.getResources().smallPlayerJS().getText();
		ScriptInjector.fromString(jsCode).inject();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget, com.google.gwt.event.shared.EventBus)
	 */
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		SmallPlayerViewInterface view = clientFactory.getSmallPlayer();
		view.sePresenter(this);
		panel.setWidget(view);
	}

	@Override
	public void updateSmallPlayer() {
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				//load current song
				clientFactory.getService().getLastSong(new AsyncCallback<SongGWTDTO>() {

					@Override
					public void onSuccess(SongGWTDTO newSong) {
						if ((newSong != null) && (newSong.getArtist() != null) && (newSong.getTitle() != null)) { //if some song provided
							if (!newSong.equals(currentSong)) {
								//set up image and text
								String text = clientFactory.getMessages().currentSongText(newSong.getArtist(), newSong.getTitle());
								clientFactory.getSmallPlayer().setCurrentSong(text);
														
								//finish update						
								currentSong = newSong;
								
								LOGGER.info("Small  player updated : " + newSong);
								
							} else
								LOGGER.fine("Small player already up-to-date");					
						} else { 
							LOGGER.warning("Small player updated : null");
							displayErrorSong();
						}			
						
					}

					@Override
					public void onFailure(Throwable caught) { //in case of error
						LOGGER.log(Level.SEVERE, "Error during small player update", caught);
						displayErrorSong();
					}
					
				});				
			}
			
			@Override
			public void onFailure(Throwable reason) {
				LOGGER.log(Level.SEVERE, "Error while loading small player", reason);
			}
		});
	}


	/**
	 * Display "unavailable" song
	 */
	private void displayErrorSong() {
		//update player
		String text = clientFactory.getConstants().songUnaivalaible();
		clientFactory.getSmallPlayer().setCurrentSong(text);
	}

	
	/**
	 * Open the main player
	 */
	private native void openPlayer() /*-{
		$wnd.openMainPlayer();
	}-*/;
	
	@Override
	public void openMainPlayer() {
		openPlayer();
	}
	
}