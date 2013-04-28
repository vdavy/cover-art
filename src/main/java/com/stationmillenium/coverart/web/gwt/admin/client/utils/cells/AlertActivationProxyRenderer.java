/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.utils.cells;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertActivationProxy;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertType;

/**
 * Renderer for {@link AlertActivationProxy}
 * @author vincent
 *
 */
public class AlertActivationProxyRenderer implements SafeHtmlRenderer<AlertActivationProxy> {
	
	private ClientFactory clientFactory;
	
	/**
	 * Create a new {@link AlertActivationProxyRenderer}
	 * @param clientFactory the client factory
	 */
	public AlertActivationProxyRenderer(ClientFactory clientFactory) {
		super(); //default constructor
		this.clientFactory = clientFactory; 
	}
	
	@Override
	public SafeHtml render(AlertActivationProxy object) {
		String returnString = "";
		if (object != null)
			returnString = getAlertTranslation(object.getAlertType());	
		return SafeHtmlUtils.fromString(returnString);
	}

	@Override
	public void render(AlertActivationProxy object, SafeHtmlBuilder builder) {
		String returnString = "";
		if (object != null)
			returnString = getAlertTranslation(object.getAlertType());	
		builder.appendEscaped(returnString);
	}

	/**
	 * Get the translation for the alert
	 * @param type the alert type
	 * @return the matching translation
	 */
	private String getAlertTranslation(AlertType type) {
		String translation = "";
		switch (type) {
		case CUSTOM_IMAGE:
			translation = clientFactory.getConfigureAlertsConstants().getCustomImagesAlertName();
			break;

		case FM:
			translation = clientFactory.getConfigureAlertsConstants().getFMAlertName();
			break;
		
		case PLAYLIST:
			translation = clientFactory.getConfigureAlertsConstants().getPlaylistAlertName();
			break;

		case SHOUTCAST:
			translation = clientFactory.getConfigureAlertsConstants().getShoutcastAlertName();
			break;
		}
		
		return translation;
	}
}

