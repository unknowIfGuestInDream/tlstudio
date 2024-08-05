/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
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
package org.eclipse.ui.forms.examples.internal;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

public abstract class OpenFormEditorAction extends AbstractHandler {

	protected Object openEditor(String inputName, String editorId, IWorkbenchWindow window) {
		return openEditor(new FormEditorInput(inputName), editorId, window);
	}

	protected Object openEditor(IEditorInput input, String editorId, IWorkbenchWindow window) {
		IWorkbenchPage page = window.getActivePage();
		try {
			page.openEditor(input, editorId);
		} catch (PartInitException e) {
			System.out.println(e);
		}
		return null;
	}
}
