/*******************************************************************************
 * Copyright (c) 2005, 2014 IBM Corporation and others.
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
package org.eclipse.jface.examples.databinding.mask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EditMaskTest {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);

		Text text = new Text(shell, SWT.BORDER);
		text.setText("XXXXXXXXXXXXX");// Put some X's in there to pad out the field's default size

		Text text2 = new Text(shell, SWT.BORDER);
		text2.setText("630XXXXXXXXXX");

		shell.setLayout(new RowLayout(SWT.VERTICAL));
		shell.setSize(800, 600);

		new EditMask(text).setMask("(###) ###-####");
		new EditMask(text2).setMask("(###) ###-####");

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

}
