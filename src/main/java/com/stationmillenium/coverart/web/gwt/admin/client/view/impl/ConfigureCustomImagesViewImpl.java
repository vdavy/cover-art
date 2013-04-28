/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.view.impl;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.utils.cells.ProxyAbstractSafeHtmlCell;
import com.stationmillenium.coverart.web.gwt.admin.client.utils.cells.SongGWTRenderer;
import com.stationmillenium.coverart.web.gwt.admin.client.view.ConfigureCustomImagesView;
import com.stationmillenium.coverart.web.gwt.admin.shared.rpc.SongGWT;

/**
 * Implementation for {@link ConfigureCustomImagesView}
 * @author vincent
 *
 */
public class ConfigureCustomImagesViewImpl extends AbstractMessageView implements ConfigureCustomImagesView {

	//static instances
	private static final Logger LOGGER = Logger.getLogger(ConfigureCustomImagesViewImpl.class.getName());
	
	interface ConfigureCustomImagesViewImplUiBinder extends	UiBinder<Widget, ConfigureCustomImagesViewImpl> {}
	private static ConfigureCustomImagesViewImplUiBinder uiBinder = GWT.create(ConfigureCustomImagesViewImplUiBinder.class);

	//local instances
	private ClientFactory clientFactory;
	private Presenter presenter;
	
	//widgets
	//search songs
	@UiField Label messageLabel;
	@UiField(provided = true) SuggestBox searchKeywords;
	@UiField Button launchSearch;
	@UiField Button cancelSearch;
	@UiField(provided = true) CellList<SongGWT> songSearchCellList;
	@UiField(provided = true) SimplePager songSearchCellListPager;
	@UiField Image songImage;
	@UiField Button addImageButton;
	
	//custom images songs search
	@UiField(provided = true) SuggestBox searchCustomKeywords;
	@UiField Button launchCustomSearch;
	@UiField Button cancelCustomSearch;
	@UiField(provided = true) CellList<SongGWT> songSearchCustomCellList;
	@UiField(provided = true) SimplePager songSearchCustomCellListPager;
	@UiField Image songCustomImage;
	@UiField Button removeCustomImageButton;
	
	//file upload
	@UiField FormPanel uploadForm;
	@UiField Hidden jsonParam;
	@UiField FileUpload uploadFile;
	@UiField Button  uploadImageButton;
	
	//data provider
	ListDataProvider<SongGWT> songSearchDataProvider = new ListDataProvider<SongGWT>();
	ListDataProvider<SongGWT> songSearchCustomDataProvider = new ListDataProvider<SongGWT>();
		
	/**
	 * Create an instance of {@link ConfigureCustomImagesViewImpl}
	 * @param clientFactory the client factory
	 */
	public ConfigureCustomImagesViewImpl(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		setupSuggestBox(); 
		initSearchSongsCellList();
		initSearchSongsCustomCellList();
		setupCustomSuggestBox();
		initWidget(uiBinder.createAndBindUi(this));
		
		//set upload form url and callback
		uploadForm.setAction(GWT.getModuleBaseURL() + "addCustomImage");
		uploadForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				LOGGER.fine("Upload form submit complete");
				presenter.uploadFormSubmitComplete(event.getResults());
			}
			
		});
	}
	
	/**
	 * Init the song search cell list
	 */
	private void initSearchSongsCellList() {
		//init song search cell list
		songSearchCellList = new CellList<SongGWT>(new ProxyAbstractSafeHtmlCell<SongGWT>(new SongGWTRenderer(clientFactory)));
		final SingleSelectionModel<SongGWT> ssm =  new SingleSelectionModel<SongGWT>();
		ssm.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				SongGWT selectedSong  = ssm.getSelectedObject();
				LOGGER.fine("Selection " + selectedSong);
				if (selectedSong != null)
					addImageButton.setEnabled(true);
				presenter.searchedSongSelected(selectedSong);
			}
		});
		songSearchCellList.setSelectionModel(ssm);
		songSearchDataProvider.addDataDisplay(songSearchCellList);
		
		songSearchCellListPager = new SimplePager(TextLocation.CENTER, false, true); //pagination
		songSearchCellListPager.setDisplay(songSearchCellList);
	}

	/**
	 * Set up the suggest box for song search
	 */
	private void setupSuggestBox() {
		searchKeywords = new SuggestBox(new SuggestOracle() {
			
			@Override
			public void requestSuggestions(Request request, Callback callback) {
				presenter.getSearchSuggestions(request, callback);	
			}
		});
		searchKeywords.setLimit(10);		
	}
	
	/**
	 * Init the song search cell list
	 */
	private void initSearchSongsCustomCellList() {
		//init song search cell list
		songSearchCustomCellList = new CellList<SongGWT>(new ProxyAbstractSafeHtmlCell<SongGWT>(new SongGWTRenderer(clientFactory)));
		final SingleSelectionModel<SongGWT> ssm =  new SingleSelectionModel<SongGWT>();
		ssm.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				SongGWT selectedSong  = ssm.getSelectedObject();
				LOGGER.fine("Selection with image " + selectedSong);
				if (selectedSong != null) 
					enableCustomImageButtons(true);
				presenter.searchedSongWithImageSelected(selectedSong);
			}
		});
		songSearchCustomCellList.setSelectionModel(ssm);
		songSearchCustomDataProvider.addDataDisplay(songSearchCustomCellList);
		
		songSearchCustomCellListPager = new SimplePager(TextLocation.CENTER, false, true); //pagination
		songSearchCustomCellListPager.setDisplay(songSearchCustomCellList);
	}
	
	/**
	 * Set up the suggest box for song search
	 */
	private void setupCustomSuggestBox() {
		searchCustomKeywords = new SuggestBox(new SuggestOracle() {
			
			@Override
			public void requestSuggestions(Request request, Callback callback) {
				presenter.getSearchSuggestionsWithImage(request, callback); //call callback			
			}
		});
		searchCustomKeywords.setLimit(10);		
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void setSongSearchList(List<SongGWT> searchSongList) {
		songSearchDataProvider.setList(searchSongList);
		addImageButton.setEnabled(false);
	}
	
	/**
	 * Handle click on search songs button
	 * @param event the vent
	 */
	@UiHandler("launchSearch")
	public void searchSongsClick(ClickEvent event) {
		presenter.searchSongs(searchKeywords.getValue());
	}
	
	/**
	 * Hancle click on cancel search button
	 * @param event the event
	 */
	@UiHandler("cancelSearch")
	public void cancelSearch(ClickEvent event) {
		searchKeywords.setValue("");
		songSearchDataProvider.setList(Collections.<SongGWT> emptyList());
		addImageButton.setEnabled(false);
	}
	
	@Override
	public void setSearchedImage(SafeUri uri, String width, String height) {
		if (uri != null) {
			songImage.setUrl(uri);
			songImage.setVisible(true);
			songImage.setWidth(width);
			songImage.setHeight(height);
		} else
			songImage.setVisible(false);
	}

	@Override
	protected Label getMessageLabel() {
		return messageLabel;
	}

	@Override
	protected ClientFactory getClientFactory() {
		return clientFactory;
	}
	
	@Override
	public void setMessageLabelTextAndStyle(String text, MessageLabelStyle style) {
		super.setMessageLabelTextAndStyle(text, style);
	}
	
	@Override
	public void setSearchedCustomImage(SafeUri uri, String width, String height) {
		if (uri != null) {
			songCustomImage.setUrl(uri);
			songCustomImage.setVisible(true);
			songCustomImage.setWidth(width);
			songCustomImage.setHeight(height);
		} else
			songCustomImage.setVisible(false);
	}
	
	@Override
	public void setSongSearchWithImageList(List<SongGWT> searchSongList) {
		songSearchCustomDataProvider.setList(searchSongList);
		enableCustomImageButtons(false);
	}
	
	/**
	 * Hancle click on cancel custom search button
	 * @param event the event
	 */
	@UiHandler("cancelCustomSearch")
	public void cancelCustomImageSearch(ClickEvent event) {
		searchCustomKeywords.setValue("");
		songSearchCustomDataProvider.setList(Collections.<SongGWT> emptyList());
		presenter.getAllSongsWithCustomImage();
		enableCustomImageButtons(false);
	}
	
	/**
	 * Handle click on search custom songs button
	 * @param event the vent
	 */
	@UiHandler("launchCustomSearch")
	public void searchCustomSongsClick(ClickEvent event) {
		presenter.searchSongsWithImage(searchCustomKeywords.getValue());
	}
	
	/**
	 * Handle click on add image button - to add custom image to song
	 * @param event
	 */
	@UiHandler("addImageButton")
	public void onClickAddCustomImageToSong(ClickEvent event) {
		presenter.setSongAsCustomImageSong();
	}
	
	/**
	 * Handle click on remove custom image button - to remove custom image from a song
	 * @param event the event
	 */
	@UiHandler("removeCustomImageButton")
	public void onClickRemoveCustomImage(ClickEvent event) {
		presenter.removeCustomImage();
	}
	
	@Override
	public void setJSONData(String data) {
		jsonParam.setValue(data);
	}
	
	@Override
	public String getSelectedFile() {
		return uploadFile.getFilename();
	}
	
	/**
	 * Handle on click upload file button
	 * @param event the event
	 */
	@UiHandler("uploadImageButton")
	public void onClickUploadFile(ClickEvent event) {
		presenter.onClickUploadFileEvent();
	}
	
	/**
	 * Enable the widgets of the custom image part
	 * @param enabled <code>true</code> to enable, <code>false</code> to disable
	 */
	private void enableCustomImageButtons(boolean enabled) {
		uploadFile.setEnabled(enabled);
		removeCustomImageButton.setEnabled(enabled);
		uploadImageButton.setEnabled(enabled);
	}
	
	@Override
	public void submitUploadForm() {
		uploadForm.submit();
	}
	
}
