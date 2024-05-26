/*******************************************************************************
 * Copyright (c) 2000, 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * SWT StyledText snippet: different types of indent and combining wrap indent and bulleted lists.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 *
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet331 {

	static String text =
		"The first paragraph has an indentation of fifty pixels and zero indentation for wrapped lines. If this paragraph wraps to several lines you will see the indentation only on the first line.\n\n" +
		"The second paragraph has an indentation of fifty pixels for all lines in the paragraph. Visually this paragraph has a fifty pixel left margin.\n\n" +
		"The third paragraph has wrap indentation of fifty pixels and zero indentation for the first line. If this paragraph wraps to several lines you should see the indentation for all the lines but the first.\n\n" +
		"This paragraph starts with a bullet and does not have any kind of indentation. If this paragraph wraps to several lines, the wrapped lines will start on the lead edge of the editor.\n\n" +
		"This paragraph starts with a bullet and has wrap indentation with the same width as the bullet. If this paragraph wraps to several lines, all the wrapped lines will line up with the first one.";

	public static void main(String [] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Snippet 331");
		shell.setLayout(new FillLayout());
		StyledText styledText = new StyledText(shell, SWT.WRAP | SWT.BORDER);
		styledText.setText(text);
		styledText.setLineIndent(0, 1, 50);
		styledText.setLineIndent(2, 1, 50);
		styledText.setLineWrapIndent(2, 1, 50);
		styledText.setLineWrapIndent(4, 1, 50);

		StyleRange style = new StyleRange();
		style.metrics = new GlyphMetrics(0, 0, 50);
		Bullet bullet = new Bullet (style);
		styledText.setLineBullet(6, 1, bullet);
		styledText.setLineBullet(8, 1, bullet);
		styledText.setLineWrapIndent(8, 1, 50);

		shell.setSize(350, 550);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
