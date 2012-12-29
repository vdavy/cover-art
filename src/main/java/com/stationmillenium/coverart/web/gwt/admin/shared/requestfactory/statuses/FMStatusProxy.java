/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.stationmillenium.coverart.domain.statuses.FMStatus;
import com.stationmillenium.coverart.web.gwt.admin.server.requestfactory.FMStatusRequestFactoryEntity;

/**
 * Proxy for the {@link FMStatus} entity
 * @author vincent
 *
 */
@ProxyFor(value = FMStatusRequestFactoryEntity.class)
public interface FMStatusProxy extends StatusProxy {	
	boolean isFmUp();    
    void setFmUp(boolean fmUp);
}
