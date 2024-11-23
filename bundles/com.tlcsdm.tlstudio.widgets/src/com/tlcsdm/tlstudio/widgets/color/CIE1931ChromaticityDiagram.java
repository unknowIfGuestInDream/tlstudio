package com.tlcsdm.tlstudio.widgets.color;

import java.awt.color.ColorSpace;
import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

import com.tlcsdm.tlstudio.widgets.model.CIEData;
import com.tlcsdm.tlstudio.widgets.model.CIEData1931;

public class CIE1931ChromaticityDiagram extends ChromaticityDiagram {

	public CIE1931ChromaticityDiagram(Composite parent, int style) {
		super(parent, style);
		this.title = "CIE 1931 Color Space Chromaticity Diagram";
	}

	@Override
	protected void drawOutLines(GC gc) {
		super.drawOutLines(gc);
		List<CIEData> data = CIEData1931.getInstance();
		outline(data, "CIE 1931", 535, textColor, gc);
	}

	@Override
	protected void handlerMouseClick(double x, double y) {
		float[] rgb = coor2sRgb(x, y);
		setCalculatePoint(x, y, new Color((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255)));
	}

	@Override
	protected Color getColorByCoor(double x, double y) {
		float[] rgb = coor2sRgb(x, y);
		return new Color((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255));
	}

	protected boolean checkValidPoint(double x, double y) {
		List<CIEData> data = CIEData1931.getInstance();
		double[][] polygon = new double[data.size()][2];
		for (int i = 0; i < data.size(); i++) {
			double[] xy = new double[] { data.get(i).getNormalizedX(), data.get(i).getNormalizedY() };
			polygon[i] = xy;
		}
		return isPointInPolygon(new double[] { x, y }, polygon);
	}

	private boolean isPointInPolygon(double[] point, double[][] polygon) {
		double x = point[0];
		double y = point[1];
		int n = polygon.length;
		boolean inside = false;
		for (int i = 0, j = n - 1; i < n; j = i++) {
			double xi = polygon[i][0], yi = polygon[i][1];
			double xj = polygon[j][0], yj = polygon[j][1];
			boolean intersect = ((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi);
			if (intersect) {
				inside = !inside;
			}
		}
		return inside;
	}

	/**
	 * Translate XY to sRGB.
	 * 
	 * @param coorX X [0, 1]
	 * @param coorY Y [0, 1]
	 * @return sRGB [0, 1]
	 */
	private float[] coor2sRgb(double coorX, double coorY) {
		double[] liner = calculateForLinerRgbFromCoor(coorX, coorY);
		return linerRgb2sRgb(liner);
	}

	/**
	 * Calculate liner-sRGB from xy-coordinate.
	 * 
	 * @param coorX X [0, 1]
	 * @param coorY Y [0, 1]
	 * @return liner-RGB [0, 1]
	 */
	private double[] calculateForLinerRgbFromCoor(double coorX, double coorY) {
		int[] xyz = new int[3];
		double[] liner = new double[3];
		double sum = 0;
		xyz[0] = (int) (coorX * 65534);
		xyz[1] = (int) (coorY * 65534);
		xyz[2] = 65536 - xyz[0] - xyz[1];
		liner[0] = ((xyz[0] * 3318) + (xyz[1] * (-1574)) + (xyz[2] * (-510))) >> 16;
		liner[1] = ((xyz[0] * (-992)) + (xyz[1] * 1921) + (xyz[2] * 42)) >> 16;
		liner[2] = ((xyz[0] * 56) + (xyz[1] * (-208)) + (xyz[2] * 1075)) >> 16;
		for (int i = 0; i < liner.length; i++) {
			if (liner[i] <= 0.0) {
				liner[i] = 0.0;
			}
			sum += liner[i];
		}
		liner[0] /= sum;
		liner[1] /= sum;
		liner[2] = (1.0 - (liner[0] + liner[1]));
		return liner;
	}

	/**
	 * Translate liner-sRGB to sRGB.
	 * 
	 * @param liner liner-sRGB [0, 1]
	 * @return sRGB [0, 1]
	 */
	public static float[] linerRgb2sRgb(double[] liner) {
		float[] lRgb = new float[3];
		lRgb[0] = (float) liner[0];
		lRgb[1] = (float) liner[1];
		lRgb[2] = (float) liner[2];
		return ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB).toRGB(lRgb);
	}

}
