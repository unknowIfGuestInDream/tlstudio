package com.tlcsdm.tlstudio.widgets.viewers;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Used with AbstractCheckBoxCellLabelProvider
 */
public class QeCheckBoxCellEditor extends CellEditor {

	boolean value = false;

	public QeCheckBoxCellEditor(Composite parent) {
		super(parent);
	}

	@Override
	protected Control createControl(Composite parent) {
		return null;
	}

	@Override
	public void activate() {
		toggle();
	}

	protected void toggle() {
		value = !value;
		markDirty();
		fireApplyEditorValue();
	}

	@Override
	protected Object doGetValue() {
		return value ? Boolean.TRUE : Boolean.FALSE;
	}

	/*
	 * (non-Javadoc) Method declared on CellEditor.
	 */
	@Override
	protected void doSetFocus() {
		// Ignore
	}

	@Override
	protected void doSetValue(Object val) {
		this.value = Boolean.TRUE.equals(val);
	}

	@Override
	public void activate(ColumnViewerEditorActivationEvent activationEvent) {
		if (activationEvent.eventType != ColumnViewerEditorActivationEvent.TRAVERSAL) {
			super.activate(activationEvent);
		}
	}

}
