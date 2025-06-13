package com.tlcsdm.tlstudio.widgets.color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import com.tlcsdm.tlstudio.widgets.custom.AbstractCustomCanvas;

public class ColorBand extends AbstractCustomCanvas {

	public ColorBand(Composite parent, int style) {
		super(parent, SWT.DOUBLE_BUFFERED | ((style & SWT.BORDER) == SWT.BORDER ? SWT.BORDER : SWT.NONE));

		addPaintListener(e -> {
			paintControl(e.gc);
		});
		addListener(SWT.Resize, event -> {
			paintControl(event.gc);
		});
	}

	private void paintControl(final GC gc) {
		final Rectangle rect = getClientArea();
		if (rect.width == 0 || rect.height == 0 || gc == null) {
			return;
		}
		gc.setAdvanced(true);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.setTextAntialias(SWT.ON);

		PaletteData palette = new PaletteData(0xff, 0xff00, 0xff0000);
		ImageData hueData = new ImageData(rect.width, rect.height, 24, palette);
		float hue = 0;
		for (int x = 0; x < hueData.width; x++) {
			for (int y = 0; y < hueData.height; y++) {
				int pixel = palette.getPixel(new RGB(hue, 1f, 1f));
				hueData.setPixel(x, y, pixel);
			}
			hue += 360f / hueData.width;
		}

		Image hueImage = new Image(this.getDisplay(), hueData);
		gc.drawImage(hueImage, 0, 0);
		hueImage.dispose();
	}

}
