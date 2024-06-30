/*******************************************************************************
 * Copyright (c) 2005, 2023 IBM Corporation and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.draw2d.examples.thumbnail;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.GroupBoxBorder;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.parts.Thumbnail;

/**
 * This example demonstrates an overview window
 *
 * @author hudsonr
 */
public class ThumbnailExample {

	private static Figure contents;
	private static Shell mainShell;
	private static Shell overviewShell;
	private static Dimension offset = new Dimension();

	public static void main(String[] args) {
		Display display = new Display();

		mainShell = new Shell(display);
		mainShell.setText("Source Shell"); //$NON-NLS-1$
		mainShell.setLayout(new FillLayout());
		FigureCanvas mainCanvas = new FigureCanvas(mainShell);
		mainCanvas.setContents(getContents());

		overviewShell = new Shell(mainShell, SWT.TITLE | SWT.RESIZE | SWT.NO_REDRAW_RESIZE | SWT.NO_BACKGROUND);
		overviewShell.setText("Overview Shell"); //$NON-NLS-1$
		overviewShell.setLayout(new FillLayout());
		LightweightSystem overviewLWS = new LightweightSystem(overviewShell);
		overviewLWS.setContents(createThumbnail(getContents()));

		mainShell.setSize(600, 600);
		mainShell.open();
		overviewShell.setSize(200, 200);
		overviewShell.open();

		while (!mainShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		mainShell.dispose();
		overviewShell.dispose();
	}

	protected static Figure getContents() {
		if (contents == null) {
			contents = createContents();
		}
		return contents;
	}

	private static Figure createContents() {
		Figure contents = new Figure();
		contents.setBorder(new LineBorder());
		contents.setLayoutManager(new XYLayout());
		final Figure figure1 = new RectangleFigure();
		figure1.setBackgroundColor(ColorConstants.green);
		figure1.setBounds(new Rectangle(50, 50, 200, 200));
		figure1.addMouseListener(new MouseListener.Stub() {
			@Override
			public void mousePressed(MouseEvent event) {
				offset.setWidth(event.x - figure1.getLocation().x());
				offset.setHeight(event.y - figure1.getLocation().y());
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				offset.setWidth(0);
				offset.setHeight(0);
			}
		});
		figure1.addMouseMotionListener(new MouseMotionListener.Stub() {
			@Override
			public void mouseDragged(MouseEvent event) {
				Rectangle rect = figure1.getBounds().getCopy();
				rect.setX(event.x - offset.width());
				rect.setY(event.y - offset.height());
				figure1.setBounds(rect);
			}
		});
		contents.add(figure1);
		final Figure figure2 = new RectangleFigure();
		figure2.setBackgroundColor(ColorConstants.blue);
		figure2.setBounds(new Rectangle(350, 350, 150, 200));
		figure2.addMouseListener(new MouseListener.Stub() {
			@Override
			public void mousePressed(MouseEvent event) {
				offset.setWidth(event.x - figure2.getLocation().x());
				offset.setHeight(event.y - figure2.getLocation().y());
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				offset.setWidth(0);
				offset.setHeight(0);
			}
		});
		figure2.addMouseMotionListener(new MouseMotionListener.Stub() {
			@Override
			public void mouseDragged(MouseEvent event) {
				Rectangle rect = figure2.getBounds().getCopy();
				rect.setX(event.x - offset.width());
				rect.setY(event.y - offset.height());
				figure2.setBounds(rect);
			}
		});
		contents.add(figure2);
		return contents;
	}

	protected static Figure createThumbnail(Figure source) {
		Thumbnail thumbnail = new Thumbnail();
		thumbnail.setBorder(new GroupBoxBorder("Overview Figure")); //$NON-NLS-1$
		thumbnail.setSource(source);
		return thumbnail;
	}

}
