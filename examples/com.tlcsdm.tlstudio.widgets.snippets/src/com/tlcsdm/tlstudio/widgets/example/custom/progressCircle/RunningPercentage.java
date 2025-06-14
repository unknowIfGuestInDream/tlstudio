package com.tlcsdm.tlstudio.widgets.example.custom.progressCircle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.custom.ProgressCircle;

public class RunningPercentage extends BasePanel {

	public RunningPercentage(Shell shell) {
		final Color white = shell.getDisplay().getSystemColor(SWT.COLOR_WHITE);

		final Group group = new Group(shell, SWT.NONE);
		group.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		group.setText("Running Percentage");
		group.setLayout(new GridLayout(3, false));
		group.setBackground(white);

		final Button update = new Button(group, SWT.PUSH);
		update.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false, 3, 1));
		update.setText("Run");

		final ProgressCircle circle = new ProgressCircle(group, SWT.NONE);
		final GridData gdCircle = new GridData(GridData.CENTER, GridData.CENTER, true, true, 3, 1);
		gdCircle.minimumHeight = gdCircle.minimumWidth = 200;
		circle.setBackground(white);
		circle.setLayoutData(gdCircle);
		circle.setTextPattern(ProgressCircle.PERCENTAGE_PATTERN);

		circle.setSelection(0);
		circle.setThickness(20);
		circle.setCircleSize(200);
		circle.setShowText(true);

		update.addListener(SWT.Selection, e -> {
			int percentage = 0;
			while (percentage++ != 100) {
				circle.setSelection(percentage++);
				circle.setAnimationDelay(0);
				sleepFor(10);
			}
			sleepFor(1000);
			circle.setSelection(100);
		});
	}

	private void sleepFor(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
}
