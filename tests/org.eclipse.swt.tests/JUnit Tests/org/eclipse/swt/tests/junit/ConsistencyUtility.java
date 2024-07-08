/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.tests.junit;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;


public class ConsistencyUtility {

	static HashMap<String, String[]> eventOrdering = new HashMap<>();
	static HashMap<String, Integer> eventTypes = new HashMap<>();
	static {

		eventOrdering.put("DoubleClick", 			new String[] {"MouseDown", "Selection:", "MouseUp", "MouseDown", "MouseDoubleClick", "DefaultSelection", "MouseUp"});
		eventOrdering.put("DragDetect", 			new String[] {"MouseDown", "DragDetect", "MouseUp"});
		eventOrdering.put("EnterSelection", 		new String[] {"Traverse:Return", "KeyDown", "DefaultSelection", "KeyUp"});
		eventOrdering.put("KeySelection", 			new String[] {"Traverse:Arrow Next", "KeyDown", "Selection:", "KeyUp"});
		eventOrdering.put("MenuDetect", 			new String[] {"MouseDown", "MenuDetect", "MouseUp"});

		eventOrdering.put("ButtonMouseSelection", 	new String[] {"MouseDown", "MouseUp", "Selection:"});
		eventOrdering.put("ComboMouseSelection",	new String[] {"MouseDown", "MouseUp", "Verify", "Modify", "Selection:"});
		eventOrdering.put("CComboMouseSelection",	new String[] {"Modify", "Selection:"});
		eventOrdering.put("ExpandBarMouseSelection",new String[] {"MouseDown", "MouseUp", "Selection:"});
		eventOrdering.put("ListMouseSelection", 	new String[] {"MouseDown", "MouseUp", "Selection:"});
		eventOrdering.put("SashMouseSelection", 	new String[] {"Selection:Drag", "MouseDown", "Selection:", "MouseUp"});
		eventOrdering.put("TabFolderMouseSelection",new String[] {"MouseDown", "Selection:", "MouseUp" });
		eventOrdering.put("TableMouseSelection",	new String[] {"MouseDown", "Selection:", "MouseUp" });
		eventOrdering.put("ToolBarMouseSelection", 	new String[] {"MouseDown", "MouseUp", "Selection:"});
		eventOrdering.put("TreeMouseSelection",		new String[] {"MouseDown", "Selection:", "MouseUp" });
		eventOrdering.put("CTabFolderMouseSelection",new String[] {"Selection:", "MouseDown", "MouseUp" });

		eventOrdering.put("ListDoubleClick", 		new String[] {"MouseDown", "MouseUp", "Selection:", "MouseDown", "MouseDoubleClick", "DefaultSelection", "MouseUp"});
		eventOrdering.put("ButtonEnterSelection", 	new String[] {"Traverse:Return", "KeyDown", "KeyUp"});
		eventOrdering.put("ExpandBarEnterSelection",new String[] {"Traverse:Return", "Selection:", "KeyDown", "KeyUp"});
		eventOrdering.put("CComboEnterSelection", 	new String[] {"Traverse:Return", "DefaultSelection", "KeyDown", "KeyUp"});
		eventOrdering.put("ToolBarEnterSelection", 	new String[] {"Traverse:Return", "Selection:", "KeyDown", "KeyUp"});
		eventOrdering.put("TreeDragDetect", 		new String[] {"MouseDown", "Selection:", "DragDetect", "MouseUp"});
		eventOrdering.put("TableMenuDetect", 		new String[] {"MenuDetect", "MouseDown", "Selection:", "MouseUp"});

		eventOrdering.put("ButtonSpaceSelection", 	new String[] {"Traverse:Mnemonic", "KeyDown", "KeyUp", "Selection:"});
		eventOrdering.put("ExpandBarSpaceSelection",new String[] {"MouseDown", "MouseUp", "Selection:", "Traverse:Mnemonic", "KeyDown", "KeyUp"});
		eventOrdering.put("ListSpaceSelection", 	new String[] {"Selection:", "KeyDown", "KeyUp"});
		eventOrdering.put("ToolBarSpaceSelection", 	new String[] {"MouseDown", "MouseUp", "Selection:", "Traverse:Mnemonic", "KeyDown", "KeyUp"});
		eventOrdering.put("TreeSpaceSelection", 	new String[] {"Selection:", "KeyDown", "KeyUp"});

		eventOrdering.put("ComboKeySelection", 		new String[] {"KeyDown", "Verify", "Modify", "Selection:", "KeyUp"});
		eventOrdering.put("CComboKeySelection", 	new String[] {"Traverse:Arrow Next", "Modify", "Selection:", "KeyDown", "KeyUp"});
		eventOrdering.put("CTabFolderKeySelection",	new String[] {"Traverse:Arrow Next", "Selection:", "KeyDown", "KeyUp"});
		eventOrdering.put("SliderKeySelection",		new String[] {"Traverse:Arrow Next", "KeyDown", "Selection:Arrow Down", "KeyUp" });

		eventOrdering.put("SliderArrowSelection",	new String[] {"MouseDown", "Selection:Arrow Down", "MouseUp" });

		eventOrdering.put("CoolBarChevronDragDetect",new String[] {"MouseDown", "DragDetect"});
		eventOrdering.put("CoolBarChevronMenuDetect",new String[] {"MouseDown", "Selection:Arrow"});
		eventOrdering.put("CoolBarChevronMouseSelection",new String[] {"MouseDown", "Selection:Arrow", });

		eventOrdering.put("ScaleTroughSelection",	new String[] {"Selection:", "MouseDown", "MouseUp" });
		eventOrdering.put("SliderTroughSelection",	new String[] {"MouseDown", "Selection:Page Down", "MouseUp" });
		eventOrdering.put("ScaleThumbSelection",	new String[] {"Selection:", "MouseDown", "MouseUp" });
		eventOrdering.put("SliderThumbSelection",	new String[] {"MouseDown", "Selection:Drag", "MouseUp" });

		eventOrdering.put("ShellClose",				new String[] {"Close", "Dispose"});
		eventOrdering.put("ShellDispose",			new String[] {"Dispose"});
		eventOrdering.put("ShellIconify",			new String[] {"FocusOut", "Iconify", "Deactivate", "Deiconify", "Activate", "FocusIn", "Activate"});
		eventOrdering.put("ShellOpen",				new String[] {"Activate", "FocusIn", "Show"});

		eventOrdering.put("TabFolderPgdwnSelection",new String[] {"KeyDown", "Traverse:Page Next", "Selection:", "KeyUp", "KeyUp"});
		eventOrdering.put("TabFolderPgupSelection", new String[] {"KeyDown", "Traverse:Page Previous", "Selection:", "KeyUp", "KeyUp"});
		eventOrdering.put("CTabFolderPgdwnSelection",new String[] {"KeyDown", "Selection:", "Traverse:None", "KeyUp", "KeyUp"});
		eventOrdering.put("CTabFolderPgupSelection",new String[] {"KeyDown", "Selection:", "Traverse:None", "KeyUp", "KeyUp"});

		eventOrdering.put("StyledTextModify",		new String[] {"Verify", "Modify", "KeyDown", "KeyUp"});
		eventOrdering.put("TextModify",				new String[] {"KeyDown", "Verify", "Modify", "KeyUp"});

		eventOrdering.put("TreeKeyExpand", 			new String[] {"Traverse:Arrow Next", "KeyDown", "Expand", "KeyUp"});
		eventOrdering.put("TreeMouseExpand", 		new String[] {"Expand", "MouseDown", "MouseUp"});

		eventTypes.put("None", Integer.valueOf(SWT.None));
		eventTypes.put("KeyDown", Integer.valueOf(SWT.KeyDown));
		eventTypes.put("KeyUp", Integer.valueOf(SWT.KeyUp));
		eventTypes.put("MouseDown", Integer.valueOf(SWT.MouseDown));
		eventTypes.put("MouseUp", Integer.valueOf(SWT.MouseUp));
		eventTypes.put("MouseMove", Integer.valueOf(SWT.MouseMove));
		eventTypes.put("MouseEnter", Integer.valueOf(SWT.MouseEnter));
		eventTypes.put("MouseExit", Integer.valueOf(SWT.MouseExit));
		eventTypes.put("MouseDoubleClick", Integer.valueOf(SWT.MouseDoubleClick));
		eventTypes.put("Paint", Integer.valueOf(SWT.Paint));
		eventTypes.put("Move", Integer.valueOf(SWT.Move));
		eventTypes.put("Resize", Integer.valueOf(SWT.Resize));
		eventTypes.put("Dispose", Integer.valueOf(SWT.Dispose));
		eventTypes.put("Selection", Integer.valueOf(SWT.Selection));
		eventTypes.put("DefaultSelection", Integer.valueOf(SWT.DefaultSelection));
		eventTypes.put("FocusIn", Integer.valueOf(SWT.FocusIn));
		eventTypes.put("FocusOut", Integer.valueOf(SWT.FocusOut));
		eventTypes.put("Expand", Integer.valueOf(SWT.Expand));
		eventTypes.put("Collapse", Integer.valueOf(SWT.Collapse));
		eventTypes.put("Iconify", Integer.valueOf(SWT.Iconify));
		eventTypes.put("Deiconify", Integer.valueOf(SWT.Deiconify));
		eventTypes.put("Close", Integer.valueOf(SWT.Close));
		eventTypes.put("Show", Integer.valueOf(SWT.Show));
		eventTypes.put("Hide", Integer.valueOf(SWT.Hide));
		eventTypes.put("Modify", Integer.valueOf(SWT.Modify));
		eventTypes.put("Verify", Integer.valueOf(SWT.Verify));
		eventTypes.put("Activate", Integer.valueOf(SWT.Activate));
		eventTypes.put("Deactivate", Integer.valueOf(SWT.Deactivate));
		eventTypes.put("Help", Integer.valueOf(SWT.Help));
		eventTypes.put("DragDetect", Integer.valueOf(SWT.DragDetect));
		eventTypes.put("Arm", Integer.valueOf(SWT.Arm));
		eventTypes.put("Traverse", Integer.valueOf(SWT.Traverse));
		eventTypes.put("MouseHover", Integer.valueOf(SWT.MouseHover));
		eventTypes.put("HardKeyDown", Integer.valueOf(SWT.HardKeyDown));
		eventTypes.put("HardKeyUp", Integer.valueOf(SWT.HardKeyUp));
		eventTypes.put("MenuDetect", Integer.valueOf(SWT.MenuDetect));
		eventTypes.put("SetData", Integer.valueOf(SWT.SetData));
	}

	static String[] eventNames = {
		"None", 			"KeyDown", 		"KeyUp", 			"MouseDown",
		"MouseUp",			"MouseMove",	"MouseEnter",		"MouseExit",
		"MouseDoubleClick",	"Paint",		"Move",				"Resize",
		"Dispose",			"Selection",	"DefaultSelection",	"FocusIn",
		"FocusOut",			"Expand",		"Collapse",			"Iconify",
		"Deiconify",		"Close",		"Show",				"Hide",
		"Modify",			"Verify",		"Activate",			"Deactivate",
		"Help",				"DragDetect",	"Arm",				"Traverse",
		"MouseHover",		"HardKeyDown",	"HardKeyUp",		"MenuDetect",
		"SetData"};

	static int[] convertEventNames(String[] origNames) {
		String[] names = new String[origNames.length];
		System.arraycopy(origNames, 0, names, 0, origNames.length);
		names = removeDuplicates(names);
		int[] types = new int[names.length];
		for(int i=0; i<names.length; i++) {
			types[i] = eventTypes.get(names[i]).intValue();
		}
		return types;
	}

	private static String[] removeDuplicates(String[] names) {
		int dups=0;
		for(int i=0; i<names.length; i++) {
			int index = names[i].indexOf(':');
			if(index != -1)
				names[i] = names[i].substring(0, index);
			for(int j=0; j<i; j++) {
				if(names[i].equals(names[j])) {
					for(int k=i+1; k<names.length; k++) {
						names[k-1] = names[k];
					}
					names[names.length-1] = "duplicate"+ dups;
					dups++;
				}
			}
		}
		String[] temp = new String[names.length-dups];
		System.arraycopy(names, 0, temp, 0, names.length-dups);
		return temp;
	}

	public static String[] selectionTypes = {
		"Drag", 		"Home",  			"End", 				"Arrow Down",
		"Arrow Up", 	"Page Down", 		"Page Up", 			"Check",
		"Arrow",
	};

	public static int[] [] selectionConversion = {
		{SWT.DRAG, 0},
		{SWT.HOME, 1},
		{SWT.END, 2},
		{SWT.ARROW_DOWN, 3},
		{SWT.ARROW_UP, 4},
		{SWT.PAGE_DOWN, 5},
		{SWT.PAGE_UP, 6},
		{SWT.CHECK, 7},
		{SWT.ARROW, 8},
	};

	static String getSelectionType(int type) {
		for (int[] element : selectionConversion) {
			if(type == element[0])
				return selectionTypes[element[1]];
		}
		return "";
	}

	static String[] traversalTypes = {
			"None", 		"Escape", 			"Return", 			"Tab Previous",
			"Tab Next", 	"Arrow Previous",	"Arrow Next", 		"Mnemonic",
			"Page Previous", "Page Next"};

	static String getTraversalType(int type) {
		int pow = 0;
		if(type != 0)
			pow =(int)(Math.log(type)/Math.log(2));
		return traversalTypes[pow];
	}

	static final int NONE = 0;
	static final int SHELL_ICONIFY = 1;
	static final int KEY_PRESS = 10;
	static final int DOUBLE_KEY_PRESS = 20;
	static final int MOUSE_CLICK = 30;
	static final int MOUSE_DOUBLECLICK = 40;
	static final int MOUSE_DRAG = 50;
	static final int SELECTION = 60;

	static final int ESCAPE_MENU = 1;


	//posts a click at display mapped x and y with button button
	static boolean postClick(Display display, int x, int y, int button) {
		Event event = new Event();
		event.type = SWT.MouseMove;
		event.x = x;
		event.y = y;
		if(!display.post(event))  {
			System.out.println("MouseMove not posted");
			return false;
		}

		event = new Event();
		event.type = SWT.MouseDown;
		event.button = button;
		if(!display.post(event)) {
			System.out.println("MouseDown not posted");
			return false;
		}
		event = new Event();
		event.type = SWT.MouseUp;
		event.button = button;
		if(!display.post(event)) {
			System.out.println("MouseUp not posted");
			//TODO: potentially dangerous to have a mousedown and no mouseup
			//force the issue?
			return false;
		}
//	  	try {
//	        Thread.sleep(1600);
//	    } catch(InterruptedException ie) {}
		return true;
	}

	static boolean postClick(Display display, Point pt, int button) {
		return postClick(display, pt.x, pt.y, button);
	}

	//doubleclicks at display mapped pt with button button
	static boolean postDoubleClick(Display display, Point pt, int button) {
		boolean ret = postClick(display, pt, button);
		ret &= postClick(display, pt, button);
//	  	try {
//	        Thread.sleep(1600);
//	    } catch(InterruptedException ie) {}
		return ret;
	}

	//post a key press
	static boolean postKeyPress(Display display, int ch, int keycode) {
		Event event = new Event();
		event.type = SWT.KeyDown;
		event.character = (char)ch;
		event.keyCode = keycode;
		if(!display.post(event)) {
			System.out.println("KeyDown not posted");
			return false;
		}
		event = new Event();
		event.type = SWT.KeyUp;
		event.character = (char)ch;
		event.keyCode = keycode;
		if(!display.post(event)) {
			System.out.println("KeyUp not posted");
			//TODO: potentially dangerous to have a keydown and no keyup
			//force the issue?
			return false;
		}
		return true;
	}

	//post pressing and holding ch1/keycode1 and then pressing ch2/keycode2
	static boolean postDoubleKeyPress(Display display, int ch1, int keycode1,
									  int ch2, int keycode2) {
		Event event = new Event();
		event.type = SWT.KeyDown;
		event.character = (char)ch1;
		event.keyCode = keycode1;
		if(!display.post(event)) {
			System.out.println("KeyDown not posted");
			return false;
		}
		postKeyPress(display, ch2, keycode2);
		event = new Event();
		event.type = SWT.KeyUp;
		event.character = (char)ch1;
		event.keyCode = keycode1;
		if(!display.post(event)) {
			System.out.println("KeyUp not posted");
			//TODO: potentially dangerous to have a keydown and no keyup
			//force the issue?
			return false;
		}
		return true;
	}

	//posts a drag from display mapped origin to destination
	//the button to drag with is system dependent
	static boolean postDrag(Display display, Point origin, Point destination) {
		int button = determineDrag();
		Event event = new Event();
		event.type = SWT.MouseMove;
		event.x = origin.x;
		event.y = origin.y;
		if(!display.post(event))  {
			System.out.println("MouseMove not posted");
			return false;
		}

		event = new Event();
		event.type = SWT.MouseDown;
		event.button = button;
		if(!display.post(event)) {
			System.out.println("MouseDown not posted");
			return false;
		}

		event = new Event();
		event.type = SWT.MouseMove;
		event.x = destination.x;
		event.y = destination.y;
		if(!display.post(event))  {
			System.out.println("MouseMove not posted");
			return false;
		}

		event = new Event();
		event.type = SWT.MouseUp;
		event.button = button;
		if(!display.post(event)) {
			System.out.println("MouseUp not posted");
			//TODO: potentially dangerous to have a mousedown and no mouseup
			//force the issue?
			return false;
		}
		return true;
	}

	//determines which button to drag with
	private static int determineDrag() {
		if(SwtTestUtil.isWindows || SwtTestUtil.isGTK)
			return 1;
		return 2;
	}

	//posts a selection ie clicks on pt1, then click on pt2 (for example, selecting
	//one of the options in a combo by dropping down the list)
	static boolean postSelection(final Display display, Point pt1, Point pt2) {
		boolean ret = postClick(display, pt1, 1);
		display.syncExec(new Thread() {
			@Override
			public void run() {
				display.update();
		}});
		ret &= postClick(display, pt2, 1);
		return ret;
	}

	//iconifies the shell. pt is a system dependent point on the shell.
	static boolean postShellIconify(Display display, Point pt, int button) {
		int x = pt.x;
		int y = pt.y - 10;
		if(SwtTestUtil.isWindows) {
			x += -35;
		} else if(SwtTestUtil.isLinux) {
			x += -16;
		}
		return postClick(display, x, y, button);
	}
}
