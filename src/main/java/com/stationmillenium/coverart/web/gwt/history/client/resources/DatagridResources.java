package com.stationmillenium.coverart.web.gwt.history.client.resources;

import com.google.gwt.user.cellview.client.DataGrid;

public interface DatagridResources extends DataGrid.Resources {
	
	@Source({DataGrid.Style.DEFAULT_CSS, "datagrid.css"})
	DataGridStyle dataGridStyle();
	 
	interface DataGridStyle extends DataGrid.Style {	}

}
