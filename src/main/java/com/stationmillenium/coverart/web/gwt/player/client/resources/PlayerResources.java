/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.player.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.cellview.client.CellList;

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
	
	@Source("logo-small-player.png")
	ImageResource logoSmallPlayer();
	
	@Source("piwik.js")
	TextResource piwikJS();
	
	@Source("smallPlayer.js")
	TextResource smallPlayerJS();
	
	@Source("player-muses.js")
	TextResource playerMusesJS();
	
	@Source("empty-cell-list.css")
	CellList.Style emptyCellList();
	
}
