package com.tlcsdm.tlstudio.widgets.window;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;

/**
 * A tool tip to display an image and that reacts to user clicks.
 */
public class ImageToolitp extends ToolTip {

	private final ImageDescriptor desc;
	private Image img;

	public ImageToolitp(Control control, ImageDescriptor desc) {
		super(control);
		this.desc = desc;
	}

	/*
	 * (non-Jsdoc)
	 * 
	 * @see org.eclipse.jface.window.ToolTip
	 * #createToolTipContentArea(org.eclipse.swt.widgets.Event,
	 * org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite createToolTipContentArea(Event event, Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());
		Label label = new Label(container, SWT.NONE);
		this.img = this.desc.createImage();
		label.setImage(this.img);
		container.setSize(this.img.getBounds().width, this.img.getBounds().height);
		parent.addDisposeListener(e -> {
			dispose();
		});
		return container;
	}

	/**
	 * Disposes the created resources.
	 */
	public void dispose() {
		if (this.img != null && !this.img.isDisposed()) {
			this.img.dispose();
		}
	}
}
