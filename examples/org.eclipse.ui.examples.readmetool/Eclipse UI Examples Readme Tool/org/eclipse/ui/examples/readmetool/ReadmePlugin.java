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
 *     Joe Bowbeer (jozart@blarg.net) - removed dependency on runtime compatibility layer (bug 74528)
 *******************************************************************************/
package org.eclipse.ui.examples.readmetool;

import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * This is the top-level class of the Readme plugin tool.
 *
 * @see AbstractUIPlugin for additional information on UI plugins
 */
public class ReadmePlugin extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.eclipse.ui.examples.readmetool"; //$NON-NLS-1$

	/**
	 * Default instance of the receiver
	 */
	private static ReadmePlugin inst;

	/**
	 * Creates the Readme plugin and caches its default instance
	 */
	public ReadmePlugin() {
		if (inst == null)
			inst = this;
	}

	/**
	 * Gets the plugin singleton.
	 *
	 * @return the default ReadmePlugin instance
	 */
	static public ReadmePlugin getDefault() {
		return inst;
	}

}
