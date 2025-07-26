package com.tlcsdm.tlstudio.widgets.example.custom;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.custom.CustomCheckbox;

public class CustomCheckBoxSnippet {

	public static void main(final String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);

		GridLayoutFactory.fillDefaults().applyTo(shell);
		CustomCheckbox checkbox = new CustomCheckbox(shell, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(checkbox);
		checkbox.setSelection(true);
		checkbox.setText("Custom SWT check button");

		checkbox.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_BLUE));
		checkbox.setCenterColor(shell.getDisplay().getSystemColor(SWT.COLOR_GRAY));
		checkbox.setCheckColor(shell.getDisplay().getSystemColor(SWT.COLOR_BLUE));

		Button checkButton = new Button(shell, SWT.CHECK);
		checkButton.setSelection(true);
		checkButton.setText("Usual SWT check button");

		shell.setSize(new Point(436, 546));
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

	}

}
