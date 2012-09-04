/**
 * 
 */
package com.stationmillenium.coverart.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.stationmillenium.coverart.services.PollingServiceTimer;

/**
 * 
 * @author vincent
 *
 */
@WebServlet("/pollingServiceStatus")
@Configurable
public class PollingServiceTimerStatusServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//the polling service
	@Autowired
	private PollingServiceTimer pollingServiceTimer;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().print("Polling status : " + pollingServiceTimer.isEnable());
	}
	
}
