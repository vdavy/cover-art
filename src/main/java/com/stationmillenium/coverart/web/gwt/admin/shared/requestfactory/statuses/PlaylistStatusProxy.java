/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.stationmillenium.coverart.domain.statuses.PlaylistStatus;
import com.stationmillenium.coverart.web.gwt.admin.server.requestfactory.PlaylistStatusRequestFactoryEntity;

/**
 * Proxy for the {@link PlaylistStatus} entity
 * @author vincent
 *
 */
@ProxyFor(PlaylistStatusRequestFactoryEntity.class)
public interface PlaylistStatusProxy extends StatusProxy {
	boolean isPlaylistUpdated();    
    void setPlaylistUpdated(boolean playlistUpdated) ;
}
