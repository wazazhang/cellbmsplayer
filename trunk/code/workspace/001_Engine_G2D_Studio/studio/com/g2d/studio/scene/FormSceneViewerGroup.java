package com.g2d.studio.scene;

import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResourceManager;
import com.g2d.studio.ATreeNodeGroup;
import com.g2d.studio.ATreeNodeLeaf;
import com.g2d.studio.ATreeNodeSet;
import com.g2d.studio.Config;
import com.g2d.studio.ResourceManager;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.SetResource;


public class FormSceneViewerGroup  extends ATreeNodeGroup<FormSceneViewer>
{
	public FormSceneViewerGroup(Studio studio)  throws Exception
	{
		super(studio, Config.ROOT_SCENE, Config.RES_SCENE_);
	}
	
	
	

	@Override
	public ATreeNodeLeaf<FormSceneViewer>[] createViewers(ATreeNodeSet<FormSceneViewer> parent) throws Exception
	{
		SetResource res = CellSetResourceManager.getManager().getSet(parent.path + "/" + Config.RES_SCENE_OUTPUT);
		
		FormSceneViewer[] viewers = new FormSceneViewer[res.WorldTable.size()];
		FormSceneViewerLeaf[] leafs = new FormSceneViewerLeaf[res.WorldTable.size()];
		                               
		int i=0;
		for (CellSetResource.WorldSet world : res.WorldTable.values()) {
			try{
				leafs[i] = new FormSceneViewerLeaf(world.Name, parent);
				viewers[i] = new FormSceneViewer(leafs[i], res, world.Name);
				leafs[i].setUserObject(viewers[i]);
			}catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
		
		return leafs;
	
	}
	
	
}
