/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.view.impl;

import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.view.PlaylistExtractView;

/**
 * Implementation for the playlist extract view
 * @author vincent
 *
 */
public class PlaylistExtractViewImpl extends AbstractMessageView implements PlaylistExtractView {

	interface PlaylistExtractViewImplUiBinder extends UiBinder<Widget, PlaylistExtractViewImpl> {}
	
	//static variables
	private static final Logger LOGGER = Logger.getLogger(PlaylistExtractViewImpl.class.getName());
	private static PlaylistExtractViewImplUiBinder uiBinder = GWT.create(PlaylistExtractViewImplUiBinder.class);
	
	//instance variables
	private Presenter presenter;
	private ClientFactory clientFactory;

	//fields
	@UiField Label messageLabel;
	@UiField DateBox startDateSearch;
	@UiField TextBox  startHourSearch;
	@UiField TextBox  startMinuteSearch;
	@UiField DateBox endDateSearch;
	@UiField TextBox  endHourSearch;
	@UiField TextBox  endMinuteSearch;
	@UiField FormPanel form;
	@UiField Hidden jsonContent;
	
	/**
	 * New {@link PlaylistExtractViewImpl}
	 * @param clientFactory the client factory
	 */
	public PlaylistExtractViewImpl(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		initWidget(uiBinder.createAndBindUi(this));
		
		//display current date in date search
		initDateFields();		
		
		//init form action
		String action = GWT.getModuleBaseURL() + "playlistExtract";
		form.setAction(action);
	}

	/**
	 * Init the date fields
	 */
	private void initDateFields() {
		Date currentDate = new Date();
		startDateSearch.setFormat(new DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));		
		startDateSearch.setValue(currentDate);
		startHourSearch.setText(DateTimeFormat.getFormat("HH").format(currentDate));
		startMinuteSearch.setText(DateTimeFormat.getFormat("mm").format(currentDate));
		endDateSearch.setFormat(new DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));		
		endDateSearch.setValue(currentDate);
		endHourSearch.setText(DateTimeFormat.getFormat("HH").format(currentDate));
		endMinuteSearch.setText(DateTimeFormat.getFormat("mm").format(currentDate));
		setMessageLabelTextAndStyle(clientFactory.getPlaylistExtractConstants().getDefaultMessage(), MessageLabelStyle.DEFAULT);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	/**
	 * Method to call when clicking on send button
	 * @param event the click event
	 */
	@UiHandler("sendButton")
	public void onClickExtractButton(ClickEvent event) {
			presenter.launchPlaylistExtract(startDateSearch.getValue(), startHourSearch.getValue(), startMinuteSearch.getValue(), 
					endDateSearch.getValue(), endHourSearch.getValue(), endMinuteSearch.getValue());
	}
	
	/**
	 * Reset the form
	 * @param event the click event
	 */
	@UiHandler("resetButton")
	public void onClickResetButton(ClickEvent event) {
		LOGGER.fine("Reset form");
		initDateFields();
	}
	
	@Override
	public void setJSONAndSendForm(String json) {
		jsonContent.setValue(json);
		form.submit();
	}
	
	@Override
	public void setMessageLabelTextAndStyle(String text, MessageLabelStyle style) {
		super.setMessageLabelTextAndStyle(text, style);
	}
	
	@Override
	protected ClientFactory getClientFactory() {
		return clientFactory;
	}
	
	@Override
	protected Label getMessageLabel() {
		return messageLabel; 
	}

}
