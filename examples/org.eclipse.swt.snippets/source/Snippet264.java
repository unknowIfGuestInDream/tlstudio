/*******************************************************************************
 * Copyright (c) 2007, 2016 IBM Corporation and others.
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
 *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 502845
 *******************************************************************************/
package org.eclipse.swt.snippets;

import static org.eclipse.swt.events.SelectionListener.*;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.widgets.*;

/*
 * Open an OLE Media Player.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 *
 * @since 3.3
 */
public class Snippet264 {
	static OleClientSite clientSite;

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Media Player Example");
		shell.setLayout(new FillLayout());
		try {
			OleFrame frame = new OleFrame(shell, SWT.NONE);
			clientSite  = new OleClientSite(frame, SWT.NONE, "MPlayer");
			addFileMenu(frame);
		} catch (SWTError e) {
			System.out.println("Unable to open activeX control");
			display.dispose();
			return;
		}
		shell.setSize(800, 600);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	static void addFileMenu(OleFrame frame) {
		final Shell shell = frame.getShell();
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		MenuItem fileMenu = new MenuItem(menuBar, SWT.CASCADE);
		fileMenu.setText("&File");
		Menu menuFile = new Menu(fileMenu);
		fileMenu.setMenu(menuFile);
		MenuItem menuFileControl = new MenuItem(menuFile, SWT.CASCADE);
		menuFileControl.setText("Exit");
		menuFileControl.addSelectionListener(widgetSelectedAdapter(e-> shell.dispose()));
		frame.setFileMenus(new MenuItem[] { fileMenu });
	}
}