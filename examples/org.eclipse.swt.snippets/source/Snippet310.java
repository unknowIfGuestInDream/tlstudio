/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
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
package org.eclipse.swt.snippets;

/*
 * Spinner example snippet: validate input in a spinner widget
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 *
 * @since 3.5
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet310 {
	public static void main (String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Snippet 310");
		shell.setLayout(new GridLayout());
		final Spinner spinner = new Spinner (shell, SWT.BORDER);
		spinner.setValues(0, -100, 100, 0, 1, 10);
		spinner.setLayoutData(new GridData(200, SWT.DEFAULT));
		final ToolTip toolTip = new ToolTip(shell, SWT.BALLOON | SWT.ICON_WARNING);
		spinner.addModifyListener(e -> {
			String string = spinner.getText();
			String message = null;
			try {
				int value = Integer.parseInt(string);
				int maximum = spinner.getMaximum();
				int minimum = spinner.getMinimum();
				if (value > maximum) {
					message = "Current input is greater than the maximum limit ("+maximum+")";
				} else if (value < minimum) {
					message = "Current input is less than the minimum limit ("+minimum+")";
				}
			} catch (Exception ex) {
				message = "Current input is not numeric";
			}
			if (message != null) {
				spinner.setForeground(display.getSystemColor(SWT.COLOR_RED));
				Rectangle rect = spinner.getBounds();
				GC gc = new GC(spinner);
				Point pt = gc.textExtent(string);
				gc.dispose();
				toolTip.setLocation(display.map(shell, null, rect.x + pt.x, rect.y + rect.height));
				toolTip.setMessage(message);
				toolTip.setVisible(true);
			} else {
				toolTip.setVisible(false);
				spinner.setForeground(null);
			}
		});
		shell.setSize(300, 100);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
