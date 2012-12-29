/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests;

import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.FMStatusRequest;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.PlaylistStatusRequest;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.ServerStatusRequest;

/**
 * Request factory for GWT admin module
 * @author vincent
 *
 */
public interface AdminRequestFactory extends RequestFactory {

	FMStatusRequest fmStatusRequest();	
	PlaylistStatusRequest playlistStatusRequest();
	ServerStatusRequest serverStatusRequest();
	
}
