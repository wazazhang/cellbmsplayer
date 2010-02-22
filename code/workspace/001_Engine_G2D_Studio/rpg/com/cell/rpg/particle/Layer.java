package com.cell.rpg.particle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * 包含多个例子的视图
 * @author WAZA
 */
public class Layer implements Serializable
{
	/** 设置别名 */
	public String	alias;
	
	/** CPJ工程名 */
	public String	cpj_project_name;
	
	/** CPJ图片组名*/
	public String	cpj_images_name;
	
	/** CPJ图片组图片编号*/
	public String	cpj_image_id;

	@Override
	public String toString() {
		if (alias!=null) {
			return alias;
		}
		return super.toString();
	}
	
//	------------------------------------------------------------------------------------------------------------------
//	Scene
//	------------------------------------------------------------------------------------------------------------------
	
	
	/** 粒子生命周期时间范围(秒)*/
	public float	particle_min_age_sec, particle_max_age_sec;
	
	/** 每秒释放多少个粒子 */
	public float	particles_per_sec;
	
	/** 粒子容量 */
	public int 		particles_capacity;
	
	
	
//	------------------------------------------------------------------------------------------------------------------
//	Origin
//	------------------------------------------------------------------------------------------------------------------
	
	/** 发射基地位置 */
	public float 	origin_x, origin_y;
	
	/** 发射基地变换角度 */
	public float 	origin_rotation;
	
	/** 发射基地拉伸 */
	public float 	origin_scale_x, origin_scale_y;
	
	/** 发射基地几何造型 */
	public Shape	origin_shape;
	
//	------------------------------------------------------------------------------------------------------------------
//	Emission
//	------------------------------------------------------------------------------------------------------------------

	/** 发射角度 */
	public float emission_angle;
	
	/** 发射角度随机范围 */
	public float emission_angle_range;
	
	/** 发射速度 */
	public float emission_velocity;
	
	/** 发射速度随机范围 */
	public float emission_velocity_range;
	
	
//	------------------------------------------------------------------------------------------------------------------
//	TimeLine
//	------------------------------------------------------------------------------------------------------------------

	
	public static class TimeLine implements Iterable<TimeNode> , Serializable
	{
		final public TimeNode start	= new TimeNode(0);
		
		final public TimeNode end	= new TimeNode(1);
		
		final private ArrayList<TimeNode> nodes = new ArrayList<TimeNode>();
		
		public ArrayList<TimeNode> getTimeNodes() {
			ArrayList<TimeNode> ret = new ArrayList<TimeNode>(nodes);
			ret.add(start);
			ret.add(end);
			Collections.sort(ret);
			return ret;
		}
		
		@Override
		public Iterator<TimeNode> iterator() {
			return getTimeNodes().iterator();
		}
	}
	
	public static class TimeNode implements Comparable<TimeNode>, Serializable
	{
		final public float position;
		
		public int		color		= 0xffffff;
		
		public float	size		= 1;
		
		public float	alpha		= 1;
		
		public float	spin		= 0;
		
		public TimeNode(float pos) {
			this.position = pos;
		}
		
		@Override
		public int compareTo(TimeNode o) {
			return (int)(this.position*1000000 - o.position*1000000);
		}
	}
	
}
