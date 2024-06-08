package org.eclipse.swt.snippets;

import org.eclipse.core.commands.*;

public class SnippetHandler extends AbstractHandler{
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
        try {
        	SnippetExplorer.main(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.valueOf(0);
	}
}
