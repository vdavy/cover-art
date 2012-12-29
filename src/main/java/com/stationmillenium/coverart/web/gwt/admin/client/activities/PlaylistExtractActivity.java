/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.activities;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.view.PlaylistExtractView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.PlaylistExtractView.Presenter;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.AbstractMessageView.MessageLabelStyle;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.autobean.PlaylistExtract;

/**
 * Activity for the playlist extract part
 * @author vincent
 *
 */
public class PlaylistExtractActivity extends AbstractActivity implements Presenter {

	//logger
	private static final Logger LOGGER = Logger.getLogger(PlaylistExtractActivity.class.getName());
	
	private ClientFactory clientFactory;
	/**
	 * Create {@link PlaylistExtractActivity}
	 * @param clientFactory the client factory
	 */
	public PlaylistExtractActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget, com.google.gwt.event.shared.EventBus)
	 */
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		PlaylistExtractView view = clientFactory.getPlaylistExtractView();
		view.setPresenter(this);
		panel.setWidget(view);
	}
	
	@Override
	public void launchPlaylistExtract(Date startDate, String startHours, String startMinutes, 
			Date endDate, String endHours, String endMinutes) {
		
		//parse dates
		Date startDateComplete = parseStartDate(startDate, startHours, startMinutes);
		Date endDateComplete = parseEndDate(endDate, endHours, endMinutes);
		
		if ((startDateComplete != null) && (endDateComplete != null)) { //both of dates have been properly parsed
			if (startDateComplete.after(endDateComplete))
				clientFactory.getPlaylistExtractView().setMessageLabelTextAndStyle(clientFactory.getPlaylistExtractConstants().getEndDateBeforeStartDate(), MessageLabelStyle.RED);
			else //launch extract
				sendDataWithAutobean(startDateComplete, endDateComplete);			
		} else //display correct error message
			displayDateErrorMessage(startDateComplete, endDateComplete);	
	}

	/**
	 * Send the data with autobean
	 * @param startDateComplete the start date
	 * @param endDateComplete the end date
	 */
	private void sendDataWithAutobean(Date startDateComplete, Date endDateComplete) {
		AutoBean<PlaylistExtract> autobean = clientFactory.getAutobeanFactory().getPlaylistExtract(); // creata autobean
		PlaylistExtract playlistExtract =  autobean.as(); //get bean
		playlistExtract.setStartExtractDate(startDateComplete); //set dates 
		playlistExtract.setEndExtractDate(endDateComplete); 
		String json  = AutoBeanCodex.encode(autobean).getPayload(); //get the json string
		LOGGER.fine("Generated JSON : " + json);
		
		//send to the form
		clientFactory.getPlaylistExtractView().setJSONAndSendForm(json);				
		clientFactory.getPlaylistExtractView().setMessageLabelTextAndStyle(clientFactory.getPlaylistExtractConstants().getExtract(), MessageLabelStyle.GREEN);
	}

	/**
	 * Display date error message if needed
	 * @param startDateComplete the start date
	 * @param endDateComplete the end date
	 */
	private void displayDateErrorMessage(Date startDateComplete,
			Date endDateComplete) {
		if ((startDateComplete == null) && (endDateComplete == null))
			clientFactory.getPlaylistExtractView().setMessageLabelTextAndStyle(clientFactory.getPlaylistExtractConstants().getInvalidStartEndDate(), MessageLabelStyle.RED);
		else if  (startDateComplete == null)
			clientFactory.getPlaylistExtractView().setMessageLabelTextAndStyle(clientFactory.getPlaylistExtractConstants().getInvalidStartDate(), MessageLabelStyle.RED);
		else
			clientFactory.getPlaylistExtractView().setMessageLabelTextAndStyle(clientFactory.getPlaylistExtractConstants().getInvalidEndDate(), MessageLabelStyle.RED);
	}

	/**
	 * Parse the end date
	 * @param endDate the end date in {@link Date} format
	 * @param endHours the end hours in {@link String} format
	 * @param endMinutes the end minutes in {@link String} format
	 * @return the parsed end date or <code>null</code> if error
	 */
	private Date parseEndDate(Date endDate, String endHours, String endMinutes) {
		Date endDateComplete = null;
		try { //parse end dat
			long hoursInLong = Long.valueOf(endHours) * 3600 * 1000;
			long minutesInLong = Long.valueOf(endMinutes) * 60 * 1000;
			endDateComplete = new Date(endDate.getTime() + hoursInLong + minutesInLong);
			LOGGER.fine("End date : " + endDateComplete);
		} catch(NumberFormatException e) {
			LOGGER.log(Level.WARNING, "Error during parsing start date", e);			
		}
		return endDateComplete;
	}

	/**
	 * Parse the start date
	 * @param endDate the start date in {@link Date} format
	 * @param endHours the start hours in {@link String} format
	 * @param endMinutes the start minutes in {@link String} format
	 * @return the parsed start date or <code>null</code> if error
	 */
	private Date parseStartDate(Date startDate, String startHours, String startMinutes) {
		Date startDateComplete = null;
		try { //parse start date
			long hoursInLong = Long.valueOf(startHours) * 3600 * 1000;
			long minutesInLong = Long.valueOf(startMinutes) * 60 * 1000;
			startDateComplete = new Date(startDate.getTime() + hoursInLong + minutesInLong);
			LOGGER.fine("Start date : " + startDateComplete);
		} catch(NumberFormatException e) {
			LOGGER.log(Level.WARNING, "Error during parsing start date", e);			
		}
		return startDateComplete;
	}

}
