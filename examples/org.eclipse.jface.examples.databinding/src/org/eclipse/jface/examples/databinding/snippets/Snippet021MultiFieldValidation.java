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
 *     Matthew Hall - initial API and implementation (bug 218269)
 *     Matthew Hall - bug 260329
 *     Simon Scholz <simon.scholz@vogella.com> - Bug 442278, 434283
 ******************************************************************************/

package org.eclipse.jface.examples.databinding.snippets;

import java.util.ArrayList;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.validation.MultiValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

public class Snippet021MultiFieldValidation extends WizardPage {

	private List list_1;
	private List list;
	private Button addAddendButton;
	private Button removeAddendButton;
	private Text sumModelValue;
	private Text field2ModelValue;
	private Text field1ModelValue;
	private Text sumTarget;
	private Text field2Target;
	private Text field1Target;
	private ListViewer addendsTarget;
	private ListViewer addendsModelValue;

	public static void main(String[] args) {
		final Display display = new Display();

		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			IWizard wizard = new MultiFieldValidationWizard();
			WizardDialog dialog = new WizardDialog(null, wizard);
			dialog.open();
		});

		display.dispose();
	}

	/**
	 * Create the wizard.
	 */
	public Snippet021MultiFieldValidation() {
		super("snippet021");
		setTitle("Snippet 021 - Multi-field Validators");
		setDescription("Enter values which satisfy the cross-field constraints");
	}

	/**
	 * Create contents of the wizard.
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		setControl(container);

		final Group bothEvenOrGroup = new Group(container, SWT.NONE);
		bothEvenOrGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		bothEvenOrGroup.setText("Numbers must be both even or both odd");
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 3;
		bothEvenOrGroup.setLayout(gridLayout_1);
		new Label(bothEvenOrGroup, SWT.NONE);

		final Label targetLabel = new Label(bothEvenOrGroup, SWT.NONE);
		targetLabel.setText("Target");

		final Label modelLabel = new Label(bothEvenOrGroup, SWT.NONE);
		modelLabel.setText("Model");

		final Label field1Label = new Label(bothEvenOrGroup, SWT.NONE);
		field1Label.setText("Field 1");

		field1Target = new Text(bothEvenOrGroup, SWT.BORDER);
		final GridData gd_field1Target = new GridData(SWT.FILL, SWT.CENTER, true, false);
		field1Target.setLayoutData(gd_field1Target);

		field1ModelValue = new Text(bothEvenOrGroup, SWT.READ_ONLY | SWT.BORDER);
		final GridData gd_field1ModelValue = new GridData(SWT.FILL, SWT.CENTER, true, false);
		field1ModelValue.setLayoutData(gd_field1ModelValue);

		final Label field2Label = new Label(bothEvenOrGroup, SWT.NONE);
		field2Label.setText("Field 2");

		field2Target = new Text(bothEvenOrGroup, SWT.BORDER);
		final GridData gd_field2Target = new GridData(SWT.FILL, SWT.CENTER, true, false);
		field2Target.setLayoutData(gd_field2Target);

		field2ModelValue = new Text(bothEvenOrGroup, SWT.READ_ONLY | SWT.BORDER);
		final GridData gd_field2ModelValue = new GridData(SWT.FILL, SWT.CENTER, true, false);
		field2ModelValue.setLayoutData(gd_field2ModelValue);

		final Group sumOfAllGroup = new Group(container, SWT.NONE);
		sumOfAllGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		sumOfAllGroup.setText("Addends must add up to sum");
		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.numColumns = 3;
		sumOfAllGroup.setLayout(gridLayout_2);
		new Label(sumOfAllGroup, SWT.NONE);

		final Label targetLabel_1 = new Label(sumOfAllGroup, SWT.NONE);
		targetLabel_1.setText("Target");

		final Label modelLabel_1 = new Label(sumOfAllGroup, SWT.NONE);
		modelLabel_1.setText("Model");

		final Label expectedSumLabel = new Label(sumOfAllGroup, SWT.NONE);
		expectedSumLabel.setText("Sum");

		sumTarget = new Text(sumOfAllGroup, SWT.BORDER);
		final GridData gd_sumTarget = new GridData(SWT.FILL, SWT.CENTER, true, false);
		sumTarget.setLayoutData(gd_sumTarget);

		sumModelValue = new Text(sumOfAllGroup, SWT.READ_ONLY | SWT.BORDER);
		final GridData gd_sumModelValue = new GridData(SWT.FILL, SWT.CENTER, true, false);
		sumModelValue.setLayoutData(gd_sumModelValue);

		final Label addendsLabel = new Label(sumOfAllGroup, SWT.NONE);
		addendsLabel.setText("Addends");

		addendsTarget = new ListViewer(sumOfAllGroup, SWT.V_SCROLL | SWT.BORDER);
		list_1 = addendsTarget.getList();
		list_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2));

		addendsModelValue = new ListViewer(sumOfAllGroup, SWT.V_SCROLL | SWT.BORDER);
		list = addendsModelValue.getList();
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2));

		final Composite composite = new Composite(sumOfAllGroup, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		final GridLayout gridLayout_3 = new GridLayout();
		gridLayout_3.marginWidth = 0;
		gridLayout_3.marginHeight = 0;
		composite.setLayout(gridLayout_3);

		addAddendButton = new Button(composite, SWT.NONE);
		addAddendButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		addAddendButton.setText("Add");

		removeAddendButton = new Button(composite, SWT.NONE);
		removeAddendButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		removeAddendButton.setText("Remove");

		bindUI();
	}

	private void bindUI() {
		DataBindingContext bindingContext = new DataBindingContext();

		bindEvensAndOddsGroup(bindingContext);
		bindSumAndAddendsGroup(bindingContext);

		WizardPageSupport.create(this, bindingContext);
	}

	private void bindEvensAndOddsGroup(DataBindingContext bindingContext) {
		IObservableValue<String> targetField1 = WidgetProperties.text(SWT.Modify).observe(field1Target);
		final IObservableValue<Integer> middleField1 = new WritableValue<>(0, int.class);
		bindingContext.bindValue(targetField1, middleField1);

		IObservableValue<String> targetField2 = WidgetProperties.text(SWT.Modify).observe(field2Target);
		final IObservableValue<Integer> middleField2 = new WritableValue<>(0, int.class);
		bindingContext.bindValue(targetField2, middleField2);

		MultiValidator validator = new MultiValidator() {
			@Override
			protected IStatus validate() {
				Integer field1 = middleField1.getValue();
				Integer field2 = middleField2.getValue();
				if (field1 == null || field2 == null) {
					return ValidationStatus.error("The fields must all be non-empty");
				}

				if (Math.abs(field1) % 2 != Math.abs(field2) % 2) {
					return ValidationStatus.error("Fields 1 and 2 must be both even or both odd");
				}
				return null;
			}
		};
		bindingContext.addValidationStatusProvider(validator);

		IObservableValue<Integer> modelField1 = new WritableValue<>(1, int.class);
		IObservableValue<Integer> modelField2 = new WritableValue<>(4, int.class);
		bindingContext.bindValue(validator.observeValidatedValue(middleField1), modelField1);
		bindingContext.bindValue(validator.observeValidatedValue(middleField2), modelField2);

		bindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(field1ModelValue), modelField1);
		bindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(field2ModelValue), modelField2);
	}

	private void bindSumAndAddendsGroup(DataBindingContext bindingContext) {
		IObservableValue<String> targetSum = WidgetProperties.text(SWT.Modify).observe(sumTarget);
		final IObservableValue<Integer> middleSum = new WritableValue<>(0, int.class);
		bindingContext.bindValue(targetSum, middleSum);

		final IObservableList<Integer> targetAddends = new WritableList<>(new ArrayList<>(), int.class);
		addendsTarget.setContentProvider(new ObservableListContentProvider<>());
		addendsTarget.setInput(targetAddends);

		addAddendButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				InputDialog dialog = new InputDialog(getShell(), "Input addend", "Enter an integer addend", "0",
						newText -> {
							try {
								Integer.valueOf(newText);
								return null;
							} catch (NumberFormatException e1) {
								return "Enter a number between " + Integer.MIN_VALUE + " and " + Integer.MAX_VALUE;
							}
						});
				if (dialog.open() == Window.OK) {
					targetAddends.add(Integer.valueOf(dialog.getValue()));
				}
			}
		});

		removeAddendButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = addendsTarget.getStructuredSelection();
				if (!selection.isEmpty()) {
					targetAddends.remove(selection.getFirstElement());
				}
			}
		});

		IObservableValue<Integer> modelSum = new WritableValue<>(5, int.class);
		bindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(sumModelValue), modelSum);

		IObservableList<Integer> modelAddends = new WritableList<>(new ArrayList<>(), int.class);

		MultiValidator validator = new MultiValidator() {
			@Override
			protected IStatus validate() {
				int sum = middleSum.getValue();
				int actualSum = 0;
				for (int i : targetAddends) {
					actualSum += i;
				}
				if (sum != actualSum) {
					return ValidationStatus.error("Sum of addends is " + actualSum + ", expecting " + sum);
				}
				return ValidationStatus.ok();
			}
		};
		bindingContext.addValidationStatusProvider(validator);

		addendsModelValue.setContentProvider(new ObservableListContentProvider<>());
		addendsModelValue.setInput(modelAddends);

		bindingContext.bindValue(validator.observeValidatedValue(middleSum), modelSum);
		bindingContext.bindList(validator.observeValidatedList(targetAddends), modelAddends);
	}

	static class MultiFieldValidationWizard extends Wizard {
		@Override
		public void addPages() {
			addPage(new Snippet021MultiFieldValidation());
		}

		@Override
		public String getWindowTitle() {
			return "Snippet 021 - Multi-field Validation";
		}

		@Override
		public boolean performFinish() {
			return true;
		}
	}
}
