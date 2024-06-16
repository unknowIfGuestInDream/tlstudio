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
 * Menu example snippet: enable menu items dynamically (when menu shown)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet73 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setText("Snippet 73");
	final Tree tree = new Tree (shell, SWT.BORDER | SWT.MULTI);
	final Menu menu = new Menu (shell, SWT.POP_UP);
	tree.setMenu (menu);
	for (int i=0; i<12; i++) {
		TreeItem treeItem = new TreeItem (tree, SWT.NONE);
		treeItem.setText ("Item " + i);
		MenuItem menuItem = new MenuItem (menu, SWT.PUSH);
		menuItem.setText (treeItem.getText ());
	}
	menu.addListener (SWT.Show, event -> {
		MenuItem [] menuItems = menu.getItems ();
		TreeItem [] treeItems = tree.getSelection ();
		for (MenuItem menuItem : menuItems) {
			String text = menuItem.getText ();
			int index = 0;
			while (index<treeItems.length) {
				if (treeItems [index].getText ().equals (text)) break;
				index++;
			}
			menuItem.setEnabled (index != treeItems.length);
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
