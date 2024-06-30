/*******************************************************************************
 * Copyright (c) 2008, 2018 Matthew Hall and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Matthew Hall - initial API and implementation (bug 249992)
 *     Matthew Hall - bug 260329
 *     Simon Scholz <simon.scholz@vogella.com> - Bug 434283
 ******************************************************************************/

package org.eclipse.jface.examples.databinding.snippets;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.SelectObservableValue;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.databinding.viewers.typed.ViewerProperties;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

/**
 * Demonstrate usage of SelectObservableValue
 */
public class Snippet024SelectObservableValue {
	public static void main(String[] args) {
		final Display display = Display.getDefault();

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
		shell.setSize(400, 300);
		shell.setLayout(new GridLayout(2, true));
		shell.setText("Snippet024SelectObservableValue");

		final ListViewer listViewer = new ListViewer(shell, SWT.BORDER);
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.getList().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Group group = new Group(shell, SWT.NONE);
		group.setText("Radio Group");
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		group.setLayout(new GridLayout());

		// Data Binding
		Color[] colors = Color.values();

		listViewer.setInput(colors);
		IObservableValue<Color> listViewerSelection = ViewerProperties.singleSelection(Color.class).observe(listViewer);

		SelectObservableValue<Color> radioGroup = new SelectObservableValue<>();
		for (Color color : colors) {
			Button button = new Button(group, SWT.RADIO);
			button.setText(color.toString());
			radioGroup.addOption(color, WidgetProperties.buttonSelection().observe(button));
		}

		DataBindingContext bindingContext = new DataBindingContext();
		bindingContext.bindValue(radioGroup, listViewerSelection);

		shell.open();
		return shell;
	}

	public static class Color {
		public static final Color RED = new Color("Red");
		public static final Color ORANGE = new Color("Orange");
		public static final Color YELLOW = new Color("Yellow");
		public static final Color GREEN = new Color("Green");
		public static final Color BLUE = new Color("Blue");
		public static final Color INDIGO = new Color("Indigo");
		public static final Color VIOLET = new Color("Violet");

		private final String name;

		private Color(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

		public static Color[] values() {
			return new Color[] { RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET };
		}
	}
}
