/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.utils.cells;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertActivationProxy;

/**
 * Renderer for {@link AlertActivationProxy}
 * @author vincent
 *
 */
public class AlertActivationProxyRenderer implements SafeHtmlRenderer<AlertActivationProxy> {
	
	@Override
	public SafeHtml render(AlertActivationProxy object) {
		String returnString = "";
		if (object != null)
			returnString = object.getAlertType().toString();		
		return SafeHtmlUtils.fromString(returnString);
	}

	@Override
	public void render(AlertActivationProxy object, SafeHtmlBuilder builder) {
		String returnString = "";
		if (object != null)
			returnString = object.getAlertType().toString();		
		builder.appendEscaped(returnString);
	}

}

