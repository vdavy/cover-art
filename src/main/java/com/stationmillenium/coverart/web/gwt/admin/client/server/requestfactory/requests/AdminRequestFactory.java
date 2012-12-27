/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.server.requests;

import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requests.context.FMStatusRequest;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requests.context.PlaylistStatusRequest;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requests.context.ServerStatusRequest;

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
