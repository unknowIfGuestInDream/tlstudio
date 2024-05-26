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
 * Sash example snippet: create a sash (allow it to be moved)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet54 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setText("Snippet 54");
	shell.setSize(400, 300);
	final Sash sash = new Sash (shell, SWT.BORDER | SWT.VERTICAL);
	Rectangle clientArea = shell.getClientArea ();
	sash.setBounds (180, clientArea.y, 32, clientArea.height);
	sash.addListener (SWT.Selection, e -> sash.setBounds (e.x, e.y, e.width, e.height));
	shell.open ();
	sash.setFocus ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
