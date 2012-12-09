/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.history.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.stationmillenium.coverart.web.gwt.modules.history.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.modules.history.client.view.HistoryViewInterface.HistoryViewPresenter.SearchFieldType;

/**
 * Test case for history
 * @author vincent
 *
 */
public class TestHistoryGWT extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "com.stationmillenium.coverart.web.gwt.history.History";
	}

	/**
	 * Test available search cases
	 */
	public void testSearchCases() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		clientFactory.getHistoryActivity().launchSearchFields("Guetta", SearchFieldType.ARTIST);
		clientFactory.getHistoryActivity().launchSearchFields("Turn me on", SearchFieldType.TITLE);
		clientFactory.getHistoryActivity().launchSearchFields("BAM", SearchFieldType.ALL);
		clientFactory.getHistoryActivity().launchSearchDates(new Date(), "12", "00");
	}
	
	/**
	 * Test refresh history
	 */
	public void testRefreshHistory() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		clientFactory.getHistoryActivity().refreshHistory();	
	}
	
	/**
	 * Test date format
	 */
	public void testFormatDate() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		String date = clientFactory.getHistoryActivity().formatDate(new Date());
		assertNotNull(date);
	}
	
}
