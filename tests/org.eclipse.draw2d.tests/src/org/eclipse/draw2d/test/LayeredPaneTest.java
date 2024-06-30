/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
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
package org.eclipse.draw2d.test;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayeredPane;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LayeredPaneTest extends Assert {

	private LayeredPane pane;
	private IFigure fig1;
	private IFigure fig2;
	private IFigure fig3;

	@Before
	public void setUp() throws Exception {

		pane = new LayeredPane();
	}

	@Test
	public void testIndexOutOfBounds() {
		fig1 = new Figure();
		fig2 = new Figure();
		fig3 = new Figure();

		pane.add(fig1);
		pane.add(fig2);

		pane.remove(fig1);
		boolean failed = false;

		try {
			pane.add(fig3);
		} catch (IndexOutOfBoundsException e) {
			failed = true;
		}

		assertEquals(false, failed);
	}

	@After
	public void tearDown() throws Exception {
		pane = null;
	}

}
