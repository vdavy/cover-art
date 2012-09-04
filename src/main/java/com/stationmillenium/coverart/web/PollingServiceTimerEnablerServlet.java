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
@WebServlet("/pollingServiceEnabler")
@Configurable
public class PollingServiceTimerEnablerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//the polling service
	@Autowired
	private PollingServiceTimer pollingServiceTimer;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean enabled = (req.getParameter("active") != null);
		pollingServiceTimer.setEnable(enabled);
		resp.getWriter().print("Enable Polling status : " + pollingServiceTimer.isEnable());
	}
	
}
