/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.utils.editors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertType;

/**
 * Editor for alert type using a set
 * @author vincent
 *
 */
public class AlertTypeSetEditor extends Composite implements LeafValueEditor<Set<AlertType>>, HasEditorErrors<Set<AlertType>> {

	//ui binder
	private static AlertTypeSetEditorUiBinder uiBinder = GWT.create(AlertTypeSetEditorUiBinder.class);
	interface AlertTypeSetEditorUiBinder extends UiBinder<Widget, AlertTypeSetEditor> {}

	//fields
	@UiField Label errors;
	@UiField CheckBox alertEnabledFM;
	@UiField CheckBox alertEnabledShoutcast;
	@UiField CheckBox alertEnabledPlaylist;
	
	/**
	 * Create a new editor for the alert type using a set
	 */
	public AlertTypeSetEditor() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public void setValue(Set<AlertType> value) {
		if (value != null) {
			alertEnabledFM.setValue(value.contains(AlertType.FM)); //alert fm case
			alertEnabledShoutcast.setValue(value.contains(AlertType.SHOUTCAST)); //shoutcast alert case
			alertEnabledPlaylist.setValue(value.contains(AlertType.PLAYLIST)); //playlist alert case
		} else
			for (CheckBox checkbox : new CheckBox[]{alertEnabledFM, alertEnabledShoutcast, alertEnabledPlaylist})
				checkbox.setValue(false);
	}

	@Override
	public Set<AlertType> getValue() {
		Set<AlertType> returnSet = new HashSet<AlertType>();
		
		if (alertEnabledFM.getValue()) //alert fm case
			returnSet.add(AlertType.FM);
		
		if (alertEnabledShoutcast.getValue()) //alert shoutcast case
			returnSet.add(AlertType.SHOUTCAST);
		
		if (alertEnabledPlaylist.getValue()) //alert fm case
			returnSet.add(AlertType.PLAYLIST);
		
		return returnSet;
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		StringBuilder sb = new StringBuilder();
		for (EditorError error : errors) {
			if ((error.getEditor().equals(this)) && (!error.isConsumed())) { //erro for us and not consumed 
				sb.append(SafeHtmlUtils.htmlEscape(error.getMessage())); //display error
				error.setConsumed(true); //error consumed
			}
		}
		this.errors.setText(sb.toString());
	}
	
}
