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

import com.stationmillenium.coverart.services.cron.CronTimer;
import com.stationmillenium.coverart.services.cron.CronType;

/**
 * 
 * @author vincent
 *
 */
@WebServlet("/admin/serviceStatus")
@Configurable
public class ServiceTimerStatusServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//the polling service
	@Autowired
	private CronTimer cronTimer;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try  {
			CronType type = CronType.valueOf(req.getParameter("type"));
			resp.getWriter().print("Service " + type + " status : " + cronTimer.isEnable(type));
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new ServletException(e);			
		}
		
	}
	
}
