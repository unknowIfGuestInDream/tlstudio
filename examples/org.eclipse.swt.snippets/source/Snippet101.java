/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
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
 * Table example snippet: insert a table item (at an index)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet101 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setText("Snippet 101");
	Table table = new Table (shell, SWT.BORDER | SWT.MULTI);
	Rectangle clientArea = shell.getClientArea ();
	table.setBounds (clientArea.x, clientArea.y, 200, 200);
	for (int i=0; i<12; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText ("Item " + i);
	}
	TableItem item = new TableItem (table, SWT.NONE, 1);
	item.setText ("*** New Item " + table.indexOf (item) + " ***");
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

}

