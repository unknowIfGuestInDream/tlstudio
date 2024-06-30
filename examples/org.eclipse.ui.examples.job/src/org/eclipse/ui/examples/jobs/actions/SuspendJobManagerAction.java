/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
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
package org.eclipse.ui.examples.jobs.actions;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Toggles the suspend/resume state of the job manager.
 */
public class SuspendJobManagerAction implements IWorkbenchWindowActionDelegate {

	@Override
	public void run(IAction action) {
		try {
			if (action.isChecked())
				Job.getJobManager().suspend();
			else
				Job.getJobManager().resume();
		} catch (OperationCanceledException e) {
			//thrown if the user cancels the attempt to suspend
			e.printStackTrace();
		}
	}
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		//do nothing
	}
	@Override
	public void dispose() {
		//do nothing
	}
	@Override
	public void init(IWorkbenchWindow window) {
		//do nothing
	}
}
