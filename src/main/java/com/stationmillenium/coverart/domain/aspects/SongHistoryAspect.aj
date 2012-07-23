package com.stationmillenium.coverart.domain.aspects;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.stationmillenium.coverart.domain.SongHistory;;

/**
 * @Pre* aspects for SongHistory entity
 * @author vincent
 *
 */
privileged aspect SongHistoryAspect {

	/**
	 * Truncate artist and title
	 */
	@PrePersist
	@PreUpdate
	public void SongHistory.truncateUserAgentValue() {
		truncateField("artist");
		truncateField("title");
	}
}
