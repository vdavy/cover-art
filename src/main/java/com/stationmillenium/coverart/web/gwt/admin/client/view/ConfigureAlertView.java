/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.view;

import java.util.List;

import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.user.client.ui.IsWidget;
import com.stationmillenium.coverart.web.gwt.admin.client.utils.editors.AlertEmailEditor;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.AbstractMessageView.MessageLabelStyle;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertActivationProxy;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertEmailProxy;

/**
 * Interface of the configure alert view
 * @author vincent
 *
 */
public interface ConfigureAlertView extends IsWidget {

	/**
	 * Set the alert activation list for display
	 * @param alertList the list to display
	 */
	void setAlertActivationList(List<AlertActivationProxy> alertList);
	
	/**
	 * Set the alert list email for display
	 * @param alertList the list to display
	 */
	void setAlertEmailList(List<AlertEmailProxy> alertList);
	
	/**
	 * Set the presenter for events
	 * @param presenter the presenter
	 */
	void setPresenter(Presenter presenter);
	
	/**
	 * Set the message label text and style
	 * @param text the text to display
	 * @param style the style to apply to the text
	 */
	void setMessageLabelTextAndStyle(String text, MessageLabelStyle style);
	
	/**
	 * Display the green or red icon
	 * @param greenIcon <code>true</code> to display green icon, <code>false</code> to display red icon
	 */
	void setDisplayedImageIcon(boolean greenIcon);
	
	/**
	 * Get the alert email editor
	 * @return the {@link AlertEmailEditor}
	 */
	@Ignore
	AlertEmailEditor getAlertEmailEditor();
	
	/**
	 * Hide {@link AlertEmailEditor} and deselected the current element in the list
	 */
	void hideAlertEmailEditor();
	
	/**
	 * Get the selected alert email
	 * @return the selected {@link AlertEmailProxy}
	 */
	AlertEmailProxy getSelectedAlertEmail();
	
	/**
	 * Clear the alert activation list selection
	 */
	void clearAlertActivationListSelection();
	
	
	/**
	 * Interface to be implemented by the activity presenter for the {@link ConfigureAlertView}
	 * @author vincent
	 *
	 */
	public interface Presenter {
		
		/**
		 * Called when a alert activation is selected for edition
		 * @param selectedAlert the selected alert
		 */
		void onSelectedAlert(AlertActivationProxy selectedAlert);
		
		/**
		 * Called when a alert email is selected for edition
		 * @param selectedAlert the selected alert
		 */
		void onSelectedAlert(AlertEmailProxy selectedAlert);
		
		/**
		 * Called when clicking on checkbow chen alert selected
		 */
		void onChangeAlertActivation();
		
		/**
		 * Called when clicking on save alert email button
		 */
		void onClickSaveAlertEmail(); 
		
		/**
		 * Called when clicking on add email button
		 */
		void onClickAddEmail();
		
		/**
		 * Called when clicking on delete email button
		 */
		void onClickDeleteButton();
			
	}
	
}
