/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
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
 * Browser example snippet: Close a Browser such that it can be
 * cancelled by an onbeforeunload handler.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 *
 * @since 3.6
 */
import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet326 {
	static final String HTML =
		"<html><head><script>" +
		"function doit() {" +
		"  return 'The box may contain some unsaved text.';" +
		"}" +
		"window.onbeforeunload = doit;" +
		"</script></head><body><textarea rows=\"5\" cols=\"22\">some text</textarea></body></html>";

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setText("Snippet 326");
	shell.setLayout(new GridLayout());

	final Browser browser;
	try {
		browser = new Browser(shell, SWT.NONE);
	} catch (SWTError e) {
		System.out.println("Could not instantiate Browser: " + e.getMessage());
		display.dispose();
		return;
	}
	browser.setLayoutData(new GridData(400,400));
	browser.setText(HTML);

	final Button button = new Button(shell, SWT.PUSH);
	button.setText("Invoke Browser.close()");
	button.addListener(SWT.Selection, event -> {
		boolean result = browser.close();
		System.out.println("was Browser disposed: " + result);
		if (result) {
			button.setEnabled(false);
		}
	});
	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose ();
}

}
