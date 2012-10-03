/**
 * 
 */
package com.stationmillenium.coverart.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.services.PollingService;

/**
 * 
 * @author vincent
 *
 */
@WebServlet("/currentSong")
@Configurable
public class CurrentSongServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//the polling service
	@Autowired
	private PollingService pollingService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SongHistoryItemDTO song = pollingService.getCurrentSong();
		resp.getWriter().print((song != null) ? song.toString() : "null");
	}
	
}
