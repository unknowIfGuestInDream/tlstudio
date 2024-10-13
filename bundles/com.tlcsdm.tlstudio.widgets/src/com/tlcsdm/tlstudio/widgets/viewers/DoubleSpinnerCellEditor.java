package com.tlcsdm.tlstudio.widgets.viewers;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tlcsdm.tlstudio.widgets.custom.DoubleSpinner;

/**
 * A cell editor that presents a list of items in a spinner box.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 */
public class DoubleSpinnerCellEditor extends CellEditor {
	/**
	 * The custom combo box control.
	 */
	protected DoubleSpinner spinner;

	private FocusAdapter focusListener;

	private KeyListener keyListener;

	/**
	 * Spinner Editor
	 *
	 * @param parent the parent control
	 */
	public DoubleSpinnerCellEditor(Composite parent) {
		this(parent, SWT.BORDER);
	}

	/**
	 * Spinner Editor
	 *
	 * @param parent the parent control
	 * @param style  the style bits
	 */
	public DoubleSpinnerCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected Control createControl(Composite parent) {

		spinner = new DoubleSpinner(parent, getStyle());
		spinner.setFont(parent.getFont());
		this.focusListener = new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				DoubleSpinnerCellEditor.this.focusLost();
			}
		};
		spinner.addFocusListener(focusListener);
		this.keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				// Do nothing
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.character == '\n') {
					DoubleSpinnerCellEditor.this.focusLost();
				}
				if (e.character == '\r') {
					DoubleSpinnerCellEditor.this.focusLost();
				}
			}
		};
		spinner.addKeyListener(keyListener);

		return spinner;
	}

	@Override
	public void dispose() {
		if (focusListener != null) {
			spinner.removeFocusListener(focusListener);
		}
		if (keyListener != null) {
			spinner.removeKeyListener(keyListener);
		}
		super.dispose();
	}

	/**
	 * The <code>ComboBoxCellEditor</code> implementation of this
	 * <code>CellEditor</code> framework method returns the zero-based index of the
	 * current selection.
	 *
	 * @return the zero-based index of the current selection wrapped as an
	 *         <code>Integer</code>
	 */
	@Override
	protected Object doGetValue() {
		return spinner.getDouble();
	}

	@Override
	protected void doSetFocus() {
		spinner.setFocus();
	}

	/**
	 * The <code>ComboBoxCellEditor</code> implementation of this
	 * <code>CellEditor</code> framework method sets the minimum width of the cell.
	 * The minimum width is 10 characters if <code>comboBox</code> is not
	 * <code>null</code> or <code>disposed</code> else it is 60 pixels to make sure
	 * the arrow button and some text is visible. The list of CCombo will be wide
	 * enough to show its longest item.
	 * 
	 * @return layoutData
	 */
	@Override
	public LayoutData getLayoutData() {
		LayoutData layoutData = super.getLayoutData();
		if ((spinner == null) || spinner.isDisposed()) {
			layoutData.minimumWidth = 60;
		} else {
			// make the comboBox 10 characters wide
			GC gc = new GC(spinner);
			layoutData.minimumWidth = (int) ((gc.getFontMetrics().getAverageCharacterWidth() * 10) + 10);
			gc.dispose();
		}
		return layoutData;
	}

	/**
	 * The <code>ComboBoxCellEditor</code> implementation of this
	 * <code>CellEditor</code> framework method accepts a zero-based index of a
	 * selection.
	 *
	 * @param value the zero-based index of the selection wrapped as an
	 *              <code>Integer</code>
	 */
	@Override
	protected void doSetValue(Object value) {
		if (spinner != null && (value instanceof Number val)) {
			spinner.setDouble(val.doubleValue());
		}
	}

	/**
	 * Applies the currently selected value and deactivates the cell editor
	 */
	void applyEditorValueAndDeactivate() {
		// must set the selection before getting value
		Object newValue = doGetValue();
		markDirty();
		boolean isValid = isCorrect(newValue);
		setValueValid(isValid);
		fireApplyEditorValue();
		deactivate();
	}

	@Override
	protected void focusLost() {
		if (isActivated()) {
			applyEditorValueAndDeactivate();
		}
	}

	/**
	 * @param i
	 */
	public void setMaximum(double i) {
		if (spinner != null) {
			spinner.setMaximum(i);
		}
	}

	/**
	 * @param i
	 */
	public void setMinimum(double i) {
		if (spinner != null) {
			spinner.setMinimum(i);
		}
	}

	public DoubleSpinner getSpinner() {
		return spinner;
	}

	protected int getDoubleClickTimeout() {
		return 0;
	}

	public void setIncrement(double inc) {
		if (spinner != null) {
			spinner.setIncrement(inc);
		}
	}

	/**
	 * Set the format and automatically set minimum and maximum allowed values
	 * 
	 * @param width     of displayed value as total number of digits
	 * @param precision of value in decimal places
	 */
	public void setFormat(int i, int j) {
		if (spinner != null) {
			spinner.setFormat(i, j);
		}
	}

	public void setBounds(Rectangle bnds) {
		if (spinner != null) {
			spinner.setBounds(bnds);
		}
	}

	public void addKeyListener(KeyListener listener) {
		if (spinner != null) {
			spinner.addKeyListener(listener);
		}
	}

	public void addSelectionListener(SelectionListener listener) {
		if (spinner != null) {
			spinner.addSelectionListener(listener);
		}
	}
}
