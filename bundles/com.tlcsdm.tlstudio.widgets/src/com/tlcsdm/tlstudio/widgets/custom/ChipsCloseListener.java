package com.tlcsdm.tlstudio.widgets.custom;

import java.util.EventListener;

/**
 * Classes which implement this interface provide methods that deal with the
 * events that are generated when a Chips widget is closed.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a control using the <code>addCloseListener</code> method and
 * removed using the <code>removeCloseListener</code> method. When selection
 * occurs in a control the appropriate method will be invoked.
 * </p>
 *
 * @see ChipsCloseEvent
 */
@FunctionalInterface
public interface ChipsCloseListener extends EventListener {
	/**
	 * Sent when a Chips widget is closed.
	 *
	 * @param e an event containing information about the chips that is closed
	 */
	void onClose(ChipsCloseEvent event);
}
