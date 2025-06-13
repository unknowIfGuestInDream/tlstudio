package com.tlcsdm.tlstudio.widgets.custom;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

/**
 * The RangeSlider control supports for two 'thumbs', rather than one. A thumb
 * is the non-technical name for the draggable area inside the Slider.
 *
 * <p>
 * Because the RangeSlider has two thumbs, it also has a few additional rules
 * and user interactions:
 *
 * <ol>
 * <li>The 'lower value' thumb can not move past the 'higher value' thumb.
 * Whereas the Slider control only has one.
 * <li>the RangeSlider has a {@link #lowerValue low value} and a
 * {@link #upperValue high value}, represented by the 'low value' and 'high
 * value' thumbs.
 * <li>The area between the low and high values represents the allowable range.
 * For example, if the low value is 2 and the high value is 8, then the
 * allowable range is between 2 and 8.
 *
 * <h3>Code Samples</h3>
 * <p>
 * Instantiating a RangeSlider is simple. The first decision is to decide
 * whether a horizontal or a vertical track is more appropriate. By default
 * RangeSlider instances are horizontal, but this can be changed by setting the
 * {@link #orientation}.
 * 
 * <pre>{@code
 * RangeSlider hSlider = new RangeSlider(this, SWT.HORIZONTAL);
 * RangeSlider vSlider = new RangeSlider(this, SWT.VERTICAL);
 * }
 * <p>
 * Once the orientation is determined, the next most important decision is to
 * determine what the {@link #minimum} / {@link #maximum}
 * and default {@link #lowerValue} / {@link #upperValue} values. 
 * The minimum / maximum values represent the smallest and largest
 * legal values for the thumbs to be set to, whereas the lowerValue / upperValue values
 * represent where the thumbs are currently, within the bounds of the min / max
 * values. All four values are required in all circumstances, 
 * and integer precision is used by default.
 * <p>
 * For example, here is a simple horizontal RangeSlider that has a minimum value
 * of 0, a maximum value of 100, a low value of 10 and a high value of 90:
 *
 * <pre>{@code
 * final RangeSlider hSlider = new RangeSlider(this, SWT.HORIZONTAL);
 * hSlider.setMinimum(0);
 * hSlider.setMaximum(100);
 * hSlider.setLowerValue(10);
 * hSlider.setUpperValue(90);
 * }</pre>
 *
 * <p>
 * To configure listener of RangeSlider value:
 *
 * <pre>
 * {@code
 * hSlider.addSelectionListener(new SelectionAdapter() {
 * 	@Override
 * 	public void widgetSelected(final SelectionEvent e) {
 * 		System.out.println(hSlider.getLowerValue());
 * 		System.out.println(hSlider.getUpperValue());
 * 	}
 * });
 * }</pre>
 */
public class RangeSlider extends AbstractCustomCanvas {

	private static final byte NONE = 0;
	private static final byte UPPER = 1 << 0;
	private static final byte LOWER = 1 << 1;
	private static final byte BOTH = UPPER | LOWER;
	private int minWHint = 300;
	private int minHHint = 30;
	private int barHeight = 8;
	private int hOffset = 5;
	private int vOffset = 5;

	private Color selectionColor;
	private double minimum;
	private double maximum;
	private double lowerValue;
	private double upperValue;
	private final Image slider, sliderHover, sliderDrag, sliderSelected;
	private final Image vSlider, vSliderHover, vSliderDrag, vSliderSelected;
	private int orientation;
	private int increment;
	private int pageIncrement;
	private byte selectedElement, priorSelectedElement;
	private boolean dragInProgress;
	private boolean upperHover, lowerHover;
	private double previousUpperValue, previousLowerValue;
	private double startDragUpperValue, startDragLowerValue;
	private Point startDragPoint;
	private boolean hasFocus;
	private final boolean isSmooth;
	private final boolean isFullSelection;
	private final boolean isHighQuality;
	private final boolean isOn;
	private Format toolTipFormatter;
	private String clientToolTipText;
	private NumberFormat numberFormat;

	public RangeSlider(Composite parent, int style) {
		super(parent, SWT.DOUBLE_BUFFERED | ((style & SWT.BORDER) == SWT.BORDER ? SWT.BORDER : SWT.NONE));
		minimum = lowerValue = 0;
		maximum = upperValue = 100;
		increment = 1;
		pageIncrement = 10;
		selectionColor = getAndDisposeColor(68, 113, 169);
		slider = createImageFromFile("custom/rangeslider/slider-normal.png");
		sliderHover = createImageFromFile("custom/rangeslider/slider-hover.png");
		sliderDrag = createImageFromFile("custom/rangeslider/slider-drag.png");
		sliderSelected = createImageFromFile("custom/rangeslider/slider-selected.png");

		vSlider = createImageFromFile("custom/rangeslider/h-slider-normal.png");
		vSliderHover = createImageFromFile("custom/rangeslider/h-slider-hover.png");
		vSliderDrag = createImageFromFile("custom/rangeslider/h-slider-drag.png");
		vSliderSelected = createImageFromFile("custom/rangeslider/h-slider-selected.png");
		numberFormat = new DecimalFormat("0");

		if ((style & SWT.VERTICAL) == SWT.VERTICAL) {
			orientation = SWT.VERTICAL;
		} else {
			orientation = SWT.HORIZONTAL;
		}
		isSmooth = (style & SWT.SMOOTH) == SWT.SMOOTH;
		isFullSelection = (style & SWT.CONTROL) == SWT.CONTROL;
		isHighQuality = (style & SWT.HIGH) == SWT.HIGH;
		isOn = (style & SWT.ON) == SWT.ON;
		selectedElement = isFullSelection ? BOTH : LOWER;

		addListener(SWT.Dispose, event -> {
			safeDispose(slider);
			safeDispose(sliderHover);
			safeDispose(sliderDrag);
			safeDispose(sliderSelected);

			safeDispose(vSlider);
			safeDispose(vSliderHover);
			safeDispose(vSliderDrag);
			safeDispose(vSliderSelected);
		});
		addMouseListeners();
		addListener(SWT.FocusIn, e -> {
			hasFocus = true;
			redraw();
		});
		addListener(SWT.FocusOut, e -> {
			hasFocus = false;
			redraw();
		});
		addListener(SWT.KeyDown, this::handleKeyDown);
		addPaintListener(this::drawWidget);
	}

	@Override
	public int getStyle() {
		return super.getStyle() | orientation | (isSmooth ? SWT.SMOOTH : SWT.NONE) | //
				(isOn ? SWT.ON : SWT.NONE) | //
				(isFullSelection ? SWT.CONTROL : SWT.NONE) | //
				(isHighQuality ? SWT.HIGH : SWT.NONE);
	}

	/**
	 * Add the mouse listeners (mouse up, mouse down, mouse move, mouse wheel)
	 */
	private void addMouseListeners() {
		addListener(SWT.MouseDown, this::handleMouseDown);
		addListener(SWT.MouseUp, this::handleMouseUp);
		addListener(SWT.MouseMove, this::handleMouseMove);
		addListener(SWT.MouseWheel, this::handleMouseWheel);
		addListener(SWT.MouseHover, this::handleMouseHover);
		addListener(SWT.MouseDoubleClick, this::handleMouseDoubleClick);
	}

	/**
	 * Code executed when the mouse is down
	 *
	 * @param e event
	 */
	private void handleMouseDown(final Event e) {
		selectKnobs(e);
		if (e.count == 1) {
			priorSelectedElement = selectedElement;
		}
		if (upperHover || lowerHover) {
			selectedElement = isFullSelection && lowerHover && upperHover ? BOTH
					: lowerHover ? LOWER : upperHover ? UPPER : selectedElement;
			dragInProgress = true;
			startDragLowerValue = previousLowerValue = lowerValue;
			startDragUpperValue = previousUpperValue = upperValue;
			startDragPoint = new Point(e.x, e.y);
		}
	}

	/**
	 * Code executed when the mouse is up
	 *
	 * @param e event
	 */
	private void handleMouseUp(final Event e) {
		if (dragInProgress) {
			startDragPoint = null;
			validateNewValues(e);
			dragInProgress = false;
			super.setToolTipText(clientToolTipText);
		}
	}

	/**
	 * invoke selection listeners if either upper or lower value has changed. if
	 * listeners reject the change, restore the previous values. redraw if either
	 * upper or lower value has changed.
	 *
	 * @param e event
	 */
	private void validateNewValues(final Event e) {
		if (upperValue != previousUpperValue || lowerValue != previousLowerValue) {
			if (!fireSelectionListeners(this, e)) {
				upperValue = previousUpperValue;
				lowerValue = previousLowerValue;
			}
			previousUpperValue = upperValue;
			previousLowerValue = lowerValue;
			redraw();
		}
	}

	/**
	 * Code executed when the mouse pointer is moving
	 *
	 * @param e event
	 */
	private void handleMouseMove(final Event e) {
		if (!dragInProgress) {
			final boolean wasUpper = upperHover;
			final boolean wasLower = lowerHover;
			selectKnobs(e);
			if (wasUpper != upperHover || wasLower != lowerHover) {
				redraw();
			}
		} else {
			final int x = e.x, y = e.y;
			if (orientation == SWT.HORIZONTAL) {
				if (selectedElement == BOTH) {
					final double diff = ((startDragPoint.x - x) / computePixelSizeForHorizontalSlider()) + minimum;
					double newUpper = startDragUpperValue - diff;
					double newLower = startDragLowerValue - diff;
					if (newUpper > maximum) {
						newUpper = maximum;
						newLower = maximum - (startDragUpperValue - startDragLowerValue);
					} else if (newLower < minimum) {
						newLower = minimum;
						newUpper = minimum + startDragUpperValue - startDragLowerValue;
					}
					upperValue = newUpper;
					lowerValue = newLower;
					if (!isSmooth) {
						lowerValue = (lowerValue / increment) * increment - increment;
						upperValue = (upperValue / increment) * increment - increment;
					}
					handleToolTip(lowerValue, upperValue);
				} else if ((selectedElement & UPPER) != 0) {
					upperValue = (x - 9d) / computePixelSizeForHorizontalSlider() + minimum;
					if (!isSmooth) {
						upperValue = (upperValue / increment) * increment - increment;
					}
					checkUpperValue();
					handleToolTip(upperValue);
				} else {
					lowerValue = (x - 9d) / computePixelSizeForHorizontalSlider() + minimum;
					if (!isSmooth) {
						lowerValue = (lowerValue / increment) * increment - increment;
					}
					checkLowerValue();
					handleToolTip(lowerValue);
				}
			} else {
				if (selectedElement == BOTH) {
					final double diff = ((startDragPoint.y - y) / computePixelSizeForVerticalSlider()) + minimum;
					double newUpper = startDragUpperValue - diff;
					double newLower = startDragLowerValue - diff;
					if (newUpper > maximum) {
						newUpper = maximum;
						newLower = maximum - (startDragUpperValue - startDragLowerValue);
					} else if (newLower < minimum) {
						newLower = minimum;
						newUpper = minimum + startDragUpperValue - startDragLowerValue;
					}
					upperValue = newUpper;
					lowerValue = newLower;
					if (!isSmooth) {
						lowerValue = (lowerValue / increment) * increment - increment;
						upperValue = (upperValue / increment) * increment - increment;
					}
					handleToolTip(lowerValue, upperValue);
				} else if ((selectedElement & UPPER) != 0) {
					upperValue = (y - 9d) / computePixelSizeForVerticalSlider() + minimum;
					if (!isSmooth) {
						upperValue = (upperValue / increment) * increment - increment;
					}
					checkUpperValue();
					handleToolTip(upperValue);
				} else {
					lowerValue = (y - 9d) / computePixelSizeForVerticalSlider() + minimum;
					if (!isSmooth) {
						lowerValue = (lowerValue / increment) * increment - increment;
					}
					checkLowerValue();
					handleToolTip(lowerValue);
				}
			}
			if (isOn) {
				validateNewValues(e);
			} else {
				redraw();
			}
		}
	}

	/**
	 * determine whether the input coordinate is within the scale bounds and between
	 * the current upper and lower values.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isBetweenKnobs(int x, int y) {
		return orientation == SWT.HORIZONTAL
				? x < coordUpper.x && x > coordLower.x && y >= 9 && y <= 9 + getClientArea().height - 20
				: y < coordUpper.y && y > coordLower.y && x >= 9 && x <= 9 + getClientArea().width - 20;
	}

	/**
	 * set the upperHover and lowerHover values according to the coordinates of the
	 * input event, the key modifier state, and whether the style allows selection
	 * of both knobs.
	 *
	 * @param e
	 */
	private void selectKnobs(final Event e) {
		if (coordLower == null) {
			return;
		}
		final Image img = orientation == SWT.HORIZONTAL ? slider : vSlider;
		final int x = e.x, y = e.y;
		lowerHover = x >= coordLower.x && x <= coordLower.x + img.getBounds().width && y >= coordLower.y
				&& y <= coordLower.y + img.getBounds().height;
		upperHover = ((e.stateMask & (SWT.CTRL | SWT.SHIFT)) != 0 || !lowerHover) && //
				x >= coordUpper.x && x <= coordUpper.x + img.getBounds().width && //
				y >= coordUpper.y && y <= coordUpper.y + img.getBounds().height;
		lowerHover &= (e.stateMask & SWT.CTRL) != 0 || !upperHover;
		if (!lowerHover && !upperHover && isFullSelection && isBetweenKnobs(x, y)) {
			lowerHover = upperHover = true;
		}
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
		double value = -1;
		final Rectangle clientArea = getClientArea();
		if (orientation == SWT.HORIZONTAL) {
			if (x < 9 + clientArea.width - 20 && x >= 9 && y >= 9 && y <= 9 + clientArea.height - 20) {
				value = (x - 9d) / computePixelSizeForHorizontalSlider() + minimum;
			}
		} else if (y < 9 + clientArea.height - 20 && y >= 9 && x >= 9 && x <= 9 + clientArea.width - 20) {
			value = (y - 9d) / computePixelSizeForVerticalSlider() + minimum;
		}
		return value;
	}

	/**
	 * Code executed when the mouse double click
	 *
	 * @param e event
	 */
	private void handleMouseDoubleClick(final Event e) {
		final double value = getCursorValue(e.x, e.y);
		if (value >= 0) {
			selectedElement = priorSelectedElement;
			if (value > upperValue) {
				if (selectedElement == BOTH) {
					lowerValue += value - upperValue;
					upperValue = value;
				} else if ((selectedElement & UPPER) != 0) {
					upperValue = value;
				} else if ((selectedElement & LOWER) != 0) {
					final double diff = upperValue - lowerValue;
					if (value + diff > maximum) {
						upperValue = maximum;
						lowerValue = maximum - diff;
					} else {
						upperValue = value + diff;
						lowerValue = value;
					}
				}
			} else if (value < lowerValue) {
				if (selectedElement == BOTH) {
					upperValue += value - lowerValue;
					lowerValue = value;
				} else if ((selectedElement & LOWER) != 0) {
					lowerValue = value;
				} else if ((selectedElement & UPPER) != 0) {
					final double diff = upperValue - lowerValue;
					if (value - diff < minimum) {
						lowerValue = minimum;
						upperValue = minimum + diff;
					} else {
						upperValue = value;
						lowerValue = value - diff;
					}
				}
			} else if (value > lowerValue && value < upperValue && selectedElement != BOTH) {
				if ((selectedElement & LOWER) != 0) {
					lowerValue = value;
				} else if ((selectedElement & UPPER) != 0) {
					upperValue = value;
				}
			}
			validateNewValues(e);
		}
	}

	private StringBuffer toolTip;
	private Point coordUpper;
	private Point coordLower;

	/**
	 * set the tooltip if a toolTipFormatter is present. either one or two values
	 * are accepted.
	 *
	 * @param values
	 */
	private void handleToolTip(double... values) {
		if (toolTipFormatter != null) {
			try {
				if (values.length == 1) {
					toolTip.setLength(0);
					toolTipFormatter.format(values[0], toolTip, null);
					super.setToolTipText(toolTip.toString());
				} else if (values.length == 2) {
					toolTip.setLength(0);
					toolTipFormatter.format(values[0], toolTip, null);
					toolTip.append(" \u2194 ");
					toolTipFormatter.format(values[1], toolTip, null);
					super.setToolTipText(toolTip.toString());
				}
			} catch (final IllegalArgumentException ex) {
				super.setToolTipText(clientToolTipText);
			}
		}
	}

	/**
	 * Code executed on mouse hover
	 *
	 * @param e event
	 */
	private void handleMouseHover(final Event e) {
		if (!dragInProgress && toolTipFormatter != null) {
			final double value = getCursorValue(e.x, e.y);
			if (value >= 0) {
				try {
					toolTip.setLength(0);
					toolTipFormatter.format(value, toolTip, null);
					super.setToolTipText(toolTip.toString());
				} catch (final IllegalArgumentException ex) {
					super.setToolTipText(clientToolTipText);
				}
			} else {
				super.setToolTipText(clientToolTipText);
			}
		}
	}

	/**
	 * a formatter for displaying a tool tip when hovering over the scale and during
	 * thumb modification events. The
	 * {@link Format#format(Object, StringBuffer, java.text.FieldPosition)} method
	 * is invoked to retrieve the text for the tooltip where the input
	 * {@code Object} is an {@code Integer} with a value within the minimum and
	 * maximum.
	 *
	 * @param formatter
	 */
	public void setToolTipFormatter(Format formatter) {
		toolTip = formatter != null ? new StringBuffer() : null;
		toolTipFormatter = formatter;
	}

	@Override
	public void setToolTipText(String string) {
		clientToolTipText = string;
		super.setToolTipText(clientToolTipText);
	}

	/**
	 * Code executed when the mouse wheel is activated
	 *
	 * @param e event
	 */
	private void handleMouseWheel(final Event e) {
		if (selectedElement == NONE || dragInProgress) {
			e.doit = false;
			return;
		}
		previousLowerValue = lowerValue;
		previousUpperValue = upperValue;
		final int amount = increment * ((e.stateMask & SWT.SHIFT) != 0 ? 10 : (e.stateMask & SWT.CTRL) != 0 ? 2 : 1);
		if (selectedElement == BOTH) {
			double newLower = lowerValue + e.count * amount;
			double newUpper = upperValue + e.count * amount;
			if (newUpper > maximum) {
				newUpper = maximum;
				newLower = maximum - (upperValue - lowerValue);
			} else if (newLower < minimum) {
				newLower = minimum;
				newUpper = minimum + upperValue - lowerValue;
			}
			upperValue = newUpper;
			lowerValue = newLower;
		} else if ((selectedElement & LOWER) != 0) {
			lowerValue += e.count * amount;
			checkLowerValue();
		} else {
			upperValue += e.count * amount;
			checkUpperValue();
		}
		validateNewValues(e);
		e.doit = false; // we are consuming this event
	}

	/**
	 * Check if the lower value is in ranges
	 */
	private void checkLowerValue() {
		if (lowerValue < minimum) {
			lowerValue = minimum;
		}
		if (lowerValue > maximum) {
			lowerValue = maximum;
		}
		if (lowerValue > upperValue) {
			lowerValue = upperValue;
		}
	}

	/**
	 * Check if the upper value is in ranges
	 */
	private void checkUpperValue() {
		if (upperValue < minimum) {
			upperValue = minimum;
		}
		if (upperValue > maximum) {
			upperValue = maximum;
		}
		if (upperValue < lowerValue) {
			upperValue = lowerValue;
		}
	}

	/**
	 * Draws the widget
	 *
	 * @param e paint event
	 */
	private void drawWidget(final PaintEvent e) {
		final Rectangle rect = getClientArea();
		if (rect.width == 0 || rect.height == 0) {
			return;
		}
		paintControl(e.gc);
	}

	private void paintControl(final GC gc) {
		gc.setAdvanced(true);
		gc.setAntialias(SWT.ON);
		if (orientation == SWT.HORIZONTAL) {
			drawHorizontalRangeSlider(gc);
		} else {
			drawVerticalRangeSlider(gc);
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		GC gc = new GC(this);
		paintControl(gc);
		gc.dispose();
	}

	/**
	 * Draw the range slider (horizontal)
	 *
	 * @param gc graphic context
	 */
	private void drawHorizontalRangeSlider(final GC gc) {
		drawBackgroundHorizontal(gc);
		drawBarsHorizontal(gc);
		if (lowerHover || (selectedElement & LOWER) != 0) {
			coordUpper = drawHorizontalKnob(gc, upperValue, true);
			coordLower = drawHorizontalKnob(gc, lowerValue, false);
		} else {
			coordLower = drawHorizontalKnob(gc, lowerValue, false);
			coordUpper = drawHorizontalKnob(gc, upperValue, true);
		}
	}

	/**
	 * Draw the background
	 *
	 * @param gc graphic context
	 */
	private void drawBackgroundHorizontal(final GC gc) {
		final Rectangle clientArea = getClientArea();

		gc.setBackground(getBackground());
		gc.fillRectangle(clientArea);

		if (isEnabled()) {
			gc.setForeground(getForeground());
		} else {
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
		}
		gc.drawRoundRectangle(hOffset, 9, clientArea.width - 20, barHeight, barHeight, barHeight);

		final float pixelSize = computePixelSizeForHorizontalSlider();
		final int startX = (int) (pixelSize * lowerValue);
		final int endX = (int) (pixelSize * upperValue);
		if (isEnabled()) {
			gc.setBackground(selectionColor);
		} else {
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
		}
		gc.fillRectangle(hOffset + startX, 9, endX - startX, barHeight);

	}

	/**
	 * @return how many pixels corresponds to 1 point of value
	 */
	private float computePixelSizeForHorizontalSlider() {
		return (float) ((getClientArea().width - 20f) / (maximum - minimum));
	}

	/**
	 * Draw the bars
	 *
	 * @param gc graphic context
	 */
	private void drawBarsHorizontal(final GC gc) {
		// Do nothing
	}

	/**
	 * Draws an horizontal knob
	 *
	 * @param gc    graphic context
	 * @param value corresponding value
	 * @param upper if <code>true</code>, draws the upper knob. If
	 *              <code>false</code>, draws the lower knob
	 * @return the coordinate of the upper left corner of the knob
	 */
	private Point drawHorizontalKnob(final GC gc, final double value, final boolean upper) {
		final float pixelSize = computePixelSizeForHorizontalSlider();
		final int x = (int) (pixelSize * value);
		Image image;
		int offset = 0;
		if (upper) {
			if (upperHover) {
				image = dragInProgress || (selectedElement & UPPER) != 0 ? sliderDrag : sliderHover;
			} else if ((selectedElement & UPPER) != 0 && !lowerHover) {
				image = hasFocus ? sliderSelected : sliderHover;
			} else {
				image = slider;
			}
			offset = hOffset;
		} else {
			if (lowerHover) {
				image = dragInProgress || (selectedElement & LOWER) != 0 ? sliderDrag : sliderHover;
			} else if ((selectedElement & LOWER) != 0 && !upperHover) {
				image = hasFocus ? sliderSelected : sliderHover;
			} else {
				image = slider;
			}
		}
		if (isEnabled()) {
			gc.drawImage(image, x + offset, getClientArea().height / 2 - slider.getBounds().height / 2);
		} else {
			final Image temp = new Image(getDisplay(), image, SWT.IMAGE_DISABLE);
			gc.drawImage(temp, x + offset, getClientArea().height / 2 - slider.getBounds().height / 2);
			temp.dispose();
		}
		return new Point(x + offset, getClientArea().height / 2 - slider.getBounds().height / 2);
	}

	/**
	 * Draw the range slider (vertical)
	 *
	 * @param gc graphic context
	 */
	private void drawVerticalRangeSlider(final GC gc) {
		drawBackgroundVertical(gc);
		drawBarsVertical(gc);
		if (lowerHover || (selectedElement & LOWER) != 0) {
			coordUpper = drawVerticalKnob(gc, upperValue, true);
			coordLower = drawVerticalKnob(gc, lowerValue, false);
		} else {
			coordLower = drawVerticalKnob(gc, lowerValue, false);
			coordUpper = drawVerticalKnob(gc, upperValue, true);
		}
	}

	/**
	 * Draws the background
	 *
	 * @param gc graphic context
	 */
	private void drawBackgroundVertical(final GC gc) {
		final Rectangle clientArea = getClientArea();
		gc.setBackground(getBackground());
		gc.fillRectangle(clientArea);

		if (isEnabled()) {
			gc.setForeground(getForeground());
		} else {
			gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
		}
		gc.drawRoundRectangle(9, vOffset, barHeight, clientArea.height - 20, barHeight, barHeight);

		final float pixelSize = computePixelSizeForVerticalSlider();
		final int startY = (int) (pixelSize * lowerValue);
		final int endY = (int) (pixelSize * upperValue);
		if (isEnabled()) {
			gc.setBackground(selectionColor);
		} else {
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
		}
		gc.fillRectangle(9, vOffset + startY, barHeight, endY - startY + 1);

	}

	/**
	 * @return how many pixels corresponds to 1 point of value
	 */
	private float computePixelSizeForVerticalSlider() {
		return (float) ((getClientArea().height - 20f) / (maximum - minimum));
	}

	/**
	 * Draws the bars
	 *
	 * @param gc graphic context
	 */
	private void drawBarsVertical(final GC gc) {
		// Do nothing
	}

	/**
	 * Draws a vertical knob
	 *
	 * @param gc    graphic context
	 * @param value corresponding value
	 * @param upper if <code>true</code>, draws the upper knob. If
	 *              <code>false</code>, draws the lower knob
	 * @return the coordinate of the upper left corner of the knob
	 */
	private Point drawVerticalKnob(final GC gc, final double value, final boolean upper) {
		final float pixelSize = computePixelSizeForVerticalSlider();
		final int y = (int) (pixelSize * value);

		Image image;
		int offset = 0;
		if (upper) {
			if (upperHover) {
				image = dragInProgress || (selectedElement & UPPER) != 0 ? vSliderDrag : vSliderHover;
			} else if ((selectedElement & UPPER) != 0 && !lowerHover) {
				image = hasFocus ? vSliderSelected : vSliderHover;
			} else {
				image = vSlider;
			}
			offset = vOffset;
		} else {
			if (lowerHover) {
				image = dragInProgress || (selectedElement & LOWER) != 0 ? vSliderDrag : vSliderHover;
			} else if ((selectedElement & LOWER) != 0 && !upperHover) {
				image = hasFocus ? vSliderSelected : vSliderHover;
			} else {
				image = vSlider;
			}
		}
		if (isEnabled()) {
			gc.drawImage(image, 9, y + offset);
		} else {
			final Image temp = new Image(getDisplay(), image, SWT.IMAGE_DISABLE);
			gc.drawImage(temp, 9, y + offset);
			temp.dispose();
		}
		return new Point(9, y + offset);
	}

	/**
	 * move the cursor location by the input delta values.
	 *
	 * @param xDelta
	 * @param yDelta
	 */
	private void moveCursorPosition(int xDelta, int yDelta) {
		final Point cursorPosition = getDisplay().getCursorLocation();
		cursorPosition.x += xDelta;
		cursorPosition.y += yDelta;
		getDisplay().setCursorLocation(cursorPosition);
	}

	/**
	 * Code executed when a key is typed
	 *
	 * @param event event
	 */
	private void handleKeyDown(final Event event) {
		int accelerator = (event.stateMask & SWT.SHIFT) != 0 ? 10 : (event.stateMask & SWT.CTRL) != 0 ? 2 : 1;
		if (dragInProgress) {
			switch (event.keyCode) {
			case SWT.ESC:
				startDragPoint = null;
				upperValue = startDragUpperValue;
				lowerValue = startDragLowerValue;
				validateNewValues(event);
				dragInProgress = false;
				if (!isOn) {
					redraw();
				}
				event.doit = false;
				break;
			case SWT.ARROW_UP:
				accelerator = orientation == SWT.HORIZONTAL ? -accelerator : accelerator;
			case SWT.ARROW_LEFT:
				if (orientation == SWT.VERTICAL) {
					moveCursorPosition(0, -accelerator);
				} else {
					moveCursorPosition(-accelerator, 0);
				}
				event.doit = false;
				break;
			case SWT.ARROW_DOWN:
				accelerator = orientation == SWT.HORIZONTAL ? -accelerator : accelerator;
			case SWT.ARROW_RIGHT:
				if (orientation == SWT.VERTICAL) {
					moveCursorPosition(0, accelerator);
				} else {
					moveCursorPosition(accelerator, 0);
				}
				event.doit = false;
				break;
			}
			return;
		}
		previousLowerValue = lowerValue;
		previousUpperValue = upperValue;

		if (selectedElement == NONE) {
			selectedElement = LOWER;
		}
		switch (event.keyCode) {
		case SWT.HOME:
			if (selectedElement == BOTH) {
				if ((event.stateMask & SWT.SHIFT) != 0) {
					lowerValue = maximum - (upperValue - lowerValue);
					upperValue = maximum;
				} else {
					upperValue = minimum + upperValue - lowerValue;
					lowerValue = minimum;
				}
			} else if ((selectedElement & UPPER) != 0) {
				upperValue = maximum;
			} else {
				lowerValue = minimum;
			}
			break;
		case SWT.END:
			if (selectedElement == BOTH) {
				if ((event.stateMask & SWT.SHIFT) != 0) {
					upperValue = minimum + upperValue - lowerValue;
					lowerValue = minimum;
				} else {
					lowerValue = maximum - (upperValue - lowerValue);
					upperValue = maximum;
				}
			} else if ((selectedElement & UPPER) != 0) {
				upperValue = lowerValue;
			} else {
				lowerValue = upperValue;
			}
			break;
		case SWT.PAGE_UP:
			accelerator = orientation == SWT.HORIZONTAL ? -accelerator : accelerator;
			if (selectedElement == BOTH) {
				translateValues(pageIncrement * -accelerator);
			} else if ((selectedElement & UPPER) != 0) {
				upperValue -= pageIncrement * accelerator;
			} else {
				lowerValue -= pageIncrement * accelerator;
			}
			break;
		case SWT.PAGE_DOWN:
			accelerator = orientation == SWT.HORIZONTAL ? -accelerator : accelerator;
			if (selectedElement == BOTH) {
				translateValues(pageIncrement * accelerator);
			} else if ((selectedElement & UPPER) != 0) {
				upperValue += pageIncrement * accelerator;
			} else {
				lowerValue += pageIncrement * accelerator;
			}
			break;
		case SWT.ARROW_DOWN:
			accelerator = orientation == SWT.HORIZONTAL ? -accelerator : accelerator;
		case SWT.ARROW_RIGHT:
			if (selectedElement == BOTH) {
				translateValues(accelerator * increment);
			} else if ((selectedElement & UPPER) != 0) {
				upperValue += accelerator * increment;
			} else {
				lowerValue += accelerator * increment;
			}
			break;
		case SWT.ARROW_UP:
			accelerator = orientation == SWT.HORIZONTAL ? -accelerator : accelerator;
		case SWT.ARROW_LEFT:
			if (selectedElement == BOTH) {
				translateValues(-accelerator * increment);
			} else if ((selectedElement & UPPER) != 0) {
				upperValue -= accelerator * increment;
			} else {
				lowerValue -= accelerator * increment;
			}
			break;
		case SWT.TAB:
			final boolean next = (event.stateMask & SWT.SHIFT) == 0;
			if (next && (selectedElement & LOWER) != 0) {
				selectedElement = isFullSelection && selectedElement == LOWER ? BOTH : UPPER;
				redraw();
			} else if (!next && (selectedElement & UPPER) != 0) {
				selectedElement = isFullSelection && selectedElement == UPPER ? BOTH : LOWER;
				redraw();
			} else {
				traverse(next ? SWT.TRAVERSE_TAB_NEXT : SWT.TRAVERSE_TAB_PREVIOUS);
			}
			return;
		}
		if (previousLowerValue != lowerValue || previousUpperValue != upperValue) {
			if (selectedElement == BOTH) {
				checkLowerValue();
				checkUpperValue();
			} else if ((selectedElement & UPPER) != 0) {
				checkUpperValue();
			} else {
				checkLowerValue();
			}
			validateNewValues(event);
		}
	}

	/**
	 * translate both the upper and lower values by the input amount. The updated
	 * values are constrained to be within the minimum and maximum. The difference
	 * between upper and lower values is retained.
	 *
	 * @param amount
	 */
	private void translateValues(int amount) {
		double newLower = lowerValue + amount;
		double newUpper = upperValue + amount;
		if (newUpper > maximum) {
			newUpper = maximum;
			newLower = maximum - (upperValue - lowerValue);
		} else if (newLower < minimum) {
			newLower = minimum;
			newUpper = minimum + upperValue - lowerValue;
		}
		upperValue = newUpper;
		lowerValue = newLower;
	}

	/**
	 * Adds the listener to the collection of listeners who will be notified when
	 * the user changes the receiver's value, by sending it one of the messages
	 * defined in the <code>SelectionListener</code> interface.
	 * <p>
	 * <code>widgetSelected</code> is called when the user changes the receiver's
	 * value. <code>widgetDefaultSelected</code> is not called.
	 * </p>
	 *
	 * @param listener the listener which should be notified
	 * @exception IllegalArgumentException
	 * @exception org.eclipse.swt.SWTException
	 * @see SelectionListener
	 * @see #removeSelectionListener
	 */
	public void addSelectionListener(final SelectionListener listener) {
		checkWidget();
		addSelectionListener(this, listener);
	}

	/**
	 * @see org.eclipse.swt.widgets.Composite#computeSize(int, int, boolean)
	 */
	@Override
	public Point computeSize(final int wHint, final int hHint, final boolean changed) {
		final int width, height;
		checkWidget();
		if (orientation == SWT.HORIZONTAL) {
			width = Math.max(minWHint, wHint);
			height = Math.max(minHHint, hHint);
		} else {
			width = Math.max(minHHint, wHint);
			height = Math.max(minWHint, hHint);
		}

		return new Point(width, height);
	}

	/**
	 * Returns the amount that the selected receiver's value will be modified by
	 * when the up/down (or right/left) arrows are pressed.
	 *
	 * @return the increment
	 * @exception org.eclipse.swt.SWTException
	 */
	public int getIncrement() {
		checkWidget();
		return increment;
	}

	/**
	 * Returns the 'lower selection', which is the lower receiver's position.
	 *
	 * @return the selection
	 * @exception org.eclipse.swt.SWTException
	 */
	public double getLowerValue() {
		checkWidget();
		return Double.parseDouble(numberFormat.format(lowerValue));
	}

	/**
	 * Returns the maximum value which the receiver will allow.
	 *
	 * @return the maximum
	 * @exception org.eclipse.swt.SWTException
	 */
	public double getMaximum() {
		checkWidget();
		return Double.parseDouble(numberFormat.format(maximum));
	}

	/**
	 * Returns the minimum value which the receiver will allow.
	 *
	 * @return the minimum
	 * @exception org.eclipse.swt.SWTException
	 */
	public double getMinimum() {
		checkWidget();
		return Double.parseDouble(numberFormat.format(minimum));
	}

	/**
	 * Returns the amount that the selected receiver's value will be modified by
	 * when the page increment/decrement areas are selected.
	 *
	 * @return the page increment
	 * @exception org.eclipse.swt.SWTException
	 */
	public int getPageIncrement() {
		checkWidget();
		return pageIncrement;
	}

	/**
	 * Returns the 'selection', which is an array where the first element is the
	 * lower selection, and the second element is the upper selection
	 *
	 * @return the selection
	 * @exception org.eclipse.swt.SWTException
	 */
	public double[] getSelection() {
		checkWidget();
		final double[] selection = new double[2];
		selection[0] = lowerValue;
		selection[1] = upperValue;
		return selection;
	}

	/**
	 * Returns the 'upper selection', which is the upper receiver's position.
	 *
	 * @return the selection
	 * @exception org.eclipse.swt.SWTException
	 */
	public double getUpperValue() {
		checkWidget();
		return Double.parseDouble(numberFormat.format(upperValue));
	}

	/**
	 * Removes the listener from the collection of listeners who will be notified
	 * when the user changes the receiver's value.
	 *
	 * @param listener the listener which should no longer be notified
	 *
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
	 * Sets the amount that the selected receiver's value will be modified by when
	 * the up/down (or right/left) arrows are pressed to the argument, which must be
	 * at least one.
	 *
	 * @param increment the new increment (must be greater than zero)
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setIncrement(final int increment) {
		checkWidget();
		this.increment = increment;
		redraw();
	}

	/**
	 * Sets the 'lower selection', which is the receiver's lower value, to the input
	 * argument which must be less than or equal to the current 'upper selection'
	 * and greater or equal to the minimum. If either condition fails, no action is
	 * taken.
	 *
	 * @param value the new lower selection
	 * @exception org.eclipse.swt.SWTException
	 * @see #getUpperValue()
	 * @see #getMinimum()
	 * @see #setSelection(int, int)
	 */
	public void setLowerValue(final double value) {
		setSelection(value, upperValue);
	}

	/**
	 * Sets the maximum value that the receiver will allow. This new value will be
	 * ignored if it is not greater than the receiver's current minimum value. If
	 * the new maximum is applied then the receiver's selection value will be
	 * adjusted if necessary to fall within its new range.
	 *
	 * @param value the new maximum, which must be greater than the current minimum
	 * @exception org.eclipse.swt.SWTException
	 * @see #setExtrema(int, int)
	 */
	public void setMaximum(final double value) {
		setExtrema(minimum, value);
	}

	/**
	 * Sets the minimum value that the receiver will allow. This new value will be
	 * ignored if it is negative or is not less than the receiver's current maximum
	 * value. If the new minimum is applied then the receiver's selection value will
	 * be adjusted if necessary to fall within its new range.
	 *
	 * @param value the new minimum, which must be nonnegative and less than the
	 *              current maximum
	 * @exception org.eclipse.swt.SWTException
	 * @see #setExtrema(int, int)
	 */
	public void setMinimum(final double value) {
		setExtrema(value, maximum);
	}

	/**
	 * Sets the minimum and maximum values that the receiver will allow. The new
	 * values will be ignored if either are negative or the min value is not less
	 * than the max. The receiver's selection values will be adjusted if necessary
	 * to fall within the new range.
	 *
	 * @param min the new minimum, which must be nonnegative and less than the max
	 * @param max the new maximum, which must be greater than the min
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setExtrema(final double min, final double max) {
		checkWidget();
		if (min >= 0 && min < max && (min != minimum || max != maximum)) {
			minimum = min;
			maximum = max;
			if (lowerValue < minimum) {
				lowerValue = minimum;
			} else if (lowerValue > maximum) {
				lowerValue = maximum;
			}
			if (upperValue < minimum) {
				upperValue = minimum;
			} else if (upperValue > maximum) {
				upperValue = maximum;
			}
			redraw();
		}
	}

	/**
	 * Sets the amount that the receiver's value will be modified by when the page
	 * increment/decrement areas are selected to the argument, which must be at
	 * least one.
	 *
	 * @param pageIncrement the page increment (must be greater than zero)
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setPageIncrement(final int pageIncrement) {
		checkWidget();
		this.pageIncrement = pageIncrement;
		redraw();
	}

	/**
	 * Sets the 'selection', which is the receiver's value. The lower value must be
	 * less than or equal to the upper value. Additionally, both values must be
	 * inclusively between the slider minimum and maximum. If either condition
	 * fails, no action is taken.
	 *
	 * @param value the new selection (first value is lower value, second value is
	 *              upper value)
	 * @exception org.eclipse.swt.SWTException
	 */
	public void setSelection(final double[] values) {
		if (values.length == 2) {
			setSelection(values[0], values[1]);
		}
	}

	/**
	 * Sets the 'selection', which is the receiver's value. The lower value must be
	 * less than or equal to the upper value. Additionally, both values must be
	 * inclusively between the slider minimum and maximum. If either condition
	 * fails, no action is taken.
	 *
	 * @param lowerValue the new lower selection
	 * @param upperValue the new upper selection
	 * @exception org.eclipse.swt.SWTException
	 * @see #getMinimum()
	 * @see #getMaximum()
	 */
	public void setSelection(final double lowerValue, final double upperValue) {
		checkWidget();
		if (lowerValue <= upperValue && lowerValue >= minimum && upperValue <= maximum
				&& (this.lowerValue != lowerValue || this.upperValue != upperValue)) {
			this.lowerValue = lowerValue;
			this.upperValue = upperValue;
			redraw();
		}
	}

	/**
	 * Sets the 'upper selection', which is the upper receiver's value, to the input
	 * argument which must be greater than or equal to the current 'lower selection'
	 * and less or equal to the maximum. If either condition fails, no action is
	 * taken.
	 *
	 * @param value the new upper selection
	 * @exception org.eclipse.swt.SWTException
	 * @see #getLowerValue()
	 * @see #getMaximum()
	 * @see #setSelection(int, int)
	 */
	public void setUpperValue(final double value) {
		setSelection(lowerValue, value);
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
