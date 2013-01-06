/**
 * 
 */
package com.stationmillenium.coverart.web.gwt.admin.client.utils.cells;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;

/**
 * Cell table for proxy displaying
 * @author vincent
 * @param <C> the type of data to display
 *
 */
public class ProxyAbstractSafeHtmlCell<C> extends AbstractSafeHtmlCell<C> {

	  /**
	   * Construct an ProxyAbstractSafeHtmlCell using a given {@link SafeHtmlRenderer}
	   * that will consume a given set of events.
	   * 
	   * @param renderer a SafeHtmlRenderer
	   * @param consumedEvents a varargs list of event names
	   */
	public ProxyAbstractSafeHtmlCell(SafeHtmlRenderer<C> renderer, String... consumedEvents) {
		super(renderer, consumedEvents);
	}

	@Override
	protected void render(Context context, SafeHtml data, SafeHtmlBuilder sb) {
		 if (data != null)
			 sb.append(data);
	}
	
}
