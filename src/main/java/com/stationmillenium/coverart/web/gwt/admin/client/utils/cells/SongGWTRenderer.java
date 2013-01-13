/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.utils.cells;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.SongGWT;

/**
 * Renderer for {@link SongGWT}
 * @author vincent
 *
 */
public class SongGWTRenderer implements SafeHtmlRenderer<SongGWT> {
	
	@Override
	public SafeHtml render(SongGWT object) {
		String returnString = "";
		if (object != null)
			returnString = object.getArtist() + " - " + object.getTitle(); 
		return SafeHtmlUtils.fromString(returnString);
	}

	@Override
	public void render(SongGWT object, SafeHtmlBuilder builder) {
		String returnString = "";
		if (object != null)
			returnString = object.getArtist() + " - " + object.getTitle(); 
		builder.appendEscaped(returnString);
	}

}

