/*******************************************************************************
 * Copyright (c) 2008, 2018 IBM Corporation and others.
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
 *     Simon Scholz <simon.scholz@vogella.com> - Bug 434283
 ******************************************************************************/

package org.eclipse.jface.examples.databinding.snippets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.ComputedList;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class Snippet022ComputedListCombo {
	private static WritableList<Thing> model;

	public static void main(String[] args) {
		final Display display = new Display();

		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = new Snippet022ComputedListCombo().createShell();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		});

		display.dispose();
	}

	protected void createModel() {
		model = new WritableList<>();
		model.add(new Thing("Alice", true, false));
		model.add(new Thing("Beth", true, false));
		model.add(new Thing("Cathy", true, false));
		model.add(new Thing("Arthur", false, true));
		model.add(new Thing("Bob", false, true));
		model.add(new Thing("Curtis", false, true));
		model.add(new Thing("Snail", true, true));
		model.add(new Thing("Nail", false, false));
	}

	protected Shell createShell() {
		createModel();

		Shell shell = new Shell();
		shell.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(shell, SWT.NONE);
		Group group = new Group(composite, SWT.NONE);
		group.setText("Filter");
		Button male = new Button(group, SWT.CHECK);
		male.setText("Male");
		Button female = new Button(group, SWT.CHECK);
		female.setText("Female");
		final IObservableValue<Boolean> femaleObservable = WidgetProperties.buttonSelection().observe(female);
		final IObservableValue<Boolean> maleObservable = WidgetProperties.buttonSelection().observe(male);
		Combo combo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridDataFactory.defaultsFor(combo).align(SWT.BEGINNING, SWT.BEGINNING).applyTo(combo);
		ComboViewer viewer = new ComboViewer(combo);
		viewer.setContentProvider(new ObservableListContentProvider<>());

		// We should really have an out-of-the box filtered list...
		IObservableList<Thing> filteredList = new ComputedList<>() {
			@Override
			protected List<Thing> calculate() {
				List<Thing> result = new ArrayList<>();
				for (Thing thing : model) {
					if (femaleObservable.getValue() && !thing.female) {
						continue;
					}
					if (maleObservable.getValue() && !thing.male) {
						continue;
					}
					result.add(thing);
				}
				return result;
			}
		};
		viewer.setInput(filteredList);
		GridLayoutFactory.swtDefaults().applyTo(group);
		GridLayoutFactory.swtDefaults().applyTo(composite);

		shell.pack();
		shell.open();

		return shell;
	}

	static class Thing {
		String name;
		boolean male;
		boolean female;

		public Thing(String name, boolean female, boolean male) {
			this.name = name;
			this.female = female;
			this.male = male;
		}

		@Override
		public String toString() {
			return name;
		}
	}
}
