/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
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
 * Table example snippet: compute the number of visible rows in a table
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet253 {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Snippet 253");
		FillLayout layout = new FillLayout (SWT.VERTICAL);
		shell.setLayout (layout);
		final Table table = new Table (shell, SWT.NONE);
		for (int i=0; i<32; i++) {
			TableItem item = new TableItem (table, SWT.NONE);
			item.setText ("Item " + (i+1) + " is quite long");
		}
		final Button button = new Button (shell, SWT.PUSH);
		button.setText ("Visible Items []");
		button.addListener (SWT.Selection, e -> {
			Rectangle rect = table.getClientArea ();
			int itemHeight = table.getItemHeight ();
			int headerHeight = table.getHeaderHeight ();
			int visibleCount = (rect.height - headerHeight + itemHeight - 1) / itemHeight;
			button.setText ("Visible Items [" + visibleCount + "]");
		});
		shell.setSize(200, 250);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
}
