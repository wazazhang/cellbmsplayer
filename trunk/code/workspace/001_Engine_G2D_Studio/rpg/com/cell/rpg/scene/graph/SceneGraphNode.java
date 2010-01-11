package com.cell.rpg.scene.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.cell.game.ai.pathfind.AbstractAstarMapNode;
import com.cell.math.MathVector;
import com.cell.math.Vector;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.SceneUnit;
import com.cell.rpg.scene.ability.ActorTransport;
import com.cell.util.Pair;

public class SceneGraphNode implements AbstractAstarMapNode
{

//	--------------------------------------------------------------------------------------------

	final public int		scene_id;
	final public String		scene_name;
	final public float		x;
	final public float		y;
	final public float		width;
	final public float		height;
	
	/** 
	 * key is next scene id, <br>
	 * value is next link info*/
	final public LinkedHashMap<Integer, LinkInfo>
							next_links 		= new LinkedHashMap<Integer, LinkInfo>(1);
	
	final ArrayList<AbstractAstarMapNode>		
							nexts 			= new ArrayList<AbstractAstarMapNode>();

//	--------------------------------------------------------------------------------------------

	public SceneGraphNode(Scene scene) 
	{
		this.scene_id	= scene.getIntID();
		this.scene_name	= scene.name;
		this.x			= scene.scene_node.x;
		this.y			= scene.scene_node.y;
		this.width		= scene.scene_node.width;
		this.height		= scene.scene_node.height;
		for (SceneUnit unit : scene.scene_units) {
			ActorTransport tp = unit.getAbility(ActorTransport.class);
			if (tp != null) {
				LinkInfo next = new LinkInfo(
						unit.id,
						Integer.parseInt(tp.next_scene_id), 
						tp.next_scene_object_id); 
				next_links.put(next.next_scene_id, next);
			}
		}
		nexts.ensureCapacity(next_links.size());
	}
	
	@Override
	public Collection<AbstractAstarMapNode> getNexts() {
		return nexts;
	}

	@Override
	public boolean testCross(AbstractAstarMapNode father) {
		return true;
	}

	@Override
	public int getDistance(AbstractAstarMapNode target) {
		SceneGraphNode t = (SceneGraphNode)target;
		return (int)MathVector.getDistance(x, y, t.x, t.y);
	}
	
	@Override
	public int getPriority(AbstractAstarMapNode target) {
		return 10;
	}

//	--------------------------------------------------------------------------------------------
	
	public class LinkInfo
	{
		final public String		this_scene_obj_name;
		final public Integer	next_scene_id;
		final public String		next_scene_obj_name;
		
		public LinkInfo(String this_scene_obj, int next_scene_id, String next_scene_obj) {
			this.this_scene_obj_name	= this_scene_obj;
			this.next_scene_id			= next_scene_id;
			this.next_scene_obj_name	= next_scene_obj;
		}
	}
}
