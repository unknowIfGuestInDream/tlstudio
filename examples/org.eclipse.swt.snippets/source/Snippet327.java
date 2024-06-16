/*******************************************************************************
 * Copyright (c) 2009, 2020 IBM Corporation and others.
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
 * Browser example snippet: Render HTML from memory in response to a link click.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet327 {

	final static String PREAMBLE = "https://git.eclipse.org/c/platform/eclipse.platform.swt.git/log/?ofs=";

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setText("Snippet 327");
	shell.setLayout(new FillLayout());

	final Browser browser;
	try {
		browser = new Browser(shell, SWT.NONE);
	} catch (SWTError e) {
		System.out.println("Could not instantiate Browser: " + e.getMessage());
		display.dispose();
		return;
	}
	browser.setText(createPage(0));
	browser.addLocationListener(LocationListener.changingAdapter(event -> {
		String location = event.location;
		int index = location.indexOf(PREAMBLE);
		if (index != -1) {
			int pageNumber = Integer.valueOf(location.substring(index + PREAMBLE.length())).intValue();
			browser.setText(createPage(pageNumber));
			event.doit = false;
		}
	}));

	shell.setBounds(10,10,200,200);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose ();
}

static String createPage(int index) {
	return "<html><body>This is page " + index + "<p><a href=\"" + PREAMBLE + ++index + "\">Go to page " + index + "</a></body></html>";
}

}
