/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts;

import java.util.Set;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.stationmillenium.coverart.domain.alert.AlertActivation;
import com.stationmillenium.coverart.web.gwt.admin.server.requestfactory.alerts.AlertEmailRequestFactoryEntity;

/**
 * Proxy for the {@link AlertActivation} entity
 * @author vincent
 *
 */
@ProxyFor(AlertEmailRequestFactoryEntity.class)
public interface AlertEmailProxy extends EntityProxy {	
	public String getEmail(); 
    public void setEmail(String email);
    
    public Set<AlertType> getAlertType();
    public void setAlertType(Set<AlertType> alertType);
    
    @Override
    public EntityProxyId<AlertEmailProxy> stableId();
}
