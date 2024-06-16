/*******************************************************************************
 * Copyright (c) 2000, 2018 IBM Corporation and others.
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
 * Browser example snippet: call Java from JavaScript.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 *
 * @since 3.5
 */
import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet307 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setText("Snippet 307");
	shell.setLayout (new FillLayout ());
	shell.setBounds (10,10,300,200);

	final Browser browser;
	try {
		browser = new Browser (shell, SWT.NONE);
	} catch (SWTError e) {
		System.out.println ("Could not instantiate Browser: " + e.getMessage ());
		display.dispose();
		return;
	}
	browser.setText (createHTML ());
	final BrowserFunction function = new CustomFunction (browser, "theJavaFunction");

	browser.addProgressListener(ProgressListener.completedAdapter(event -> {
		browser.addLocationListener(new LocationAdapter() {
			@Override
			public void changed(LocationEvent event) {
				browser.removeLocationListener(this);
				System.out.println("left java function-aware page, so disposed CustomFunction");
				function.dispose();
			}
		});
	}));

	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ())
			display.sleep ();
	}
	display.dispose ();
}

static class CustomFunction extends BrowserFunction {
	CustomFunction (Browser browser, String name) {
		super (browser, name);
	}
	@Override
	public Object function (Object[] arguments) {
		System.out.println ("theJavaFunction() called from javascript with args:");
		for (Object arg : arguments) {
			if (arg == null) {
				System.out.println ("\t-->null");
			} else {
				System.out.println ("\t-->" + arg.getClass ().getName () + ": " + arg.toString ());
			}
		}
		Object returnValue = new Object[] {
			Short.valueOf ((short)3),
			true,
			null,
			new Object[] {"a string", false},
			"hi",
			Float.valueOf (2.0f / 3.0f),
		};
		//int z = 3 / 0; // uncomment to cause a java error instead
		return returnValue;
	}
}

static String createHTML () {
	StringBuilder buffer = new StringBuilder ();
	buffer.append ("<html>\n");
	buffer.append ("<head>\n");
	buffer.append ("<script language=\"JavaScript\">\n");
	buffer.append ("function function1() {\n");
	buffer.append ("    var result;\n");
	buffer.append ("    try {\n");
	buffer.append ("        result = theJavaFunction(12, false, null, [3.6, ['swt', true]], 'eclipse');\n");
	buffer.append ("    } catch (e) {\n");
	buffer.append ("        alert('a java error occurred: ' + e.message);\n");
	buffer.append ("        return;\n");
	buffer.append ("    }\n");
	buffer.append ("    for (var i = 0; i < result.length; i++) {\n");
	buffer.append ("        alert('returned ' + i + ': ' + result[i]);\n");
	buffer.append ("    }\n");
	buffer.append ("}\n");
	buffer.append ("</script>\n");
	buffer.append ("</head>\n");
	buffer.append ("<body>\n");
	buffer.append ("<input id=button type=\"button\" value=\"Push to Invoke Java\" onclick=\"function1();\">\n");
	buffer.append ("<p><a href=\"http://www.eclipse.org\">go to eclipse.org</a>\n");
	buffer.append ("</body>\n");
	buffer.append ("</html>\n");
	return buffer.toString ();
}

}
