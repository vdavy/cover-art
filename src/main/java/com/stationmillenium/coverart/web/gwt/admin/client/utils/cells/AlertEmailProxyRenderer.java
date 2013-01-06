/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.utils.cells;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertEmailProxy;

/**
 * Renderer for {@link AlertEmailProxy}
 * @author vincent
 *
 */
public class AlertEmailProxyRenderer implements SafeHtmlRenderer<AlertEmailProxy> {
	
	@Override
	public SafeHtml render(AlertEmailProxy object) {
		return SafeHtmlUtils.fromString(object.getEmail());
	}

	@Override
	public void render(AlertEmailProxy object, SafeHtmlBuilder builder) {
		builder.appendEscaped(object.getEmail());
	}

}

