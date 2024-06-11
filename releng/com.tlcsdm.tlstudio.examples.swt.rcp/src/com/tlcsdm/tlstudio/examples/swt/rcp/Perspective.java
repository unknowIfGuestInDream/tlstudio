package com.tlcsdm.tlstudio.examples.swt.rcp;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public final static String RCP_VIEWId = "com.tlcsdm.tlstudio.examples.swt.rcp.view";
	public final static String BROWSER_VIEWID = "org.eclipse.swt.examples.browserexample.view";
	public final static String CONTROL_VIEWID = "org.eclipse.swt.examples.controls.view";
	public final static String CUSTOM_CONTROL_VIEWID = "org.eclipse.swt.examples.customcontrols.view";
	public final static String LAUNCHER_VIEWID = "org.eclipse.swt.examples.launcher.view";
	public final static String LAYOUT_VIEWID = "org.eclipse.swt.examples.layouts.view";
	public final static String PAINT_VIEWID = "org.eclipse.swt.examples.paint.view";
	public static final String CONSOLEVIEWID = "org.eclipse.ui.console.ConsoleView";

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.createFolder("left", IPageLayout.LEFT, 0.5f, layout.getEditorArea());
		layout.createFolder("leftTop", IPageLayout.TOP, 0.5f, "left").addView(CONTROL_VIEWID);
		layout.createFolder("leftTop1", IPageLayout.TOP, 0.4f, "left").addView(LAUNCHER_VIEWID);
		layout.createFolder("leftBottom", IPageLayout.TOP, 0.1f, "left").addView(LAYOUT_VIEWID);
		layout.createFolder("right", IPageLayout.LEFT, 0.5f, layout.getEditorArea());
		layout.createFolder("rightTop", IPageLayout.TOP, 0.65f, "right").addView(CUSTOM_CONTROL_VIEWID);
		layout.createFolder("rightTop1", IPageLayout.TOP, 0.35f, "right").addView(PAINT_VIEWID);
	}

}
