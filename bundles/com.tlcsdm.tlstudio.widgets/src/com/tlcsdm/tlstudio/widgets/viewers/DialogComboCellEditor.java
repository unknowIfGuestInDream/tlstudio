package com.tlcsdm.tlstudio.widgets.viewers;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 功能不稳定，需要在生产环境校验
 */
@Deprecated
public class DialogComboCellEditor extends CellEditor {

	private String value;
	private List<String> allItems;
	private Composite editorComposite;

	public DialogComboCellEditor(Composite parent, List<String> items) {
		super(parent);
		this.allItems = items;
	}

	@Override
	protected Control createControl(Composite parent) {
		// 用Composite代替Label，避免布局相关问题
		editorComposite = new Composite(parent, SWT.NONE);
		editorComposite.setLayout(new GridLayout(1, false));
		Label label = new Label(editorComposite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		return editorComposite;
	}

	@Override
	protected void doSetFocus() {
		openDialog();
	}

	@Override
	protected Object doGetValue() {
		return value;
	}

	@Override
	protected void doSetValue(Object value) {
		this.value = value == null ? "" : value.toString();
		if (editorComposite != null && !editorComposite.isDisposed()) {
			for (Control c : editorComposite.getChildren()) {
				if (c instanceof Label) {
					((Label) c).setText(this.value);
					editorComposite.layout(); // 刷新布局
					break;
				}
			}
		}
	}

	@Override
	public void activate() {
		super.activate();
		openDialog();
	}

	private void openDialog() {
		if (editorComposite == null || editorComposite.isDisposed())
			return;
		Shell shell = editorComposite.getShell();
		FilterDialog dialog = new FilterDialog(shell, allItems, value);
		int result = dialog.open();
		if (result == Window.OK) {
			value = dialog.getSelectedValue();
			if (editorComposite != null && !editorComposite.isDisposed()) {
				for (Control c : editorComposite.getChildren()) {
					if (c instanceof Label) {
						((Label) c).setText(value);
						editorComposite.layout();
						break;
					}
				}
			}
			fireApplyEditorValue();
		} else {
			fireCancelEditor();
		}
		deactivate();
	}

	private static class FilterDialog extends Dialog {
		private List<String> allItems;
		private List<String> filteredItems;
		private String selectedValue;
		private String initialValue;

		private Text filterText;
		private org.eclipse.swt.widgets.List list;

		protected FilterDialog(Shell parentShell, List<String> items, String initialValue) {
			super(parentShell);
			this.allItems = items;
			this.initialValue = initialValue;
			this.filteredItems = items;
			this.selectedValue = initialValue;
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite container = (Composite) super.createDialogArea(parent);
			container.setLayout(new GridLayout(2, false));

			filterText = new Text(container, SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL);
			filterText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			filterText.setText("");

			filterText.addModifyListener(e -> {
				String filter = filterText.getText().toLowerCase();
				filteredItems = allItems.stream().filter(item -> item.toLowerCase().contains(filter))
						.collect(Collectors.toList());
				refreshList();
			});

			list = new org.eclipse.swt.widgets.List(container, SWT.BORDER | SWT.V_SCROLL | SWT.SINGLE);
			GridData listData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
			list.setLayoutData(listData);

			refreshList();

			if (initialValue != null) {
				int idx = filteredItems.indexOf(initialValue);
				if (idx >= 0) {
					list.select(idx);
				}
			}

			list.addListener(SWT.Selection, e -> {
				int idx = list.getSelectionIndex();
				if (idx >= 0) {
					selectedValue = filteredItems.get(idx);
				}
			});

			return container;
		}

		private void refreshList() {
			if (list == null || list.isDisposed())
				return;
			list.removeAll();
			for (String item : filteredItems) {
				list.add(item);
			}
			if (selectedValue != null) {
				int idx = filteredItems.indexOf(selectedValue);
				if (idx >= 0) {
					list.select(idx);
				}
			}
		}

		@Override
		protected void okPressed() {
			int idx = list.getSelectionIndex();
			if (idx >= 0) {
				selectedValue = filteredItems.get(idx);
			} else {
				selectedValue = filterText.getText();
			}
			super.okPressed();
		}

		public String getSelectedValue() {
			return selectedValue;
		}
	}
}
