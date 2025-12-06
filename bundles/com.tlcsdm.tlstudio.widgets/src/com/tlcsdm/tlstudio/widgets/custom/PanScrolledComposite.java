package com.tlcsdm.tlstudio.widgets.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ScrollBar;

/**
 * A ScrolledComposite that supports click-and-drag panning and changes the
 * cursor while dragging. The drag listeners are attached to the content control
 * when setContent(...) is called.
 */
public class PanScrolledComposite extends ScrolledComposite {

	private boolean dragging = false;
	// display coordinates
	private int lastX, lastY;
	// display coordinates at mouseDown
	private int dragStartX, dragStartY;
	// pixels - small hysteresis to avoid jitter
	private int dragThreshold = 3;
	private Cursor dragCursor;

	private boolean pressed = false; // track whether left mouse button is currently down

	private MouseAdapter mouseAdapter;
	private MouseMoveListener mouseMoveListener;
	private Control attachedContent;

	public PanScrolledComposite(Composite parent, int style) {
		super(parent, style);
		dragCursor = Display.getCurrent().getSystemCursor(SWT.CURSOR_SIZEALL);

		// prepare listener instances so we can remove them later
		mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 1) {
					ScrollBar vBar = getVerticalBar();
					ScrollBar hBar = getHorizontalBar();
					boolean hasVbar = vBar != null && vBar.isEnabled() && vBar.isVisible();
					boolean hasHbar = hBar != null && hBar.isEnabled() && hBar.isVisible();
					if (hasVbar || hasHbar) {
						// store start point in display coordinates to avoid coordinate shifts while
						// panning
						Point abs = Display.getCurrent().map((Control) e.widget, null, e.x, e.y);
						dragStartX = abs.x;
						dragStartY = abs.y;
						// record that button is pressed; do not start drag yet until movement exceeds
						// threshold
						pressed = true;
						dragging = false;
						// change cursor immediately on press
						if (hasVbar && hasHbar) {
							dragCursor = Display.getCurrent().getSystemCursor(SWT.CURSOR_SIZEALL);
						} else if (hasVbar) {
							dragCursor = Display.getCurrent().getSystemCursor(SWT.CURSOR_SIZENS);
						} else if (hasHbar) {
							dragCursor = Display.getCurrent().getSystemCursor(SWT.CURSOR_SIZEWE);
						}
						setCursor(dragCursor);
					}
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				if (e.button == 1) {
					pressed = false;
					dragging = false;
					setCursor(null);
				}
			}
		};

		mouseMoveListener = new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				// only respond to moves while left button is pressed
				if (!pressed) {
					return;
				}
				// current pointer in display coordinates
				Point cur = Display.getCurrent().map((Control) e.widget, null, e.x, e.y);
				if (!dragging) {
					// check if user moved beyond threshold since mouseDown
					int dx0 = Math.abs(dragStartX - cur.x);
					int dy0 = Math.abs(dragStartY - cur.y);
					if (dx0 <= dragThreshold && dy0 <= dragThreshold) {
						// not enough movement yet
						return;
					}
					// begin dragging
					dragging = true;
					setCursor(dragCursor);
					lastX = cur.x;
					lastY = cur.y;
					// consume initial move, next move will pan
					return;
				}
				if (dragging) {
					int dx = lastX - cur.x;
					int dy = lastY - cur.y;
					if (dx == 0 && dy == 0) {
						return;
					}
					Point origin = getOrigin();
					int newX = origin.x + dx;
					int newY = origin.y + dy;
					// clamp to valid range to avoid overscrolling which can cause visual jumps
					Point min = new Point(0, 0);
					Point max = computeMaxOrigin();
					if (newX < min.x) {
						newX = min.x;
					}
					if (newY < min.y) {
						newY = min.y;
					}
					if (newX > max.x) {
						newX = max.x;
					}
					if (newY > max.y) {
						newY = max.y;
					}
					setOrigin(newX, newY);
					lastX = cur.x;
					lastY = cur.y;
				}
			}
		};
	}

	@Override
	public void setContent(Control content) {
		// detach from previous content
		if (attachedContent != null && !attachedContent.isDisposed()) {
			attachedContent.removeMouseListener(mouseAdapter);
			attachedContent.removeMouseMoveListener(mouseMoveListener);
		}

		super.setContent(content);
		attachedContent = content;
		if (content != null && !content.isDisposed()) {
			content.addMouseListener(mouseAdapter);
			content.addMouseMoveListener(mouseMoveListener);
		}
	}

	@Override
	public void dispose() {
		// detach listeners to avoid leaks
		if (attachedContent != null && !attachedContent.isDisposed()) {
			attachedContent.removeMouseListener(mouseAdapter);
			attachedContent.removeMouseMoveListener(mouseMoveListener);
		}
		super.dispose();
	}

	/**
	 * Compute the maximum origin (content size - client area) to clamp scrolling.
	 */
	private Point computeMaxOrigin() {
		Control content = getContent();
		if (content == null || content.isDisposed()) {
			return new Point(0, 0);
		}
		Point contentSize = content.getSize();
		org.eclipse.swt.graphics.Rectangle clientRect = getClientArea();
		Point clientSize = new Point(clientRect.width, clientRect.height);
		int maxX = Math.max(0, contentSize.x - clientSize.x);
		int maxY = Math.max(0, contentSize.y - clientSize.y);
		return new Point(maxX, maxY);
	}
}
