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

package org.eclipse.ui.examples.contributions.model;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

/**
 * Supply the person service to the IServiceLocator framework.
 *
 * @since 3.4
 */
public class PersonServiceFactory extends AbstractServiceFactory {

	@Override
	public Object create(Class serviceInterface, IServiceLocator parentLocator,
			IServiceLocator locator) {
		if (!IPersonService.class.equals(serviceInterface)) {
			return null;
		}
		Object parentService = parentLocator.getService(IPersonService.class);
		if (parentService == null) {
			// the global level person service implementation
			return new PersonService(locator);
		}
		return new PersonServiceSlave(locator, (IPersonService) parentService);
	}

}
