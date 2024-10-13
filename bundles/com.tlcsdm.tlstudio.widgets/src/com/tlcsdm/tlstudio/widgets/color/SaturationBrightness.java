package com.tlcsdm.tlstudio.widgets.color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.custom.AbstractCustomCanvas;

public class SaturationBrightness extends AbstractCustomCanvas {

	public SaturationBrightness(Composite parent, int style) {
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
		ImageData saturationBrightnessData = new ImageData(360, 360, 24, palette);
		float saturation = 0f;
		float brightness = 1f;
		for (int x = 0; x < saturationBrightnessData.width; x++) {
			brightness = 1f;
			for (int y = 0; y < saturationBrightnessData.height; y++) {
				int pixel = palette.getPixel(new RGB(360f, saturation, brightness));
				saturationBrightnessData.setPixel(x, y, pixel);
				brightness -= 1f / saturationBrightnessData.height;
			}
			saturation += 1f / saturationBrightnessData.width;
		}

		Image saturationImage = new Image(this.getDisplay(), saturationBrightnessData);
		gc.drawImage(saturationImage, 0, 0);
		saturationImage.dispose();
	}

	public static void main(String[] args) {
		Display display = new Display();

		Shell shell = new Shell(display);
		shell.setText("Hue");
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 10;
		gridLayout.marginWidth = gridLayout.marginHeight = 16;
		shell.setLayout(gridLayout);

		SaturationBrightness cd = new SaturationBrightness(shell, SWT.NONE);
		GridData data = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1);
		data.widthHint = 500;
		data.heightHint = 500;
		cd.setLayoutData(data);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
