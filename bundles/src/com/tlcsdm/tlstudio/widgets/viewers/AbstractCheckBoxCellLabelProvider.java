package com.tlcsdm.tlstudio.widgets.viewers;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.viewers.ObservableMapCellLabelProvider;
import org.eclipse.jface.util.Util;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractCheckBoxCellLabelProvider extends ObservableMapCellLabelProvider {

	protected AbstractCheckBoxCellLabelProvider(IObservableMap<?, ?> attributeMap) {
		super(attributeMap);
	}

	@Override
	public void update(ViewerCell cell) {
		super.update(cell);
		cell.setText("");
	}

	protected Image getCellEditorImage(Control control, boolean value, boolean enable) {
		return makeShot(control, value, enable);
	}

	private Image makeShot(Control control, boolean type, boolean enable) {
		Color greenScreen = new Color(control.getDisplay(), 222, 223, 224);
		Shell shell = new Shell(control.getShell(), SWT.NO_TRIM);
		shell.setBackground(greenScreen);

		if (Util.isMac()) {
			Button button2 = new Button(shell, SWT.CHECK);
			Point bsize = button2.computeSize(SWT.DEFAULT, SWT.DEFAULT);

			// otherwise an image is stretched by width
			bsize.x = Math.max(bsize.x - 1, bsize.y - 1);
			bsize.y = Math.max(bsize.x - 1, bsize.y - 1);
			button2.setSize(bsize);
			button2.setLocation(100, 100);
		}

		Button button = new Button(shell, SWT.CHECK);
		button.setBackground(greenScreen);
		button.setSelection(type);
		button.setEnabled(enable);

		button.setLocation(1, 1);
		Point bsize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		bsize.x = Math.max(bsize.x - 1, bsize.y - 1);
		bsize.y = Math.max(bsize.x - 1, bsize.y - 1);
		button.setSize(bsize);
		bsize.x += 20;

		shell.setSize(bsize);
		shell.open();

		GC gc = new GC(shell);
		Image image = new Image(control.getDisplay(), bsize.x, bsize.y);
		gc.copyArea(image, 0, 0);
		gc.dispose();
		shell.close();

		ImageData imageData = image.getImageData();
		imageData.transparentPixel = imageData.palette.getPixel(greenScreen.getRGB());

		Image img = new Image(control.getDisplay(), imageData);
		image.dispose();
		return img;
	}

}
