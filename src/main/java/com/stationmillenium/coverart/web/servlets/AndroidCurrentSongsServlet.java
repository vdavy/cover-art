/**
 * 
 */
package com.stationmillenium.coverart.web.servlets;

import java.io.IOException;
import java.util.List;

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

import com.stationmillenium.coverart.beans.utils.GeneralPropertiesBean;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.dto.services.images.SongImageDTO;
import com.stationmillenium.coverart.repositories.SongImageRepository;
import com.stationmillenium.coverart.repositories.SongItemRepository;
import com.stationmillenium.coverart.schema.androidcurrentsongs.AndroidCurrentSongs;
import com.stationmillenium.coverart.schema.androidcurrentsongs.AndroidCurrentSongs.CurrentSong;
import com.stationmillenium.coverart.schema.androidcurrentsongs.AndroidCurrentSongs.CurrentSong.Image;
import com.stationmillenium.coverart.schema.androidcurrentsongs.AndroidCurrentSongs.Last5Songs;
import com.stationmillenium.coverart.schema.androidcurrentsongs.AndroidCurrentSongs.Last5Songs.Song;
import com.stationmillenium.coverart.services.PollingService;

/**
 * Servlet to get the current songs for Android app
 * @author vincent
 *
 */
@WebServlet("/android/currentSongs")
@Configurable
public class AndroidCurrentSongsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//the polling service
	@Autowired
	private PollingService pollingService;
	
	//xml marshallers
	@Autowired
	@Qualifier("oxmAndroidCurrentSongs")
	private Jaxb2Marshaller oxmAndroidCurrentSongs;
		
	//dozer
	@Autowired
	private Mapper mapper;
		
	//song repository
	@Autowired
	private SongItemRepository songItemRepository;
		
	//song image service
	@Autowired
	private SongImageRepository songImageRepository;
		
	//config
	@Autowired
	private GeneralPropertiesBean config;
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AndroidCurrentSongs androidCurrentSongs = initResponse(); //init response
		
		//get data
		fillInData(androidCurrentSongs);
					
		//mashall output
		oxmAndroidCurrentSongs.setSchema(new ClassPathResource("xsd/AndroidCurrentSongs.xsd")); 
		oxmAndroidCurrentSongs.marshal(androidCurrentSongs, new StreamResult(resp.getWriter()));
	}

	/**
	 * Fill in XML data
	 * @param androidCurrentSongs the XML bean
	 */
	private void fillInData(AndroidCurrentSongs androidCurrentSongs) {
		SongHistoryItemDTO song = pollingService.getCurrentSong(); //get song
		
		//set song avalaible
		androidCurrentSongs.getCurrentSong().setAvailable((song != null) 
				&& (song.getArtist() != null) && (song.getTitle() != null)
				&& (song.getArtist().length() > 0) && (song.getTitle().length() > 0)); 
		
		//image part
		SongImageDTO image = null;
		if (song != null) {
			image = songImageRepository.getImageForSong(song); //get image
			
			if (image != null) {
				image.setFileName(config.getCoverImagesPath() + "/" + image.getFileName()); //adjust path
				androidCurrentSongs.getCurrentSong().setImage(new Image());
				mapper.map(image, androidCurrentSongs); //map image
			}
		}
		//dozer conversion for current song
		if (androidCurrentSongs.getCurrentSong().isAvailable()) 
			mapper.map(song, androidCurrentSongs);
		
		//last 5 songs list
		List<SongHistoryItemDTO> lastSongsList = songItemRepository.getLast5PlayedSongs(!androidCurrentSongs.getCurrentSong().isAvailable()); //last 5 songs
		for (SongHistoryItemDTO lastSong : lastSongsList) {
			Song lastSongXML = new Song();
			mapper.map(lastSong, lastSongXML);
			androidCurrentSongs.getLast5Songs().getSong().add(lastSongXML);
		}
	}
	
	/**
	 * Init the return type
	 * @return the initialized {@link AndroidCurrentSongs}
	 */
	private AndroidCurrentSongs initResponse() {
		AndroidCurrentSongs returnSong = new AndroidCurrentSongs();
		returnSong.setCurrentSong(new CurrentSong());
		returnSong.setLast5Songs(new Last5Songs());
		returnSong.getLast5Songs().getSong(); //init arraylist
		return returnSong; 
	}
	
}
