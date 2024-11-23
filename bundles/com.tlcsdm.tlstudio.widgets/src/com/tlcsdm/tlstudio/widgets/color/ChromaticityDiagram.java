package com.tlcsdm.tlstudio.widgets.color;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.WidgetsUtility;
import com.tlcsdm.tlstudio.widgets.custom.AbstractCustomCanvas;
import com.tlcsdm.tlstudio.widgets.model.CIEData;

public class ChromaticityDiagram extends AbstractCustomCanvas {

	/**
	 * Font size for title and legend
	 */
	protected int fontSize;
	/**
	 * Width and height of the canvas
	 */
	protected int width = 600, height = 600;
	protected int margins = 36;
	/**
	 * Chart style
	 */
	protected int ruler = 10, each = 5, grid = each * ruler;
	/**
	 * Style of each data Line/Point
	 */
	protected boolean isLine;
	private double markerXRatio = -1;
	private double markerYRatio = -1;
	private int markerX;
	private int markerY;
	/**
	 * Whether to include wavelength data
	 */
	protected boolean hasWaveLength;
	/**
	 * Whether to include grid style
	 */
	protected boolean hasGrid;
	/**
	 * Whether to include calculation points
	 */
	protected boolean hasCalculate;
	/**
	 * Data line length. Require isLine = true
	 */
	protected int dotSize;
	/**
	 * Coordinates of the calculate point
	 */
	private double calculateX = -1, calculateY = -1;

	protected String title, legend, calculateTxt;
	/**
	 * Background Color
	 */
	protected Color bgColor;
	protected boolean hasMarkWave;
	/**
	 * The color of the calculate point
	 */
	protected Color calculateColor;
	protected Color textColor;
	protected Color gridColor;
	protected Color rulerColor;

	private Font dataFont;
	private Font waveFont;
	private int endH;

	public ChromaticityDiagram(Composite parent, int style) {
		super(parent, SWT.DOUBLE_BUFFERED | ((style & SWT.BORDER) == SWT.BORDER ? SWT.BORDER : SWT.NONE));
		fontSize = 10;
		dotSize = 3;
		title = "Chromaticity Diagram";
		legend = "";
		gridColor = new Color(230, 230, 230);
		rulerColor = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
		hasGrid = true;
		hasWaveLength = true;
		isLine = true;
		hasCalculate = true;
		calculateTxt = "";
		hasMarkWave = false;

		addListener(SWT.Dispose, event -> {
			safeDispose(bgColor);
			safeDispose(calculateColor);
			safeDispose(textColor);
			safeDispose(gridColor);
			safeDispose(rulerColor);
			safeDispose(dataFont);
			safeDispose(waveFont);
		});
		addPaintListener(e -> {
			paintControl(e.gc);
		});
		addListener(SWT.MouseDown, e -> {
			if (!hasCalculate) {
				return;
			}
			int w = e.x - margins;
			int h = e.y - margins;
			if (w < 0 || w > width || h < 0 || h > height) {
				return;
			}
			int dataW = width - margins * 2;
			int dataH = height - margins * 2;

			BigDecimal wBigDecimal = new BigDecimal(w);
			BigDecimal dataWBigDecimal = new BigDecimal(dataW);
			BigDecimal xBigDecimal = wBigDecimal.divide(dataWBigDecimal, 4, RoundingMode.HALF_UP);

			BigDecimal hBigDecimal = new BigDecimal(endH);
			BigDecimal eyBigDecimal = new BigDecimal(e.y);
			BigDecimal dataHBigDecimal = new BigDecimal(dataH);
			BigDecimal yBigDecimal = hBigDecimal.subtract(eyBigDecimal).divide(dataHBigDecimal, 4,
					RoundingMode.HALF_UP);
			handlerMouseClick(xBigDecimal.doubleValue(), yBigDecimal.doubleValue());
		});
		this.addMouseMoveListener(this::doMouseMoveAction);
		this.addMouseTrackListener(doMouseExitAdapter);
	}

	protected void handlerMouseClick(double x, double y) {
		setCalculatePoint(x, y);
	}

	public void setHasCalculate(boolean hasCalculate) {
		if (this.hasCalculate != hasCalculate) {
			this.hasCalculate = hasCalculate;
			redraw();
		}
	}

	public double getCalculateX() {
		return this.calculateX;
	}

	public double getCalculateY() {
		return this.calculateY;
	}

	public void setCalculatePoint(double calculateX, double calculateY) {
		this.setCalculatePoint(calculateX, calculateY, null);
	}

	public void setCalculatePoint(double calculateX, double calculateY, Color calculateColor) {
		this.calculateX = calculateX;
		this.calculateY = calculateY;
		this.calculateColor = calculateColor;
		redraw();
	}

	/*
	 * The following set methods will not cause the UI to refresh, and you need to
	 * call redraw() manually
	 */

	public void setFontSize(int fontSize) {
		if (fontSize > 0) {
			this.fontSize = fontSize;
		}
	}

	public void setIsLine(boolean isLine) {
		this.isLine = isLine;
	}

	public void setHasWaveLength(boolean hasWaveLength) {
		this.hasWaveLength = hasWaveLength;
	}

	public void setHasGrid(boolean hasGrid) {
		this.hasGrid = hasGrid;
	}

	public void setDotSize(int dotSize) {
		if (dotSize > 0) {
			this.dotSize = dotSize;
		}
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}

	public void setBackgroundColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public void setGridColor(Color gridColor) {
		this.gridColor = gridColor;
	}

	public void setRulerColor(Color rulerColor) {
		this.rulerColor = rulerColor;
	}

	private void paintControl(final GC gc) {
		final Rectangle rect = getClientArea();
		if (rect.width == 0 || rect.height == 0) {
			return;
		}
		gc.setAdvanced(true);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.setTextAntialias(SWT.ON);

		drawBackGround(gc);
		drawOutLines(gc);
		drawCalculate(gc);
		drawMarker(gc);
	}

	private void drawBackGround(GC gc) {
		if (bgColor != null) {
			gc.setBackground(bgColor);
			gc.fillRectangle(0, 0, width, height);
		}
		FontDescriptor fd = FontDescriptor.createFrom(this.getFont()).setHeight(fontSize);
		dataFont = fd.createFont(this.getDisplay());
		FontDescriptor fdc = FontDescriptor.createFrom(this.getFont()).setHeight(fontSize + 3).setStyle(SWT.BOLD);
		Font commentsFont = fdc.createFont(this.getDisplay());
		FontDescriptor fdwave = FontDescriptor.createFrom(this.getFont()).setHeight(fontSize - 2);
		waveFont = fdwave.createFont(this.getDisplay());

		if (Display.getDefault().getSystemColor(SWT.COLOR_BLACK).equals(bgColor)) {
			textColor = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
		} else {
			textColor = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		}
		double stepH = (height - margins * 2) * 1.0 / grid;
		double stepW = (width - margins * 2) * 1.0 / grid;
		double startH = margins + stepH;
		double startW = margins + stepW;
		endH = (int) (stepH * grid + margins);
		int endW = (int) (stepW * grid + margins);
		if (title != null && !title.isEmpty()) {
			GC tgc = new GC(this);
			int titleWidth = tgc.textExtent(title).x;
			tgc.dispose();
			gc.setFont(commentsFont);
			gc.setForeground(textColor);
			gc.drawString(title, (width - margins * 2 - titleWidth) / 2, 3);
		}
		if (legend != null && !legend.isEmpty()) {
			gc.setFont(dataFont);
			gc.drawString(legend, 20, endH + margins - 5);
		}

		gc.setLineWidth(2);
		gc.setFont(dataFont);
		if (hasGrid) {
			gc.setBackground(gridColor);
			gc.setForeground(gridColor);
			for (int i = 0; i < grid; ++i) {
				int h = (int) (startH + i * stepH);
				gc.drawLine(margins, h, endW, h);
				int w = (int) (startW + i * stepW);
				gc.drawLine(w, margins, w, endH);
			}
			gc.setBackground(rulerColor);
			gc.setForeground(rulerColor);
			for (int i = 0; i < ruler; ++i) {
				int h = (int) (margins + i * stepH * each);
				gc.drawLine(margins, h, endW, h);
				int w = (int) (margins + i * stepW * each);
				gc.drawLine(w, margins, w, endH);
			}
		}

		gc.setForeground(textColor);
		gc.setBackground(this.getBackground());
		gc.drawLine(margins, margins, margins, endH);
		gc.drawLine(margins, endH, endW, endH);
		for (int i = 0; i < ruler; ++i) {
			int h = (int) (margins + (i + 1) * stepH * each);
			gc.drawString("0." + (9 - i), 3, h - 10);
			int w = (int) (margins + i * stepW * each);
			gc.drawString("0." + i, w - 10, endH + 20);
		}
		gc.drawString("1.0", endW - 10, endH + 20);
		gc.drawString("1.0", 3, margins - 5);
	}

	protected void drawOutLines(GC gc) {
		gc.setFont(waveFont);
	}

	protected void outline(List<CIEData> data, String name, int markWave, Color waveColor, GC gc) {
		try {
			if (data == null || data.isEmpty()) {
				return;
			}
			Color pColor;
			int dotSizeHalf = dotSize / 2;
			int dataW = width - margins * 2, dataH = height - margins * 2;
			int x, y, wave, lastx = -1, lasty = -1;
			double[] srgb;
			for (CIEData d : data) {
				wave = d.getWaveLength();
				x = (int) Math.round(margins + dataW * d.getNormalizedX());
				y = (int) Math.round(endH - dataH * d.getNormalizedY());
				srgb = CIEData.sRGB65(d);
				pColor = new Color((int) (srgb[0] * 255), (int) (srgb[1] * 255), (int) (srgb[2] * 255));
				gc.setForeground(pColor);
				gc.setLineWidth(1);
				if (isLine) {
					if (lastx >= 0 && lasty >= 0) {
						gc.setLineWidth(dotSize);
						gc.drawLine(lastx, lasty, x, y);
					} else {
						gc.fillRectangle(x - dotSizeHalf, y - dotSizeHalf, dotSize, dotSize);
					}
					lastx = x;
					lasty = y;
				} else {
					gc.fillRectangle(x - dotSizeHalf, y - dotSizeHalf, dotSize, dotSize);
				}
				gc.setBackground(this.getBackground());
				if (hasMarkWave && (wave == markWave)) {
					gc.setForeground(waveColor);
					gc.drawLine(x, y, x + 300, y + markWave - 560);
					gc.drawString(name, x + 310, y + markWave - 560, true);
				}
				if (hasWaveLength) {
					if (wave == 360 || wave == 830 || wave == 460) {
						gc.setForeground(waveColor);
						gc.drawString(wave + "nm", x + 10, y, true);
					} else if (wave > 470 && wave < 620) {
						if (wave % 5 == 0) {
							gc.setForeground(waveColor);
							gc.drawString(wave + "nm", x + 10, y, true);
						}
					} else if (wave >= 620 && wave <= 640) {
						if (wave % 10 == 0) {
							gc.setForeground(waveColor);
							gc.drawString(wave + "nm", x + 10, y, true);
						}
					}
				}
			}

			// Bottom line
			CIEData d1 = data.get(0);
			CIEData d2 = data.get(data.size() - 1);
			double x1 = d1.getNormalizedX(), x2 = d2.getNormalizedX();
			double y1 = d1.getNormalizedY(), y2 = d2.getNormalizedY();
			colorLine(x1, y1, x2, y2, gc);
		} catch (Exception e) {
			WidgetsUtility.logException(e);
		}
	}

	private void colorLine(double ix1, double iy1, double ix2, double iy2, GC gc) {
		if (gc == null || ix1 == ix2) {
			return;
		}
		double x1, y1, x2, y2;
		if (ix1 < ix2) {
			x1 = ix1;
			y1 = iy1;
			x2 = ix2;
			y2 = iy2;
		} else {
			x1 = ix2;
			y1 = iy2;
			x2 = ix1;
			y2 = iy1;
		}
		Color pColor;
		int dataW = width - margins * 2, dataH = height - margins * 2;
		int x, y, halfDot = dotSize / 2, lastx = -1, lasty = -1;
		double ratio = (y2 - y1) / (x2 - x1);
		double step = (x2 - x1) / 100;
		for (double bx = x1 + step; bx < x2; bx += step) {
			double by = (bx - x1) * ratio + y1;
			pColor = getColorByCoor(bx, by);
			gc.setForeground(pColor);

			x = (int) Math.round(margins + dataW * bx);
			y = (int) Math.round(endH - dataH * by);
			if (isLine) {
				if (lastx >= 0 && lasty >= 0) {
					gc.setLineWidth(dotSize);
					gc.drawLine(lastx, lasty, x, y);
				} else {
					gc.setLineWidth(1);
					gc.fillRectangle(x - halfDot, y - halfDot, dotSize, dotSize);
				}
				lastx = x;
				lasty = y;
			} else {
				gc.setLineWidth(1);
				gc.fillRectangle(x - halfDot, y - halfDot, dotSize, dotSize);
			}
		}
	}

	protected Color getColorByCoor(double x, double y) {
		double z = 1 - x - y;
		double[] relativeXYZ = CIEData.relative(x, y, z);
		double[] srgb = CIEData.XYZd50toSRGBd65(relativeXYZ);
		return new Color((int) (srgb[0] * 255), (int) (srgb[1] * 255), (int) (srgb[2] * 255));
	}

	private void drawCalculate(GC gc) {
		if (!hasCalculate || calculateX < 0 || calculateX > 1 || calculateY <= 0 || calculateY > 1) {
			return;
		}
		int dataW = width - margins * 2;
		int dataH = height - margins * 2;
		gc.setLineWidth(1);

		Color pColor = calculateColor;
		if (pColor == null) {
			pColor = getColorByCoor(calculateX, calculateY);
		}
		if (!checkValidPoint(calculateX, calculateY)) {
			pColor = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
		}
		int x = (int) Math.round(margins + dataW * calculateX);
		int y = (int) Math.round(endH - dataH * calculateY);
		gc.setBackground(pColor);
		gc.fillOval(x - 10, y - 10, 20, 20);
		if (calculateTxt != null && !calculateTxt.isEmpty()) {
			gc.setFont(dataFont);
			gc.setForeground(textColor);
			gc.setBackground(this.getBackground());
			gc.drawString(calculateTxt, x + 15, y + 5, true);
		}
	}

	protected boolean checkValidPoint(double x, double y) {
		return true;
	}

	private void drawMarker(GC gc) {
		if (!isEqualDouble(this.markerXRatio, -1) && !isEqualDouble(this.markerYRatio, -1)) {
			int dataW = width - margins * 2;
			int dataH = height - margins * 2;
			markerX = (int) Math.round(margins + dataW * markerXRatio);
			markerY = (int) Math.round(endH - dataH * markerYRatio);

			gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			gc.setLineStyle(SWT.LINE_DASH);
			gc.setLineWidth(2);
			gc.drawLine(this.markerX, margins, this.markerX, endH);
			gc.drawLine(margins, this.markerY, margins + dataW, this.markerY);
			String txtXMarker = String.format(Locale.ENGLISH, "%.4f", markerXRatio);
			gc.setBackground(rulerColor);
			gc.drawText(txtXMarker, this.markerX + 1, margins + 5, false);

			String txtYMarker = String.format(Locale.ENGLISH, "%.4f", markerYRatio);
			gc.drawText(txtYMarker, margins + dataW + 5, this.markerY, false);
		}
	}

	@Override
	public Point computeSize(final int wHint, final int hHint) {
		return this.computeSize(width, height, true);
	}

	private void doMouseMoveAction(MouseEvent e) {
		int dataW = width - margins * 2;
		int dataH = height - margins * 2;
		if (e.x < margins || e.x > (this.width - margins)) {
			this.markerXRatio = -1;
		} else {
			this.markerXRatio = (e.x - margins) * 1.0 / dataW;
		}

		if (e.y < margins || e.y > endH) {
			this.markerYRatio = -1;
		} else {
			this.markerYRatio = (endH - e.y) * 1.0 / dataH;
		}
		this.redraw();
	}

	private MouseTrackListener doMouseExitAdapter = new MouseTrackAdapter() {

		@Override
		public void mouseExit(MouseEvent e) {
			markerXRatio = -1;
			markerYRatio = -1;
			redraw();
		}

	};

	private boolean isEqualDouble(double d1, double d2) {
		return Math.abs(d1 - d2) < 0.000001;
	}

	public static void main(String[] args) {
		Display display = new Display();

		Shell shell = new Shell(display);
		shell.setText("Chromaticity Diagram");
		shell.setLayout(new GridLayout(1, false));
		shell.setSize(800, 600);
		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

		ChromaticityDiagram cd = new CIE1931ChromaticityDiagram(scrolledComposite, SWT.NONE);
		cd.setCalculatePoint(0.3, 0.4);

		scrolledComposite.setContent(cd);
		scrolledComposite.setMinSize(cd.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrolledComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(final ControlEvent e) {
				scrolledComposite.setMinSize(cd.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
