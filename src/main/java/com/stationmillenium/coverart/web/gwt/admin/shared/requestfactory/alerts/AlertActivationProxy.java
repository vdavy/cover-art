/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.stationmillenium.coverart.web.gwt.admin.server.requestfactory.alerts.AlertActivationRequestFactoryEntity;

/**
 * Proxy for the {@link AlertActivationProxy} entity
 * @author vincent
 *
 */
@ProxyFor(AlertActivationRequestFactoryEntity.class)
public interface AlertActivationProxy extends EntityProxy {	
	public boolean isEnableAlert(); 
    public void setEnableAlert(boolean enableAlert); 
    
    public AlertType getAlertType(); 
    public void setAlertType(AlertType alertType);
    
    @Override
    EntityProxyId<AlertActivationProxy> stableId();
}
