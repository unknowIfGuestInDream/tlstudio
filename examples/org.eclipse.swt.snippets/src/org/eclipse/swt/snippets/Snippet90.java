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
 * Tree example snippet: detect mouse down in a tree item
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet90 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setText("Snippet 90");
	final Tree tree = new Tree (shell, SWT.BORDER | SWT.MULTI);
	for (int i=0; i<12; i++) {
		TreeItem treeItem = new TreeItem (tree, SWT.NONE);
		treeItem.setText ("Item " + i);
	}
	tree.addListener (SWT.MouseDown, event -> {
		Point point = new Point (event.x, event.y);
		TreeItem item = tree.getItem (point);
		if (item != null) {
			System.out.println ("Mouse down: " + item);
		}
	});
	Rectangle clientArea = shell.getClientArea ();
	tree.setBounds (clientArea.x, clientArea.y, 200, 200);
	shell.setSize (300, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

}
