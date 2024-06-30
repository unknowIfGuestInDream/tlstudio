/*******************************************************************************
 * Copyright (c) 2006, 2018 IBM Corporation and others.
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
 *     Brad Reynolds - bug 116920
 *******************************************************************************/
package org.eclipse.jface.examples.databinding.snippets;

import java.util.Date;

import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.sideeffect.ISideEffect;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class Snippet005MenuUpdater {
	public static void main(String[] args) {
		final Display display = new Display();

		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = createShell();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		});

		display.dispose();
	}

	private static Shell createShell() {
		Shell shell = new Shell();

		final WritableList<String> menuItemStrings = new WritableList<>();
		shell.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				System.out.println("adding item");
				menuItemStrings.add(new Date().toString());
				shell.getDisplay().timerExec(5000, this);
			}
		});

		Menu bar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(bar);
		MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
		fileItem.setText("&Test Menu");
		final Menu submenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(submenu);

		IObservableValue<Boolean> visible = WidgetProperties.visible().observe(submenu);

		ISideEffect.create(() -> {
			ObservableTracker.getterCalled(menuItemStrings);
			if (!visible.getValue()) {
				return;
			}

			System.out.println("updating menu");
			MenuItem[] items = submenu.getItems();
			int itemIndex = 0;

			for (String s : menuItemStrings) {
				MenuItem item;
				if (itemIndex < items.length) {
					item = items[itemIndex++];
				} else {
					item = new MenuItem(submenu, SWT.NONE);
				}
				item.setText(s);
			}
			while (itemIndex < items.length) {
				items[itemIndex++].dispose();
			}
		});

		shell.open();
		return shell;
	}

}
