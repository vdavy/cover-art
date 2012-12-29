/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.AbstractMessageView.MessageLabelStyle;

/**
 * Interface of the main configuration view
 * @author vincent
 *
 */
public interface GeneralConfigView extends IsWidget {

	/**
	 * Set the presenter
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
	 * Set the polling service information
	 * @param activeService if the service is active
	 * @param associatedText the associated text to display with
	 */
	void setPollingServiceInformation(boolean activeService, String associatedText);

	/**
	 * Set the FM alert information
	 * @param activeService if the alert service is active
	 * @param associatedText the associated text to display with
	 */
	void setFMAlertInformation(boolean activeService, String associatedText);

	/**
	 * Set the Hibernate Search zone information
	 * If the indexing is active, the index button is disabled
	 * @param activeIndexing if the indexing is currently active
	 * @param associatedText the associated text to display with
	 */
	void setHibernateSearchInformation(boolean activeIndexing, String associatedText);

	/**
	 * Set the current title text
	 * @param text the text
	 */
	void setCurrentTitleText(String text);

	/**
	 * Set the polling service checkbox checked
	 * @param checked <code>true</code> if checked, <code>false</code> if not
	 */
	void setPollingServiceCheckboxChecked(boolean checked);

	/**
	 * Set the FM alert checkbox checked
	 * @param checked <code>true</code> if checked, <code>false</code> if not
	 */
	void setFMAlertCheckboxChecked(boolean checked);

	/**
	 * Set the polling service checkbox enabled
	 * @param enabled <code>true</code> if enabled, <code>false</code>
	 */
	void setPollingServiceCheckboxActivation(boolean enabled);

	/**
	 * Set the FM alert checkbox enabled
	 * @param enabled <code>true</code> if enabled, <code>false</code>
	 */
	void setFMAlertCheckboxActivation(boolean enabled);
	
	/**
	 * Lock the indexing button
	 */
	void lockIndexButton();

	public interface Presenter {

		/**
		 * Called when polling service checkbox if clicked
		 * @param value the value of the checkbox 
		 */
		void onClickPollingServiceCheckbox(boolean value);

		/**
		 *  Called when FM alert checkbox if clicked
		 * @param value the value of the checkbox 
		 */
		void onClickFMAlertCheckbox(boolean value);

		/**
		 * Called when index button if 
		 */
		void onClickIndexButton();

	}

}
