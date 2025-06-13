package com.tlcsdm.tlstudio.widgets.custom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * 支持收缩展开的面板.
 */
public class CollapsibleComposite extends Composite {

	public enum Direction {
		LEFT, RIGHT, TOP, BOTTOM
	}

	public enum ButtonPosition {
		LEADING, TRAILING
	}

	public interface CollapseListener {
		void onCollapse();

		void onExpand();
	}

	private boolean collapsed = false;
	private Composite contentArea;
	private Button toggleButton;
	private Direction direction;
	private ButtonPosition buttonPosition;
	private List<CollapseListener> listeners = new ArrayList<>();

	public CollapsibleComposite(Composite parent, int style, Direction direction, ButtonPosition buttonPosition) {
		super(parent, style);
		this.direction = direction;
		this.buttonPosition = buttonPosition;

		createControls();
	}

	private void createControls() {
		boolean horizontal = (direction == Direction.LEFT || direction == Direction.RIGHT);
		GridLayout layout = new GridLayout(horizontal ? 2 : 1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		this.setLayout(layout);

		if (buttonPosition == ButtonPosition.LEADING) {
			createToggleButton();
			createContentArea();
		} else {
			createContentArea();
			createToggleButton();
		}

		updateLayout();
	}

	private void createToggleButton() {
		toggleButton = new Button(this, SWT.PUSH);
		toggleButton.setText(getToggleText());
		toggleButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				this.direction.equals(Direction.TOP) || this.direction.equals(Direction.BOTTOM),
				this.direction.equals(Direction.LEFT) || this.direction.equals(Direction.RIGHT)));
		toggleButton.addListener(SWT.Selection, e -> toggle());
	}

	private void createContentArea() {
		contentArea = new Composite(this, SWT.BORDER);
		contentArea.setLayout(new FillLayout());
		contentArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	private String getToggleText() {
		switch (direction) {
		case LEFT:
			return collapsed ? ">" : "<";
		case RIGHT:
			return collapsed ? "<" : ">";
		case TOP:
			return collapsed ? "v" : "^";
		case BOTTOM:
			return collapsed ? "^" : "v";
		}
		return "";
	}

	public Composite getContentArea() {
		return contentArea;
	}

	public void addCollapseListener(CollapseListener listener) {
		listeners.add(listener);
	}

	public void toggle() {
		collapsed = !collapsed;
		updateLayout();

		// 回调监听器
		if (collapsed) {
			listeners.forEach(CollapseListener::onCollapse);
		} else {
			listeners.forEach(CollapseListener::onExpand);
		}
	}

	public void collapse() {
		if (!collapsed) {
			collapsed = true;
			updateLayout();
			listeners.forEach(CollapseListener::onCollapse);
		}
	}

	public void expand() {
		if (collapsed) {
			collapsed = false;
			updateLayout();
			listeners.forEach(CollapseListener::onExpand);
		}
	}

	private void updateLayout() {
		contentArea.setVisible(!collapsed);
		((GridData) contentArea.getLayoutData()).exclude = collapsed;
		toggleButton.setText(getToggleText());
		this.layout(true, true);
	}

}
