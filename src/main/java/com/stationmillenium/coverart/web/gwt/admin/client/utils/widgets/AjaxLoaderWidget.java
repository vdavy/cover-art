/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.utils.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Popup panel with ajax loader
 * @author vincent
 *
 */
public class AjaxLoaderWidget extends Composite {

	//ui binder
	private static AjaxLoaderWidgetUiBinder uiBinder = GWT.create(AjaxLoaderWidgetUiBinder.class);
	interface AjaxLoaderWidgetUiBinder extends UiBinder<Widget, AjaxLoaderWidget> {}

	//widgets 
	@UiField PopupPanel ajaxLoading;
	
	/**
	 * Create a new Asynca
	 */
	public AjaxLoaderWidget() {
		super();
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	/**
	 * Center and show the panel
	 */
	public void showPanel() {
		ajaxLoading.center();
		//ajaxLoading.show();
	}
	
	/**
	 * Hide the panel
	 */
	public void hidePanel() {
		ajaxLoading.hide();
	}

}
