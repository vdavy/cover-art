/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.view.impl;

import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
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
	
	//presenter
	private PlayerViewPresenter presenter;

	//data provider
	ListDataProvider<String> dataProvider = new ListDataProvider<String>();

	public PlayerViewImpl() {		
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
		
		
	}

	@Override
	public void setCurrentSong(SafeHtml currentSong) {
		this.currentSong.setText(currentSong.asString());
	}
	
	@Override
	public void setPresenter(PlayerViewPresenter presenter) {
		this.presenter = presenter;
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
