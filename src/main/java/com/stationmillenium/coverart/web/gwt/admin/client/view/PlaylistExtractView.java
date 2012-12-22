/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.view;

import java.util.Date;

import com.google.gwt.user.client.ui.IsWidget;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.PlaylistExtractViewImpl.MessageLabelStyle;

/**
 * Interface for the playlist extract view
 * @author vincent
 *
 */
public interface PlaylistExtractView extends IsWidget {

	/**
	 * Set the presenter to push events
	 * @param presenter
	 */
	void setPresenter(Presenter presenter);
	
	/**
	 * Set the JSON content and send the form
	 * @param json the json content
	 */
	void setJSONAndSendForm(String json);
	
	/**
	 * Set the message label text and style
	 * @param text the text to display
	 * @param style the style to apply to the text
	 */
	void setMessageLabelTextAndStyle(String text, MessageLabelStyle style);
	
	/**
	 * Presenter for playlist extract interface
	 * @author vincent
	 *
	 */
	public interface Presenter {
		
		/**
		 * Launch the playlist extract, by getting extract params
		 * @param startDate the start date
		 * @param startHours the start hours
		 * @param startMinutes the start minutes
		 * @param endDate the end date
		 * @param endHours the end hours
		 * @param endMinutes the end minutes
		 */
		void launchPlaylistExtract(Date startDate, String startHours, String startMinutes, 
				Date endDate, String endHours, String endMinutes);		
	}
	
}
