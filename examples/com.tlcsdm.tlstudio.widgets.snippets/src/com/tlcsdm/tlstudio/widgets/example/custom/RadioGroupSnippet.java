package com.tlcsdm.tlstudio.widgets.example.custom;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.custom.RadioGroup;
import com.tlcsdm.tlstudio.widgets.custom.RadioItem;

public class RadioGroupSnippet {

	public static void main(String[] arrrrgs) {
		final Display display = new Display();

		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			public void run() {
				Shell shell = createShell();

				shell.open();
				while (!shell.isDisposed())
					if (!display.readAndDispatch())
						display.sleep();

				display.dispose();
			}
		});
	}

	private static Shell createShell() {
		final Shell shell = new Shell();
		shell.setLayout(new GridLayout(1, false));

		RadioGroup radioGroup = new RadioGroup(shell, SWT.BORDER);
		new RadioItem(radioGroup, SWT.NONE).setText("Red");
		new RadioItem(radioGroup, SWT.NONE).setText("Orange");
		new RadioItem(radioGroup, SWT.NONE).setText("Yellow");
		new RadioItem(radioGroup, SWT.NONE).setText("Green");
		new RadioItem(radioGroup, SWT.NONE).setText("Blue");
		new RadioItem(radioGroup, SWT.NONE).setText("Indigo");
		new RadioItem(radioGroup, SWT.NONE).setText("Violet");

		return shell;
	}
}
