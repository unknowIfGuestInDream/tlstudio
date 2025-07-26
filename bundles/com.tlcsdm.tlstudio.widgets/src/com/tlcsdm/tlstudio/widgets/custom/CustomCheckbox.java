package com.tlcsdm.tlstudio.widgets.custom;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

public class CustomCheckbox extends Canvas {

	private ListenerList selectionListener = new ListenerList();

	private boolean isSelected;

	private String text;

	private Point textExtent;

	private Color checkColor;

	private Color centerColor;

	public CustomCheckbox(Composite parent, int style) {
		super(parent, style);
		addListeners();
	}

	private void addListeners() {
		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;

				gc.setBackground(getCenterColor() != null ? getCenterColor()
						: getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
				gc.setForeground(getForeground());
				gc.fillRoundRectangle(4, 4, 16, 16, 6, 6);

				gc.drawRoundRectangle(4, 4, 16, 16, 6, 6);

				if (getSelection()) {
					gc.setLineWidth(4);
					gc.setForeground(getCheckColor() != null ? getCheckColor()
							: getDisplay().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));

					gc.drawLine(6, 7, 13, 16);

					gc.drawLine(11, 16, 21, 0);
				}

				textExtent = gc.textExtent(text);

				gc.setForeground(getForeground());
				gc.setBackground(getBackground());
				gc.drawText(text, 24, 4);
			}
		});

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				setSelection(!getSelection());

				notifySelectionListener(e);

				redraw();
			}

		});
	}

	protected void notifySelectionListener(MouseEvent e) {
		checkWidget();
		Object[] listeners = selectionListener.getListeners();
		for (Object listener : listeners) {
			Event event = new Event();
			event.widget = e.widget;
			SelectionEvent selectionEvent = new SelectionEvent(event);
			selectionEvent.data = e.data;
			selectionEvent.display = e.display;
			selectionEvent.stateMask = e.stateMask;
			selectionEvent.x = e.x;
			selectionEvent.y = e.y;
			((SelectionListener) listener).widgetSelected(selectionEvent);
		}
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		checkWidget();
		int textWidth = textExtent != null ? textExtent.x : 0;
		return super.computeSize(Math.max(26 + textWidth, wHint), Math.max(26, hHint), changed);
	}

	public boolean getSelection() {
		checkWidget();
		return isSelected;
	}

	public void setSelection(boolean isSelected) {
		checkWidget();
		this.isSelected = isSelected;
	}

	public void setText(String string) {
		checkWidget();
		if (string == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		if ((getStyle() & SWT.ARROW) != 0) {
			return;
		}
		text = string;

		redraw();
	}

	public String getText() {
		checkWidget();
		if ((getStyle() & SWT.ARROW) != 0) {
			return "";
		}
		return text;
	}

	public void addSelectionListener(SelectionListener listener) {
		checkWidget();
		if (null == listener) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		selectionListener.add(listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		checkWidget();
		if (null == listener) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		selectionListener.remove(listener);
	}

	public Color getCheckColor() {
		checkWidget();
		return checkColor;
	}

	public void setCheckColor(Color checkColor) {
		checkWidget();
		this.checkColor = checkColor;
	}

	public Color getCenterColor() {
		checkWidget();
		return centerColor;
	}

	public void setCenterColor(Color centerColor) {
		checkWidget();
		this.centerColor = centerColor;
	}
}
