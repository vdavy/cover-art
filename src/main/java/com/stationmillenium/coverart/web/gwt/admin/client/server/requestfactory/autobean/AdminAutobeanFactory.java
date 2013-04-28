/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.autobean;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.autobean.AddCustomImageParam;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.autobean.AddCustomImageReport;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.autobean.PlaylistExtract;

/**
 * Autobean factory for GWT admin module
 * @author vincent
 *
 */
public interface AdminAutobeanFactory extends AutoBeanFactory {

	AutoBean<PlaylistExtract> getPlaylistExtract();
	AutoBean<AddCustomImageReport> getAddCustomImageReport();
	AutoBean<AddCustomImageParam> getAddCustomImageParam();	
	
}
