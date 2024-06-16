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
 * example snippet: detect CR in a text or combo control (default selection)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet24 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setText("Snippet 24");
	shell.setLayout (new RowLayout ());
	Combo combo = new Combo (shell, SWT.NONE);
	combo.setItems ("A-1", "B-1", "C-1");
	Text text = new Text (shell, SWT.SINGLE | SWT.BORDER);
	text.setText ("some text");
	combo.addListener (SWT.DefaultSelection, e -> System.out.println (e.widget + " - Default Selection"));
	text.addListener (SWT.DefaultSelection, e -> System.out.println (e.widget + " - Default Selection"));
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
