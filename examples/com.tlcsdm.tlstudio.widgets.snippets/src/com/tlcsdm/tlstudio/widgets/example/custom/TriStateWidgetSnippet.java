package com.tlcsdm.tlstudio.widgets.example.custom;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.custom.TriStateWidget;

public class TriStateWidgetSnippet {

	public static void main(final String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);

		GridLayoutFactory.fillDefaults().applyTo(shell);
		TriStateWidget triStateWidget = new TriStateWidget(shell, SWT.NONE);

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
