/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.utils.cells;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.SongGWT;

/**
 * Renderer for {@link SongGWT}
 * @author vincent
 *
 */
public class SongGWTRenderer implements SafeHtmlRenderer<SongGWT> {
	
	//client factory for translation
	private ClientFactory clientFactory;
	
	/**
	 * Create a new {@link SongGWTRenderer}
	 * @param clientFactory the client factory
	 */
	public SongGWTRenderer(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public SafeHtml render(SongGWT object) {
		String returnString = "";
		if (object != null)
			returnString = ((object.isCustomImage()) ? clientFactory.getGeneralConfigConstants().getCustomImagePrefix() : "") + object.getArtist() + " - " + object.getTitle(); 
		return SafeHtmlUtils.fromString(returnString);
	}

	@Override
	public void render(SongGWT object, SafeHtmlBuilder builder) {
		String returnString = "";
		if (object != null)
			returnString = ((object.isCustomImage()) ? clientFactory.getGeneralConfigConstants().getCustomImagePrefix() : "") + object.getArtist() + " - " + object.getTitle(); 
		builder.appendEscaped(returnString);
	}

}

