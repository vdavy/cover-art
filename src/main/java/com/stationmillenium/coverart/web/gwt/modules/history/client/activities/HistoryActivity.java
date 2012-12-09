/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.modules.history.client.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.stationmillenium.coverart.web.gwt.modules.history.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.modules.history.client.view.HistoryViewInterface;
import com.stationmillenium.coverart.web.gwt.modules.history.client.view.HistoryViewInterface.HistoryViewPresenter;
import com.stationmillenium.coverart.web.gwt.modules.history.shared.HistoryGWTDTO;

/**
 * Player GWT module main activity
 * @author vincent
 *
 */
public class HistoryActivity extends AbstractActivity implements HistoryViewPresenter {

	//log
	private static final Logger LOGGER = Logger.getLogger(HistoryActivity.class.getName());
	
	//members
	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
	private ClientFactory clientFactory;
	private AsyncCallback<List<HistoryGWTDTO>> asyncCallback = new AsyncCallback<List<HistoryGWTDTO>>() {
		
		@Override
		public void onSuccess(List<HistoryGWTDTO> result) {
			LOGGER.fine("Found songs list : " + result);
			clientFactory.getHistoryView().setSongHistoryList(result);
			initImage(result);
			clientFactory.getHistoryView().showAjaxLoading(false);
		}

		@Override
		public void onFailure(Throwable caught) {
			LOGGER.log(Level.SEVERE, "Error during songs search", caught);
			clientFactory.getHistoryView().showAjaxLoading(false);
			Window.alert(clientFactory.getConstants().songSearchError());			
		}
		
	};
	
	/**
	 * Instanciate new activity
	 * @param clientFactory client factory
	 */
	public HistoryActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget, com.google.gwt.event.shared.EventBus)
	 */
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		HistoryViewInterface view = clientFactory.getHistoryView();
		panel.setWidget(view);
		view.setPresenter(this);
		displayDefaultImage(); //initialize image
	}

	@Override
	public String formatDate(Date dateToFormat) {
		return DATE_FORMAT.format(dateToFormat);
	}
	
	@Override
	public void refreshHistory() {
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				clientFactory.getHistoryView().showAjaxLoading(true);
				clientFactory.getService().getSongHistory(new AsyncCallback<List<HistoryGWTDTO>>() {
					
					@Override
					public void onSuccess(List<HistoryGWTDTO> result) {
						LOGGER.fine("History received : " + result);
						clientFactory.getHistoryView().setSongHistoryList(result);
						initImage(result);
						clientFactory.getHistoryView().showAjaxLoading(false);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						LOGGER.log(Level.SEVERE, "Error during gathering history list", caught);
						clientFactory.getHistoryView().showAjaxLoading(false);
						Window.alert(clientFactory.getConstants().historyLoadingError());
					}
				});				
			}
			
			@Override
			public void onFailure(Throwable reason) {
				LOGGER.log(Level.WARNING, "Error during refresh history", reason);
			}
		});		
	}
	
	@Override
	public void getSearchSuggestions(final Request request, final Callback callback) {
		GWT.runAsync(new RunAsyncCallback() {

			@Override
			public void onSuccess() {
				if ((request.getQuery() != null) && (request.getQuery().length() >= 3)) { //if valid query
					clientFactory.getService().getSearchSuggestions(request.getQuery(), request.getLimit(), new AsyncCallback<List<HistoryGWTDTO>>() {

						@Override
						public void onFailure(Throwable caught) {
							LOGGER.log(Level.WARNING, "Error during suggestions gathering for "+ request.getQuery(), caught);				
							Response response = new Response();				
							callback.onSuggestionsReady(request, response); //send callback
						}

						@Override
						public void onSuccess(List<HistoryGWTDTO> result) {
							LOGGER.fine("Gathered suggestion for query '" + request.getQuery() + "': " + result);

							//build suggestion list
							List<Suggestion> suggestionList = new ArrayList<Suggestion>();
							for (final HistoryGWTDTO suggestion : result) {
								suggestionList.add(new Suggestion() {

									@Override
									public String getReplacementString() {
										return suggestion.getArtist() + " " + suggestion.getTitle();
									}

									@Override
									public String getDisplayString() {
										return suggestion.getArtist() + " - " + suggestion.getTitle();
									}
								});
							}
							Response response = new Response(suggestionList);

							callback.onSuggestionsReady(request, response); //send callback
						}
					});
				}
			}
			
			@Override
			public void onFailure(Throwable reason) {
				LOGGER.log(Level.WARNING, "Error during suggestions", reason);
			}
			
		});
	}
	
	@Override
	public void launchSearchFields(final String query, final SearchFieldType searchType) {
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				LOGGER.fine("Search type : " + searchType + " - search query : " + query);
				if ((query != null) && (query.length() > 0)) {
					clientFactory.getHistoryView().showAjaxLoading(true);
					switch (searchType) { //follow search type
					case ALL:
						clientFactory.getService().searchAll(query, asyncCallback);
						break;
			
					case ARTIST:
						clientFactory.getService().searchByArtist(query, asyncCallback);
						break;
					
					case TITLE:
						clientFactory.getService().searchByTitle(query, asyncCallback);
						break;
					}
				} else
					LOGGER.fine("Query empty !");				
			}
			
			@Override
			public void onFailure(Throwable reason) {
				LOGGER.log(Level.WARNING, "Error during search by fields", reason);
			}
			
		});		
	}
	
	@Override
	public void launchSearchDates(final Date date, final String hours, final String minutes) {
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				clientFactory.getHistoryView().showAjaxLoading(true);
				try {
					long hoursInLong = Long.valueOf(hours) * 3600 * 1000;
					long minutesInLong = Long.valueOf(minutes) * 60 * 1000;
					Date searchDate = new Date(date.getTime() + hoursInLong + minutesInLong);
					LOGGER.fine("Search by date : " + searchDate);
					clientFactory.getService().searchByDate(searchDate, asyncCallback);
				} catch(NumberFormatException e) {
					LOGGER.log(Level.WARNING, "Error durgin parsing date", e);
					clientFactory.getService().getSongHistory(asyncCallback); //get defaut song list
				}				
			}
			
			@Override
			public void onFailure(Throwable reason) {
				LOGGER.log(Level.WARNING, "Error during search by date", reason);				
			}
			
		});		
	}
	
	@Override
	public void songSelected(HistoryGWTDTO selectedSong) {
		if (selectedSong.getImagePath() != null) { //if image for selected song
			SafeUri imageUri = UriUtils.fromString(GWT.getHostPageBaseURL() + selectedSong.getImagePath());
			clientFactory.getHistoryView().setImage(imageUri, selectedSong.getImageWidth(), selectedSong.getImageHeight());
		} else //display fallback image
			displayDefaultImage();	
	}

	/**
	 * Display default image
	 */
	private void displayDefaultImage() {
		clientFactory.getHistoryView().setImage(
				clientFactory.getResources().logoMillenium().getSafeUri(), 
				String.valueOf(clientFactory.getResources().logoMillenium().getWidth()), 
				String.valueOf(clientFactory.getResources().logoMillenium().getHeight()));
	}
	
	/**
	 * Initialize image following the result list
	 * @param result the list of {@link HistoryGWTDTO}
	 */
	private void initImage(List<HistoryGWTDTO> result) {
		if (result.size() > 0)
			songSelected(result.get(0)); //set first image
		else
			displayDefaultImage(); //initialize image
	}
}
