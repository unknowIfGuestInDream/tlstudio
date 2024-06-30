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
package org.eclipse.draw2d.examples.layouts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.examples.AbstractExample;

/**
 * @author hudsonr Created on Apr 30, 2003
 */
public class ToolbarLayoutExample extends AbstractExample {

	ToolbarLayout layout;

	Shape ellipse, rect, roundRect, ellipse2, rect2;

	public static void main(String[] args) {
		new ToolbarLayoutExample().run();
	}

	/**
	 * @see org.eclipse.draw2d.examples.AbstractExample#createContents()
	 */
	@Override
	protected IFigure createContents() {
		Figure container = new Figure();
		container.setBorder(new LineBorder());
		container.setLayoutManager(layout = new ToolbarLayout(true));

		ellipse = new Ellipse();
		ellipse.setBackgroundColor(ColorConstants.blue);
		ellipse.setSize(60, 40);
		container.add(ellipse);

		rect = new RectangleFigure();
		rect.setBackgroundColor(ColorConstants.red);
		rect.setSize(30, 70);
		container.add(rect);

		roundRect = new RoundedRectangle();
		roundRect.setBackgroundColor(ColorConstants.yellow);
		roundRect.setSize(90, 30);
		container.add(roundRect);

		rect2 = new RectangleFigure();
		rect2.setBackgroundColor(ColorConstants.gray);
		rect2.setSize(50, 80);
		container.add(rect2);

		ellipse2 = new Ellipse();
		ellipse2.setBackgroundColor(ColorConstants.green);
		ellipse2.setSize(50, 50);
		container.add(ellipse2);

		return container;
	}

	/**
	 * @see org.eclipse.draw2d.examples.AbstractExample#hookShell(Shell)
	 */
	@Override
	protected void hookShell(Shell shell) {
		Composite composite = new Composite(shell, 0);
		composite.setLayoutData(new GridData(GridData.FILL_VERTICAL));

		composite.setLayout(new GridLayout());

		final Button horizontal = new Button(composite, SWT.CHECK);
		horizontal.setText("Horizontal"); //$NON-NLS-1$
		horizontal.setSelection(true);
		horizontal.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				layout.setHorizontal(!layout.isHorizontal());
				if (layout.isStretchMinorAxis()) {
					resetShapes();
				}
				getContents().revalidate();
				shell.layout(true);
			}
		});

		final Button stretch = new Button(composite, SWT.CHECK);
		stretch.setText("Stretch Minor Axis"); //$NON-NLS-1$
		stretch.setSelection(false);
		stretch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				layout.setStretchMinorAxis(!layout.isStretchMinorAxis());
				resetShapes();
				getContents().revalidate();
				shell.layout(true);
			}
		});
		{
			Group major = new Group(composite, 0);
			major.setLayout(new FillLayout(SWT.VERTICAL));
			major.setText("Minor Axis"); //$NON-NLS-1$

			Button left = new Button(major, SWT.RADIO);
			left.setText("Top/Left"); //$NON-NLS-1$
			left.setSelection(true);
			left.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					layout.setMinorAlignment(FlowLayout.ALIGN_TOPLEFT);
					getContents().revalidate();
				}
			});

			Button center = new Button(major, SWT.RADIO);
			center.setText("Middle/Center"); //$NON-NLS-1$
			center.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					layout.setMinorAlignment(FlowLayout.ALIGN_CENTER);
					getContents().revalidate();
				}
			});

			Button right = new Button(major, SWT.RADIO);
			right.setText("Buttom/Right"); //$NON-NLS-1$
			right.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					layout.setMinorAlignment(FlowLayout.ALIGN_BOTTOMRIGHT);
					getContents().revalidate();
				}
			});

			final Scale spacing = new Scale(major, 0);
			spacing.setMinimum(0);
			spacing.setMaximum(20);
			spacing.setSelection(5);
			spacing.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					layout.setSpacing(spacing.getSelection());
					getContents().revalidate();
				}
			});
			Label spacingLabel = new Label(major, SWT.CENTER);
			spacingLabel.setText("Spacing"); //$NON-NLS-1$

		}
	}

	private void resetShapes() {
		rect.setSize(30, 70);
		rect2.setSize(50, 80);
		roundRect.setSize(90, 30);
		ellipse.setSize(60, 40);
		ellipse2.setSize(50, 50);
	}

}
