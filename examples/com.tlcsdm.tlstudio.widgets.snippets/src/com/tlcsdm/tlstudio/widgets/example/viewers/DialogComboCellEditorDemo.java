package com.tlcsdm.tlstudio.widgets.example.viewers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.viewers.DialogComboCellEditor;

public class DialogComboCellEditorDemo {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("DialogComboCellEditor Demo");
		shell.setSize(400, 300);
		shell.setLayout(new FillLayout());

		// 原始数据和可选项
		List<String> input = new ArrayList<>(Arrays.asList("Apple", "Banana"));
		List<String> options = Arrays.asList("Apple", "Banana", "Orange", "Mango", "Grape", "Pineapple");

		TableViewer viewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);

		viewer.setContentProvider(ArrayContentProvider.getInstance());

		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(300);
		column.getColumn().setText("Fruit");

		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return element.toString();
			}
		});

		column.setEditingSupport(new EditingSupport(viewer) {
			@Override
			protected CellEditor getCellEditor(Object element) {
				return new DialogComboCellEditor(viewer.getTable(), options);
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected Object getValue(Object element) {
				return element;
			}

			@Override
			protected void setValue(Object element, Object value) {
				int index = viewer.getTable().getSelectionIndex();
				if (index >= 0 && index < input.size()) {
					input.set(index, value.toString());
					viewer.refresh();
				}
			}
		});

		viewer.setInput(input);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
