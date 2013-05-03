/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.view.impl;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.stationmillenium.coverart.web.gwt.player.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.player.client.view.SmallPlayerViewInterface;

/**
 * Implementation for {@link SmallPlayerViewInterface}
 * @author vincent
 *
 */
public class SmallPlayerViewImpl extends Composite implements SmallPlayerViewInterface {

	//log
	private static final Logger LOGGER = Logger.getLogger(SmallPlayerViewImpl.class.getName());
	
	//ui binder
	private static SmallPlayerViewImplUiBinder uiBinder = GWT.create(SmallPlayerViewImplUiBinder.class);
	interface SmallPlayerViewImplUiBinder extends UiBinder<Widget, SmallPlayerViewImpl> {
	}

	//local instances
	private SmallPlayerViewPresenter presenter;
	
	//ui fields
	@UiField Label currentSong;
	@UiField FocusPanel mainPanel;
	
	/**
	 * Create a new {@link SmallPlayerViewImpl}
	 * @param clientFactory the client factory
	 */
	public SmallPlayerViewImpl(ClientFactory clientFactory) {		
		//init ui binder
		initWidget(uiBinder.createAndBindUi(this));
	}

	
	@Override
	public void setCurrentSong(String currentSong) {
		this.currentSong.setText(currentSong);
	}
	
	/**
	 * Handle click on main panel to open main player
	 * @param event the event
	 */
	@UiHandler("mainPanel")
	public void onClickMainPanel(ClickEvent event) {
		LOGGER.fine("Open main player");
		presenter.openMainPlayer();
	}
	
	@Override
	public void sePresenter(SmallPlayerViewPresenter presenter) {
		this.presenter = presenter;
	}
}
