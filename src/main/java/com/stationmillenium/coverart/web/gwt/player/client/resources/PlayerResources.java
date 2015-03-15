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

	@Source("images/logo-millenium.png")
	ImageResource logoMillenium();

	@Source("images/logo-player-live.png")
	ImageResource logoPlayerLive();
	
	@Source("images/logo-small-player.png")
	ImageResource logoSmallPlayer();
	
	@Source("images/live-button.png")
	ImageResource liveButton();
	
	@Source("images/top-wave.png")
	ImageResource topWave();
	
	@Source("js/piwik.js")
	TextResource piwikJS();
	
	@Source("js/smallPlayer.js")
	TextResource smallPlayerJS();
	
	@Source("js/player-muses.js")
	TextResource playerMusesJS();
	
	@Source("css/empty-cell-list.css")
	CellList.Style emptyCellList();
	
}
