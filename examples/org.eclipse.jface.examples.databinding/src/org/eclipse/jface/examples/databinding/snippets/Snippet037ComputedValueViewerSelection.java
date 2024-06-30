/*******************************************************************************
 * Copyright (c) 2016 vogella GmbH and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Simon Scholz <simon.scholz@vogella.com> - initial API and implementation
 ******************************************************************************/

package org.eclipse.jface.examples.databinding.snippets;

import java.util.List;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.sideeffect.ISideEffect;
import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.viewers.IViewerObservableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.typed.ViewerProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Snippet037ComputedValueViewerSelection {
	public static void main(String[] args) {
		final Display display = new Display();

		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = new View().createShell();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		});

		display.dispose();
	}

	static class View {

		private Button deleteSelectionButton;
		private TableViewer tableViewer;

		public Shell createShell() {
			Shell shell = new Shell();
			GridLayoutFactory.swtDefaults().numColumns(2).applyTo(shell);

			tableViewer = new TableViewer(shell);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(tableViewer.getControl());
			tableViewer.setContentProvider(new ObservableListContentProvider<>());

			IObservableList<String> input = new WritableList<>();
			input.add("Stefan Xenos");
			input.add("Lars Vogel");
			input.add("Dirk Fauth");
			input.add("Elena Laskavaia");
			input.add("Simon Scholz");

			tableViewer.setInput(input);

			deleteSelectionButton = new Button(shell, SWT.PUSH);
			deleteSelectionButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.TOP, false, false));
			deleteSelectionButton.setText("X");
			deleteSelectionButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// Since an IObservableList and a ObservableListContentProvider is used the
					// input list can simply be modified and automatically gets reflected in the
					// viewer
					List<String> list = tableViewer.getStructuredSelection().toList();
					input.removeAll(list);
				}
			});

			bindData();

			shell.pack();
			shell.open();

			return shell;
		}

		private void bindData() {
			// Observe the selection list of a viewer
			IViewerObservableList<String> viewerSelectionObservable = ViewerProperties.multipleSelection(String.class)
					.observe(tableViewer);
			// Track whether the selection list is empty or not
			// (viewerSelectionObservable.isEmpty() is a tracked getter!)
			IObservableValue<Boolean> hasSelectionObservable = ComputedValue
					.create(() -> !viewerSelectionObservable.isEmpty());

			// Once the selection state(Boolean) changes the ISideEffect will update the
			// button
			ISideEffect deleteButtonEnablementSideEffect = ISideEffect.create(hasSelectionObservable::getValue,
					deleteSelectionButton::setEnabled);

			deleteSelectionButton.addDisposeListener(e -> deleteButtonEnablementSideEffect.dispose());
		}
	}

}
