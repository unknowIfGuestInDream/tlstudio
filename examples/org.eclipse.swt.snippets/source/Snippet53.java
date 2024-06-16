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
 * Table example snippet: remove selected items (using popup menu)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet53 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setText("Snippet 53");
	final Table table = new Table (shell, SWT.BORDER | SWT.MULTI);
	Rectangle clientArea = shell.getClientArea ();
	table.setBounds (clientArea.x, clientArea.y, 200, 200);
	for (int i=0; i<128; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText ("Item " + i);
	}
	Menu menu = new Menu (shell, SWT.POP_UP);
	table.setMenu (menu);
	MenuItem item = new MenuItem (menu, SWT.PUSH);
	item.setText ("Delete Selection");
	item.addListener (SWT.Selection, event -> table.remove (table.getSelectionIndices ()));
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
