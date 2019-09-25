/**
 * 
 */
package com.stationmillenium.coverart.web.servlets.android;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.stationmillenium.coverart.beans.utils.GeneralPropertiesBean;
import com.stationmillenium.coverart.dto.hybrid.SongHistoryItemImageDTO;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.dto.services.images.SongImageDTO;
import com.stationmillenium.coverart.repositories.SongImageRepository;
import com.stationmillenium.coverart.repositories.SongItemRepository;
import com.stationmillenium.coverart.repositories.SongSearchRepository;
import com.stationmillenium.coverart.schema.androidsearchsongshistory.AndroidSearchSongsHistory;
import com.stationmillenium.coverart.schema.androidsearchsongshistory.AndroidSearchSongsHistory.HistorySong;
import com.stationmillenium.coverart.services.PollingService;

/**
 * Servlet to search for songs in history for Android app
 * @author vincent
 *
 */
@WebServlet("/android/searchSongsHistory")
@Configurable
public class AndroidSongsSearchHistoryServlet extends HttpServlet {

	/**
	 * Static part
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidSongsSearchHistoryServlet.class);

	//HTTP request params
	private static final String ACTION_PARAM = "action";
	private static final String QUERY_PARAM = "query";
	private static final String DATE_FORMAT = "yyyyMMdd-hhmm";
	
	//HTTP request available actions
	private static final String FULL_TEXT = "FULL_TEXT";
	private static final String SUGGEST = "SUGGEST";
	private static final String DATE = "DATE";

	//the polling service
	@Autowired
	private PollingService pollingService;

	//xml marshallers
	@Autowired
	@Qualifier("oxmAndroidSearchSongsHistory")
	private Jaxb2Marshaller oxmAndroidSearchSongsHistory;

	@Autowired
	private ObjectMapper objectMapper;

	//song repository
	@Autowired
	private SongItemRepository songItemRepository;

	//song search repository
	@Autowired
	private SongSearchRepository songSearchRepository;

	//dozer
	@Autowired
	private Mapper mapper;

	//song image service
	@Autowired
	private SongImageRepository songImageRepository;

	//config
	@Autowired
	private GeneralPropertiesBean config;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get requested action
		String action = req.getParameter(ACTION_PARAM);
		action = (action == null) ? "" : action; //avoid NPE
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Requested search action : " + action);

		//init the return list
		AndroidSearchSongsHistory androidSearchSongsHistory = null;
		String query = req.getParameter(QUERY_PARAM);
		switch (action) {
		case FULL_TEXT : //full text search - if no query, use default search
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("Requested search query : " + query);
	
			List<SongHistoryItemImageDTO> searchSongsHistoryList = null;
			if (query != null) //querying
				searchSongsHistoryList = songSearchRepository.searchSongs(query, config.getHistorySearchMaxResults());
			else 
				searchSongsHistoryList = getInitSongHistory();
			
			androidSearchSongsHistory = convertDTOForMarshalling(searchSongsHistoryList); //mapping
			break;

		case DATE : //date search
			try {
				//parse the date
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Date searchDate = sdf.parse(query);
				
				if (LOGGER.isDebugEnabled())
					LOGGER.debug("Search songs by date - date : " + searchDate);
				
				//set up date
				int searchDateDelta =  config.getDateSearchDelta();
				Calendar minDate = Calendar.getInstance();
				minDate.setTime(searchDate);
				minDate.add(Calendar.MINUTE, -searchDateDelta);
				Calendar maxDate = Calendar.getInstance();
				maxDate.setTime(searchDate);
				maxDate.add(Calendar.MINUTE, searchDateDelta);
				
				//search & mapping
				searchSongsHistoryList = songItemRepository.getSongsPlayedBetween2DatesWithImages(minDate, maxDate);
				androidSearchSongsHistory = convertDTOForMarshalling(searchSongsHistoryList); //mapping
				
			} catch (ParseException e) {
				LOGGER.warn("Can't parse the date - search nothing");
				androidSearchSongsHistory = new AndroidSearchSongsHistory(); //suggest empty list
			}
			break;

		case SUGGEST : //suggest search
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("Suggest query : " + query);
			
			if (query != null) { //if query is available
				List<SongHistoryItemDTO> suggestionList = songSearchRepository.searchSongsForSuggest(query, config.getAndroidSuggestListLimit());
				androidSearchSongsHistory = convertSuggestDTOForMarshalling(suggestionList);
			} else {
				LOGGER.warn("Suggest query is null - suggest nothing");
				androidSearchSongsHistory = new AndroidSearchSongsHistory(); //suggest empty list
			}
			break;

		default : //if no specific action
			LOGGER.debug("No action specified, use default search");
			searchSongsHistoryList = getInitSongHistory(); //querying
			androidSearchSongsHistory = convertDTOForMarshalling(searchSongsHistoryList); //mapping
			break;
		}

		//mashall output
		if (req.getParameter("json") != null && req.getParameter("json").equals("true")) {
			objectMapper.writeValue(resp.getWriter(), androidSearchSongsHistory);
		} else {
			oxmAndroidSearchSongsHistory.setSchema(new ClassPathResource("xsd/AndroidSearchSongsHistory.xsd"));
			oxmAndroidSearchSongsHistory.marshal(androidSearchSongsHistory, new StreamResult(resp.getWriter()));
		}
		//final log
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Android current songs : " + androidSearchSongsHistory);
	}


	//if posting request, redirect it on get request
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		doGet(req, resp);
	}

	/**
	 * Convert the list of {@link SongHistoryItemImageDTO} into {@link AndroidSearchSongsHistory} for marshalling
	 * @param searchSongsHistoryList the list of {@link SongHistoryItemImageDTO}
	 * @return the {@link AndroidSearchSongsHistory}
	 */
	private AndroidSearchSongsHistory convertDTOForMarshalling(List<SongHistoryItemImageDTO> searchSongsHistoryList) {
		AndroidSearchSongsHistory androidSearchSongsHistory = new AndroidSearchSongsHistory(); //init response

		for (SongHistoryItemImageDTO songHistoryItemImageDTO : searchSongsHistoryList) { //proess each found song
			HistorySong historySong = new HistorySong();

			//map the song text ata
			mapper.map(songHistoryItemImageDTO, historySong);

			//map the image, if available
			if ((songHistoryItemImageDTO.getSongImageDTO() != null) && (songHistoryItemImageDTO.getSongImageDTO().getFileName() != null)) {
				SongImageDTO image = songHistoryItemImageDTO.getSongImageDTO();
				image.setFileName(config.getCoverImagesPath() + "/" + image.getFileName()); //adjust path
				mapper.map(image, historySong); //map fields for marshalling
			}
			
			//ad it to final list
			androidSearchSongsHistory.getHistorySong().add(historySong);
		}

		return androidSearchSongsHistory;
	}
	
	/**
	 * Convert the list of {@link SongHistoryItemDTO} into {@link AndroidSearchSongsHistory} for marshalling
	 * @param searchSongsHistoryList the list of {@link SongHistoryItemDTO}
	 * @return the {@link AndroidSearchSongsHistory}
	 */
	private AndroidSearchSongsHistory convertSuggestDTOForMarshalling(List<SongHistoryItemDTO> searchSongsHistoryList) {
		AndroidSearchSongsHistory androidSearchSongsHistory = new AndroidSearchSongsHistory(); //init response

		for (SongHistoryItemDTO songHistoryItemDTO : searchSongsHistoryList) { //proess each found song
			HistorySong historySong = new HistorySong();

			//map the song text ata
			mapper.map(songHistoryItemDTO, historySong);
			
			//ad it to final list
			androidSearchSongsHistory.getHistorySong().add(historySong);
		}

		return androidSearchSongsHistory;
	}

	/**
	 * Get the init search songs history list
	 * @return the list of {@link SongHistoryItemImageDTO}
	 */
	private List<SongHistoryItemImageDTO> getInitSongHistory() {
		LOGGER.debug("Get song history");
		//setup min date
		int historyDelayMinutes =  config.getHistoryDisplayMinMinutes();
		Calendar minDate = Calendar.getInstance();
		minDate.add(Calendar.MINUTE, -historyDelayMinutes);

		//get data
		List<SongHistoryItemImageDTO>  songList =  songItemRepository.getLastSongsFromMinDate(minDate);

		return songList;
	}

}
