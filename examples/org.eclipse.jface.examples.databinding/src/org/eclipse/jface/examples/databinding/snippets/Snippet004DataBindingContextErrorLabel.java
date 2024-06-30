/*******************************************************************************
 * Copyright (c) 2006, 2018 Brad Reynolds and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Brad Reynolds - initial API and implementation
 *     Brad Reynolds - bug 116920, 159768
 *     Matthew Hall - bug 260329
 *     Hendrik Still <hendrik.still@vogella.com> - Bug 434283
 *     Simon Scholz <simon.scholz@vogella.com> - Bug 489106
 ******************************************************************************/

package org.eclipse.jface.examples.databinding.snippets;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Snippet that displays how to bind the validation error of the
 * {@link DataBindingContext} to a label. http://www.eclipse.org
 */
public class Snippet004DataBindingContextErrorLabel {
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
		shell.setText("Data Binding Snippet 004");
		shell.setLayout(new GridLayout(2, false));

		new Label(shell, SWT.NONE).setText("Enter '5' to be valid:");

		Text text = new Text(shell, SWT.BORDER);
		WritableValue<String> value = WritableValue.withValueType(String.class);
		new Label(shell, SWT.NONE).setText("Error:");

		Label errorLabel = new Label(shell, SWT.BORDER);
		errorLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		GridDataFactory.swtDefaults().hint(200, SWT.DEFAULT).applyTo(errorLabel);

		DataBindingContext bindingContext = new DataBindingContext();

		// Bind the text to the value
		bindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(text), value,
				new UpdateValueStrategy<String, String>().setAfterConvertValidator(new FiveValidator()), null);

		// Bind the error label to the validation error on the bindingContext
		bindingContext.bindValue(WidgetProperties.text().observe(errorLabel),
				new AggregateValidationStatus(bindingContext.getBindings(), AggregateValidationStatus.MAX_SEVERITY));

		shell.pack();
		shell.open();
		return shell;
	}

	/**
	 * Validator that returns validation errors for any value other than 5.
	 */
	private static class FiveValidator implements IValidator<String> {
		@Override
		public IStatus validate(String value) {
			return ("5".equals(value)) ? Status.OK_STATUS
					: ValidationStatus.error("the value was '" + value + "', not '5'");
		}
	}
}
