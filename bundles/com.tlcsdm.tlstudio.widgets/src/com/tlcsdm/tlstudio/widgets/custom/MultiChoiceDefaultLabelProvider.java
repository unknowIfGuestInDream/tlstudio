package com.tlcsdm.tlstudio.widgets.custom;

/**
 * Default MultiChoiceLabelProvider that uses the toString() method to determine
 * the content of a given element
 */
public class MultiChoiceDefaultLabelProvider implements MultiChoiceLabelProvider {

	/**
	 * @see org.eclipse.nebula.widgets.opal.multichoice.MultiChoiceLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object element) {
		return element == null ? "" : element.toString();
	}
}
