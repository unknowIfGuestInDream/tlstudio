package com.tlcsdm.tlstudio.widgets.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.tlcsdm.tlstudio.widgets.utils.SWTGraphicUtil;

/**
 * This class represents the configuration for a given state (enable
 * state+selection state)
 */
class RoundedSwitchConfiguration {

	Color borderColor;
	Color circleColor;
	Color backgroundColor;

	private RoundedSwitchConfiguration(Color borderColor, Color circleColor, Color backgroundColor) {
		this.borderColor = borderColor;
		this.circleColor = circleColor;
		this.backgroundColor = backgroundColor;
	}

	static RoundedSwitchConfiguration createCheckedEnabledConfiguration(RoundedSwitch parent) {
		Display display = parent.getDisplay();
		RoundedSwitchConfiguration config = new RoundedSwitchConfiguration(display.getSystemColor(SWT.COLOR_BLACK), //
				display.getSystemColor(SWT.COLOR_WHITE), display.getSystemColor(SWT.COLOR_BLACK));
		return config;
	}

	static RoundedSwitchConfiguration createUncheckedEnabledConfiguration(RoundedSwitch parent) {
		Display display = parent.getDisplay();
		RoundedSwitchConfiguration config = new RoundedSwitchConfiguration(display.getSystemColor(SWT.COLOR_BLACK), //
				display.getSystemColor(SWT.COLOR_BLACK), display.getSystemColor(SWT.COLOR_WHITE));
		return config;
	}

	static RoundedSwitchConfiguration createCheckedDisabledConfiguration(RoundedSwitch parent) {
		Display display = parent.getDisplay();
		Color lightGrey = new Color(display, 233, 233, 233);
		Color darkGrey = new Color(display, 208, 208, 208);
		SWTGraphicUtil.addDisposer(parent, lightGrey, darkGrey);
		RoundedSwitchConfiguration config = new RoundedSwitchConfiguration(lightGrey, darkGrey, lightGrey);
		return config;
	}

	static RoundedSwitchConfiguration createUncheckedDisabledConfiguration(RoundedSwitch parent) {
		Display display = parent.getDisplay();
		Color lightGrey = new Color(display, 233, 233, 233);
		Color darkGrey = new Color(display, 208, 208, 208);
		SWTGraphicUtil.addDisposer(parent, lightGrey, darkGrey);
		RoundedSwitchConfiguration config = new RoundedSwitchConfiguration(lightGrey, darkGrey, lightGrey);
		return config;
	}
}
