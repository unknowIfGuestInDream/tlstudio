/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
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

package org.eclipse.ui.examples.adapterservice.snippets;

import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.internal.services.EclipseAdapter;
import org.eclipse.e4.core.services.adapter.Adapter;

@SuppressWarnings("restriction")
public class SnippetSetup  {
	private static IEclipseContext context;

	public static void initializeServices() {

		// Create a context representing OSGi services
		IEclipseContext osgiContext = EclipseContextFactory.getServiceContext(SnippetActivator.bundleContext);
		Assert.isNotNull(osgiContext);

		// Now create a child context that represents Eclipse/e4 services
		context = osgiContext.createChild();
		Assert.isNotNull(context);

		// Set up the e4 services into the context
		context.set(Adapter.class.getName(), ContextInjectionFactory.make(
				EclipseAdapter.class, context));
	}

	public static void setup(Object toBeSetup) {
		ContextInjectionFactory.inject(toBeSetup, context);
	}

	public static void dispose() {
		context = null;
	}
}