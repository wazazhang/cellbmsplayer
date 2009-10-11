package com.g2d.studio.scene;

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
