package com.g2d.editor.property;

import java.awt.Component;

public interface ObjectPropertyEdit {

	public CellEditAdapter<?>[] getAdapters();
	
	public Component getComponent();
	
	/**
	 * 通知单元格编辑器，已经完成了编辑
	 */
	public void fireEditingStopped();
}
