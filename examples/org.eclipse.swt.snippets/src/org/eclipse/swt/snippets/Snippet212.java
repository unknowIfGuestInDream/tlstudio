/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
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

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * StyledText snippet: embed images
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 *
 * @since 3.2
 */
public class Snippet212 {

	static StyledText styledText;
	static String text =
		"This snippet shows how to embed images in a StyledText.\n"+
		"Here is one: \uFFFC, and here is another: \uFFFC."+
		"Use the add button to add an image from your filesystem to the StyledText at the current caret offset.";

	static void addImage(Image image, int offset) {
		StyleRange style = new StyleRange ();
		style.start = offset;
		style.length = 1;
		style.data = image;
		Rectangle rect = image.getBounds();
		style.metrics = new GlyphMetrics(rect.height, 0, rect.width);
		styledText.setStyleRange(style);
	}

	public static void main(String [] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Snippet 212");
		shell.setLayout(new GridLayout());
		styledText = new StyledText(shell, SWT.WRAP | SWT.BORDER);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		styledText.setText(text);
		int offset = text.indexOf("\uFFFC", 0);
		addImage(display.getSystemImage(SWT.ICON_QUESTION), offset);
		offset = text.indexOf("\uFFFC", offset + 1);
		addImage(display.getSystemImage(SWT.ICON_INFORMATION), offset);

		// use a verify listener to dispose the images
		styledText.addVerifyListener(event -> {
			if (event.start == event.end) return;
			String text = styledText.getText(event.start, event.end - 1);
			int index = text.indexOf('\uFFFC');
			while (index != -1) {
				StyleRange style = styledText.getStyleRangeAtOffset(event.start + index);
				if (style != null) {
					Image image = (Image)style.data;
					if (image != null) image.dispose();
				}
				index = text.indexOf('\uFFFC', index + 1);
			}
		});
		// draw images on paint event
		styledText.addPaintObjectListener(event -> {
			StyleRange style = event.style;
			Image image = (Image)style.data;
			if (!image.isDisposed()) {
				int x = event.x;
				int y = event.y + event.ascent - style.metrics.ascent;
				event.gc.drawImage(image, x, y);
			}
		});
		styledText.addListener(SWT.Dispose, event -> {
			StyleRange[] styles = styledText.getStyleRanges();
			for (StyleRange style : styles) {
				if (style.data != null) {
					Image image = (Image)style.data;
					if (image != null) image.dispose();
				}
			}
		});
		Button button = new Button (shell, SWT.PUSH);
		button.setText("Add Image");
		button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		button.addListener(SWT.Selection, event -> {
			FileDialog dialog = new FileDialog(shell);
			String filename = dialog.open();
			if (filename != null) {
				try {
					Image image = new Image(display, filename);
					int offset1 = styledText.getCaretOffset();
					styledText.replaceTextRange(offset1, 0, "\uFFFC");
					addImage(image, offset1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		shell.setSize(400, 400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
