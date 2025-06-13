package com.tlcsdm.tlstudio.widgets.example.color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.color.CIE1931ChromaticityDiagram;
import com.tlcsdm.tlstudio.widgets.color.ChromaticityDiagram;

public class ChromaticityDiagramSnippet {

	public static void main(String[] args) {
		Display display = new Display();

		Shell shell = new Shell(display);
		shell.setText("Chromaticity Diagram");
		shell.setLayout(new GridLayout(1, false));
		shell.setSize(800, 600);
		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

		ChromaticityDiagram cd = new CIE1931ChromaticityDiagram(scrolledComposite, SWT.NONE);
		cd.setCalculatePoint(0.3, 0.4);

		scrolledComposite.setContent(cd);
		scrolledComposite.setMinSize(cd.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrolledComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(final ControlEvent e) {
				scrolledComposite.setMinSize(cd.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
