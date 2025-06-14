package com.tlcsdm.tlstudio.widgets.example.custom.progressCircle;

import java.time.LocalTime;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import com.tlcsdm.tlstudio.widgets.custom.ProgressCircle;

public class TimePanel extends BasePanel {

	public TimePanel(Shell shell) {
		final Color white = shell.getDisplay().getSystemColor(SWT.COLOR_WHITE);

		final Group group = new Group(shell, SWT.NONE);
		group.setText("CountDown");
		group.setLayout(new GridLayout(3, false));
		group.setBackground(white);
		group.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		createLeftLabel(group, "Time");

		final Composite temp = new Composite(group, SWT.NONE);
		temp.setBackground(white);
		temp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 2, 1));
		temp.setLayout(new GridLayout(6, false));

		final Spinner hours = new Spinner(temp, SWT.NONE);
		hours.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		hours.setMinimum(0);
		hours.setMaximum(23);

		final Label lblHour = new Label(temp, SWT.NONE);
		lblHour.setText("hour(s)");
		lblHour.setBackground(white);
		lblHour.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));

		final Spinner minutes = new Spinner(temp, SWT.NONE);
		minutes.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		minutes.setMinimum(0);
		minutes.setMaximum(59);
		minutes.setSelection(1);

		final Label lblMinutes = new Label(temp, SWT.NONE);
		lblMinutes.setText("minute(s)");
		lblMinutes.setBackground(white);
		lblMinutes.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));

		final Spinner seconds = new Spinner(temp, SWT.NONE);
		seconds.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		seconds.setMinimum(0);
		seconds.setMaximum(59);
		seconds.setSelection(18);

		final Label lblSeconds = new Label(temp, SWT.NONE);
		lblSeconds.setText("second(s)");
		lblSeconds.setBackground(white);
		lblSeconds.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));

		createCommonPart(group);

		final Button update = new Button(group, SWT.PUSH);
		update.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false, 3, 1));
		update.setText("Start Counter");

		final ProgressCircle circle = new ProgressCircle(group, SWT.NONE);
		final GridData gdCircle = new GridData(GridData.CENTER, GridData.CENTER, true, true, 3, 1);
		gdCircle.minimumHeight = gdCircle.minimumWidth = 200;
		circle.setBackground(white);
		circle.setLayoutData(gdCircle);
		circle.setTextPattern(ProgressCircle.INTEGER_PATTERN);
		circle.setThickness(10);
		circle.setCircleSize(100);
		circle.setShowText(true);

		final Color red = new Color(shell.getDisplay(), 222, 80, 79);
		shell.addDisposeListener((e) -> red.dispose());
		circle.setHighlightColor(red);

		update.addListener(SWT.Selection, e -> {
			int newCircleSize = 0;
			try {
				newCircleSize = Integer.valueOf(circleSize.getText());
			} catch (final NumberFormatException nfe) {
				showError(shell, "The value [" + circleSize.getText() + "] is not a number");
				return;
			}
			if (newCircleSize > 1000) {
				showError(shell, "The value [" + newCircleSize + "] should be between lower than 1000");
				return;
			}
			//
			int newThickness = 0;
			try {
				newThickness = Integer.valueOf(thickness.getText());
			} catch (final NumberFormatException nfe) {
				showError(shell, "The value [" + thickness.getText() + "] is not a number");
				return;
			}
			if (newThickness < 1 || newThickness > 50) {
				showError(shell, "The value [" + newThickness + "] should be between 1 and 50");
				return;
			}
			int newDelay = 0;
			try {
				newDelay = Integer.valueOf(delay.getText());
			} catch (final NumberFormatException nfe) {
				showError(shell, "The value [" + delay.getText() + "] is not a number");
				return;
			}
			if (newDelay < 1 || newDelay > 5000) {
				showError(shell, "The value [" + newDelay + "] should be between 1 and 5000");
				return;
			}
			//
			circle.setAnimationDelay(newDelay);
			circle.setThickness(newThickness);
			circle.setCircleSize(newCircleSize);
			circle.setShowText(checkbox.getSelection());
			final LocalTime time = LocalTime.of(hours.getSelection(), minutes.getSelection(), seconds.getSelection());
			circle.startCountDown(time);
		});
	}

}
