/**
 * 
 */
package com.stationmillenium.coverart.services.covergraber.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stationmillenium.coverart.beans.covergraber.LastFMCoverServicePropertiesBean;
import com.stationmillenium.coverart.services.covergraber.CoverGraberServiceInterface;

/**
 * Cover graber for Last FM service
 * @author vincent
 *
 */
@Service
public class LastFmCoverGraberService implements CoverGraberServiceInterface {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(LastFmCoverGraberService.class);
	
	//conf
	@Autowired
	private LastFMCoverServicePropertiesBean lastFMCoverServicePropertiesBean;
	
	@Override
	public byte[] grabCover(String artist, String title) {
		gatherXMLData(artist, title);
		return null;
	}

	/**
	 * Gather XML data for a song search
	 * @param artist the artist to search
	 * @param title the title to search
	 * @return the XML data as {@code String}
	 */
	private String gatherXMLData(String artist, String title)  {
		RestTemplate template = new RestTemplate();
		String xmlData = template.getForObject(lastFMCoverServicePropertiesBean.getUrl(), String.class, lastFMCoverServicePropertiesBean.getApiKey(), artist, title);
		xmlData = xmlData.replace(lastFMCoverServicePropertiesBean.getTextToRemoveInXML(), "");
		
		LOGGER.debug("Gathered XML data : " + xmlData);
		return xmlData;
	}
}
