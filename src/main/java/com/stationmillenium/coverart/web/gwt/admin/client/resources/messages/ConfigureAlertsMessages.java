/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.resources.messages;

import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.LocalizableResource.GenerateKeys;
import com.google.gwt.i18n.client.Messages;

/**
 * Constants for admin GWT module alert config
 * @author vincent
 *
 */
@GenerateKeys
@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
public interface ConfigureAlertsMessages extends Messages {

	@DefaultMessage("Alerte éditée : {0}")
	String getEditSelectedAlertActivation(String alert);
	
	@DefaultMessage("Email alerte édité : {0}")
	String getEditSelectedAlertEmail(String email);
	
}
