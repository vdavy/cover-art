/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.alerts;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.stationmillenium.coverart.web.gwt.admin.server.requestfactory.alerts.AlertEmailRequestFactoryEntity;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertEmailProxy;

/**
 * Request context for {@link AlertEmailProxy}
 * @author vincent
 *
 */
@Service(AlertEmailRequestFactoryEntity.class)
public interface AlertEmailRequest extends RequestContext {
	
	Request<List<AlertEmailProxy>> findAllAlertEmails();	
	InstanceRequest<AlertEmailProxy, Void> merge();
	InstanceRequest<AlertEmailProxy, Void> persist();
	InstanceRequest<AlertEmailProxy, Void> remove();
	
}
