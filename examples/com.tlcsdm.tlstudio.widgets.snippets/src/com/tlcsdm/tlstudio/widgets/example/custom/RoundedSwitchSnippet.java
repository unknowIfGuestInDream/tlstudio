package com.tlcsdm.tlstudio.widgets.example.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.custom.RoundedSwitch;
import com.tlcsdm.tlstudio.widgets.utils.SWTGraphicUtil;

/**
 * This snippet demonstrates the RoundedSwitch widget
 *
 */
public class RoundedSwitchSnippet {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2, false));

		final Label lbl1 = new Label(shell, SWT.NONE);
		lbl1.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		lbl1.setText("Enabled checked");

		final RoundedSwitch button1 = new RoundedSwitch(shell, SWT.NONE);
		button1.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));
		button1.setEnabled(true);
		button1.setSelection(true);
		button1.addListener(SWT.Selection, e -> {
			System.out.println("Click");
		});

		final Label lbl2 = new Label(shell, SWT.NONE);
		lbl2.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		lbl2.setText("Enabled unchecked");

		final RoundedSwitch button2 = new RoundedSwitch(shell, SWT.NONE);
		button2.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));
		button2.setEnabled(true);
		button2.setSelection(false);

		final Label lbl3 = new Label(shell, SWT.NONE);
		lbl3.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		lbl3.setText("Disabled checked");

		final RoundedSwitch button3 = new RoundedSwitch(shell, SWT.NONE);
		button3.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));
		button3.setEnabled(false);
		button3.setSelection(true);

		final Label lbl4 = new Label(shell, SWT.NONE);
		lbl4.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		lbl4.setText("Disabled unchecked");

		final RoundedSwitch button4 = new RoundedSwitch(shell, SWT.NONE);
		button4.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));
		button4.setEnabled(false);
		button4.setSelection(false);

		shell.setSize(400, 350);
		shell.open();
		SWTGraphicUtil.centerShell(shell);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();
	}
}
