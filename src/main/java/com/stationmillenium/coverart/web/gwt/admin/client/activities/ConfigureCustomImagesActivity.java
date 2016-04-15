/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.utils.callbacks.AjaxLoaderAsyncCallback;
import com.stationmillenium.coverart.web.gwt.admin.client.view.ConfigureCustomImagesView;
import com.stationmillenium.coverart.web.gwt.admin.client.view.ConfigureCustomImagesView.Presenter;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.AbstractMessageView.MessageLabelStyle;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.autobean.AddCustomImageParam;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.autobean.AddCustomImageReport;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.SongGWT;

/**
 * Activity for the configure custom images part
 * @author vincent
 *
 */
public class ConfigureCustomImagesActivity extends AbstractActivity implements Presenter {

	/**
	 * Async callback for suggestion
	 * @author vincent
	 *
	 */
	private static class SuggestionAsyncCallback implements AsyncCallback<List<SongGWT>> {
		
		private Request request; 
		private Callback callback;
		
		/**
		 * @param request the request to set
		 */
		public void setRequest(Request request) {
			this.request = request;
		}

		/**
		 * @param callback the callback to set
		 */
		public void setCallback(Callback callback) {
			this.callback = callback;
		}

		@Override
		public void onFailure(Throwable caught) {
			LOGGER.log(Level.WARNING, "Error during suggestions gathering for "+ request.getQuery(), caught);				
			Response response = new Response();				
			callback.onSuggestionsReady(request, response); //send callback
		}

		@Override
		public void onSuccess(List<SongGWT> result) {
			LOGGER.fine("Gathered suggestion for query '" + request.getQuery() + "': " + result);

			//build suggestion list
			List<Suggestion> suggestionList = new ArrayList<Suggestion>();
			for (final SongGWT suggestion : result) {
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
	}
	
	//logger
	private static final Logger LOGGER = Logger.getLogger(ConfigureCustomImagesActivity.class.getName());
	
	//instances
	private ClientFactory clientFactory;
	private SuggestionAsyncCallback asyncCallbackForSuggestion = new SuggestionAsyncCallback();
	private SongGWT selectedCustomSong;
	private SongGWT selectedSong;
	
	/**
	 * Create a new {@link ConfigureCustomImagesActivity}
	 * @param clientFactory the client factory
	 */
	public ConfigureCustomImagesActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget, com.google.gwt.event.shared.EventBus)
	 */
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		//init panel
		ConfigureCustomImagesView view = clientFactory.getConfigureCustomImagesView();
		view.setPresenter(this);
		panel.setWidget(view);
		getAllSongsWithCustomImage(); //list songs with image
	}
	
	@Override
	public void getSearchSuggestions(final Request request, final Callback callback) {
		if ((request.getQuery() != null) && (request.getQuery().length() >= 3)) { //if valid query
			asyncCallbackForSuggestion.setRequest(request);
			asyncCallbackForSuggestion.setCallback(callback);
			clientFactory.getAdminService().searchSongsWithoutCustomImage(request.getQuery(), request.getLimit(), asyncCallbackForSuggestion);
		}		
	}
	
	@Override
	public void getSearchSuggestionsWithImage(Request request, Callback callback) {
		if ((request.getQuery() != null) && (request.getQuery().length() >= 3)) { //if valid query
			asyncCallbackForSuggestion.setRequest(request);
			asyncCallbackForSuggestion.setCallback(callback);
			clientFactory.getAdminService().searchSongsWithCustomImage(request.getQuery(), request.getLimit(), asyncCallbackForSuggestion);
		}		
	}
	
	@Override
	public void searchSongsWithImage(final String keywords) {
		LOGGER.fine("Search songs with image for keywords : " + keywords);		
		if ((keywords != null) && (keywords.length() > 0)) {
			clientFactory.getAdminService().searchSongsWithCustomImage(keywords, new AjaxLoaderAsyncCallback<List<SongGWT>>(clientFactory) {
	
				@Override
				public void onCustomFailure(Throwable caught) {
					LOGGER.log(Level.WARNING, "Error during suggestions gathering with image for "+ keywords, caught);			
					clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getSongsWithImageSearchError(), MessageLabelStyle.RED);
				}
	
				@Override
				public void onCustomSuccess(List<SongGWT> result) {
					LOGGER.fine("Gathered suggestions with image for query '" + keywords+ "' : " + result);
					clientFactory.getConfigureCustomImagesView().setSongSearchWithImageList(result);
					displayNoCustomImage();
					clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getSongsWithImageSearchFinished(), MessageLabelStyle.GREEN);
				}
			});
		} else
			LOGGER.fine("No keywords entered for song search");
	}
	
	@Override
	public void searchSongs(final String keywords) {
		LOGGER.fine("Search songs for keywords : " + keywords);		
		if ((keywords != null) && (keywords.length() > 0)) {
			clientFactory.getAdminService().searchSongsWithoutCustomImage(keywords, new AjaxLoaderAsyncCallback<List<SongGWT>>(clientFactory) {
	
				@Override
				public void onCustomFailure(Throwable caught) {
					LOGGER.log(Level.WARNING, "Error during suggestions gathering for "+ keywords, caught);			
					clientFactory.getConfigureCustomImagesView().setSongSearchList(Collections.<SongGWT> emptyList());	
					clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getSongsSearchError(), MessageLabelStyle.RED);
				}
	
				@Override
				public void onCustomSuccess(List<SongGWT> result) {
					LOGGER.fine("Gathered suggestions for query '" + keywords+ "' : " + result);
					clientFactory.getConfigureCustomImagesView().setSongSearchList(result);	
					displayNoImage();
					clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getSongsSearchFinished(), MessageLabelStyle.GREEN);
				}
			});
		} else
			LOGGER.fine("No keywords entered for song search");
	}
	
	@Override
	public void searchedSongSelected(SongGWT selectedSong) {
		//save selected song
		LOGGER.fine("Selected song : " + selectedSong);
		this.selectedSong = selectedSong;
		String messageText = clientFactory.getConfigureCustomImagesMessages().getSelectedSong(selectedSong.getArtist(), selectedSong.getTitle());
		clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(messageText, MessageLabelStyle.DEFAULT);
		
		if (selectedSong.getImagePath() != null) { //if image for selected song
			SafeUri imageUri = UriUtils.fromString(GWT.getHostPageBaseURL() + selectedSong.getImagePath().substring(1));
			clientFactory.getConfigureCustomImagesView().setSearchedImage(imageUri, selectedSong.getImageWidth(), selectedSong.getImageHeight());
		} else //display fallback image
			displayDefaultImage();	
	}

	/**
	 * Display default image
	 */
	private void displayDefaultImage() {
		clientFactory.getConfigureCustomImagesView().setSearchedImage(
				clientFactory.getAdminBundle().logoMillenium().getSafeUri(), 
				String.valueOf(clientFactory.getAdminBundle().logoMillenium().getWidth()), 
				String.valueOf(clientFactory.getAdminBundle().logoMillenium().getHeight()));
	}
	
	/**
	 * Display no image
	 */
	private void displayNoImage() {
		LOGGER.fine("Selected song : null");
		selectedSong = null;
		clientFactory.getConfigureCustomImagesView().setSearchedImage(null, "0", "0");
	}
	
	/**
	 * Display no custom image
	 */
	private void displayNoCustomImage() {
		LOGGER.fine("Selected song with image : null");
		selectedCustomSong = null;
		clientFactory.getConfigureCustomImagesView().setSearchedCustomImage(null, "0", "0");
	}
	
	@Override
	public void searchedSongWithImageSelected(SongGWT selectedSong) {
		//save selected song with image
		LOGGER.fine("Selected song with image : " + selectedSong);
		selectedCustomSong = selectedSong;
		String messageText = clientFactory.getConfigureCustomImagesMessages().getSelectedCustomSong(selectedSong.getArtist(), selectedSong.getTitle());
		clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(messageText, MessageLabelStyle.DEFAULT);
		
		if (selectedSong.getImagePath() != null) { //if image for selected song
			SafeUri imageUri = UriUtils.fromString(GWT.getHostPageBaseURL() + selectedSong.getImagePath().substring(1));
			clientFactory.getConfigureCustomImagesView().setSearchedCustomImage(imageUri, selectedSong.getImageWidth(), selectedSong.getImageHeight());
		} else //display fallback image
			displayDefaultCustomImage();	
	}
	
	/**
	 * Display default custom image
	 */
	private void displayDefaultCustomImage() {
		clientFactory.getConfigureCustomImagesView().setSearchedCustomImage(
				clientFactory.getAdminBundle().logoMillenium().getSafeUri(), 
				String.valueOf(clientFactory.getAdminBundle().logoMillenium().getWidth()), 
				String.valueOf(clientFactory.getAdminBundle().logoMillenium().getHeight()));
	}
	
	/**
	 * Get all the songs with custom image and fill in the list
	 */
	@Override
	public void getAllSongsWithCustomImage() {
		LOGGER.fine("Get all the songs with custom image");
		clientFactory.getAdminService().getAllCustomImageSong(new AjaxLoaderAsyncCallback<List<SongGWT>>(clientFactory) {
			
			@Override
			public void onCustomFailure(Throwable caught) {
				LOGGER.log(Level.WARNING, "Error while getting all songs with custom image", caught);			
				clientFactory.getConfigureCustomImagesView().setSongSearchWithImageList(Collections.<SongGWT> emptyList());
				clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getAllSongsWithImageListingError(), MessageLabelStyle.RED);
			}
			
			@Override
			public void onCustomSuccess(List<SongGWT> result) {
				LOGGER.fine("Gathered all songs with image list : " + result);
				clientFactory.getConfigureCustomImagesView().setSongSearchWithImageList(result);
				displayNoCustomImage();
				clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getAllSongsWithImageListingOK(), MessageLabelStyle.DEFAULT);
			}
			
		});
	}
	
	@Override
	public void setSongAsCustomImageSong() {
		LOGGER.fine("Set song as custom image song " + selectedSong);
		if (selectedSong != null) { //if a song is selected
			clientFactory.getAdminService().setSongAsSongWithCustomImage(selectedSong, new AjaxLoaderAsyncCallback<Void>(clientFactory) {
				
				@Override
				public void onCustomFailure(Throwable caught) {
					LOGGER.log(Level.WARNING, "Error while setting song as custom image song : " + selectedSong, caught);	
					clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getCustomImageSetError(), MessageLabelStyle.RED);
				}
				
				@Override
				public void onCustomSuccess(Void result) {
					LOGGER.fine("Song properly set as custom image song : " + selectedSong);
					clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getCustomImageSetOK(), MessageLabelStyle.GREEN);
					getAllSongsWithCustomImage();
					
					//empty search list
					displayNoImage();
					clientFactory.getConfigureCustomImagesView().setSongSearchList(Collections.<SongGWT> emptyList());
				}
				
			});
		} else {
			LOGGER.fine("No song selected for custom image set");
			clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getNoSongSelected(), MessageLabelStyle.RED);
		}
	}
	
	@Override
	public void removeCustomImage() {
		LOGGER.fine("Remove custom image on selected song " + selectedCustomSong);
		if (selectedCustomSong != null) { //if a song is selected
			if (Window.confirm(clientFactory.getConfigureCustomImagesConstants().getConfirmCustomImageRemoval())) { //if user confirms deletion
				LOGGER.fine("User confirmed custom image deletion");
				clientFactory.getAdminService().removeCustomImageOnSong(selectedCustomSong, new AjaxLoaderAsyncCallback<Void>(clientFactory) {
					
					@Override
					public void onCustomFailure(Throwable caught) {
						LOGGER.log(Level.WARNING, "Error while removing a custom image on song : " + selectedCustomSong, caught);	
						clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getCustomImageRemovalError(), MessageLabelStyle.RED);
					}
					
					@Override
					public void onCustomSuccess(Void result) {
						LOGGER.fine("Custom image properly removed on song : " + selectedCustomSong);
						clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getCustomImageRemovalOK(), MessageLabelStyle.GREEN);
						getAllSongsWithCustomImage();
						
						//empty search list
						displayNoImage();
						clientFactory.getConfigureCustomImagesView().setSongSearchList(Collections.<SongGWT> emptyList());
					}
				});
			} else
				LOGGER.fine("User cancelled custom image deletion");
		} else {
			LOGGER.fine("No song selected for custom image removal");
			clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getNoSongSelected(), MessageLabelStyle.RED);
		}
	}
	
	@Override
	public void onClickUploadFileEvent() {
		LOGGER.fine("Add custom image on selected song " + selectedCustomSong);
		if (selectedCustomSong != null) { //if a song is selected
			String selectedFile = clientFactory.getConfigureCustomImagesView().getSelectedFile();
			LOGGER.fine("Selected file : " + selectedFile);
			if ((selectedFile == null) || (selectedFile.length() == 0)){
				LOGGER.fine("Selected file is null");
				clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getSelectedFileNull(), MessageLabelStyle.RED);
				
			} else {
				String fileExtension = selectedFile.substring(selectedFile.lastIndexOf(".") + 1).toLowerCase();
				fileExtension = (fileExtension == null) ? "" : fileExtension; 
				if ((fileExtension.equals("png")) || (fileExtension.equals("jpeg") || (fileExtension.equals("jpg")))) {
					//file is OK - go upload
					LOGGER.fine("File is OK for upload");
					AutoBean<AddCustomImageParam> autobean = clientFactory.getAutobeanFactory().getAddCustomImageParam();
					AddCustomImageParam addCustomImageParam =  autobean.as(); //get bean
					addCustomImageParam.setArtist(selectedCustomSong.getArtist()); //set artist
					addCustomImageParam.setTitle(selectedCustomSong.getTitle()); //set title
					String json  = AutoBeanCodex.encode(autobean).getPayload(); //get the json string
					LOGGER.fine("Generated JSON : " + json);
					
					//set the params and submit form
					clientFactory.getAjaxLoaderWidget().showPanel();
					clientFactory.getConfigureCustomImagesView().setJSONData(json);
					clientFactory.getConfigureCustomImagesView().submitUploadForm();
					
				} else {
					LOGGER.fine("Selected file has wrong extension : " + fileExtension);
					clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getSelectedFileWrongExtension(), MessageLabelStyle.RED);
				}
			}
				
		} else {
			LOGGER.fine("No song selected for custom image adding");
			clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getNoSongSelected(), MessageLabelStyle.RED);
		}
	}
	
	@Override
	public void uploadFormSubmitComplete(String resultJSON) {
		//clear waiting panel
		LOGGER.fine("Upload form submit finished - result : " + resultJSON);
		clientFactory.getAjaxLoaderWidget().hidePanel();
		
		//process data
		resultJSON = resultJSON.replaceAll("<pre .*\">", "");
		resultJSON = resultJSON.replaceAll("</pre>", "");
		AutoBean<AddCustomImageReport> autobean = AutoBeanCodex.decode(clientFactory.getAutobeanFactory(), AddCustomImageReport.class, resultJSON);
		AddCustomImageReport report = autobean.as();
		
		//set the new image
		if (report.isAddOK()) {
			//save properties
			LOGGER.fine("Add custom image OK");
			selectedCustomSong.setImagePath(report.getFileName());
			selectedCustomSong.setImageHeight(String.valueOf(report.getHeight()));
			selectedCustomSong.setImageWidth(String.valueOf(report.getWidth()));

			//display new image
			clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getCustomImageProperlyAdded(), MessageLabelStyle.GREEN);
			SafeUri imageUri = UriUtils.fromString(GWT.getHostPageBaseURL() + selectedCustomSong.getImagePath().substring(1));
			clientFactory.getConfigureCustomImagesView().setSearchedCustomImage(imageUri, selectedCustomSong.getImageWidth(), selectedCustomSong.getImageHeight());
			
		}	else {
			LOGGER.warning("Error during adding custom image");
			clientFactory.getConfigureCustomImagesView().setMessageLabelTextAndStyle(clientFactory.getConfigureCustomImagesConstants().getCustomImageAddError(), MessageLabelStyle.RED);
		}
	}
	
}
