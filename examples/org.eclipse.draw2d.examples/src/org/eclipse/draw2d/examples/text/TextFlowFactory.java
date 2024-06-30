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
package org.eclipse.draw2d.examples.text;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.text.BlockFlow;
import org.eclipse.draw2d.text.FlowFigure;
import org.eclipse.draw2d.text.TextFlow;

/**
 * Used by TextFlowLargeExample to build paragraphs.
 *
 * @author hudsonr
 */
public class TextFlowFactory {

	static void addFontSizes(Figure parent, int min, int max) {
		for (int i = min; i < max; i++) {
			TextFlow tf = new TextFlow(Integer.toString(i) + " pt. Font "); //$NON-NLS-1$
			tf.setFont(new org.eclipse.swt.graphics.Font(org.eclipse.swt.widgets.Display.getDefault(), "Helvetica", i, //$NON-NLS-1$
					org.eclipse.swt.SWT.NORMAL));
			parent.add(tf);
		}
	}

	static void addSentences(IFigure parent, int count) {
		for (int i = 0; i < count; i++) {
			parent.add(new TextFlow(
					"one two three four five six seven eight nine ten eleven twelve thirteen fourteen fifteen sixteen seventeen eighteen nineteen twenty twenty-one twenty-two twenty-three twenty-four twenty-five twenty-six twenty-seven twenty-eight twenty-nine thirty thirty-one thirty-two thirty-three thirty-four thirty-five thirty-six thirty-seven thirty-eight thirty-nine forty forty-one forty-two forty-three forty-four forty-five forty-six forty-seven forty-eight forty-nine fifty fifty-one fifty-two fifty-three fifty-four fifty-five fifty-six fifty-seven fifty-eight fifty-nine sixty>>>")); //$NON-NLS-1$
		}
	}

	static IFigure block(IFigure child) {
		FlowFigure block = new BlockFlow();
		block.add(child);
		return block;
	}

}