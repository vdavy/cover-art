/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.history.client.view.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.stationmillenium.coverart.web.gwt.history.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.history.client.view.HistoryViewInterface;
import com.stationmillenium.coverart.web.gwt.history.client.view.HistoryViewInterface.HistoryViewPresenter.SearchFieldType;
import com.stationmillenium.coverart.web.gwt.history.shared.HistoryGWTDTO;

/**
 * History view
 * @author vincent
 *
 */
public class HistoryViewImpl extends Composite implements HistoryViewInterface {

	//log
	private static final Logger LOGGER = Logger.getLogger(HistoryViewImpl.class.getName());	
		
	//ui binder
	private static HistoryViewImplUiBinder uiBinder = GWT.create(HistoryViewImplUiBinder.class);

	interface HistoryViewImplUiBinder extends UiBinder<Widget, HistoryViewImpl> {
	}
	
	//ui fields
	@UiField RadioButton searchOnField;
	@UiField RadioButton searchOnDate;
	@UiField(provided = true) SuggestBox  searchKeywords;
	@UiField RadioButton searchTypeAll;
	@UiField RadioButton searchTypeArtist;
	@UiField RadioButton searchTypeTitle;
	@UiField DateBox dateSearch;
	@UiField TextBox hourSearch;
	@UiField TextBox minuteSearch;
	@UiField(provided = true) DataGrid<HistoryGWTDTO> historyGrid = new DataGrid<HistoryGWTDTO>();
	@UiField(provided = true) SimplePager historyGridPager;
	@UiField Image songImage;
	@UiField PopupPanel ajaxLoading;
	
	//data provider
	private ListDataProvider<HistoryGWTDTO> dataProvider = new ListDataProvider<HistoryGWTDTO>();
	private ListHandler<HistoryGWTDTO> sortHandler = new ListHandler<HistoryGWTDTO>(dataProvider.getList()); //add sorting		
	
	private HistoryViewPresenter presenter;

	public HistoryViewImpl(ClientFactory clientFactory) {		
		//init datagrid
		dataProvider.addDataDisplay(historyGrid); //set datasource		
		historyGrid.addColumnSortHandler(sortHandler);
		
		historyGridPager = new SimplePager(TextLocation.CENTER, false, true); //pagination
		historyGridPager.setDisplay(historyGrid);
		
		defineDatagridColumns(clientFactory);		
		 
		//add selection
		final SingleSelectionModel<HistoryGWTDTO> ssm =  new SingleSelectionModel<HistoryGWTDTO>();
		ssm.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				HistoryGWTDTO selected  = ssm.getSelectedObject();
				LOGGER.fine("Selection " + selected);
				presenter.songSelected(selected);
			}
		});
		historyGrid.setSelectionModel(ssm);
		
		//init ui binder
		setupSuggestBox();
		initWidget(uiBinder.createAndBindUi(this));
		initSearchFields();		
	}

	/**
	 * Init search fields
	 */
	private void initSearchFields() {
		searchKeywords.setValue("");
		searchTypeAll.setValue(true);
		selectSearchOnDate();
		
		//display current date in date search
		dateSearch.setFormat(new DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
		Date currentDate = new Date();
		dateSearch.setValue(currentDate);
		hourSearch.setText(DateTimeFormat.getFormat("HH").format(currentDate));
		minuteSearch.setText(DateTimeFormat.getFormat("mm").format(currentDate));
	}

	/**
	 * Set up the suggest box
	 */
	private void setupSuggestBox() {
		searchKeywords = new SuggestBox(new SuggestOracle() {
			
			@Override
			public void requestSuggestions(Request request, Callback callback) {
				presenter.getSearchSuggestions(request, callback); //call callback			
			}
		});
		searchKeywords.setLimit(10);
		searchKeywords.getValueBox().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectSearchOnField();
			}
		});
	}

	/**
	 * Define the datagrid columns
	 * @param clientFactory the client factory
	 */
	private void defineDatagridColumns(ClientFactory clientFactory) {
		//date column
		TextColumn<HistoryGWTDTO> dateColumn = new TextColumn<HistoryGWTDTO>() {			
			@Override
			public String getValue(HistoryGWTDTO object) {
				return presenter.formatDate(object.getPlayedDate());
			}
		};
		dateColumn.setSortable(true);
		historyGrid.addColumn(dateColumn, clientFactory.getConstants().getSongDateTitle());
		historyGrid.setColumnWidth(dateColumn, "100px");
		sortHandler.setComparator(dateColumn, new Comparator<HistoryGWTDTO>() {			
			@Override
			public int compare(HistoryGWTDTO arg0, HistoryGWTDTO arg1) {
				if ((arg0 != null) && (arg1 != null)
						&& (arg0.getPlayedDate() != null) 
						&& (arg1.getPlayedDate() != null))
					return arg0.getPlayedDate().compareTo(arg1.getPlayedDate());
				else
					return -1;
			}
		});
		
		//artist column
		TextColumn<HistoryGWTDTO> artistColumn = new TextColumn<HistoryGWTDTO>() {			
			@Override
			public String getValue(HistoryGWTDTO object) {
				return object.getArtist();
			}
		};
		artistColumn.setSortable(true);
		historyGrid.addColumn(artistColumn, clientFactory.getConstants().getSongArtistTitle());
		historyGrid.setColumnWidth(artistColumn, "150px");
		sortHandler.setComparator(artistColumn, new Comparator<HistoryGWTDTO>() {			
			@Override
			public int compare(HistoryGWTDTO arg0, HistoryGWTDTO arg1) {
				if ((arg0 != null) && (arg1 != null)
						&& (arg0.getArtist() != null) 
						&& (arg1.getArtist() != null))
					return arg0.getArtist().compareTo(arg1.getArtist());
				else
					return -1;
			}
		});
		
		//title column
		TextColumn<HistoryGWTDTO> titleColumn = new TextColumn<HistoryGWTDTO>() {			
			@Override
			public String getValue(HistoryGWTDTO object) {
				return object.getTitle();
			}
		};
		titleColumn.setSortable(true);
		historyGrid.addColumn(titleColumn, clientFactory.getConstants().getSongTitleTitle());
		historyGrid.setColumnWidth(titleColumn, "150px");
		sortHandler.setComparator(titleColumn, new Comparator<HistoryGWTDTO>() {			
			@Override
			public int compare(HistoryGWTDTO arg0, HistoryGWTDTO arg1) {
				if ((arg0 != null) && (arg1 != null)
						&& (arg0.getTitle() != null) 
						&& (arg1.getTitle() != null))
					return arg0.getTitle().compareTo(arg1.getTitle());
				else
					return -1;
			}
		});
	}

	@Override
	public void setPresenter(HistoryViewPresenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void setSongHistoryList(List<HistoryGWTDTO> songHistory) {
		dataProvider.setList(songHistory);		
		sortHandler.setList(dataProvider.getList());
		historyGrid.setPageStart(0);
	}

	/**
	 * Select search on date
	 */
	private void selectSearchOnDate() {
		searchOnField.setValue(false);
		searchOnDate.setValue(true);
	}
	
	/**
	 * Select search on field
	 */
	private void selectSearchOnField() {
		searchOnField.setValue(true);
		searchOnDate.setValue(false);
	}
	
	@UiHandler("searchTypeAll")
	public void onSearchTypeAllClick(ClickEvent e) {
		selectSearchOnField();
	}
	
	@UiHandler("searchTypeArtist")
	public void onSearchTypeArtistClick(ClickEvent e) {
		selectSearchOnField();
	}
	
	@UiHandler("searchTypeTitle")
	public void onSearchTypeTitleClick(ClickEvent e) {
		selectSearchOnField();
	}
	
	@UiHandler("dateSearch")
	public void onDateSearchClick(ValueChangeEvent<Date> e) {
		selectSearchOnDate();
	}
	
	@UiHandler("hourSearch")
	public void onHourSearchClick(ClickEvent e) {
		selectSearchOnDate();
	}
	
	@UiHandler("minuteSearch")
	public void onMinuteSearchClick(ClickEvent e) {
		selectSearchOnDate();
	}
	
	@UiHandler("searchKeywords")
	public void onSearchKeywordsValueChanged(ValueChangeEvent<String> event) {
		handleSearchKeywordsTitleValue(event.getValue());
	}
	
	@UiHandler("searchKeywords")
	public void onSearchKeywordsSlection(SelectionEvent<Suggestion> event) {
		handleSearchKeywordsTitleValue(event.getSelectedItem().getDisplayString());
	}
	
	/**
	 * Handle the title of search keywords field
	 * @param title the vlaue of the title to set
	 */
	private void handleSearchKeywordsTitleValue(String title) {
		if ((title != null) && (title.length() > 18))
			searchKeywords.setTitle(title);
		else
			searchKeywords.setTitle("");
	}
	
	/**
	 * On click on launch search button
	 * @param event the event
	 */
	@UiHandler("launchSearch")
	public void onClickSearchButton(ClickEvent event) {
		if (searchOnField.getValue()) { //Search by field
			LOGGER.fine("Search by field");
			SearchFieldType searchType = null; //find search type
			if (searchTypeAll.getValue())
				searchType = SearchFieldType.ALL;
			else if (searchTypeArtist.getValue())
				searchType = SearchFieldType.ARTIST;
			else
				searchType = SearchFieldType.TITLE;
			
			//launch search
			presenter.launchSearchFields(searchKeywords.getValue(), searchType); 
			
		} else { //search by date
			LOGGER.fine("Search by date");
			presenter.launchSearchDates(dateSearch.getValue(), hourSearch.getValue(), minuteSearch.getValue());
		}
	}
	
	/**
	 * On click on cancel search button
	 * @param event the event
	 */
	@UiHandler("cancelSearch")
	public void onClickCancelSearch(ClickEvent event) {
		initSearchFields();
		selectSearchOnField();
		presenter.refreshHistory();
	}
	
	@Override
	public void setImage(SafeUri uri, String width, String height) {
		songImage.setUrl(uri);
		songImage.setWidth(width);
		songImage.setHeight(height);
	}
	
	@Override
	public void showAjaxLoading(boolean display) {
		ajaxLoading.center();
		ajaxLoading.setVisible(display);
	}
}
