/*******************************************************************************
 * Copyright (c) 2006, 2019 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.views.properties.tabbed.article.views;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.ui.PlatformUI;

/**
 * a new Font dialog cell editor.
 *
 * @author Anthony Hunter
 */
public class FontDialogCellEditor
	extends DialogCellEditor {

	/**
	 * Creates a new Font dialog cell editor parented under the given control.
	 * The cell editor value is <code>null</code> initially, and has no
	 * validator.
	 *
	 * @param parent
	 *            the parent control
	 */
	protected FontDialogCellEditor(Composite parent) {
		super(parent);
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		FontDialog ftDialog = new FontDialog(PlatformUI.getWorkbench()
			.getActiveWorkbenchWindow().getShell());

		String value = (String) getValue();

		if ((value != null) && (value.length() > 0)) {
			ftDialog.setFontList(new FontData[] {new FontData(value)});
		}
		FontData fData = ftDialog.open();

		if (fData != null) {
			value = fData.toString();
		}
		return value;
	}

}
