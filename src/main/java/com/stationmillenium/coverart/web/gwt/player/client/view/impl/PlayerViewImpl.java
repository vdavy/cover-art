/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.view.impl;

import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.media.client.Audio;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
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
 * @author vincent
 *
 */
public class PlayerViewImpl extends Composite implements PlayerViewInterface {

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
				GWT.log("Selection " + selected);
			}
		});
		songList.setSelectionModel(ssm);
		dataProvider.addDataDisplay(songList);

		//init ui binder
		initWidget(uiBinder.createAndBindUi(this));
		
		//add html5 player if supported
		if (Audio.isSupported()) {
			GWT.log("Audio tag supported"); 
			
			//create it
			Audio audioTag = Audio.createIfSupported();
			audioTag.setLoop(true);
			audioTag.setControls(true);
			audioTag.setAutoplay(true);
			audioTag.setWidth("280px");
			
			for (String url : clientFactory.getConstants().streamURLs()) { //add sources
				audioTag.addSource(url);
				GWT.log("Source added : " + url);
			}
			
			//add to dom
			player.clear();
			player.add(audioTag);
		}
	}

	@Override
	public void setCurrentSong(SafeHtml currentSong) {
		this.currentSong.setText(currentSong.asString());
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
