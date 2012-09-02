/**
 * 
 */
package com.stationmillenium.coverart.services.covergraber.services.impl;

import java.io.StringReader;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stationmillenium.coverart.beans.covergraber.DeezerCoverServicePropertiesBean;
import com.stationmillenium.coverart.schema.deezertracksearch.Root;
import com.stationmillenium.coverart.schema.deezertracksearch.Root.Data;
import com.stationmillenium.coverart.schema.deezertracksearch.Root.Data.Track;
import com.stationmillenium.coverart.schema.deezertracksearch.Root.Data.Track.Album;
import com.stationmillenium.coverart.services.covergraber.services.AbstractCoverGraberService;

/**
 * Cover graber for Deezer service
 * @author vincent
 *
 */
@Service("deezerCoverGraberService")
public class DeezerCoverGraberService extends AbstractCoverGraberService<Root> {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(DeezerCoverGraberService.class);
		
	//conf
	@Autowired
	private DeezerCoverServicePropertiesBean deezerCoverServicePropertiesBean;
	
	//xml marshallers
	@Autowired
	@Qualifier("oxmDeezerTrackSearch")
	private Jaxb2Marshaller oxmDeezerTrackSearch;
		
	@Override
	protected String getImageURLFromXML(Root root) {
		Integer total = root.getTotal();			
		if ((total != null) && (total >= 1)) { //check data available
			Data data = root.getData();
			if (data != null) {
				List<Track> trackList = data.getTrack();
				if ((trackList != null) && (trackList.size() >= 1)) {
					Track track = trackList.get(0);
					if (track != null) {
						Album album = track.getAlbum();
						if (album != null) {								
							return album.getCover();

						} else
							LOGGER.warn("XML album data null");							
					} else
						LOGGER.warn("XML track data null");						
				} else
					LOGGER.warn("XML track list empty");					
			} else
				LOGGER.warn("XML <data /> tag data null");							
		} else
			LOGGER.warn("No data found for search");			
					
		return null;
	}
	
	@Override
	protected String gatherXMLData(String artist, String title)  {
		try {
			String query = artist  + " " + title;
			RestTemplate template = new RestTemplate();
			String xmlData = template.getForObject(deezerCoverServicePropertiesBean.getUrl(), String.class, query, deezerCoverServicePropertiesBean.getOutputFormat());
			
			LOGGER.debug("Gathered XML data : " + xmlData);
			return xmlData;
		} catch (Exception e) { //if error occurs
			LOGGER.warn("Error during query of Last FM", e);
			return null;
		}
	}
	
	@Override
	protected Root unmarshalllData(String xmlData) {
		try {
			oxmDeezerTrackSearch.setSchema(new ClassPathResource("xsd/DeezerTrackSearch.xsd"));
			Root returnXMl = (Root) oxmDeezerTrackSearch.unmarshal(new StreamSource(new StringReader(xmlData)));
			return returnXMl;
		} catch (XmlMappingException e) {
			LOGGER.warn("Error while unmarshalling normal data", e);
			return null;
		}
	}

}
