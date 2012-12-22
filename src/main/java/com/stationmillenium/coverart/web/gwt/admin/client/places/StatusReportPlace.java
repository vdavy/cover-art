/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.places;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Place to report status for shoutcast, playlist and fm
 * @author vincent
 *
 */
public class StatusReportPlace extends Place {

	private static final Logger LOGGER = Logger.getLogger(StatusReportPlace.class.getName());
	
	public static enum ReportType {
		SHOUTCAST,
		PLAYLIST,
		FM
	}
	
	private ReportType reportType;
	
	/**
	 * Create a new {@link StatusReportPlace}
	 * @param reportType the report type to display
	 */
	public StatusReportPlace(ReportType reportType) {
		this.reportType = reportType;
	}
	
	
	/**
	 * @return the reportType
	 */
	public ReportType getReportType() {
		return reportType;
	}


	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}

	public static class Tokenizer implements PlaceTokenizer<StatusReportPlace> {

		@Override
		public StatusReportPlace getPlace(String token) {
			try {
				ReportType reportType = ReportType.valueOf(token);
				return new StatusReportPlace(reportType);
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Error during parsing report type - go to Shoutcast", e);
				return new StatusReportPlace(ReportType.SHOUTCAST);
			}
		}

		@Override
		public String getToken(StatusReportPlace place) {
			return place.getReportType().toString();
		}
		
	}	
}
