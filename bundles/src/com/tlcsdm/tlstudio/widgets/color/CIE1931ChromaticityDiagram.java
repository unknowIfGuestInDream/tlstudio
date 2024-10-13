package com.tlcsdm.tlstudio.widgets.color;

import java.util.List;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

import com.tlcsdm.tlstudio.widgets.model.CIEData;
import com.tlcsdm.tlstudio.widgets.model.CIEData1931;

public class CIE1931ChromaticityDiagram extends ChromaticityDiagram {

	public CIE1931ChromaticityDiagram(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected void drawOutLines(GC gc) {
		List<CIEData> data = CIEData1931.getInstance();
		outline(data, "CIE 1931", 535, textColor, gc);
	}

}
