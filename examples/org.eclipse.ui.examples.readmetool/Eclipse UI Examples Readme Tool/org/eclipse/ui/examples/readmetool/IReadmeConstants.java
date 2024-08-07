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

/**
 * This interface contains constants for use only within the Readme example.
 */
public interface IReadmeConstants {
	String PLUGIN_ID = "org.eclipse.ui.examples.readmetool"; //$NON-NLS-1$

	String PREFIX = PLUGIN_ID + "."; //$NON-NLS-1$

	String P_CONTENT_OUTLINE = PREFIX + "content_outline"; //$NON-NLS-1$

	String P_SECTIONS = PREFIX + "sections"; //$NON-NLS-1$

	String EXTENSION = "readme"; //$NON-NLS-1$

	String TAG_PARSER = "parser"; //$NON-NLS-1$

	String ATT_CLASS = "class"; //$NON-NLS-1$

	String PP_SECTION_PARSER = "sectionParser"; //$NON-NLS-1$

	// Global actions
	String RETARGET2 = PREFIX + "retarget2"; //$NON-NLS-1$

	String LABELRETARGET3 = PREFIX + "labelretarget3"; //$NON-NLS-1$

	String ACTION_SET_RETARGET4 = "org_eclipse_ui_examples_readmetool_readmeRetargetAction"; //$NON-NLS-1$

	String ACTION_SET_LABELRETARGET5 = "org_eclipse_ui_examples_readmetool_readmeRelabelRetargetAction"; //$NON-NLS-1$

	// Preference constants
	String PRE_CHECK1 = PREFIX + "check1"; //$NON-NLS-1$

	String PRE_CHECK2 = PREFIX + "check2"; //$NON-NLS-1$

	String PRE_CHECK3 = PREFIX + "check3"; //$NON-NLS-1$

	String PRE_RADIO_CHOICE = PREFIX + "radio_choice"; //$NON-NLS-1$

	String PRE_TEXT = PREFIX + "text"; //$NON-NLS-1$

	// Help context ids
	String EDITOR_ACTION1_CONTEXT = PREFIX + "editor_action1_context"; //$NON-NLS-1$

	String EDITOR_ACTION2_CONTEXT = PREFIX + "editor_action2_context"; //$NON-NLS-1$

	String EDITOR_ACTION3_CONTEXT = PREFIX + "editor_action3_context"; //$NON-NLS-1$

	String SECTIONS_VIEW_CONTEXT = PREFIX + "sections_view_context"; //$NON-NLS-1$

	String PREFERENCE_PAGE_CONTEXT = PREFIX + "preference_page_context"; //$NON-NLS-1$

	String PROPERTY_PAGE_CONTEXT = PREFIX + "property_page_context"; //$NON-NLS-1$

	String PROPERTY_PAGE2_CONTEXT = PREFIX + "property_page2_context"; //$NON-NLS-1$

	String EDITOR_CONTEXT = PREFIX + "editor_context"; //$NON-NLS-1$

	String SECTIONS_DIALOG_CONTEXT = PREFIX + "sections_dialog_context"; //$NON-NLS-1$

	String CONTENT_OUTLINE_PAGE_CONTEXT = PREFIX + "content_outline_page_context"; //$NON-NLS-1$

	String CREATION_WIZARD_PAGE_CONTEXT = PREFIX + "creation_wizard_page_context"; //$NON-NLS-1$

	// Marker attributes
	String MARKER_ATT_ID = PREFIX + "id"; //$NON-NLS-1$

	String MARKER_ATT_LEVEL = PREFIX + "level"; //$NON-NLS-1$

	String MARKER_ATT_DEPT = PREFIX + "department"; //$NON-NLS-1$

	String MARKER_ATT_CODE = PREFIX + "code"; //$NON-NLS-1$

	String MARKER_ATT_LANG = PREFIX + "language"; //$NON-NLS-1$
}
