package com.tlcsdm.tlstudio.widgets.example.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.custom.FloatingText;

/**
 * A simple snippet for the FloatingText
 *
 */
public class FloatingTextSnippetSimple {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, true));

		createText(new Group(shell, SWT.NONE));

		Point p = shell.getSize();
		p.y = shell.getMonitor().getBounds().height * 75 / 100;
		p.x = shell.getMonitor().getBounds().width * 50 / 100;
		shell.setSize(p);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

	}

	private static void createText(final Group group) {
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		group.setText("Floating Text widgets");

		final FloatingText txt1 = new FloatingText(group, SWT.BORDER);
		txt1.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		txt1.getText().setMessage("First and Last Name");

		final FloatingText txt11 = new FloatingText(group, SWT.BORDER | SWT.SEPARATOR);
		txt11.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		txt11.getText().setMessage("Text + Separator + Gray label");
		txt11.getLabel().setForeground(txt11.getDisplay().getSystemColor(SWT.COLOR_GRAY));
	}
}
