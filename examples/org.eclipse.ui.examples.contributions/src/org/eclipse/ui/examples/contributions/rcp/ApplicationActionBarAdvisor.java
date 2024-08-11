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
 *******************************************************************************/
package org.eclipse.ui.examples.contributions.rcp;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * The action bar advisor must still be used sometimes to register workbench
 * actions that have not been converted into handlers.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction openWindow;
	private IWorkbenchAction save;
	private IWorkbenchAction saveAll;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	@Override
	protected void makeActions(final IWorkbenchWindow window) {
		// register any actions that need to be there as
		// default handlers for commands.

		openWindow = ActionFactory.OPEN_NEW_WINDOW.create(window);
		register(openWindow);

		save = ActionFactory.SAVE.create(window);
		register(save);

		saveAll = ActionFactory.SAVE_ALL.create(window);
		register(saveAll);
	}

	@Override
	public void dispose() {
		super.dispose();
		openWindow = null;
		save = null;
		saveAll = null;
	}
}
