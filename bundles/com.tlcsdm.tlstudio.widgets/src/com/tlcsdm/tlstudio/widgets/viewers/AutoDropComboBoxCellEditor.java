package com.tlcsdm.tlstudio.widgets.viewers;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class AutoDropComboBoxCellEditor extends ComboBoxCellEditor {
	public AutoDropComboBoxCellEditor(Composite parent, String[] items, int style) {
		super(parent, items, style);
		setActivationStyle(DROP_DOWN_ON_MOUSE_ACTIVATION);
	}

	@Override
	protected Control createControl(Composite parent) {
		final Control control = super.createControl(parent);
		((CCombo) control).addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// if the list is not visible, assume the user is done
				if (!((CCombo) control).getListVisible()) {
					focusLost();
				}
			}
		});
		return control;
	}
}
