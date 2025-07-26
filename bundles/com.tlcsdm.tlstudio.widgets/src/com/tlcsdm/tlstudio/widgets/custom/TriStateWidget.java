package com.tlcsdm.tlstudio.widgets.custom;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

@Deprecated
public class TriStateWidget extends Composite {

	private Button undefinedButton;
	private Button trueButton;
	private Button falseButton;

	public TriStateWidget(Composite parent, int style) {
		super(parent, style);
		createContents(parent);
	}

	private void createContents(Composite parent) {
		undefinedButton = new Button(this, SWT.RADIO);
		undefinedButton.setText("undefined");

		trueButton = new Button(this, SWT.RADIO);
		trueButton.setText("yes");

		falseButton = new Button(this, SWT.RADIO);
		falseButton.setText("no");

		GridLayoutFactory.fillDefaults().numColumns(3).generateLayout(this);
	}

	public Boolean getState() {
		checkWidget();
		if (trueButton.getSelection()) {
			return Boolean.TRUE;
		} else if (falseButton.getSelection()) {
			return Boolean.FALSE;
		}
		return null;
	}
}
