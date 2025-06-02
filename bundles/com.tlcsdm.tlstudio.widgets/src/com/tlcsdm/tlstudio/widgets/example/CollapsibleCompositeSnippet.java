package com.tlcsdm.tlstudio.widgets.example;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.tlcsdm.tlstudio.widgets.custom.CollapsibleComposite;

public class CollapsibleCompositeSnippet {

	public CollapsibleCompositeSnippet(Shell shell) {
		// 创建可收缩组件，设置方向为向左收缩
		CollapsibleComposite collapsible = new CollapsibleComposite(shell, SWT.NONE,
				CollapsibleComposite.Direction.LEFT, CollapsibleComposite.ButtonPosition.TRAILING);

		// 获取内容区域，往里面加控件
		Composite content = collapsible.getContentArea();
		new Label(content, SWT.NONE).setText("我是可收缩区域的内容！");

		collapsible.addCollapseListener(new CollapsibleComposite.CollapseListener() {
			@Override
			public void onCollapse() {
				System.out.println("内容已折叠");
			}

			@Override
			public void onExpand() {
				System.out.println("内容已展开");
			}
		});

		// 你可以手动控制：
		// composite.collapse();
		// composite.expand();

	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		new CollapsibleCompositeSnippet(shell);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();

	}

}
