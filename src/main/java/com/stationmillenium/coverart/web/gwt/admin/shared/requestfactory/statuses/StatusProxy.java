/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;

/**
 * Main interface for the entity proxy
 * @author vincent
 *
 */
public interface StatusProxy extends EntityProxy {
	Long getId();
	void setId(Long id);	
	Date getDateOfChange();
    void setDateOfChange(Date dateOfChange);	
}
