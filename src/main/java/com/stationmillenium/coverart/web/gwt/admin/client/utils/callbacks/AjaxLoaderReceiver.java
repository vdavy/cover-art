/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.utils.callbacks;

import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.utils.widgets.AjaxLoaderWidget;

/**
 * Async callback with ajax waiting panel
 * @author vincent
 * @param <T> the type of the async callback
 *
 */
public abstract class AjaxLoaderReceiver<T> extends Receiver<T>{

	private static final Logger LOGGER = Logger.getLogger(AjaxLoaderReceiver.class.getName());
	
	//widget
	private AjaxLoaderWidget widget;
	
	/**
	 * Create a new {@link AjaxLoaderReceiver}
	 * @param clientFactory the client factory
	 */
	public AjaxLoaderReceiver(ClientFactory clientFactory) {
		LOGGER.fine("Init new callback and show widget up");
		widget = clientFactory.getAjaxLoaderWidget();
		widget.showPanel();
	}
	
	@Override
	public void onFailure(ServerFailure error) {
		LOGGER.fine("Received onFailure callback - hiding widget");
		widget.hidePanel();
		onCustomFailure(error);
	}
	
	@Override
	public void onSuccess(T result) {
		LOGGER.fine("Received onSuccess callback - hiding widget");
		widget.hidePanel();
		onCustomSuccess(result);
	}
	
	@Override
	public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
		LOGGER.fine("Received onSuccess callback - hiding widget");
		widget.hidePanel();
		onCustomConstraintViolation(violations);
	}
	
	/**
	   * Receives general failure notifications. The default implementation looks at
	   * {@link ServerFailure#isFatal()}, and throws a runtime exception with the
	   * failure object's error message if it is true.
	   * 
	   * @param error a {@link ServerFailure} instance
	   */
	public abstract void onCustomFailure(ServerFailure error);

	/**
	   * Called when a Request has been successfully executed on the server.
	   * 
	   * @param response a response of type V
	   */
	public abstract void onCustomSuccess(T result);
	
	/**
	 * Default implementation for constraint violation
	 * To be overriden
	 * @param violations the set of violation
	 */
	public void onCustomConstraintViolation(Set<ConstraintViolation<?>> violations) {
		super.onConstraintViolation(violations);
	}
	
}
