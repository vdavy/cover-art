/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.stationmillenium.coverart.web.gwt.admin.client.places.StatusReportPlace.ReportType;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses.StatusProxy;

/**
 * Interface of the status report view
 * @author vincent
 *
 */
public interface StatusReportView extends IsWidget {

	public interface Presenter {
		
	}

	/**
	 * Set the statuses list for display
	 * @param statusesList the statuses list
	 */
	void setStatusesList(List<? extends StatusProxy> statusesList);
	
	/**
	 * Set the report type for display
	 * @param alert the report type
	 */
	void setAlertType(ReportType alert);
	
	/**
	 * Set if the current alert is active
	 * @param active <code>true</code> if alert is not active, <code>false</code> if it is
	 */
	void setAlertActive(boolean active);
	
}
