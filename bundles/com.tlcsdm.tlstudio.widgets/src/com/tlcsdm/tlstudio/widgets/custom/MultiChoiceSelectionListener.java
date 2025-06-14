package com.tlcsdm.tlstudio.widgets.custom;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

/**
 * Classes which extend this abstract class provide methods that deal with the
 * events that are generated when selection occurs in a MultiChoice control.
 */
public abstract class MultiChoiceSelectionListener<T> implements SelectionListener {
	private final MultiChoice<T> parent;

	public MultiChoiceSelectionListener(final MultiChoice<T> parent) {
		this.parent = parent;
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public final void widgetSelected(final SelectionEvent e) {
		final Button button = (Button) e.widget;
		handle(this.parent, this.parent.getLastModified(), button.getSelection(), this.parent.getPopup());
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public final void widgetDefaultSelected(final SelectionEvent inutile) {
	}

	/**
	 * This method contains the code that is called when the selection has
	 * changed
	 * 
	 * @param parent MultiChoice responsible of the event
	 * @param receiver Object modified
	 * @param selected If <code>true</code>, the check box has been checked
	 * @param popup the popup window that contains all checkboxes
	 */
	public abstract void handle(MultiChoice<T> parent, T receiver, boolean selected, Shell popup);
}