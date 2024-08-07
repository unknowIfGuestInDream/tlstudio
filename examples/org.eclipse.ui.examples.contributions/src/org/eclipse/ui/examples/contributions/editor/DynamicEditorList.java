/*******************************************************************************
 * Copyright (c) 2008, 2016 IBM Corporation and others.
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
 ******************************************************************************/

package org.eclipse.ui.examples.contributions.editor;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.examples.contributions.ContributionMessages;
import org.eclipse.ui.examples.contributions.model.PersonInput;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

/**
 * Provide a dynamic list of open editors to activate.
 *
 * @since 3.4
 */
public class DynamicEditorList extends CompoundContributionItem {
	private static final IContributionItem[] EMPTY = new IContributionItem[0];

	private static class NobodyHereContribution extends ContributionItem {
		@Override
		public void fill(Menu menu, int index) {
			MenuItem item = new MenuItem(menu, SWT.NONE, index);
			item.setText(ContributionMessages.DynamicEditorList_label);
			item.setEnabled(false);
		}
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		// maybe we can find a better way for contributed IContributionItems
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window == null) {
			return EMPTY;
		}

		IWorkbenchPage page = window.getActivePage();
		if (page == null) {
			return EMPTY;
		}
		IEditorReference[] editors = page.getEditorReferences();
		ArrayList<IContributionItem> menuList = new ArrayList<>();

		int editorNum = 1;
		for (int i = 0; i < editors.length && editorNum < 10; i++) {
			try {
				if (editors[i].getId().equals(InfoEditor.ID)) {
					menuList.add(createItem(editorNum++, editors[i]));
				}
			} catch (PartInitException e) {
			}

		}
		if (menuList.isEmpty()) {
			menuList.add(new NobodyHereContribution());
		}
		return menuList.toArray(new IContributionItem[menuList.size()]);
	}

	@SuppressWarnings("unchecked")
	private IContributionItem createItem(int i, IEditorReference ref)
			throws PartInitException {
		CommandContributionItemParameter p = new CommandContributionItemParameter(
				PlatformUI.getWorkbench(), null, ActivateEditorHandler.ID,
				CommandContributionItem.STYLE_PUSH);
		p.parameters = new HashMap<>();
		PersonInput editorInput = (PersonInput) ref.getEditorInput();
		p.parameters.put(ActivateEditorHandler.PARM_EDITOR, editorInput.getIndex());
		String menuNum = Integer.toString(i);
		p.label = menuNum + " " + ref.getTitle(); //$NON-NLS-1$
		p.mnemonic = menuNum;
		return new CommandContributionItem(p);
	}
}
