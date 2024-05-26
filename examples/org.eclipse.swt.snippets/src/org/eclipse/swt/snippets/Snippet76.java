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
 * TabFolder example snippet: create a tab folder (six pages)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;

public class Snippet76 {

public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setText("Snippet 76");
	final TabFolder tabFolder = new TabFolder (shell, SWT.BORDER);
	Rectangle clientArea = shell.getClientArea ();
	tabFolder.setLocation (clientArea.x, clientArea.y);
	for (int i=0; i<6; i++) {
		TabItem item = new TabItem (tabFolder, SWT.NONE);
		item.setText ("TabItem " + i);
		Button button = new Button (tabFolder, SWT.PUSH);
		button.setText ("Page " + i);
		item.setControl (button);
	}
	tabFolder.pack ();
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
