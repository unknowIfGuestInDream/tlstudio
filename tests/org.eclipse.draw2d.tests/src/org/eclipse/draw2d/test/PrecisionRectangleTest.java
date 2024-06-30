/*******************************************************************************
 * Copyright (c) 2006, 2010 IBM Corporation and others.
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

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author sshaw
 *
 */
public class PrecisionRectangleTest extends Assert {

	@SuppressWarnings("static-method")
	@Test
	public void testShrink() {
		Insets insets = new Insets(2, 2, 2, 2);

		PrecisionRectangle r = new PrecisionRectangle(new Rectangle(100, 100, 250, 250));
		PrecisionRectangle copy = r.getPreciseCopy();
		r.performTranslate(30, 30);
		r.performScale(2f);
		r.shrink(insets);
		r.performScale(1 / 2f);
		r.performTranslate(-30, -30);

		assertTrue(!r.equals(copy));

		insets = new Insets(1, 1, -1, -1);

		r = new PrecisionRectangle(new Rectangle(0, 0, 3, 3));
		copy = r.getPreciseCopy();
		r.performTranslate(1, 1);
		r.performScale(4f);
		r.shrink(insets);
		r.performScale(1 / 4f);
		r.performTranslate(-1, -1);

		assertTrue(!r.equals(copy));

		r = new PrecisionRectangle(-9.486614173228347, -34.431496062992125, 41.99055118110236, 25.92755905511811);
		r.performScale(26.458333333333332);
		r.performScale(1.0 / 26.458333333333332);
		assertEquals(-9.486614173228347, r.preciseX(), 0);
		assertEquals(-34.431496062992125, r.preciseY(), 0);
		assertEquals(41.99055118110236, r.preciseWidth(), 0);
		assertEquals(25.92755905511811, r.preciseHeight(), 0);
	}

	@SuppressWarnings("static-method")
	@Test
	public void testExpand() {
		PrecisionRectangle r = new PrecisionRectangle(new Rectangle(100, 100, 250, 250));
		PrecisionRectangle copy = r.getPreciseCopy();
		r.expand(0.1, 0.1);
		assertEquals(r, new PrecisionRectangle(99.9, 99.9, 250.2, 250.2));
		assertEquals(r, copy.getExpanded(0.1, 0.1));
		r.shrink(0.1, 0.1);
	}

	@SuppressWarnings("static-method")
	@Test
	public void testUnion() {
		PrecisionRectangle r = new PrecisionRectangle(-9.486614173228347, -34.431496062992125, 41.99055118110236,
				25.92755905511811);
		r.union(100.5, 100.5);
		assertEquals(new PrecisionRectangle(-9.486614173228347, -34.431496062992125, 100.5 + 9.486614173228347,
				100.5 + 34.431496062992125), r);
	}

	@SuppressWarnings("static-method")
	@Test
	public void testResize() {
		PrecisionRectangle r = new PrecisionRectangle(-9.486614173228347, -34.431496062992125, 41.99055118110236,
				25.92755905511811);
		r.resize(100.1, 100.1);
		assertEquals(new PrecisionRectangle(-9.486614173228347, -34.431496062992125, 41.99055118110236 + 100.1,
				25.92755905511811 + 100.1), r);
	}

	@SuppressWarnings("static-method")
	@Test
	public void testContains() {
		PrecisionRectangle r = new PrecisionRectangle(-9.486614173228347, -34.431496062992125, 41.99055118110236,
				25.92755905511811);
		assertTrue(r.contains(-9.486614173228347, -34.431496062992125));
		assertTrue(r.contains(-9.486614173228347 + 41.99055118110235, -34.431496062992125 + 25.92755905511810));
	}
	// contains
}
