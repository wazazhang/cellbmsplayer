package com.cell.rpg.scene.graph;

import java.io.Serializable;
import java.util.Collection;

import com.cell.game.ai.pathfind.AbstractAstarMap;


/**
 * 所有场景的链接图，并提供寻路算法
 * @author WAZA
 *
 */
public class SceneGraph implements AbstractAstarMap<SceneGraphNode>, Serializable
{
	
	public SceneGraph() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean containsNode(SceneGraphNode node) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Collection<SceneGraphNode> getAllNodes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getNodeCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
