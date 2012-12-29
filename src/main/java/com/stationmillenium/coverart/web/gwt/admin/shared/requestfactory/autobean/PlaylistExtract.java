/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.autobean;

import java.util.Date;


/**
 * Interface for autobean playlist extract
 * @author vincent
 *
 */
public interface PlaylistExtract {

	Date getStartExtractDate();
	void setStartExtractDate(Date startExtractDate);
	Date getEndExtractDate();
	void setEndExtractDate(Date endExtractDate);
	
}
