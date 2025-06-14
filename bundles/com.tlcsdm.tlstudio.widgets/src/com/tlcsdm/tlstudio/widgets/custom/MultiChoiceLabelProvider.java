package com.tlcsdm.tlstudio.widgets.custom;

/**
 * Classes which implement this interface provide methods that determine what to
 * show in a MultiChoice control.
 */
@FunctionalInterface
public interface MultiChoiceLabelProvider {
	/**
	 * @param element the element for which to provide the label text
	 * @return the text string used to label the element, or "" if there is no text
	 *         label for the given object
	 */
	String getText(Object element);
}
