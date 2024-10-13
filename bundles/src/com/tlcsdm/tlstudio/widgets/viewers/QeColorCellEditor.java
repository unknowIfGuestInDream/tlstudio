package com.tlcsdm.tlstudio.widgets.viewers;

import java.util.Objects;

import org.eclipse.jface.viewers.ColorCellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Override ${org.eclipse.jface.viewers.ColorCellEditor}. Now when you click on
 * the cell, a dialog pops up and the position is displayed in the cell.
 */
public class QeColorCellEditor extends ColorCellEditor {

	private Composite composite;
	private RGB value;

	public QeColorCellEditor(Composite parent) {
		super(parent);
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		final Display display = cellEditorWindow.getDisplay();
		final Shell centerShell = new Shell(cellEditorWindow.getShell(), SWT.NO_TRIM);
		centerShell.setLocation(display.getCursorLocation());
		ColorDialog dialog = new ColorDialog(centerShell, SWT.NONE);
		if (value != null) {
			dialog.setRGB(value);
		}
		dialog.open();
		return dialog.getRGB();
	}

	@Override
	protected Control createControl(Composite parent) {
		this.composite = parent;
		return null;
	}

	@Override
	public void activate() {
		RGB res = (RGB) openDialogBox(this.composite);
		if (res != null && !Objects.equals(res, doGetValue())) {
			doSetValue(res);
			fireApplyEditorValue();
		}
		deactivate();
	}

	@Override
	protected Object doGetValue() {
		if (value == null) {
			value = new RGB(0, 0, 0);
		}
		return value;
	}

	@Override
	protected void doSetFocus() {
		// Do nothing
	}

	@Override
	protected void doSetValue(Object value) {
		this.value = (RGB) value;
	}

	@Override
	public void activate(ColumnViewerEditorActivationEvent activationEvent) {
		if (activationEvent.eventType != ColumnViewerEditorActivationEvent.TRAVERSAL) {
			super.activate(activationEvent);
		}
	}

}
