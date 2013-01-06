/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.statuses;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.stationmillenium.coverart.web.gwt.admin.server.requestfactory.statuses.FMStatusRequestFactoryEntity;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses.FMStatusProxy;

/**
 * Request context for {@link FMStatusProxy}
 * @author vincent
 *
 */
@Service(FMStatusRequestFactoryEntity.class)
public interface FMStatusRequest extends RequestContext {

	Request<List<FMStatusProxy>> findAllFMStatuses();
	
}
