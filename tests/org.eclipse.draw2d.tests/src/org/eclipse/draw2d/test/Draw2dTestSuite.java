/*******************************************************************************
 * Copyright (c) 2000, 2024 IBM Corporation and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Alexander Shatalin (Borland) - Contribution for Bug 238874
 *******************************************************************************/
package org.eclipse.draw2d.test;

import org.eclipse.draw2d.graph.test.DirectedGraphLayoutTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The main test suite for Draw2d.
 *
 * @author Eric Bordeau
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	FanRouterTest.class,
	ShortestPathRoutingTest.class,
	XYLayoutTest.class,
	TextFlowWrapTest.class,
	LocalOptimizerTest.class,
	AdvancedGraphicsTests.class,
	FlowBorderTests.class,
	GraphicsClipping.class,
	PaintDamageEraseTest.class,
	LayeredPaneTest.class,
	ConnectionEndPointMoveTest.class,
	ImageUtilitiesTest.class,
	LookAheadTest.class,
	TextualTests.class,
	PointTests.class,
	DimensionTests.class,
	PointListTests.class,
	PrecisionDimensionTest.class,
	PrecisionPointTest.class,
	PrecisionRectangleTest.class,
	ThumbnailTest.class,
	FigurePaintingTest.class,
	FigureUtilitiesTest.class,
	RectangleTest.class,
	ColorConstantTest.class,
	RayTest.class,
	VectorTest.class,
	StraightTest.class,
	RelativeBendpointTest.class,
	GeometryTest.class,
	ScalablePolygonShapeTest.class,
	LayerTest.class,
	ShapeTest.class,
	InsetsTest.class,
	DirectedGraphLayoutTest.class,
	ScrollPaneTests.class
})
public class Draw2dTestSuite {
}
