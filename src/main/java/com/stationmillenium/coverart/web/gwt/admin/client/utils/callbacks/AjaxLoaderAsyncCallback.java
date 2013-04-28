/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.utils.callbacks;

import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.stationmillenium.coverart.web.gwt.admin.client.clientfactory.ClientFactory;
import com.stationmillenium.coverart.web.gwt.admin.client.utils.widgets.AjaxLoaderWidget;

/**
 * Async callback with ajax waiting panel
 * @author vincent
 * @param <T> the type of the async callback
 *
 */
public abstract class AjaxLoaderAsyncCallback<T> implements AsyncCallback<T> {

	private static final Logger LOGGER = Logger.getLogger(AjaxLoaderAsyncCallback.class.getName());
	
	//widget
	private AjaxLoaderWidget widget;
	
	/**
	 * Create a new {@link AjaxLoaderAsyncCallback}
	 * @param clientFactory the client factory
	 */
	public AjaxLoaderAsyncCallback(ClientFactory clientFactory) {
		LOGGER.fine("Init new callback and show widget up");
		widget = clientFactory.getAjaxLoaderWidget();
		widget.showPanel();
	}
	
	@Override
	public void onFailure(Throwable caught) {
		LOGGER.fine("Received onFailure callback - hiding widget");
		widget.hidePanel();
		onCustomFailure(caught);
	}
	
	@Override
	public void onSuccess(T result) {
		LOGGER.fine("Received onSuccess callback - hiding widget");
		widget.hidePanel();
		onCustomSuccess(result);
	}
	
	/**
	   * Called when an asynchronous call fails to complete normally.
	   * {@link IncompatibleRemoteServiceException}s, {@link InvocationException}s,
	   * or checked exceptions thrown by the service method are examples of the type
	   * of failures that can be passed to this method.
	   * 
	   * <p>
	   * If <code>caught</code> is an instance of an
	   * {@link IncompatibleRemoteServiceException} the application should try to
	   * get into a state where a browser refresh can be safely done.
	   * </p>
	   * 
	   * @param caught failure encountered while executing a remote procedure call
	   */
	public abstract void onCustomFailure(Throwable caught);

	  /**
	   * Called when an asynchronous call completes successfully.
	   * 
	   * @param result the return value of the remote produced call
	   */
	public abstract void onCustomSuccess(T result);
	
}
