/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.autobean;



/**
 * Interface for autobean add custom image report
 * @author vincent
 *
 */
public interface AddCustomImageReport {

	boolean isAddOK();
	void setAddOK(boolean addOK);
	
	String getFileName();
	void setFileName(String fileName);
	
	int getWidth();
	void setWidth(int width);
	
	int getHeight();
	void setHeight(int height);
	
}
