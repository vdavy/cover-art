/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

/**
 * Player GWT module resources bundle
 * @author vincent
 *
 */
public interface PlayerResources extends ClientBundle {

	@Source("logo-millenium.png")
	ImageResource logoMillenium();

	@Source("logo-player-live.png")
	ImageResource logoPlayerLive();
	
	@Source("piwik.js")
	TextResource piwikJS();
	
}
