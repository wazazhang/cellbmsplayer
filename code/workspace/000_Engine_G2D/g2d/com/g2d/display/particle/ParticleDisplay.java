package com.g2d.display.particle;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
import com.g2d.display.DisplayObjectLeaf;
import com.g2d.display.Sprite;
import com.g2d.display.particle.Layer.TimeNode;


public class ParticleDisplay extends com.g2d.display.particle.ParticleSystem
{
	public static Random 			random 			= new Random();

	public static int				composite_rule	= AlphaComposite.SRC_OVER;
	
	final public ParticleData 		data;
	
	final ArrayList<LayerObject> 	layers;
	
	public ParticleDisplay(ParticleData data) {
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
					
					// spawn speed direction acc
					Vector speed		= new TVector();
					if (!layer.spawn_orgin_angle) {
						MathVector.movePolar(speed, 
								layer.spawn_angle    + CUtil.getRandom(random, -layer.spawn_angle_range,    layer.spawn_angle_range), 
								layer.spawn_velocity + CUtil.getRandom(random, -layer.spawn_velocity_range, layer.spawn_velocity_range));
					} else {
						double degree = MathVector.getDegree(pos.getVectorX(), pos.getVectorY());
						MathVector.movePolar(speed, 
								degree, 
								layer.spawn_velocity + CUtil.getRandom(random, -layer.spawn_velocity_range, layer.spawn_velocity_range));
					}
					
					// node
					node.age_time		= CUtil.getRandom(random, layer.particle_min_age, layer.particle_max_age);
					node.timer			= 0;
					
					node.x				= ParticleDisplay.this.x + (float)pos.getVectorX();
					node.y				= ParticleDisplay.this.y + (float)pos.getVectorY();
					node.speed			.setVectorX(speed.getVectorX());
					node.speed			.setVectorY(speed.getVectorY());
					node.acceleration	= layer.spawn_acc + CUtil.getRandom(random, -layer.spawn_acc_range, layer.spawn_acc_range);
					
					if (ParticleDisplay.this.getParent() != null) {
						ParticleDisplay.this.getParent().addChild(node);
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
	static private class SingleObject extends DisplayObjectLeaf implements ParticleAffectNode
	{
		final LayerObject layer;
		
		/** 生命周期 */
		double			age_time;		

		// time line
		Color	tl_color		= Color.WHITE;
		float	tl_size			= 1;
		float	tl_alpha		= 1;
		float	tl_spin			= 0;
		
		Vector	speed 			= new TVector(0, 0);
		float	acceleration	= 0f;
		
		public SingleObject(LayerObject layer) {
			this.layer = layer;
		}
		
		public double 	getVectorX() {return this.x;}
		public double 	getVectorY() {return this.y;}
		public void 	setVectorX(double x) {this.x = x;}
		public void 	setVectorY(double y) {this.y = y;}
		public void 	addVectorX(double dx) {this.x+=dx;}
		public void 	addVectorY(double dy) {this.y+=dy;}
		public float 	getAlpha() {return this.tl_alpha;}
		public float 	getSize() {return this.tl_size;}
		public float 	getSpin() {return this.tl_spin;}
		public Vector 	getSpeed() {return this.speed;}
		public float 	getAcceleration() {return acceleration;}
		public void 	setAcceleration(float acc) {this.acceleration=acc;}
	
		
		@Override
		public void removed(DisplayObjectContainer parent) {
			layer.idle_nodes.add(this);
		}
		@Override
		public void added(DisplayObjectContainer parent) {}
		
		@Override
		public void update() 
		{
			if (timer >= age_time) {
				this.removeFromParent();
			}

			MathVector.move(this, 
					speed.getVectorX(), 
					speed.getVectorY());
			MathVector.scale(speed, acceleration);

			float timeline_position = (float)(timer / age_time);
			
			updateTimeLine(timeline_position);
			
			updateAffects(timeline_position);
		}
		
		@Override
		public void render(Graphics2D g) 
		{
			try{
				g.scale(tl_size, tl_size);
				g.rotate(tl_spin);
				g.setComposite(AlphaComposite.getInstance(composite_rule, tl_alpha));
				if (layer.layer.image!=null) {
					g.drawImage(layer.layer.image, 
							-layer.layer.image.getWidth()>>1, 
							-layer.layer.image.getHeight()>>1, 
							this);
				} else {
					g.setColor(tl_color);
					g.drawArc(-2, -2, 4, 4, 0, 360);
				}
			}finally{
				g.rotate(-tl_spin);
				g.scale(-tl_size, -tl_size);
			}
			
		}
		
		private void updateTimeLine(float timeline_position)
		{
			TimeNode start = null;
			for (TimeNode tn : layer.layer.timeline) {
				if (tn.enable_size) {
					if (timeline_position >= tn.getPosition()) {
						start = tn;
						tl_size = start.size;
					} else if (start != null) {
						float tnpos = timeline_position - start.getPosition();
						float tnlen = tn.getPosition() - start.getPosition();
						float tnval = tnpos / tnlen;
						tl_size	+= (tn.size - start.size) * tnval;
						break;
					}
				}
			}
			
			start = null;
			for (TimeNode tn : layer.layer.timeline) {
				if (tn.enable_spin) {
					if (timeline_position >= tn.getPosition()) {
						start = tn;
						tl_spin = start.spin;
					} else if (start != null) {
						float tnpos = timeline_position - start.getPosition();
						float tnlen = tn.getPosition() - start.getPosition();
						float tnval = tnpos / tnlen;
						tl_spin += (tn.spin - start.spin) * tnval;
						break;
					}
				}
			}
			
			start = null;
			for (TimeNode tn : layer.layer.timeline) {
				if (tn.enable_alpha) {
					if (timeline_position >= tn.getPosition()) {
						start = tn;
						tl_alpha = start.alpha;
					} else if (start != null) {
						float tnpos = timeline_position - start.getPosition();
						float tnlen = tn.getPosition() - start.getPosition();
						float tnval = tnpos / tnlen;
						tl_alpha += (tn.alpha - start.alpha) * tnval;
						break;
					}
				}
			}

		}
		
		private void updateAffects(float timeline_position)
		{
			for (ParticleAffect affect : layer.layer.affects) {
				affect.update(timeline_position, this);
			}
		}
		
	}
	
}
