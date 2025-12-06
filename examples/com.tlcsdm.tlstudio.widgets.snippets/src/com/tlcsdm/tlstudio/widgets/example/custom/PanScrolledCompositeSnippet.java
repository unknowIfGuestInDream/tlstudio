package com.tlcsdm.tlstudio.widgets.example.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.custom.PanScrolledComposite;

public class PanScrolledCompositeSnippet {

	public static void main(final String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2, true));
		final Color white = display.getSystemColor(SWT.COLOR_WHITE);
		shell.setBackground(white);

		PanScrolledComposite scrolledComposite = new PanScrolledComposite(shell,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

		Label image = new Label(scrolledComposite, SWT.NONE);

		Image icon = new Image(display, BadgedLabelSnippet.class.getClassLoader()
				.getResourceAsStream("com/tlcsdm/tlstudio/widgets/example/custom/panscrolled/park.jpg"));
		image.setImage(icon);

//		Image icon = new Image(display, BadgedLabelSnippet.class.getClassLoader()
//				.getResourceAsStream("com/tlcsdm/tlstudio/widgets/example/custom/chips/dirk.png"));
//		image.setImage(icon);

		scrolledComposite.setContent(image);
		scrolledComposite.setMinSize(image.computeSize(SWT.DEFAULT, SWT.DEFAULT));

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
