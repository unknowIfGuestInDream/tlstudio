package com.tlcsdm.tlstudio.widgets.custom;

import org.eclipse.swt.graphics.Image;

/**
 * Instances of this class are POJO to store information handled by the Launcher
 */
class LauncherItem {
	String title;
	Image image;
	LauncherLabel label;

	/**
	 * Constructor
	 *
	 * @param title text associated to the item
	 * @param image image associated to the item
	 */
	LauncherItem(final String title, final Image image) {
		this.title = title;
		this.image = image;
	}
}
