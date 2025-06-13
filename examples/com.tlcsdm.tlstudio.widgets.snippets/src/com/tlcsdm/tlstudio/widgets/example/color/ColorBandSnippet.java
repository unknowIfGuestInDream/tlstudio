package com.tlcsdm.tlstudio.widgets.example.color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.color.ColorBand;

public class ColorBandSnippet {

	public static void main(String[] args) {
		Display display = new Display();

		Shell shell = new Shell(display);
		shell.setText("Hue");
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 10;
		gridLayout.marginWidth = gridLayout.marginHeight = 16;
		shell.setLayout(gridLayout);

		ColorBand cd = new ColorBand(shell, SWT.NONE);
		GridData data = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1);
		data.widthHint = 500;
		cd.setLayoutData(data);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
