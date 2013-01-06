/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.view.impl;

import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.utils.cells.AlertActivationProxyRenderer;
import com.stationmillenium.coverart.web.gwt.admin.client.utils.cells.AlertEmailProxyRenderer;
import com.stationmillenium.coverart.web.gwt.admin.client.utils.cells.ProxyAbstractSafeHtmlCell;
import com.stationmillenium.coverart.web.gwt.admin.client.utils.editors.AlertEmailEditor;
import com.stationmillenium.coverart.web.gwt.admin.client.view.ConfigureAlertView;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertActivationProxy;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertEmailProxy;

/**
 * Implementation of {@link ConfigureAlertView}
 * @author vincent
 *
 */
public class ConfigureAlertViewImpl extends AbstractMessageView implements ConfigureAlertView, Editor<AlertActivationProxy> {

	private static final Logger LOGGER = Logger.getLogger(ConfigureAlertViewImpl.class.getName());
	private static ConfigureAlertViewImplUiBinder uiBinder = GWT.create(ConfigureAlertViewImplUiBinder.class);

	interface ConfigureAlertViewImplUiBinder extends UiBinder<Widget, ConfigureAlertViewImpl> {}

	//instances
	private Presenter presenter;
	private ClientFactory clientFactory;
	private SingleSelectionModel<AlertActivationProxy> alertActivationSsm;
	private SingleSelectionModel<AlertEmailProxy> alertEmailSsm;
	
	//fields
	@UiField @Ignore Label messageLabel;
	@UiField(provided = true) CellList<AlertActivationProxy> alertActivationList;
	@UiField @Path("enableAlert") CheckBox alertEnabled;
	@UiField Image enabledImage;
	@UiField(provided = true) CellList<AlertEmailProxy> alertEmailList;
	@UiField @Ignore AlertEmailEditor alertEmailEditor;
		
	//data provider
	ListDataProvider<AlertActivationProxy> alertActivationDataProvider = new ListDataProvider<AlertActivationProxy>();
	ListDataProvider<AlertEmailProxy> alertEmailDataProvider = new ListDataProvider<AlertEmailProxy>();
		
	/**
	 * New instance of the implementation of {@link ConfigureAlertView}
	 * @param clientFactory the client factory
	 */
	public ConfigureAlertViewImpl(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		
		//init cell list
		initAlertActivationCellList();
		initAlertEmailCellList();

		//init all display
		initWidget(uiBinder.createAndBindUi(this));
		
		//enabled editors
		alertEnabled.setEnabled(false);
		alertEmailEditor.setVisible(false);
	}

	/**
	 * Initialize the alert activation cell list
	 */
	private void initAlertActivationCellList() {
		alertActivationList = new CellList<AlertActivationProxy>(new ProxyAbstractSafeHtmlCell<AlertActivationProxy>(new AlertActivationProxyRenderer()));
		alertActivationSsm =  new SingleSelectionModel<AlertActivationProxy>();
		alertActivationSsm.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				//call presenter when alert is selected
				AlertActivationProxy selected  = alertActivationSsm.getSelectedObject();
				presenter.onSelectedAlert(selected); 
				
				if ((selected != null) && (!alertEnabled.isEnabled()))
					alertEnabled.setEnabled(true);
			}
		});
		alertActivationList.setSelectionModel(alertActivationSsm);
		alertActivationDataProvider.addDataDisplay(alertActivationList);
	}
	
	/**
	 * Initialize the alert email cell list
	 */
	private void initAlertEmailCellList() {
		alertEmailList = new CellList<AlertEmailProxy>(new ProxyAbstractSafeHtmlCell<AlertEmailProxy>(new AlertEmailProxyRenderer()));
		alertEmailSsm =  new SingleSelectionModel<AlertEmailProxy>();
		alertEmailSsm.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				//call presenter when alert is selected
				AlertEmailProxy selected  = alertEmailSsm.getSelectedObject();
				presenter.onSelectedAlert(selected); 
				
				//display editor
				if (selected != null) {
					alertEmailEditor.setDeleteButtonVisible(true);
					if (!alertEmailEditor.isVisible()) 
						alertEmailEditor.setVisible(true);
				}
			}
		});
		alertEmailList.setSelectionModel(alertEmailSsm);
		alertEmailDataProvider.addDataDisplay(alertEmailList);
	}

	@Override
	public void setAlertActivationList(List<AlertActivationProxy> alertList) {
		alertActivationDataProvider.setList(alertList);
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		alertEmailEditor.setPresenter(presenter);
	}
	
	@Override
	protected ClientFactory getClientFactory() {
		return clientFactory;
	}
	
	@Override
	@Ignore
	protected Label getMessageLabel() {
		return messageLabel;
	}
	
	@Override
	public void setMessageLabelTextAndStyle(String text, MessageLabelStyle style) {
		super.setMessageLabelTextAndStyle(text, style);
	}
	
	/**
	 * Handle click on checkbox to activate alert
	 * @param event the event
	 */
	@UiHandler("alertEnabled")
	public void onClickAlertEnabled(ValueChangeEvent<Boolean> event) {
		LOGGER.fine("Click on checkbox to change alert activation");
		alertEnabled.setEnabled(false);
		enabledImage.setVisible(false);
		presenter.onChangeAlertActivation();
	}
	
	@Override
	public void setDisplayedImageIcon(boolean greenIcon) {
		enabledImage.setVisible(true);
		if (greenIcon) //set image
			enabledImage.setUrl(clientFactory.getAdminBundle().greenBullet().getSafeUri());
		else
			enabledImage.setUrl(clientFactory.getAdminBundle().redBullet().getSafeUri());
	}
	
	@Override
	@Ignore
	public AlertEmailEditor getAlertEmailEditor() {
		return alertEmailEditor;
	}
	
	@Override
	public void setAlertEmailList(List<AlertEmailProxy> alertList) {
		alertEmailDataProvider.setList(alertList);
	}
	
	@Override
	public void hideAlertEmailEditor() {
		alertEmailEditor.setVisible(false);
		if (getSelectedAlertEmail() != null)
			alertEmailSsm.setSelected(getSelectedAlertEmail(), false);
	}
	
	/**
	 * Handle click on add email button 
	 * @param event the event
	 */
	@UiHandler("addEmailButton")
	public void onClickAddEmailButton(ClickEvent event) {
		LOGGER.fine("Click on button to add email");
		presenter.onClickAddEmail();
		
		//display editor
		alertEmailEditor.setDeleteButtonVisible(false);
		if (!alertEmailEditor.isVisible()) 
			alertEmailEditor.setVisible(true);			
		if (getSelectedAlertEmail() != null)
			alertEmailSsm.setSelected(getSelectedAlertEmail(), false);		
	}
	
	@Override
	public AlertEmailProxy getSelectedAlertEmail() {
		return alertEmailSsm.getSelectedObject();
	}
	
	@Override
	public void clearAlertActivationListSelection() {
		//clear list selection and disable editor
		if (alertActivationSsm.getSelectedObject() != null) {
			alertActivationSsm.setSelected(alertActivationSsm.getSelectedObject(), false);
			enabledImage.setVisible(false);
			alertEnabled.setEnabled(false);
			alertEnabled.setValue(false);
		}
	}
	
}
