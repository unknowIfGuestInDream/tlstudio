package com.tlcsdm.tlstudio.widgets.example.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.example.custom.progressCircle.AbsolutePanel;
import com.tlcsdm.tlstudio.widgets.example.custom.progressCircle.PercentagePanel;
import com.tlcsdm.tlstudio.widgets.example.custom.progressCircle.RunningPercentage;
import com.tlcsdm.tlstudio.widgets.example.custom.progressCircle.TimePanel;

/**
 * A snippet for the ProgressCircle Widget
 */
public class ProgressCircleSnippet {

	public static void main(final String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2, true));
		final Color white = display.getSystemColor(SWT.COLOR_WHITE);
		shell.setBackground(white);

		new PercentagePanel(shell);
		new RunningPercentage(shell);
		new AbsolutePanel(shell);
		new TimePanel(shell);

		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
