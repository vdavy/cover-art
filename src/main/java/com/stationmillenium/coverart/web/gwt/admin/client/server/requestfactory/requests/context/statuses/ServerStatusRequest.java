/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.statuses;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.stationmillenium.coverart.web.gwt.admin.server.requestfactory.statuses.ServerStatusRequestFactoryEntity;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses.ServerStatusProxy;

/**
 * Request context for {@link ServerStatusProxy}
 * @author vincent
 *
 */
@Service(ServerStatusRequestFactoryEntity.class)
public interface ServerStatusRequest extends RequestContext {

	Request<List<ServerStatusProxy>> findAllServerStatuses();
	
}
