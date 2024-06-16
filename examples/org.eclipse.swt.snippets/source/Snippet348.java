/*******************************************************************************
 * Copyright (c) 2010, 2016 IBM Corporation and others.
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
 * Display snippet: slightly more complex example for Display.getMenuBar() (compared to Snippet347)
 * Shows how to write a more cross-platform multi-window application using Display.getMenuBar().
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 *
 * @since 3.7
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

public class Snippet348 {

	static boolean createdScreenBar = false;

	static void createMenuBar(Shell s) {
		Menu bar = Display.getCurrent().getMenuBar();
		boolean hasAppMenuBar = (bar != null);
		if (bar == null) {
			bar = new Menu(s, SWT.BAR);
		}

		// Populate the menu bar once if this is a screen menu bar.
		// Otherwise, we need to make a new menu bar for each shell.
		if (!createdScreenBar || !hasAppMenuBar) {
			MenuItem item = new MenuItem(bar, SWT.CASCADE);
			item.setText("File");
			Menu menu = new Menu(item);
			item.setMenu(menu);
			menu.addMenuListener(new MenuListener() {

				@Override
				public void menuHidden(MenuEvent e) {
					System.out.println("Menu closed: " + e);
				}

				@Override
				public void menuShown(MenuEvent e) {
					System.out.println("Menu open: " + e);
				}

			});
			MenuItem newWindow = new MenuItem(menu, SWT.PUSH);
			newWindow.setText("New Window");
			newWindow.setAccelerator(SWT.MOD1 | 'N');
			newWindow.addListener(SWT.Selection, event -> {
				Shell s1 = createShell();
				s1.open();
			});
			if (!SWT.getPlatform().equals("cocoa")) {
				MenuItem exit = new MenuItem(menu, SWT.PUSH);
				exit.setText("Exit");
				exit.addListener(SWT.Selection, event -> {
					Display d = Display.getCurrent();
					Shell[] shells = d.getShells();
					for (Shell shell : shells) {
						shell.close();
					}
				});
			}
			if (!hasAppMenuBar) s.setMenuBar(bar);
			createdScreenBar = true;
		}
	}

	static Shell createShell() {
		final Shell shell = new Shell(SWT.SHELL_TRIM);
		shell.setText("Snippet 348");
		createMenuBar(shell);

		shell.addDisposeListener(e -> {
			Display d = Display.getCurrent();
			Menu bar = d.getMenuBar();
			boolean hasAppMenuBar = (bar != null);
			if (!hasAppMenuBar) {
				shell.getMenuBar().dispose();
				Shell[] shells = d.getShells();
				if ((shells.length == 1) && (shells[0] == shell)) {
					if (!d.isDisposed()) d.dispose();
				}
			}
		});

		return shell;
	}

	public static void main(String[] args) {
		final Display display = new Display();

		Shell shell = createShell();
		shell.open();

		while (!display.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
}