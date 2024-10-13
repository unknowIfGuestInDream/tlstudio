package com.tlcsdm.tlstudio.widgets.custom;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TypedListener;

import com.tlcsdm.tlstudio.widgets.WidgetsUtility;

public abstract class AbstractCustomCanvas extends Canvas {

	protected AbstractCustomCanvas(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Fire the selection listeners of a given control
	 *
	 * @param control     the control that fires the event
	 * @param sourceEvent mouse event
	 * @return true if the selection could be changed, false otherwise
	 */
	protected boolean fireSelectionListeners(final Control control, final Event sourceEvent) {
		return fireSelectionListenersEvent(control, sourceEvent, SWT.Selection);
	}

	/**
	 * Fire the default selection listeners of a given control
	 *
	 * @param control     the control that fires the event
	 * @param sourceEvent mouse event
	 * @return true if the selection could be changed, false otherwise
	 */
	public static boolean fireDefaultSelectionListeners(final Control control, final Event sourceEvent) {
		return fireSelectionListenersEvent(control, sourceEvent, SWT.DefaultSelection);
	}

	private static boolean fireSelectionListenersEvent(final Control control, final Event sourceEvent, int type) {
		Listener[] listeners = control.getListeners(SWT.Selection);
		for (final Listener listener : listeners) {
			final Event event = new Event();

			event.button = sourceEvent == null ? 1 : sourceEvent.button;
			event.display = control.getDisplay();
			event.item = null;
			event.widget = control;
			event.data = sourceEvent == null ? null : sourceEvent.data;
			event.time = sourceEvent == null ? 0 : sourceEvent.time;
			event.x = sourceEvent == null ? 0 : sourceEvent.x;
			event.y = sourceEvent == null ? 0 : sourceEvent.y;
			event.type = type;

			listener.handleEvent(event);
			if (!event.doit) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Convert color to gray scale.
	 * 
	 * @see https://en.wikipedia.org/wiki/Grayscale#Converting_color_to_grayscale
	 */
	protected Color getGrayColor(Color color, boolean enabled) {
		if (enabled) {
			return color;
		}
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		int g = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
		return new Color(g, g, g);
	}

	/**
	 * Add a <code>SelectionListener</code> to a given Control
	 *
	 * @param control  control on which the selection listener is added
	 * @param listener listener to add
	 */
	protected void addSelectionListener(final Control control, final SelectionListener listener) {
		if (listener == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		TypedListener typedListener = new TypedListener(listener);
		control.addListener(SWT.Selection, typedListener);
	}

	/**
	 * Remove a <code>SelectionListener</code> of a given Control
	 *
	 * @param control  control on which the selection listener is removed
	 * @param listener listener to remove
	 */
	protected void removeSelectionListener(final Control control, final SelectionListener listener) {
		if (listener == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		final Listener[] listeners = control.getListeners(SWT.Selection);
		for (Listener l : listeners) {
			if (l instanceof TypedListener typedListener) {
				if (typedListener.getEventListener() == listener) {
					callMethod(control, "removeListener", SWT.Selection, typedListener.getEventListener());
					return;
				}
			}
		}
	}

	/**
	 * Call a method using introspection (so ones can call a private or protected
	 * method)
	 *
	 * @param object     object on which the method will be called
	 * @param methodName method name
	 * @param args       arguments of this method (can be null)
	 * @return the value returned by this method (if this method returns a value)
	 */
	protected final Object callMethod(final Object object, final String methodName, final Object... args) {
		if (object == null) {
			return null;
		}
		final Class<?>[] array = new Class<?>[args == null ? 0 : args.length];
		int index = 0;
		if (args != null) {
			for (final Object o : args) {
				array[index++] = o == null ? Object.class : o.getClass();
			}
		}

		return callMethodWithClassType(object, methodName, array, args);
	}

	private static Object callMethodWithClassType(final Object object, final String methodName, final Class<?>[] array,
			final Object... args) {
		Class<?> currentClass = object.getClass();
		Method method = null;
		while (currentClass != null) {
			try {
				method = currentClass.getDeclaredMethod(methodName, array);
				break;
			} catch (final NoSuchMethodException nsme) {
				currentClass = currentClass.getSuperclass();
			}
		}
		try {
			method.setAccessible(true);
			return method.invoke(object, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			WidgetsUtility.logException(e, "Fail to call method with classType");
			return null;
		}
	}

	/**
	 * Loads an image and create a SWT Image corresponding to this file
	 *
	 * @param fileName file name of the image
	 * @return an image
	 * @see org.eclipse.swt.graphics.Image
	 */
	protected Image createImageFromFile(final String fileName) {
		if (new File(fileName).exists()) {
			return new Image(Display.getCurrent(), fileName);
		} else {
			ImageDescriptor descriptor = ImageDescriptor
					.createFromURL(WidgetsUtility.getEntry("/resource/" + fileName));
			return descriptor.createImage();
		}
	}

	/**
	 * Dispose safely any SWT resource
	 *
	 * @param resource the resource to dispose
	 */
	protected void safeDispose(final Resource resource) {
		if (resource != null && !resource.isDisposed()) {
			resource.dispose();
		}
	}

	protected Color getAndDisposeColor(final int r, final int g, final int b) {
		final Color color = new Color(this.getDisplay(), r, g, b);
		this.addDisposeListener(e -> {
			if (!color.isDisposed()) {
				color.dispose();
			}
		});
		return color;
	}

}
