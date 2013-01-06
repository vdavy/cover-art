/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.requests.context.alerts;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.stationmillenium.coverart.web.gwt.admin.server.requestfactory.alerts.AlertActivationRequestFactoryEntity;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertActivationProxy;

/**
 * Request context for {@link AlertActivationProxy}
 * @author vincent
 *
 */
@Service(AlertActivationRequestFactoryEntity.class)
public interface AlertActivationRequest extends RequestContext {

	Request<List<AlertActivationProxy>> findAllAlertActivations();	
	InstanceRequest<AlertActivationProxy, Void> merge();
	
}
