package com.tlcsdm.tlstudio.widgets.example.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.custom.SplitButton;

/**
 * Sample for the Split Button Widget
 */
public class SplitButtonSnippet {

	public static void main(final String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setSize(300, 300);
		shell.setLayout(new GridLayout());

		// First Button
		final SplitButton actionButton = new SplitButton(shell, SWT.NONE);
		actionButton.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		actionButton.setText("Actions"); //$NON-NLS-1$

		final Menu actionMenu = actionButton.getMenu();
		createMenuEntries(actionMenu, "Reply to", "Reply to All", "Forward", "Print");

		actionButton.addListener(SWT.Selection, (e) -> {
			System.out.println("Click on Splitbutton 'Reply To'"); //$NON-NLS-1$
		});

		// Second button
		final SplitButton stateButton = new SplitButton(shell, SWT.TOGGLE);
		stateButton.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		stateButton.setText("Toggle Button"); //$NON-NLS-1$

		final Menu stateButtonMenu = stateButton.getMenu();
		createMenuEntries(stateButtonMenu, "First Action", "Second Action", "Last Action");

		stateButton.addListener(SWT.Selection, (e) -> {
			System.out.println("Click on Splitbutton 'State Button'"); //$NON-NLS-1$
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private static void createMenuEntries(Menu menu, String... labels) {
		for (final String label : labels) {
			final MenuItem item = new MenuItem(menu, SWT.PUSH);
			item.setText(label);
			item.addListener(SWT.Selection, e -> System.out.println("Click on menu item [" + label + "]"));
		}
	}
}
