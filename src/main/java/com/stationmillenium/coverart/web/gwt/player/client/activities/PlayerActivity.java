/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.activities;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.stationmillenium.coverart.web.gwt.player.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.player.client.events.UpdateHistoryListEvent;
import com.stationmillenium.coverart.web.gwt.player.client.view.PlayerViewInterface;
import com.stationmillenium.coverart.web.gwt.player.client.view.PlayerViewInterface.PlayerViewPresenter;
import com.stationmillenium.coverart.web.gwt.player.shared.SongGWTDTO;

/**
 * Player GWT module main activity
 * @author vincent
 *
 */
public class PlayerActivity extends AbstractActivity implements PlayerViewPresenter {

	private ClientFactory clientFactory;

	//local instances	
	private SongGWTDTO currentSong;
	private boolean inError = false;
		
	/**
	 * Instanciate new activity
	 * @param clientFactory client factory
	 */
	public PlayerActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		Window.setTitle(clientFactory.getConstants().getWindowTitle());
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget, com.google.gwt.event.shared.EventBus)
	 */
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		PlayerViewInterface view = clientFactory.getPlayerView();
		view.setPresenter(this);
		panel.setWidget(view);
	}

	@Override
	public void updatePlayer() {
		//load current song
		clientFactory.getService().getLastSong(new AsyncCallback<SongGWTDTO>() {

			@Override
			public void onSuccess(SongGWTDTO newSong) {
				if ((newSong != null) && (newSong.getArtist() != null) && (newSong.getTitle() != null)) { //if some song provided
					if (!newSong.equals(currentSong)) {
						//set up image and text
						String text = clientFactory.getMessages().currentSongText(newSong.getArtist(), newSong.getTitle());
						clientFactory.getPlayerView().setCurrentSong(SafeHtmlUtils.fromString(text));
						if (newSong.getImagePath() != null) {
							SafeUri imageUri = UriUtils.fromString(GWT.getHostPageBaseURL() + newSong.getImagePath());
							clientFactory.getPlayerView().setImage(imageUri, newSong.getImageWidth(), newSong.getImageHeight());
						}
						
						//finish update						
						currentSong = newSong;
						inError = false;
						clientFactory.getEventBus().fireEvent(new UpdateHistoryListEvent()); //fire update history list event
						
						GWT.log("Player updated : " + newSong);
						
					} else
						GWT.log("Player already up-to-date");					
				} else { 
					GWT.log("Player updated : null");
					displayErrorSong();
				}			
				
			}

			@Override
			public void onFailure(Throwable caught) { //in case of error
				GWT.log("Error during player update", caught);
				displayErrorSong();
			}
			
		});	
	}

	@Override
	public void updateHistoryList(boolean displayLastSong) {
		//load songs history list
		clientFactory.getService().getLast5PreviousSongs(displayLastSong, new AsyncCallback<List<SongGWTDTO>>() {
						
			@Override
			public void onSuccess(List<SongGWTDTO> result) {
				//fill in song history list
				List<String> historyList = new ArrayList<String>();
				
				for (SongGWTDTO song : result) { // for each song to add
					if ((song != null) && (song.getArtist() != null) && (song.getTitle() != null)) { //if some data
						String text = clientFactory.getMessages().currentSongText(song.getArtist(), song.getTitle()); //format text
						historyList.add(text); //add text
						GWT.log("Added to history list : " + text);
					}
				}
				
				//update list
				clientFactory.getPlayerView().setSongHistoryList(historyList);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Error during songs history loading", caught);
			}
			
		});
	}
	
	/**
	 * Display "unavailable" song
	 */
	private void displayErrorSong() {
		//update player
		String text = clientFactory.getConstants().songUnaivalaible();
		clientFactory.getPlayerView().setCurrentSong(SafeHtmlUtils.fromString(text));
		clientFactory.getPlayerView().setImage(
				clientFactory.getResources().logoMillenium().getSafeUri(), 
				String.valueOf(clientFactory.getResources().logoMillenium().getWidth()), 
				String.valueOf(clientFactory.getResources().logoMillenium().getHeight()));	
		
		if (!inError) {
			//update history list
			UpdateHistoryListEvent event = new UpdateHistoryListEvent();
			event.setDisplayLastSong(true);
			clientFactory.getEventBus().fireEvent(event); //fire update history list event for init
			inError = true;			
		}
	}

	/**
	 * Manage song history
	 * @param text text to add
	 */
//	private void manageSongHistory(String text) {
//		//manage history
//		if ((historyList.size() > 0) && (historyList.get(0) != null)) { //if some entry
//			if (!historyList.contains(text)) { //if not already 
//				historyList.add(0, text);
//				GWT.log("Added to history list : " + text);
//			} else
//				GWT.log("Already existing in history list : " + text);
//
//		} else { //if no entry, add it
//			historyList.add(text);
//			GWT.log("Added to history list : " + text);
//		}
//
//		//manage hisotry length
//		if (historyList.size() > 5) { //no more than 5 history items
//			historyList.remove(5);
//		}
//		
//		//update list
//		clientFactory.getPlayerView().setSongHistoryList(historyList);
//	}
}
