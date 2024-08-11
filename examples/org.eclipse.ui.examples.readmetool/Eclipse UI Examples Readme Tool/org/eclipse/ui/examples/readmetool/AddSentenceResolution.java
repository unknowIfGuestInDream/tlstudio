/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
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
package org.eclipse.ui.examples.readmetool;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

/**
 * A resolution which inserts a sentence into the readme file
 */
public class AddSentenceResolution implements IMarkerResolution {

	@Override
	public String getLabel() {
		return MessageUtil.getString("Add_Sentence"); //$NON-NLS-1$
	}

	@Override
	public void run(IMarker marker) {
		// Se if there is an open editor on the file containing the marker
		IWorkbenchWindow w = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (w == null)
			return;
		IWorkbenchPage page = w.getActivePage();
		if (page == null)
			return;
		IFileEditorInput input = new FileEditorInput((IFile) marker.getResource());
		IEditorPart editorPart = page.findEditor(input);

		if (editorPart == null) {
			// open an editor
			try {
				editorPart = IDE.openEditor(page, (IFile) marker.getResource(), true);
			} catch (PartInitException e) {
				MessageDialog.openError(w.getShell(), MessageUtil.getString("Resolution_Error"), //$NON-NLS-1$
						MessageUtil.getString("Unable_to_open_file_editor")); //$NON-NLS-1$
			}
		}
		if (editorPart == null || !(editorPart instanceof ReadmeEditor))
			return;
		// insert the sentence
		ReadmeEditor editor = (ReadmeEditor) editorPart;
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		String s = MessageUtil.getString("Simple_sentence"); //$NON-NLS-1$
		try {
			doc.replace(marker.getAttribute(IMarker.CHAR_START, -1), 0, s);
		} catch (BadLocationException e) {
			// ignore
			return;
		}
		// delete the marker
		try {
			marker.delete();
		} catch (CoreException e) {
			e.printStackTrace();
			// ignore
		}

	}

}
