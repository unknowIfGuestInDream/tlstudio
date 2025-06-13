package com.tlcsdm.tlstudio.widgets.custom;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

/**
 * The Slider Control is used to display a continuous or discrete range of valid
 * numeric choices and allows the user to interact with the control. It is
 * typically represented visually as having a "track" and a "knob" or "thumb"
 * which is dragged within the track.
 * <p>
 * The three fundamental variables of the slider are {@link #minimum},
 * {@link #maximum}, and {@link #value}. The value should always be a number
 * within the range defined by {@link #minimum} and {@link #maximum}. minimum
 * should always be less than or equal to maximum (although a slider whose
 * minimum and maximum are equal is a degenerate case that makes no sense).
 * minimum defaults to {@code Integer.MIN_VALUE}, whereas maximum defaults to
 * {@code Integer.MAX_VALUE}.
 * <p>
 * This first example creates a slider whose range, or span, goes from 0 to 100,
 * and whose value defaults to 50:
 *
 * <pre>
 * Slider slider = new Slider(this, SWT.NONE);
 * slider.setMinimum(0);
 * slider.setMaximum(100);
 * slider.setValue(50);
 * </pre>
 *
 * <p>
 * By default, Slider's value only supports integers. If decimal precision is
 * required, it can be set through {@link #setNumberFormat}.The next example
 * shows a slider that supports 4 digits of precision, ranging from 0 to 1::
 *
 * <pre>{@code
 * Slider slider = new Slider(this, SWT.NONE);
 * slider.setMinimum(0);
 * slider.setMaximum(1);
 * slider.setValue(0.5);
 * slider.setNumberFormat(new DecimalFormat("0.0000"));
 * }</pre>
 * 
 * 
 * <p>
 * Slider also supports customizing the color of the slider, the color of the
 * selected part: a, the color of the unselected part: b, the color of the
 * button: c
 * <ol>
 * <li>The color of the selected part: {@link #barSelectionColor}
 * <li>The color of the unselected part: {@link #barInsideColor}
 * <li>The color of the thumb: {@link #selectorColor}
 * 
 * <pre>
 * {@code
 * slider.setBarInsideColor(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
 * slider.setBarSelectionColor(Display.getDefault().getSystemColor(SWT.COLOR_DARK_BLUE));
 * slider.setSelectorColor(Display.getDefault().getSystemColor(SWT.COLOR_RED));
 * }
 * 
 * <p>
 * To configure listener of Slider value:
 *
 * <pre>
 * {@code
 * slider.addListener(SWT.Selection, e -> {
 * 	System.out.println(slider.getValue());
 * });
 * }</pre>
 */
public class Slider extends AbstractCustomCanvas {

	private double minimum;
	private double maximum;
	private double value;
	private int xPosition;
	private int mouseDeltaX;

	private Color barInsideColor;
	private Color barBorderColor;
	private Color barSelectionColor;
	private Color selectorColor;
	private Color selectorColorBorder;
	private int horizontalMargin;
	private int selectorWidth;
	private int selectorHeight;
	private int barHeight;
	private NumberFormat numberFormat;
	private double movingValue;

	private boolean moving = false;
	private int minWHint = 300;

	public Slider(Composite parent, int style) {
		super(parent, checkStyle(style) | SWT.DOUBLE_BUFFERED);

		minimum = Integer.MIN_VALUE;
		maximum = Integer.MAX_VALUE;
		value = 0;
		xPosition = -1;

		barInsideColor = getAndDisposeColor(255, 255, 255);
		barBorderColor = getAndDisposeColor(0, 0, 0);
		barSelectionColor = getAndDisposeColor(255, 255, 255);
		selectorColor = getAndDisposeColor(68, 113, 169);
		selectorColorBorder = getAndDisposeColor(238, 234, 230);
		horizontalMargin = 0;
		selectorWidth = 15;
		selectorHeight = 15;
		barHeight = 8;
		numberFormat = new DecimalFormat("0");

		addPaintListener(e -> {
			paintControl(e.gc);
		});
		addMouseListeners();
	}

	private static int checkStyle(final int style) {
		if ((style & SWT.BORDER) != 0) {
			return style & ~SWT.BORDER;
		}
		return 0;
	}

	private void paintControl(final GC gc) {
		gc.setAdvanced(true);
		gc.setAntialias(SWT.ON);

		if (xPosition < 0) {
			// Compute xPosition
			xPosition = computeXPosition();
		}

		drawBar(gc);
		drawSelectionPart(gc);
		drawSelector(gc);
	}

	private void drawBar(final GC gc) {
		final Rectangle rect = getClientArea();
		if (isEnabled()) {
			gc.setForeground(barBorderColor);
			gc.setBackground(barInsideColor);
		} else {
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
		}

		final int hMargin = horizontalMargin;
		final int x = hMargin + selectorWidth / 2;
		final int y = (rect.height - barHeight) / 2;
		final int width = rect.width - hMargin * 2 - selectorWidth;

		gc.fillRoundRectangle(x, y, width, barHeight, barHeight, barHeight);
		gc.drawRoundRectangle(x, y, width, barHeight, barHeight, barHeight);
	}

	private void drawSelectionPart(final GC gc) {
		final Rectangle rect = getClientArea();
		if (isEnabled()) {
			gc.setForeground(barBorderColor);
			gc.setBackground(barSelectionColor);
		} else {
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
		}

		final int x = horizontalMargin + selectorWidth / 2;
		final int y = (rect.height - barHeight) / 2;

		gc.fillRoundRectangle(x, y, xPosition, barHeight, barHeight, barHeight);
		gc.drawRoundRectangle(x, y, xPosition, barHeight, barHeight, barHeight);
	}

	private int computeXPosition() {
		final int originalWidth = getClientArea().width - horizontalMargin * 2 - selectorWidth;
		final float coeff = (float) (value * 1f / (maximum - minimum));
		return (int) (coeff * originalWidth);
	}

	private void drawSelector(final GC gc) {
		final Rectangle rect = getClientArea();
		if (isEnabled()) {
			gc.setForeground(selectorColorBorder);
			gc.setBackground(selectorColor);
		} else {
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
		}

		final int hMargin = horizontalMargin;
		final int y = (rect.height - selectorHeight) / 2;

		// Draw the body
		gc.fillRoundRectangle(hMargin + xPosition, y, selectorWidth, selectorHeight, selectorHeight, selectorHeight);
		gc.drawRoundRectangle(hMargin + xPosition, y, selectorWidth, selectorHeight, selectorHeight, selectorHeight);
	}

	private void addMouseListeners() {
		addListener(SWT.MouseDown, e -> {
			final int y = (getClientArea().height - selectorHeight) / 2;
			final Rectangle rect = new Rectangle(xPosition + horizontalMargin, y, selectorWidth, selectorHeight);
			if (!rect.contains(e.x, e.y)) {
				final double val = getCursorValue(e.x, e.y);
				if (val >= 0) {
					setValue(val);
					fireSelectionListeners(this, e);
				}
				return;
			}
			moving = true;
			movingValue = value;
			mouseDeltaX = xPosition - e.x;
		});

		addListener(SWT.MouseUp, e -> {
			moving = false;
			mouseDeltaX = 0;
			if (movingValue != value) {
				fireDefaultSelectionListeners(this, e);
			}
			redraw();
		});

		addListener(SWT.MouseMove, e -> {
			if (!moving) {
				return;
			}

			xPosition = e.x + mouseDeltaX;
			if (xPosition < 0) {
				xPosition = 0;
			}
			final int originalWidth = getClientArea().width - horizontalMargin * 2 - selectorWidth;

			if (xPosition > originalWidth) {
				xPosition = originalWidth;
			}

			// Update value
			final float ratio = (float) xPosition / originalWidth;
			double val = ratio * (maximum - minimum) + minimum;
			if (this.value != val) {
				this.value = val;
				fireSelectionListeners(this, e);
			}
			redraw();
		});
	}

	/**
	 * if the input coordinate is within the scale bounds, return the corresponding
	 * scale value of the coordinate. otherwise return -1.
	 *
	 * @param x x coordinate value
	 * @param y y coordinate value
	 * @return
	 */
	private double getCursorValue(int x, int y) {
		double val = -1;
		final Rectangle clientArea = getClientArea();
		if (x < (clientArea.width - (horizontalMargin + selectorWidth / 2))
				&& x >= (horizontalMargin + selectorWidth / 2) && y >= (clientArea.height - barHeight) / 2
				&& y <= ((clientArea.height - barHeight) / 2 + barHeight)) {
			val = ((x - (horizontalMargin + selectorWidth / 2)) / computePixelSizeForHorizontalSlider()) + minimum;
		}
		return val;
	}

	/**
	 * @return how many pixels corresponds to 1 point of value
	 */
	private float computePixelSizeForHorizontalSlider() {
		return (float) ((getClientArea().width - horizontalMargin * 2 - selectorWidth) / (maximum - minimum));
	}

	/**
	 * Adds the listener to the collection of listeners who will be notified when
	 * the control is selected by the user, by sending it one of the messages
	 * defined in the <code>SelectionListener</code> interface.
	 * <p>
	 * <code>widgetDefaultSelected</code> is not called.
	 * </p>
	 *
	 * @param listener the listener which should be notified when the control is
	 *                 selected by the user,
	 * @exception IllegalArgumentException
	 * @exception org.eclipse.swt.SWTException
	 * @see SelectionListener
	 * @see #removeSelectionListener
	 * @see SelectionEvent
	 */
	public void addSelectionListener(final SelectionListener listener) {
		checkWidget();
		addSelectionListener(this, listener);
	}

	/**
	 * @see org.eclipse.swt.widgets.Control#computeSize(int, int, boolean)
	 */
	@Override
	public Point computeSize(final int wHint, final int hHint, final boolean changed) {
		return new Point(Math.max(minWHint, wHint), selectorHeight + 2);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		xPosition = -1;
		super.setBounds(x, y, width, height);
	}

	@Override
	public void setEnabled(boolean enabled) {
		if (!enabled && moving) {
			moving = false;
		}
		super.setEnabled(enabled);
		GC gc = new GC(this);
		paintControl(gc);
		gc.dispose();
	}

	/**
	 * Removes the listener from the collection of listeners who will be notified
	 * when the control is selected by the user.
	 *
	 * @param listener the listener which should no longer be notified
	 * @exception IllegalArgumentException
	 * @exception org.eclipse.swt.SWTException
	 * @see SelectionListener
	 * @see #addSelectionListener
	 */
	public void removeSelectionListener(final SelectionListener listener) {
		checkWidget();
		removeSelectionListener(this, listener);
	}

	/**
	 * Returns the minimum value which the receiver will allow.
	 *
	 * @return the minimum
	 * @exception org.eclipse.swt.SWTException
	 */
	public double getMinimum() {
		checkWidget();
		return minimum;
	}

	/**
	 * Sets the minimum value. If this value is greater than the maximum, an
	 * exception is thrown
	 *
	 * @param value the new minimum
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setMinimum(final double minimum) {
		checkWidget();
		if (minimum > maximum) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT, new IllegalArgumentException(
					String.format("Value %d is greater than the maximum value (%d)", minimum, maximum)));
		}
		this.minimum = minimum;
		redraw();
		update();
	}

	/**
	 * Returns the maximum value which the receiver will allow.
	 *
	 * @return the maximum
	 * @exception org.eclipse.swt.SWTException
	 */
	public double getMaximum() {
		checkWidget();
		return maximum;
	}

	/**
	 * Sets the maximum value. If this value is lower than the minimum, an exception
	 * is thrown
	 *
	 * @param value the new minimum
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setMaximum(final double maximum) {
		checkWidget();
		if (maximum < minimum) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT, new IllegalArgumentException(
					String.format("Value %d is lower than the minimum value (%d)", maximum, minimum)));
		}
		this.maximum = maximum;
		redraw();
		update();
	}

	/**
	 * Returns the receiver's value.
	 *
	 * @return the selection
	 * @exception org.eclipse.swt.SWTException
	 */
	public double getValue() {
		checkWidget();
		return Double.parseDouble(numberFormat.format(value));
	}

	/**
	 * Sets the receiver's value. If the value is lower to minimum or greater than
	 * the maximum, an exception is thrown
	 *
	 * @param value the new selection (must be zero or greater)
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setValue(final double value) {
		checkWidget();
		if (value < minimum || value > maximum) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT, new IllegalArgumentException(
					String.format("Value %d is not int the range [%d - %d]", value, minimum, maximum)));
		}
		this.value = value;
		xPosition = -1;
		redraw();
		update();
	}

	/**
	 * Sets the barInsideColor for this widget
	 *
	 * @param barInsideColor
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setBarInsideColor(Color barInsideColor) {
		checkWidget();
		this.barInsideColor = barInsideColor;
		redraw();
	}

	/**
	 * Sets the barBorderColor for this widget
	 *
	 * @param barBorderColor
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setBarBorderColor(Color barBorderColor) {
		checkWidget();
		this.barBorderColor = barBorderColor;
		redraw();
	}

	/**
	 * Sets the barSelectionColor for this widget
	 *
	 * @param barSelectionColor
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setBarSelectionColor(Color barSelectionColor) {
		checkWidget();
		this.barSelectionColor = barSelectionColor;
		redraw();
	}

	/**
	 * Sets the selectorColor for this widget
	 *
	 * @param selectorColor
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setSelectorColor(Color selectorColor) {
		checkWidget();
		this.selectorColor = selectorColor;
		redraw();
	}

	/**
	 * Sets the selectorColorBorder for this widget
	 *
	 * @param selectorColorBorder
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setSelectorColorBorder(Color selectorColorBorder) {
		checkWidget();
		this.selectorColorBorder = selectorColorBorder;
		redraw();
	}

	/**
	 * Sets the horizontalMargin for this widget
	 *
	 * @param horizontalMargin
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setHorizontalMargin(int horizontalMargin) {
		checkWidget();
		this.horizontalMargin = horizontalMargin;
		redraw();
	}

	/**
	 * Sets the selectorWidth for this widget
	 *
	 * @param selectorWidth
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setSelectorWidth(int selectorWidth) {
		checkWidget();
		this.selectorWidth = selectorWidth;
		redraw();
	}

	/**
	 * Sets the selectorHeight for this widget
	 *
	 * @param selectorHeight
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setSelectorHeight(int selectorHeight) {
		checkWidget();
		this.selectorHeight = selectorHeight;
		redraw();
	}

	/**
	 * Sets the barHeight for this widget
	 *
	 * @param barHeight
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setBarHeight(int barHeight) {
		checkWidget();
		this.barHeight = barHeight;
		redraw();
	}

	/**
	 * Sets the numberFormat for this widget
	 *
	 * @param numberFormat
	 */
	public void setNumberFormat(NumberFormat numberFormat) {
		this.numberFormat = numberFormat;
	}

}
