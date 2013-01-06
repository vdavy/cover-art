/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests;

import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.alerts.AlertActivationRequest;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.alerts.AlertEmailRequest;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.statuses.FMStatusRequest;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.statuses.PlaylistStatusRequest;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.statuses.ServerStatusRequest;

/**
 * Request factory for GWT admin module
 * @author vincent
 *
 */
public interface AdminRequestFactory extends RequestFactory {

	//statuses request factory
	FMStatusRequest fmStatusRequest();	
	PlaylistStatusRequest playlistStatusRequest();
	ServerStatusRequest serverStatusRequest();
	
	//alerts request factory
	AlertActivationRequest alertActivationRequest();
	AlertEmailRequest alertEmailRequest();
	
}
