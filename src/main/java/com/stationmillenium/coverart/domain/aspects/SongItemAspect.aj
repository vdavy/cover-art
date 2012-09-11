package com.stationmillenium.coverart.domain.aspects;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.stationmillenium.coverart.domain.history.SongItem;

/**
 * @Pre* aspects for SongItem entity
 * @author vincent
 *
 */
privileged aspect SongItemAspect {

	/**
	 * Truncate artist and title
	 */
	@PrePersist
	@PreUpdate
	public void SongItem.truncateUserAgentValue() {
		truncateField("artist");
		truncateField("title");
	}
}
