package com.tlcsdm.tlstudio.widgets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class WidgetsUtility {
	public static final String PLUGINID = "com.tlcsdm.tlstudio.widgets";

	private WidgetsUtility() {
		//// constructor
	}

	public static String removeTrailingZero(String s) {
		return s.indexOf('.') < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
	}

	public static Bundle getBundle() {
		return Platform.getBundle(PLUGINID);
	}

	public static URL getEntry(String path) {
		return getEntry(PLUGINID, path);
	}

	public static InputStream getInputStream(String path) {
		try {
			return new BufferedInputStream(getEntry(path).openStream());
		} catch (IOException e) {
			logException(e);
		}

		return null;
	}

	public static URL getEntry(String pluginId, String path) {
		Bundle bundle = Platform.getBundle(pluginId);
		return bundle.getEntry(path);
	}

	public static void logException(Exception e) {
		Platform.getLog(getBundle()).log(new Status(Status.ERROR, PLUGINID, e.getMessage(), e));
	}

	public static void warn(String message) {
		Platform.getLog(getBundle()).warn(message);
	}

	public static void logException(Exception e, String reason) {
		Platform.getLog(getBundle()).log(new Status(Status.ERROR, PLUGINID, reason, e));
	}

	public static void logMessage(String msg) {
		Platform.getLog(getBundle()).log(new Status(Status.INFO, PLUGINID, msg));
	}

	public static ImageDescriptor getPluginImageDescriptor(String symbolicName, String path) {
		URL url = getPluginImageURL(symbolicName, path);
		if (url != null) {
			return ImageDescriptor.createFromURL(url);
		}
		return null;
	}

	public static URL getPluginImageURL(String symbolicName, String path) {
		Bundle bundle = Platform.getBundle(symbolicName);
		if (bundle != null) {
			return bundle.getEntry(path);
		}

		// no such resource
		return null;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return getPluginImageDescriptor(PLUGINID, path);
	}

	public static String getBundlePath(String pluginId, String subpath) {

		String fullPath = null;
		Bundle bundle = Platform.getBundle(pluginId);

		try {
			if (bundle != null) {
				URL url = bundle.getEntry(subpath);
				if (url != null) {
					URL locatedURL = null;
					locatedURL = FileLocator.toFileURL(url);
					fullPath = new Path(locatedURL.getPath()).toOSString();
				}
			}
		} catch (IOException e) {
			logException(e);
		}

		return fullPath;
	}

	private static IPath getPlatformPath() {
		URL platformInstallationURL = Platform.getInstallLocation().getURL();
		String platformInstallationPath = platformInstallationURL.getPath();
		IPath newInstallationPath = new Path(platformInstallationPath);
		return newInstallationPath;
	}

	public static IPath getInstallationPath() {
		IPath newInstallationPath = getPlatformPath();
		IPath installationDirectory = newInstallationPath.removeLastSegments(1);
		return installationDirectory;
	}

	public static IPath getStateLocation() {
		return Platform.getStateLocation(FrameworkUtil.getBundle(WidgetsUtility.class));
	}

	public static File getResourceFile(String path) {
		try {
			Bundle bundle = FrameworkUtil.getBundle(WidgetsUtility.class);
			URL entryUrl = bundle.getEntry(path);
			URL fileUrl = FileLocator.toFileURL(entryUrl);
			URI uri = new URI(fileUrl.getProtocol(), fileUrl.getPath(), null);
			return new File(uri);
		} catch (IOException | URISyntaxException e) {
			logException(e, "Failed to get resource file. (" + path + ")"); //$NON-NLS-1$
		}

		return null;
	}

}
