/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.utils.editors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.ui.client.ValueBoxEditorDecorator;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.stationmillenium.coverart.web.gwt.admin.client.view.ConfigureAlertView.Presenter;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertEmailProxy;

/**
 * Editor for {@link AlertEmailProxy}
 * @author vincent
 *
 */
public class AlertEmailEditor extends Composite implements Editor<AlertEmailProxy>{

	//ui binder
	private static AlertEmailEditorUiBinder uiBinder = GWT.create(AlertEmailEditorUiBinder.class);
	interface AlertEmailEditorUiBinder extends UiBinder<Widget, AlertEmailEditor> {}

	//instances
	private Presenter presenter;
	
	//fields
	@UiField ValueBoxEditorDecorator<String> email;
	@UiField AlertTypeSetEditor alertType;
	@UiField Button delete;
	
	/**
	 * Create a new editor for the {@link AlertEmailProxy}
	 */
	public AlertEmailEditor() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Set the presenter to handle button click
	 * @param presenter the presenter
	 */
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	/**
	 * Called when save button is clicked
	 * @param event the click event
	 */
	@UiHandler("save")
	public void onClickSaveButton(ClickEvent event) {
		presenter.onClickSaveAlertEmail();
	}
	
	/**
	 * Set if the delete button is visible
	 * @param visible <code>true</code> to set visible, <code>false</code> to hide
	 */
	public void setDeleteButtonVisible(boolean visible) {
		delete.setVisible(visible);
	}
	
	/**
	 * Called when delete button is clicked
	 * @param event the click event
	 */
	@UiHandler("delete")
	public void onClickDeleteButton(ClickEvent event) {
		presenter.onClickDeleteButton();
	}
	
}
