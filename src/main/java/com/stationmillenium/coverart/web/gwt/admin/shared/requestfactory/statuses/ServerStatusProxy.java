/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.stationmillenium.coverart.domain.statuses.ServerStatus;
import com.stationmillenium.coverart.web.gwt.admin.server.ServerStatusRequestFactoryEntity;

/**
 * Proxy for the {@link ServerStatus} entity
 * @author vincent
 *
 */
@ProxyFor(ServerStatusRequestFactoryEntity.class)
public interface ServerStatusProxy extends StatusProxy {
	boolean isServerUp();    
    void setServerUp(boolean serverUp);
}
