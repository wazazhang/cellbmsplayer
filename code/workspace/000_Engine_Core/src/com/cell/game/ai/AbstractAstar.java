
package com.cell.game.ai;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * 抽象的A*寻路算法，子类可以自定义实现如何寻路。
 * @author WAZA
 */
public abstract class AbstractAstar
{
//	-----------------------------------------------------------------------------------------------------------------
	
	/**
	 * 抽象的地图，所有寻路操作都在该类中完成
	 * @author WAZA
	 */
	public static interface AbstractAstarMap
	{
		/**
		 * 测试是否包含该节点
		 * @param node
		 * @return
		 */
		public boolean 						containsNode(AbstractMapNode node);

		/**
		 * 得到所有的节点
		 * @return
		 */
		public Collection<AbstractMapNode>	getAllNodes();

		/**
		 * 得到节点数量
		 * @return
		 */
		public int 							getNodeCount();
	}

//	-----------------------------------------------------------------------------------------------------------------
	
	/**
	 * 抽象的地图节点
	 * @author WAZA
	 */
	public static interface AbstractMapNode 
	{
		/**
		 * 得到所有通向的下一个节点
		 * @return
		 */
		public Collection<AbstractMapNode> 	getNexts();

		/**
		 * 测试是否可以过
		 * @param next
		 * @return
		 */
		public boolean 						testCross(AbstractMapNode next);
		
		/**
		 * 得到和目标节点的距离
		 * @param target
		 * @return
		 */
		public int							getDistance(AbstractMapNode target);
	}

//	-----------------------------------------------------------------------------------------------------------------
	
	final public static class WayPoint implements Serializable
	{
		final public	AbstractMapNode	map_node;
		
		public			WayPoint		next;
		
		public WayPoint(AbstractMapNode	map_node) {
			this.map_node = map_node;
		}
		
	}

//	-----------------------------------------------------------------------------------------------------------------
	
	final class TempMapNode
	{
		/**从all_node里快速索引*/
		final int				node_index;
		
		/**对应的地图数据*/
		final AbstractMapNode 	data;

		/**对应的下一个节点*/
		final TempMapNode[]		nexts;
		
		
		/**每次寻路时，暂存的上一个节点*/
		transient TempMapNode	s_father;
		transient int 			s_g = 0;
		transient int 			s_h = 0;
		transient int 			s_f = 0;
		
		TempMapNode(int node_index, AbstractMapNode data) {
			this.node_index	= node_index;
			this.data		= data;
			this.nexts		= new TempMapNode[data.getNexts().size()];
		}
		
		void setFather(TempMapNode father, TempMapNode target)
		{
			this.s_father	= father;
			this.s_g 		= father.s_g + data.getDistance(target.data);
			this.s_h 		= data.getDistance(target.data);
			this.s_f 		= (s_g + s_h) ;
		}
	}

//	-----------------------------------------------------------------------------------------------------------------
	
	final class TempMapNodeList
	{
		final TempMapNode[]				list;
		final ArrayList<TempMapNode>	actives;
		
		TempMapNodeList(int size) {
			this.list 		= new TempMapNode[size];
			this.actives	= new ArrayList<TempMapNode>(size);
		}
		
		final void add(TempMapNode node) {
			actives.add(node);
			list[node.node_index] = node;
		}
		
		final void remove(TempMapNode node) {
			actives.remove(node);
			list[node.node_index] = null;
		}
		
		final void clear() {
			actives.clear();
			Arrays.fill(list, 0, list.length, null);
		}

		final boolean contains(TempMapNode node) {
			return list[node.node_index] != null;
		}
		
		final boolean isEmpty() {
			return actives.isEmpty();
		}
		
		final TempMapNode getMinF(){
			int min = Integer.MAX_VALUE;
			TempMapNode ret = null;
			for(TempMapNode a : actives){
				int v = a.s_f;
				if (min > v) {
					ret = a;
					min = v;
				}
			}
			return ret;
		}
		
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	AbstractAstarMap 	map;
	
	TempMapNode[]		all_node;
	TempMapNodeList		src_open_list;
	TempMapNodeList		src_close_list;
	
//	-----------------------------------------------------------------------------------------------------------------
	
	public AbstractAstar(AbstractAstarMap map)
	{
		this.map			= map;
		
		this.all_node		= new TempMapNode[map.getNodeCount()];
		this.src_open_list	= new TempMapNodeList(all_node.length);
		this.src_close_list	= new TempMapNodeList(all_node.length);
		
		
		HashMap<AbstractMapNode, TempMapNode> tmap = new HashMap<AbstractMapNode, TempMapNode>(all_node.length);
		
		int i = 0;
		for (AbstractMapNode node : map.getAllNodes()) {
			all_node[i] = new TempMapNode(i, node);
			tmap.put(node, all_node[i]);
			i++;
		}
		
		for (TempMapNode tnode : all_node) {
			int j = 0;
			for (AbstractMapNode next : tnode.data.getNexts()) {
				TempMapNode tnext = tmap.get(next);
				tnode.nexts[j] = tnext;
				j++;
			}
		}
		
	}
	
	protected WayPoint findPath(TempMapNode src_node, TempMapNode dst_node) throws Exception
	{
		WayPoint head = new WayPoint(src_node.data);

		if (src_node.data.equals(dst_node.data)) {
			return head;
		}

		src_open_list.clear();
		src_close_list.clear();
		
		src_open_list.add(src_node);

		do{
			// search min F
			TempMapNode cur_node = src_open_list.getMinF();
			{
				// put the min F to closed
				src_open_list.remove(cur_node);
				src_close_list.add(cur_node);
				
				// find next node
				for (TempMapNode near : cur_node.nexts)
				{
					if (cur_node.data.testCross(near.data)) {
						continue;
					}
					// ignore what if the block can not across or already in close table
					if (src_close_list.contains(near)) {
						continue;
					}
					// push and if is not in open table
					if (!src_open_list.contains(near)) {
						src_open_list.add(near);
					}
					near.setFather(cur_node, dst_node);
				}
			}
			
			// stop when :
			{
				// 1. dst node already in close list
				if (cur_node.data.equals(dst_node.data))
				{
					// finded the path
					WayPoint end = new WayPoint(cur_node.data);
					
					for (int i = all_node.length - 1; i >= 0; i--) {
						// linked to head
						if( cur_node.data.equals(src_node.data) || cur_node.s_father == cur_node) {
							head.next = end;
							break;
						}else{
							WayPoint next = new WayPoint(src_node.data);
							next.next = end;
							end = next;
						}
						cur_node = cur_node.s_father;
					}
					break;
				}
				// 2. open list is empty
				if(src_open_list.isEmpty()){
					// not find the path
					break;
				}
			}
		}while(true);

		return head;
	}


	
}





