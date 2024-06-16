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
 * Text example snippet: Display different styles of the Search Text control
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 *
 * @since 3.5
 */

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet309 {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell();
		shell.setText("Snippet 309");
		shell.setLayout(new GridLayout(1, true));
		Text text;

		Listener listener = event -> {
			Text t = (Text) event.widget;
			String msg = t.getMessage();
			if (event.detail == SWT.ICON_CANCEL) {
				System.out.println("Cancel on " + msg);
			} else if (event.detail == SWT.ICON_SEARCH) {
				System.out.println("ICON on " + msg);
			} else {
				System.out.println("Default selection on " + msg);
			}
		};

		text = new Text(shell, SWT.SEARCH);
		text.setMessage("search");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addListener(SWT.DefaultSelection, listener);

		text = new Text(shell, SWT.SEARCH | SWT.ICON_CANCEL);
		text.setMessage("search + cancel");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addListener(SWT.DefaultSelection, listener);

		text = new Text(shell, SWT.SEARCH | SWT.ICON_SEARCH);
		text.setMessage("search + icon");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addListener(SWT.DefaultSelection, listener);

		text = new Text(shell, SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
		text.setMessage("search + cancel + icon");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addListener(SWT.DefaultSelection, listener);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

	}
}
