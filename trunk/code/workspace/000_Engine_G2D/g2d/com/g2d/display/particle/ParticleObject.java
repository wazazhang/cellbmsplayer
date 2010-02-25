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
import com.g2d.display.DisplayObject;
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
			layer.spawn();
		}
	}

	@Override
	public void update() {
		for (LayerObject layer : layers) {
			layer.update();
		}
	}
	
	/**
	 * Layer Display Object
	 */
	private class LayerObject
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
		
		public void spawn() {
			for (int i = 0; i < layer.particles_per_frame; i++) {
				SingleObject node = idle_nodes.poll();
				if (node != null) {
					// spawn origin
					Vector pos			= layer.origin_shape.getPosition(random);
					MathVector.move(pos, layer.origin_x, layer.origin_y);
					MathVector.rotate(pos, layer.origin_rotation_angle);
					pos.setVectorX(pos.getVectorX() * layer.origin_scale_x);
					pos.setVectorY(pos.getVectorY() * layer.origin_scale_y);
					
					// spawn speed direction
					Vector spawn		= new TVector();
					if (!layer.spawn_orgin_angle) {
						MathVector.movePolar(spawn, 
								layer.spawn_angle    + CUtil.getRandom(random, -layer.spawn_angle_range,    layer.spawn_angle_range), 
								layer.spawn_velocity + CUtil.getRandom(random, -layer.spawn_velocity_range, layer.spawn_velocity_range));
					} else {
						double degree = MathVector.getDegree(pos.getVectorX(), pos.getVectorY());
						MathVector.movePolar(spawn, 
								degree, 
								layer.spawn_velocity + CUtil.getRandom(random, -layer.spawn_velocity_range, layer.spawn_velocity_range));
					}
					
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
		
		public void update() {
			if (layer.particles_continued) {
				spawn();
			}
		}
		
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
//	Particle 
//	-----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Single Particle
	 */
	private class SingleObject extends DisplayObject implements Vector
	{
		final LayerObject layer;
		
		int particle_age;
		
		float speed_x, speed_y;
		float acc_x, acc_y;
		
		public SingleObject(LayerObject layer) {
			this.layer = layer;
		}
		
		public double getVectorX(){return this.x;}
		public double getVectorY(){return this.y;}
		public void setVectorX(double x){this.x = x;}
		public void setVectorY(double y){this.y = y;}
		public void addVectorX(double dx){this.x+=dx;}
		public void addVectorY(double dy){this.y+=dy;}
		
		@Override
		public void removed(DisplayObjectContainer parent) {
			layer.idle_nodes.add(this);
		}
		@Override
		public void added(DisplayObjectContainer parent) {}
		
		@Override
		public void update() {
			if (timer >= particle_age) {
				this.removeFromParent();
			}
			MathVector.move(this, speed_x, speed_y);
		}
		
		@Override
		public void render(Graphics2D g) 
		{
			if (layer.layer.image!=null) {
				g.drawImage(layer.layer.image, 
						-layer.layer.image.getWidth()>>1, 
						-layer.layer.image.getHeight()>>1, 
						this);
			} else {
				g.setColor(Color.WHITE);
				g.drawArc(-2, -2, 4, 4, 0, 360);
			}
		}
		
	}
	
}
