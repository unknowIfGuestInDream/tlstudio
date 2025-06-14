package com.tlcsdm.tlstudio.widgets.custom;

import java.io.Serial;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Event;

/**
 * Instances of this class are sent as a result of widgets being closed.
 * <p>
 * Note: The fields that are filled in depend on the widget.
 * </p>
 *
 * @see ChipsCloseListener
 */
public class ChipsCloseEvent extends TypedEvent {

	@Serial
	private static final long serialVersionUID = 5028668219476966409L;

	/**
	 * A flag indicating whether the operation should be allowed. Setting this field
	 * to <code>false</code> will cancel the operation, depending on the widget.
	 */
	public boolean doit;

	/**
	 * Constructs a new instance of this class based on the information in the given
	 * untyped event.
	 *
	 * @param e the untyped event containing the information
	 */
	public ChipsCloseEvent(final Event e) {
		super(e);
	}
}
