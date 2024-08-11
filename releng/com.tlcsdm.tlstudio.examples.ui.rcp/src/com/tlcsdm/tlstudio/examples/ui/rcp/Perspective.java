package com.tlcsdm.tlstudio.examples.ui.rcp;

import org.eclipse.ui.IFolderLayout;
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
		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.5f, layout.getEditorArea());
		left.addView(CONTROL_VIEWID);
		left.addView(LAUNCHER_VIEWID);
		left.addView(LAYOUT_VIEWID);
		IFolderLayout right = layout.createFolder("right", IPageLayout.LEFT, 0.5f, layout.getEditorArea());
		right.addView(CUSTOM_CONTROL_VIEWID);
		right.addView(PAINT_VIEWID);
		right.addView(BROWSER_VIEWID);
		layout.createFolder("rightTop", IPageLayout.BOTTOM, 0.7f, "right").addView(CONSOLEVIEWID);
	}

}
