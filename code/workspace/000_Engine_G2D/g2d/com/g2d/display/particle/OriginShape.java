package com.g2d.display.particle;

import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.Random;

import com.cell.CUtil;
import com.cell.math.MathVector;
import com.cell.math.TVector;
import com.cell.math.Vector;


public interface OriginShape extends Serializable
{
	/**
	 * 得到发射位置
	 * @param random
	 * @return {x, y}
	 */
	public Vector getPosition(Random random, Layer layer);
	
	
	/** 矩形  */
	public static class Rectangle implements OriginShape
	{
		public float x = -100, y = -100, w = 200, h = 200;
		
		@Override
		public Vector getPosition(Random random, Layer layer) {
			TVector vector = new TVector(
					layer.origin_x + CUtil.getRandom(random, x, x+w), 
					layer.origin_y + CUtil.getRandom(random, y, y+h));
			vector.setVectorX(vector.getVectorX() * layer.origin_scale_x);
			vector.setVectorY(vector.getVectorY() * layer.origin_scale_y);
			MathVector.rotate(vector, Math.toRadians(layer.origin_rotation_angle));
			return vector;
		}
	}
	
	/**
	 * 圆或环
	 */
	public static class Ring implements OriginShape
	{
		/** 内圆半径 */
		public float radius1 = 50;
		
		/** 外圆半径 */
		public float radius2 = 100;
		
		@Override
		public Vector getPosition(Random random, Layer layer) {
			TVector vector = new TVector(0, 0);
			MathVector.movePolar(vector, 
					CUtil.getRandom(random, 0, (float)(Math.PI*2)), 
					CUtil.getRandom(random, radius1, radius2));
			return vector;
		}
	}
}
