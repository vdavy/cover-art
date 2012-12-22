/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Interface of main view with menu
 * @author vincent
 *
 */
public interface MainView extends IsWidget {
	
	/**
	 * Get the content panel to display views
	 * @return the content panel as {@link SimplePanel}
	 */
	SimplePanel getContentPanel();
	
}
