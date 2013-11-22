/**
 * 
 */
package com.stationmillenium.coverart.web.servlets.rds;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamResult;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.schema.currentsong.CurrentSong;
import com.stationmillenium.coverart.services.PollingService;
import com.stationmillenium.coverart.services.alerts.FMAlertService;

/**
 * Servlet to get the current song
 * @author vincent
 *
 */
@WebServlet("/admin/currentSong")
@Configurable
public class CurrentSongServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//the polling service
	@Autowired
	private PollingService pollingService;
	
	//xml marshallers
	@Autowired
	@Qualifier("oxmCurrentSong")
	private Jaxb2Marshaller oxmCurrentSong;
		
	//dozer
	@Autowired
	private Mapper mapper;
	
	//fm alert service to notify polling
	@Autowired
	private FMAlertService fmAlertService;
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SongHistoryItemDTO song = pollingService.getCurrentSong(); //get song
		CurrentSong currentSong = new CurrentSong(); //init response
		currentSong.setAvailable((song != null) 
				&& (song.getArtist() != null) && (song.getTitle() != null)
				&& (song.getArtist().length() > 0) && (song.getTitle().length() > 0)); //set song avalaible
		
		//dozer conversion
		if (currentSong.isAvailable()) 
			mapper.map(song, currentSong);
		
		//mashall output
		oxmCurrentSong.setSchema(new ClassPathResource("xsd/CurrentSong.xsd")); 
		oxmCurrentSong.marshal(currentSong, new StreamResult(resp.getWriter()));
		
		//notify polling
		fmAlertService.updateQueryDate();
	}
	
}
