/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.server.requests.context;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.stationmillenium.coverart.web.gwt.admin.server.PlaylistStatusRequestFactoryEntity;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses.PlaylistStatusProxy;

/**
 * Request context for {@link PlaylistStatusProxy}
 * @author vincent
 *
 */
@Service(PlaylistStatusRequestFactoryEntity.class)
public interface PlaylistStatusRequest extends RequestContext {

	Request<List<PlaylistStatusProxy>> findAllPlaylistStatuses();
	
}
