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

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * This class encapsulates property sheet properties for MarkElement. This will
 * display properties for the MarkElement when selected in the readme editor's
 * content outline.
 */
public class MarkElementProperties implements IPropertySource {
	protected MarkElement element;

	protected static final String PROPERTY_LINECOUNT = "lineno"; //$NON-NLS-1$

	protected static final String PROPERTY_START = "start"; //$NON-NLS-1$

	protected static final String PROPERTY_LENGTH = "length"; //$NON-NLS-1$

	/**
	 * Creates a new MarkElementProperties.
	 *
	 * @param element the element whose properties this instance represents
	 */
	public MarkElementProperties(MarkElement element) {
		super();
		this.element = element;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		// Create the property vector.
		IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[3];

		// Add each property supported.
		PropertyDescriptor descriptor;

		descriptor = new PropertyDescriptor(PROPERTY_LINECOUNT, MessageUtil.getString("Line_count")); //$NON-NLS-1$
		propertyDescriptors[0] = descriptor;
		descriptor = new PropertyDescriptor(PROPERTY_START, MessageUtil.getString("Title_start")); //$NON-NLS-1$
		propertyDescriptors[1] = descriptor;
		descriptor = new PropertyDescriptor(PROPERTY_LENGTH, MessageUtil.getString("Title_length")); //$NON-NLS-1$
		propertyDescriptors[2] = descriptor;

		// Return it.
		return propertyDescriptors;
	}

	@Override
	public Object getPropertyValue(Object name) {
		if (name.equals(PROPERTY_LINECOUNT))
			return Integer.valueOf(element.getNumberOfLines());
		if (name.equals(PROPERTY_START))
			return Integer.valueOf(element.getStart());
		if (name.equals(PROPERTY_LENGTH))
			return Integer.valueOf(element.getLength());
		return null;
	}

	@Override
	public boolean isPropertySet(Object property) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object property) {
		// do nothing
	}

	@Override
	public void setPropertyValue(Object name, Object value) {
		// do nothing
	}
}
