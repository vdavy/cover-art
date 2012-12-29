/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.view.impl;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.view.GeneralConfigView;

/**
 * Implementation of the {@link GeneralConfigView}
 * @author vincent
 *
 */
public class GeneralConfigViewImpl extends AbstractMessageView implements GeneralConfigView {

	//logger
	private static final Logger LOGGER = Logger.getLogger(GeneralConfigViewImpl.class.getName());
	
	//ui binder
	private static GeneralConfigViewImplUiBinder uiBinder = GWT.create(GeneralConfigViewImplUiBinder.class);
	interface GeneralConfigViewImplUiBinder extends UiBinder<Widget, GeneralConfigViewImpl> {}

	//fields
	@UiField Label messageLabel;
	@UiField Image pollingServiceImage;
	@UiField Label pollingServiceText;
	@UiField Image fmAlertImage;
	@UiField Label fmAlertText;
	@UiField CheckBox pollingServiceCheckbox;
	@UiField CheckBox fmAlertCheckbox;
	@UiField Image hibernateIndexImage;
	@UiField Label hibernateIndexText;
	@UiField Button hibernateIndexButton;
	@UiField Label currentTitleText;
	
	//local instances
	private Presenter presenter;
	private ClientFactory clientFactory;
	
	/**
	 * Create a new {@link GeneralConfigViewImpl}
	 */
	public GeneralConfigViewImpl(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;		
	}

	@Override
	protected ClientFactory getClientFactory() {
		return clientFactory;
	}
	
	@Override
	protected Label getMessageLabel() {
		return messageLabel;
	}
	
	@Override
	public void setMessageLabelTextAndStyle(String text, MessageLabelStyle style) {
		super.setMessageLabelTextAndStyle(text, style);
	}

	@Override
	public void setFMAlertInformation(boolean activeService, String associatedText) {
		setImageAndText(activeService, associatedText, fmAlertImage, fmAlertText);
	}
	
	@Override
	public void setPollingServiceInformation(boolean activeService, String associatedText) {
		setImageAndText(activeService, associatedText, pollingServiceImage, pollingServiceText);
	}
	
	@Override
	public void setHibernateSearchInformation(boolean activeIndexing, String associatedText) {
		setImageAndText(!activeIndexing, associatedText, hibernateIndexImage, hibernateIndexText);		
		hibernateIndexButton.setEnabled(!activeIndexing);
	}
	
	/**
	 * Set image and label with predefined image
	 * @param imageBoolean the boolean value to set the bulled
	 * @param text the text to display in the label
	 * @param imageToSet the image to set
	 * @param labelToSet the label to set
	 */
	private void setImageAndText(boolean imageBoolean, String text, Image imageToSet, Label labelToSet) {
		LOGGER.fine("Set " + imageBoolean + " to " + imageToSet + " - Set " + text + " to " + labelToSet);
		if (imageBoolean) //set image
			imageToSet.setUrl(clientFactory.getAdminBundle().greenBullet().getSafeUri());
		else
			imageToSet.setUrl(clientFactory.getAdminBundle().redBullet().getSafeUri());
		
		labelToSet.setText(text); //set label
	}
	
	@Override
	public void setCurrentTitleText(String text) {
		currentTitleText.setText(SafeHtmlUtils.htmlEscape(text));
	}
	
	@Override
	public void setFMAlertCheckboxChecked(boolean checked) {
		fmAlertCheckbox.setValue(checked);
	}
	
	@Override
	public void setPollingServiceCheckboxChecked(boolean checked) {
		pollingServiceCheckbox.setValue(checked);
	}
	
	@Override
	public void setFMAlertCheckboxActivation(boolean enabled) {
		fmAlertCheckbox.setEnabled(enabled);
	}
	
	@Override
	public void setPollingServiceCheckboxActivation(boolean enabled) {
		pollingServiceCheckbox.setEnabled(enabled);
	}
	
	/**
	 * Handles value change of polling service checkbox
	 * @param event the event
	 */
	@UiHandler("pollingServiceCheckbox")
	public void onValueChangedPollingServiceCheckbox(ValueChangeEvent<Boolean> event) {
		presenter.onClickPollingServiceCheckbox(event.getValue());
	}
	
	/**
 	 * Handles value change of FM alert checkbox
	 * @param event the event	 
	*/
	@UiHandler("fmAlertCheckbox")
	public void onValueChangedFMAlertCheckbox(ValueChangeEvent<Boolean> event) {
		presenter.onClickFMAlertCheckbox(event.getValue());
	}
	
	/**
	 * Handles click on index button
	 * @param event the event
	 */
	@UiHandler("hibernateIndexButton")
	public void onClickIndexButton(ClickEvent event) {
		presenter.onClickIndexButton();
	}
	
	@Override
	public void lockIndexButton() {
		hibernateIndexButton.setEnabled(false);
	}
	
}
