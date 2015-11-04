package com.stationmillenium.coverart.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IcecastRoot {

	private Icestats icestats;

	/**
	 * @return the icestats
	 */
	public Icestats getIcestats() {
		return icestats;
	}

	/**
	 * @param icestats the icestats to set
	 */
	public void setIcestats(Icestats icestats) {
		this.icestats = icestats;
	}
	
}
