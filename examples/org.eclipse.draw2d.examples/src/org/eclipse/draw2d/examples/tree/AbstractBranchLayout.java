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
package org.eclipse.draw2d.examples.tree;

import java.util.List;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Transposer;

/**
 *
 * @author hudsonr Created on Apr 22, 2003
 */
public abstract class AbstractBranchLayout extends AbstractLayout {

	private Transposer transposer;
	final TreeBranch branch;
	int[] cachedContourLeft;
	int[] cachedContourRight;
	int depth = -1;

	public boolean horizontal = true;
	int[] preferredRowHeights;
	int rowHeight;

	protected AbstractBranchLayout(TreeBranch branch) {
		this.branch = branch;
	}

	abstract void calculateDepth();

	public int[] getContourLeft() {
		if (cachedContourLeft == null) {
			updateContours();
		}
		return cachedContourLeft;
	}

	public int[] getContourRight() {
		if (cachedContourRight == null) {
			updateContours();
		}
		return cachedContourRight;
	}

	public int getDepth() {
		if (!branch.isExpanded()) {
			return 1;
		}
		if (depth == -1) {
			calculateDepth();
		}
		return depth;
	}

	public int[] getPreferredRowHeights() {
		if (preferredRowHeights == null) {
			updateRowHeights();
		}
		return preferredRowHeights;
	}

	List<TreeBranch> getSubtrees() {
		return branch.getSubtrees();
	}

	Transposer getTransposer() {
		if (transposer == null) {
			transposer = branch.getRoot().getTransposer();
		}
		return transposer;
	}

	int getMajorSpacing() {
		return branch.getRoot().getMajorSpacing();
	}

	@Override
	public void invalidate() {
		preferredRowHeights = null;
		cachedContourLeft = null;
		cachedContourRight = null;
		depth = -1;
		super.invalidate();
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	abstract void paintLines(Graphics g);

	public void setHorizontal(boolean value) {
		horizontal = value;
	}

	void setRowHeights(int[] heights, int offset) {
		if (rowHeight != heights[offset]) {
			rowHeight = heights[offset];
			branch.revalidate();
		}
	}

	abstract void updateContours();

	abstract void updateRowHeights();

}
