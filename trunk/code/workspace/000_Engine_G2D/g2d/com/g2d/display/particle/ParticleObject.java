package com.g2d.display.particle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.cell.CUtil;
import com.cell.math.MathVector;
import com.cell.math.TVector;
import com.cell.math.Vector;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;


public class ParticleObject extends com.g2d.display.particle.ParticleSystem
{
	public static Random 			random = new Random();
	
	
	final public ParticleData 		data;
	
	final ArrayList<LayerObject> 	layers;
	
	public ParticleObject(ParticleData data) {
		this.data = data;
		this.layers = new ArrayList<LayerObject>(data.size());
		for (Layer layer : data) {
			layers.add(new LayerObject(layer));
		}
	}
	
	public void spawn() {
		for (LayerObject layer : layers) {
			layer.update();
		}
	}
	
	@Override
	public void update() {
		if (data.particles_continued) {
			spawn();
		}
	}
	
	/**
	 * Layer Display Object
	 */
	class LayerObject
	{
		Queue<SingleObject> idle_nodes = new LinkedList<SingleObject>();
		
		Layer layer;
		
		public LayerObject(Layer layer) {
			this.layer = layer;
			for (int i = 0; i < layer.particles_capacity; i++) {
				SingleObject node = new SingleObject(this);
				idle_nodes.add(node);
			}
		}
		
		public void update() {
			for (int i = 0; i < layer.particles_per_frame; i++) {
				SingleObject node = idle_nodes.poll();
				if (node != null) {
					// spawn origin
					Vector pos			= layer.origin_shape.getPosition(random, layer);
					// spawn speed direction
					Vector spawn		= new TVector();
					MathVector.movePolar(spawn, 
							layer.spawn_angle    + CUtil.getRandom(random, -layer.spawn_angle_range,    layer.spawn_angle_range), 
							layer.spawn_velocity + CUtil.getRandom(random, -layer.spawn_velocity_range, layer.spawn_velocity_range));
					// node
					node.particle_age	= CUtil.getRandom(random, layer.particle_min_age, layer.particle_max_age);
					node.timer			= 0;
					node.x				= ParticleObject.this.x + (float)pos.getVectorX();
					node.y				= ParticleObject.this.y + (float)pos.getVectorY();
					node.speed_x		= (float)spawn.getVectorX();
					node.speed_y		= (float)spawn.getVectorY();
					if (ParticleObject.this.getParent() != null) {
						ParticleObject.this.getParent().addChild(node);
					}
				} else {
					break;
				}
			}
		}
		
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
//	Particle 
//	-----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Single Particle
	 */
	class SingleObject extends Sprite
	{
		final LayerObject layer;
		
		int particle_age;
		
		float speed_x, speed_y;
		
		
		public SingleObject(LayerObject layer) {
			this.layer = layer;
		}
		
		@Override
		public void removed(DisplayObjectContainer parent) {
			super.removed(parent);
			layer.idle_nodes.add(this);
		}
		
		@Override
		public void update() {
			if (timer >= particle_age) {
				this.removeFromParent();
			}
			this.move(speed_x, speed_y);
		}
		
		@Override
		public void render(Graphics2D g) 
		{
			g.setColor(Color.WHITE);
			g.drawArc(-2, -2, 4, 4, 0, 360);
		}
		
	}
	
}
