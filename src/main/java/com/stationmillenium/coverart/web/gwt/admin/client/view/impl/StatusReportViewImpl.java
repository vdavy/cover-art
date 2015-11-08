/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.view.impl;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.places.StatusReportPlace.ReportType;
import com.stationmillenium.coverart.web.gwt.admin.client.view.StatusReportView;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses.FMStatusProxy;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses.PlaylistStatusProxy;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses.ServerStatusProxy;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.statuses.StatusProxy;

/**
 * View with datagrid to display statuses
 * @author vincent
 *
 */
public class StatusReportViewImpl extends Composite implements StatusReportView {

	private static final Logger LOGGER = Logger.getLogger(StatusReportViewImpl.class.getName());
	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm:ss");
	
	private ClientFactory clientFactory; 
	
	//fields
	@UiField(provided = true) DataGrid<StatusProxy> statusGrid = new DataGrid<StatusProxy>();
	@UiField(provided = true) SimplePager statusGridPager;
	@UiField Label selectedAlert;
	@UiField Image selectedAlertStatusImage;
	@UiField Label selectedAlertStatus;	
	
	//data provider
	private ListDataProvider<StatusProxy> dataProvider = new ListDataProvider<StatusProxy>();
	private ListHandler<StatusProxy> sortHandler = new ListHandler<StatusProxy>(dataProvider.getList()); //add sorting		
	
	private static StatusReportViewImplUiBinder uiBinder = GWT.create(StatusReportViewImplUiBinder.class);

	interface StatusReportViewImplUiBinder extends UiBinder<Widget, StatusReportViewImpl> {					
	}

	public StatusReportViewImpl(ClientFactory clientFactory) {
		//init datagrid
		dataProvider.addDataDisplay(statusGrid); //set datasource		
		statusGrid.addColumnSortHandler(sortHandler);
			
		statusGridPager = new SimplePager(TextLocation.CENTER, false, true); //pagination
		statusGridPager.setDisplay(statusGrid);
		
		defineDatagridColumns(clientFactory);		
		
		initWidget(uiBinder.createAndBindUi(this));
		this.clientFactory = clientFactory;
	}

	/**
	 * Define the datagrid columns
	 * @param clientFactory the client factory
	 */
	private void defineDatagridColumns(final ClientFactory clientFactory) {
		//date column
		TextColumn<StatusProxy> dateColumn = new TextColumn<StatusProxy>() {			
			@Override
			public String getValue(StatusProxy object) {
				return DATE_FORMAT.format(object.getDateOfChange());
			}
		};
		dateColumn.setSortable(true);
		statusGrid.addColumn(dateColumn, clientFactory.getConstants().getDateOfChange());
		statusGrid.setColumnWidth(dateColumn, "100px");
		sortHandler.setComparator(dateColumn, new Comparator<StatusProxy>() {			
			@Override
			public int compare(StatusProxy arg0, StatusProxy arg1) {
				if ((arg0 != null) && (arg1 != null)
						&& (arg0.getDateOfChange() != null) 
						&& (arg1.getDateOfChange() != null))
					return arg0.getDateOfChange().compareTo(arg1.getDateOfChange());
				else
					return -1;
			}
		});
		
		//image column
		Column<StatusProxy, ImageResource> imageColumn = new Column<StatusProxy, ImageResource>(new ImageResourceCell()) {
			@Override
			public ImageResource getValue(StatusProxy object) {
				if (object instanceof FMStatusProxy) //fm case
					return (((FMStatusProxy) object).isFmUp() ? clientFactory.getAdminBundle().greenBullet() :clientFactory.getAdminBundle().redBullet());
				else if (object instanceof ServerStatusProxy) //server case
					return (((ServerStatusProxy) object).isServerUp() ? clientFactory.getAdminBundle().greenBullet() :clientFactory.getAdminBundle().redBullet());
				else if (object instanceof PlaylistStatusProxy) //playlist case
					return (((PlaylistStatusProxy) object).isPlaylistUpdated() ? clientFactory.getAdminBundle().greenBullet() :clientFactory.getAdminBundle().redBullet());
				else
					return null;					
			}
		};
		statusGrid.addColumn(imageColumn, "");
		statusGrid.setColumnWidth(imageColumn, "50px");
		
		//status column
		TextColumn<StatusProxy> statusColumn = new TextColumn<StatusProxy>() {			
			@Override
			public String getValue(StatusProxy object) { //test each type
				if (object instanceof FMStatusProxy) //fm case
					return (((FMStatusProxy) object).isFmUp() ? clientFactory.getConstants().getFMUp() : clientFactory.getConstants().getFMDown());
				else if (object instanceof ServerStatusProxy) //server case
					return (((ServerStatusProxy) object).isServerUp() ? clientFactory.getConstants().getServerUp() : clientFactory.getConstants().getServerDown());
				else if (object instanceof PlaylistStatusProxy) //playlist case
					return (((PlaylistStatusProxy) object).isPlaylistUpdated() ? clientFactory.getConstants().getPlaylistUp() : clientFactory.getConstants().getPlaylistDown());
				else
					return "";					
			}
		};
		statusColumn.setSortable(true);
		statusGrid.addColumn(statusColumn, clientFactory.getConstants().getStatus());
		statusGrid.setColumnWidth(statusColumn, "100px");
		sortHandler.setComparator(statusColumn, new Comparator<StatusProxy>() {			
			@Override
			public int compare(StatusProxy arg0, StatusProxy arg1) {
				if ((arg0 instanceof FMStatusProxy) && (arg1 instanceof FMStatusProxy)) //fm case
					return compareBoolean(((FMStatusProxy) arg0).isFmUp(), ((FMStatusProxy) arg1).isFmUp());
				else if ((arg0 instanceof ServerStatusProxy) && (arg1 instanceof ServerStatusProxy)) //server case
					return compareBoolean(((ServerStatusProxy) arg0).isServerUp(), ((ServerStatusProxy) arg1).isServerUp());
				else if ((arg0 instanceof PlaylistStatusProxy) && (arg1 instanceof PlaylistStatusProxy)) //playlist case
					return compareBoolean(((PlaylistStatusProxy) arg0).isPlaylistUpdated(), ((PlaylistStatusProxy) arg1).isPlaylistUpdated());
				else
					return -1;		
			}
			
			/**
			 * Compare 2 booleans 
			 * @param arg0 the first boolean to compare
			 * @param arg1 the second boolean to comapre
			 * @return comparaison number
			 */
			private int compareBoolean(boolean arg0, boolean arg1) {
				if ((!arg0) && (arg1))
					return -1;
				else if ((arg0) && (!arg1))
					return 1;
				else 
					return 0;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setStatusesList(List<? extends StatusProxy> statusesList) {
		dataProvider.setList((List<StatusProxy>) statusesList);		
		sortHandler.setList(dataProvider.getList());
		statusGrid.setPageStart(0);
	}
	
	@Override
	public void setAlertType(ReportType type) {
		LOGGER.fine("Current alert selected : " + type);
		switch (type) {
		case FM:
			selectedAlert.setText(clientFactory.getConstants().getSelectedAlertFM());
			break;
			
		case PLAYLIST:
			selectedAlert.setText(clientFactory.getConstants().getSelectedAlertPlaylist());
			break;
			
		case ICECAST:
			selectedAlert.setText(clientFactory.getConstants().getSelectedAlertServer());
			break;
		}		
	}
	
	@Override
	public void setAlertActive(boolean active) {
		LOGGER.fine("Current alert status : " + active);
		if (active) { //the alert is not active
			selectedAlertStatusImage.setUrl(clientFactory.getAdminBundle().greenBullet().getSafeUri());
			selectedAlertStatus.setText(clientFactory.getConstants().getSelectedAlertDisabled());
		} else { //the alert is active
			selectedAlertStatusImage.setUrl(clientFactory.getAdminBundle().redBullet().getSafeUri());
			selectedAlertStatus.setText(clientFactory.getConstants().getSelectedAlertEnabled());
		}
	}
	
}
