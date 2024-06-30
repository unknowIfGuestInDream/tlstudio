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
package org.eclipse.draw2d.examples.uml;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class UMLClassDiagram {

	public static void main(String[] args) {

		Display d = new Display();
		Shell shell = new Shell(d);
		shell.setLayout(new FillLayout());

		FigureCanvas canvas = new FigureCanvas(shell);
		canvas.setBackground(ColorConstants.white);

		Figure diagram = new Figure();
		diagram.setLayoutManager(new XYLayout());
		canvas.setContents(diagram);

		IFigure c1, c2;

		diagram.add(c1 = new UMLClassFigure(), new Rectangle(20, 20, -1, -1));
		diagram.add(c2 = new UMLClassFigure(), new Rectangle(230, 102, -1, -1));

		PolylineConnection assoc = new PolylineConnection();
		assoc.setTargetAnchor(new ChopboxAnchor(c1));
		assoc.setSourceAnchor(new ChopboxAnchor(c2));
		PolygonDecoration containment = new PolygonDecoration();
		containment.setTemplate(new PointList(new int[] { -2, 0, -1, 1, 0, 0, -1, -1 }));
		assoc.setTargetDecoration(containment);
		diagram.add(assoc);

		Label ref = new Label("end1"); //$NON-NLS-1$
		ConnectionEndpointLocator locator = new ConnectionEndpointLocator(assoc, false);
		locator.setUDistance(8);
		assoc.add(ref, locator);

		Label connLabel = new Label("connection"); //$NON-NLS-1$
		connLabel.setBorder(new LineBorder());
		connLabel.setOpaque(true);
		connLabel.setBackgroundColor(ColorConstants.tooltipBackground);
		assoc.add(connLabel, new ConnectionLocator(assoc, ConnectionLocator.MIDDLE));

		diagram.add(new StickyNote(), new Rectangle(180, 10, 90, -1));

		shell.setSize(500, 300);
		shell.open();
		while (!shell.isDisposed()) {
			while (!d.readAndDispatch()) {
				d.sleep();
			}
		}

	}

}
