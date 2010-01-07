
package com.cell.game.ai.pathfind;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * 实现了曼哈顿算法的A*寻路算法
 * @author WAZA
 */
public class AstarManhattan extends AbstractAstar
{
	public static class WayPoint implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
		public int BX;
		public int BY;
		public int X; 
		public int Y;
		
		public WayPoint	Next;
		
		transient	public Object	Data;
		
		public WayPoint(int bx, int by, int cellw, int cellh)
		{
			BX = bx;
			BY = by;
			X = BX*cellw + cellw/2;
			Y = BY*cellh + cellh/2;
		}
		
		public WayPoint(int x, int y)
		{
			X = x;
			Y = y;
		}
	}

//	----------------------------------------------------------------------------------------------------
	
	final static int[][] near = new int[][]{
			{-1,-1}, { 0,-1}, { 1,-1},
			{-1, 0}, /*{0,0}*/{ 1, 0},
			{-1, 1}, { 0, 1}, { 1, 1}
		};
	
	static class MMap implements AbstractAstarMap<MMapNode>
	{
		final AstarManhattanMap	map;
		final int		xcount;
		final int		ycount;
		
		final ArrayList<MMapNode> 
						all_nodes;

		final MMapNode
						all_nodes_map[][];
		
		public MMap(AstarManhattanMap map) 
		{
			this.map	= map;
			this.xcount	= map.getXCount();
			this.ycount	= map.getYCount();
			
			this.all_nodes 		= new ArrayList<MMapNode>(xcount*ycount);
			this.all_nodes_map 	= new MMapNode[ycount][xcount];
			for (int y = 0; y < map.getYCount(); y++) {
				for (int x = 0; x < map.getXCount(); x++) {
					MMapNode node = new MMapNode(this, x, y);
					all_nodes_map[y][x] = node;
					all_nodes.add(node);
				}
			}
		
			for (int y = 0; y < map.getYCount(); y++) {
				for (int x = 0; x < map.getXCount(); x++) {
					MMapNode node = all_nodes_map[y][x];
					for (int[] np : near) {
						try{
							int ndx = np[0];
							int ndy = np[1];
							MMapNode near = all_nodes_map[y+ndy][x+ndx];
//							if (map.getFlag(x, y) != 0) {
//								continue;
//							}
//							if (ndx!=0 && ndy!=0) {
//								ManhattanMapNode ta	= all_nodes_map[y][x+ndx];
//								ManhattanMapNode tb = all_nodes_map[y+ndy][x];
//								if (ta.flag!=0 || tb.flag!=0 ) {
//									continue;
//								}
//							}
							node.nexts.add(near);
						}catch(Exception err){}
					}
				}
			}
		}
		
		@Override
		public boolean containsNode(MMapNode node) {
			if (node.Y < ycount && node.Y >= 0) {
				if (node.X < xcount && node.X >= 0) {
					return true;
				}
			}
			return false;
		}
		
		@Override
		public Collection<MMapNode> getAllNodes() {
			return all_nodes;
		}
		
		@Override
		public int getNodeCount() {
			return all_nodes.size();
		}
	}

//	----------------------------------------------------------------------------------------------------
	
	static class MMapNode implements AbstractAstarMapNode
	{
		MMap 	mmap;
		short 	X;
		short 	Y;
		
		transient ArrayList<AbstractAstarMapNode> nexts = new ArrayList<AbstractAstarMapNode>(8);
		
		public MMapNode(MMap map, int x, int y)
		{
			this.mmap	= map;
			this.X 		= (short)x;
			this.Y 		= (short)y;
		}
		
		@Override
		public Collection<AbstractAstarMapNode> getNexts() {
			return nexts;
		}
		
		@Override
		public boolean testCross(AbstractAstarMapNode father) {
			return mmap.map.getFlag(X, Y) == 0;
		}
		
		@Override
		public int getDistance(AbstractAstarMapNode target) {
			MMapNode t = (MMapNode)target;
			return (Math.abs(X - t.X) + Math.abs(Y - t.Y));
		}
		
		@Override
		public int getPriority(AbstractAstarMapNode target) {
			MMapNode t = (MMapNode)target;
			return (Math.abs(X - t.X) + Math.abs(Y - t.Y));
		}
	}

//	----------------------------------------------------------------------------------------------------
	TempMapNode[][] 	node_map;
	MMap 		mmap ;
	
	public AstarManhattan(AstarManhattanMap map)
	{
		super(new MMap(map));
		
		this.mmap = (MMap)getMap();
		
		this.node_map = new TempMapNode[mmap.ycount][mmap.xcount];
		
		for (int y = 0; y < mmap.ycount; y++) {
			for (int x = 0; x < mmap.xcount; x++) {
				getTNode(x, y);
			}
		}
	}
	
	private TempMapNode getTNode(int x, int y) {
		for (TempMapNode tnode : super.getAllNode()) {
			MMapNode mnode = (MMapNode)tnode.data;
			if (mnode.X == x && mnode.Y == y) {
				node_map[y][x] = tnode;
				return tnode;
			}
		}
		return null;
	}
	
	private WayPoint toWP(com.cell.game.ai.pathfind.AbstractAstar.WayPoint wp)
	{
		WayPoint ret = null;
		WayPoint path = null;
		while (wp != null) {
			MMapNode mnpde = (MMapNode)wp.map_node;
			System.out.println(mnpde.X+","+mnpde.Y);
			if (path != null) {
				path.Next = new WayPoint(mnpde.X, mnpde.Y, mmap.map.getCellW(), mmap.map.getCellH());
				path = path.Next;
			} else {
				path = new WayPoint(mnpde.X, mnpde.Y, mmap.map.getCellW(), mmap.map.getCellH());
				ret = path;
			}
			wp = wp.next;
		}
		return ret;
	}
	
	public WayPoint findPath(int sx, int sy, int dx, int dy) 
	{
		try{
			com.cell.game.ai.pathfind.AbstractAstar.WayPoint wp = super.findPath(
					node_map[sy][sx], 
					node_map[dy][dx]);
			return toWP(wp);
		}catch(Throwable err) {
			err.printStackTrace();
		}
		return null;
	}

	@Override
	public com.cell.game.ai.pathfind.AbstractAstar.WayPoint findPath(AbstractAstarMapNode srcNode, AbstractAstarMapNode dstNode) {
		try{
			MMapNode ss = (MMapNode)srcNode;
			MMapNode ds = (MMapNode)dstNode;
			com.cell.game.ai.pathfind.AbstractAstar.WayPoint wp = super.findPath(
					node_map[ss.Y][ss.X], 
					node_map[ds.Y][ds.X]);
			return wp;
		}catch(Throwable err) {
			err.printStackTrace();
		}
		return null;
	}
}





