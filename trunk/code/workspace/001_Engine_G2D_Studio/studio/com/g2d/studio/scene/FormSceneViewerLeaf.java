package com.g2d.studio.scene;

import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JPopupMenu;
import javax.swing.JTree;

import com.g2d.studio.ATreeNodeLeaf;
import com.g2d.studio.ATreeNodeSet;

public class FormSceneViewerLeaf extends ATreeNodeLeaf<FormSceneViewer>
{
	public FormSceneViewerLeaf(String name, ATreeNodeSet<FormSceneViewer> parent) {
		super(name, parent);
	}
	
	
	@Override
	public FormSceneViewer getViewer() {

		return super.getViewer();
	}
	
}
