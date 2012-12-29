/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.shared.rpc;

import java.io.Serializable;

/**
 * DTO containing statuses (polling service & FM alert)
 * @author vincent
 *
 */
public class ServicesStatuses implements Serializable {

	private static final long serialVersionUID = 9113737137112002888L;
	
	private boolean pollingServiceStatus;
	private boolean fmAlertStatus;
	
	/**
	 * @return the pollingServiceStatus
	 */
	public boolean isPollingServiceStatus() {
		return pollingServiceStatus;
	}
	
	/**
	 * @param pollingServiceStatus the pollingServiceStatus to set
	 */
	public void setPollingServiceStatus(boolean pollingServiceStatus) {
		this.pollingServiceStatus = pollingServiceStatus;
	}
	
	/**
	 * @return the fmAlertStatus
	 */
	public boolean isFmAlertStatus() {
		return fmAlertStatus;
	}
	
	/**
	 * @param fmAlertStatus the fmAlertStatus to set
	 */
	public void setFmAlertStatus(boolean fmAlertStatus) {
		this.fmAlertStatus = fmAlertStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServicesStatuses [pollingServiceStatus=" + pollingServiceStatus
				+ ", fmAlertStatus=" + fmAlertStatus + "]";
	}
		
}
