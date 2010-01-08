package com.cell.rpg.scene.graph;

import java.util.Collection;

import com.cell.game.ai.pathfind.AbstractAstarMapNode;

public class SceneGraphNode implements AbstractAstarMapNode
{
	
	
	
	@Override
	public int getDistance(AbstractAstarMapNode target) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Collection<AbstractAstarMapNode> getNexts() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getPriority(AbstractAstarMapNode target) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean testCross(AbstractAstarMapNode father) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
