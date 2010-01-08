package com.cell.rpg.scene.graph;

import java.io.Serializable;
import java.util.Collection;

import com.cell.game.ai.pathfind.AbstractAstar;
import com.cell.game.ai.pathfind.AbstractAstarMap;
import com.cell.game.ai.pathfind.AbstractAstarMapNode;


/**
 * 所有场景的链接图，并提供寻路算法
 * @author WAZA
 *
 */
public class SceneGraphAstar extends AbstractAstar
{

	public SceneGraphAstar(SceneGraph map) {
		super(map);
	}

	public SceneGraph getMap() {
		return (SceneGraph)super.getMap();
	}
	
	@Override
	public WayPoint findPath(AbstractAstarMapNode srcNode, AbstractAstarMapNode dstNode) {

		return null;
	}
	
}
