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
 * Button example snippet: create a tri-state button (toggle three states)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 *
 * @since 3.5
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet315 {
	public static void main(String[] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setText("Snippet 315");
		shell.setLayout(new GridLayout());
		final Button button = new Button (shell, SWT.CHECK);
		button.setLayoutData(new GridData(GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_CENTER));
		button.setText ("Tri-state");
		/* Make the button toggle between three states */
		button.addListener (SWT.Selection, e -> {
			if (button.getSelection()) {
				if (!button.getGrayed()) {
					button.setGrayed(true);
				}
			} else {
				if (button.getGrayed()) {
					button.setGrayed(false);
					button.setSelection (true);
				}
			}
		});
		/* Read the tri-state button (application code) */
		button.addListener (SWT.Selection, e -> {
			if (button.getGrayed()) {
				System.out.println("Grayed");
			} else {
				if (button.getSelection()) {
					System.out.println("Selected");
				} else {
					System.out.println("Not selected");
				}
			}
		});
		shell.setSize(300, 300);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
}
