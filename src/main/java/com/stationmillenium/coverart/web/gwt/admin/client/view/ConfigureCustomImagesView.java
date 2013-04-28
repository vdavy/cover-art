/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.view;

import java.util.List;

import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.stationmillenium.coverart.web.gwt.admin.client.view.impl.AbstractMessageView.MessageLabelStyle;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.SongGWT;

/**
 * Interface for the configure custom images view
 * @author vincent
 *
 */
public interface ConfigureCustomImagesView extends IsWidget {

	/**
	 * Set the presenter for events
	 * @param presenter the presenter
	 */
	void setPresenter(Presenter presenter);
	
	/**
	 * Set the searched songs list
	 * @param searchSongList the songs list
	 */
	void setSongSearchList(List<SongGWT> searchSongList);
	
	/**
	 * Set the image to display for searched songs
	 * @param uri the URL
	 * @param width the image width
	 * @param height the image height 
	 */
	void setSearchedImage(SafeUri uri, String width, String height);
	
	/**
	 * Set the searched songs list with image
	 * @param searchSongList the songs list
	 */
	void setSongSearchWithImageList(List<SongGWT> searchSongList);
	
	/**
	 * Set the custom image to display for searched songs
	 * @param uri the URL
	 * @param width the image width
	 * @param height the image height 
	 */
	void setSearchedCustomImage(SafeUri uri, String width, String height);
	
	/**
	 * Set the message label text and style
	 * @param text the text to display
	 * @param style the style to apply to the text
	 */
	void setMessageLabelTextAndStyle(String text, MessageLabelStyle style);
	
	/**
	 * Set the JSON data
	 * @param data the data
	 */
	void setJSONData(String data);
	
	/**
	 * Get the selected file
	 * @return the selected file
	 */
	String getSelectedFile();
	
	/**
	 * Submit the upload form
	 */
	void submitUploadForm(); 
	
	/**
	 * Presenter for the configure custom images view
	 * @author vincent
	 *
	 */
	interface Presenter {
		
		/**
		 * Get the suggestions for the search
		 * @param request the oracle request
		 * @param callback the oracle callback
		 */
		void getSearchSuggestions(Request request, Callback callback);
		
		/**
		 * Get the suggestions for the search, results with image
		 * @param request the oracle request
		 * @param callback the oracle callback
		 */
		void getSearchSuggestionsWithImage(Request request, Callback callback);
		
		/**
		 * Search songs
		 * @param keywords the keywords
		 */
		void searchSongs(String keywords);
		
		/**
		 * A searched songs has been selected
		 * @param selectedSong the selected song
		 */
		void searchedSongSelected(SongGWT selectedSong);
		
		/**
		 * Search songs with image
		 * @param keywords the keywords
		 */
		void searchSongsWithImage(String keywords);
		
		/**
		 * A searched song with image has been selected
		 * @param selectedSong the selected song
		 */
		void searchedSongWithImageSelected(SongGWT selectedSong);
		
		/**
		 * Get all the songs with custom image and fill in the list
		 */
		void getAllSongsWithCustomImage();
			
		/**
		 * Set the selected song as custom image song
		 */
		void setSongAsCustomImageSong();
		
		/**
		 * Remove the custom image on a selected song
		 */
		void removeCustomImage();
		
		/**
		 * Handle click on upload file event
		 */
		void onClickUploadFileEvent();
		
		/**
		 * Called when the submit of the upload form if finished
		 * @param resultJSON the returned JSON
		 */
		void uploadFormSubmitComplete(String resultJSON);
		
	}
	
}
