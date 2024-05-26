/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
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
 * Tree example snippet: show results as a bar chart in tree
 *
 * For more info on custom-drawing TableItem and TreeItem content see
 * http://www.eclipse.org/articles/article.php?file=Article-CustomDrawingTableAndTreeItems/index.html
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 *
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet232 {

public static void main(String [] args) {
	final Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	shell.setText("Show results as a bar chart in Tree");
	final Tree tree = new Tree(shell, SWT.BORDER);
	tree.setHeaderVisible(true);
	tree.setLinesVisible(true);
	TreeColumn column1 = new TreeColumn(tree, SWT.NONE);
	column1.setText("Bug Status");
	column1.setWidth(100);
	final TreeColumn column2 = new TreeColumn(tree, SWT.NONE);
	column2.setText("Percent");
	column2.setWidth(200);
	String[] states = new String[]{"Resolved", "New", "Won't Fix", "Invalid"};
	String[] teams = new String[] {"UI", "SWT", "OSGI"};
	for (String team : teams) {
		TreeItem item = new TreeItem(tree, SWT.NONE);
		item.setText(team);
		for (String state : states) {
			TreeItem subItem = new TreeItem(item, SWT.NONE);
			subItem.setText(state);
		}
	}

	/*
	 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
	 * Therefore, it is critical for performance that these methods be
	 * as efficient as possible.
	 */
	tree.addListener(SWT.PaintItem, new Listener() {
		int[] percents = new int[] {50, 30, 5, 15};
		@Override
		public void handleEvent(Event event) {
			if (event.index == 1) {
				TreeItem item = (TreeItem)event.item;
				TreeItem parent = item.getParentItem();
				if (parent != null) {
					GC gc = event.gc;
					int index = parent.indexOf(item);
					int percent = percents[index];
					Color foreground = gc.getForeground();
					Color background = gc.getBackground();
					gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
					gc.setBackground(display.getSystemColor(SWT.COLOR_YELLOW));
					int width = (column2.getWidth() - 1) * percent / 100;
					gc.fillGradientRectangle(event.x, event.y, width, event.height, true);
					Rectangle rect2 = new Rectangle(event.x, event.y, width-1, event.height-1);
					gc.drawRectangle(rect2);
					gc.setForeground(display.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
					String text = percent+"%";
					Point size = event.gc.textExtent(text);
					int offset = Math.max(0, (event.height - size.y) / 2);
					gc.drawText(text, event.x+2, event.y+offset, true);
					gc.setForeground(background);
					gc.setBackground(foreground);
				}
			}
		}
	});

	shell.pack();
	shell.open();
	while(!shell.isDisposed()) {
		if(!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}