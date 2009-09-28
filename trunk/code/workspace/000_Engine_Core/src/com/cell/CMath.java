

package com.cell;

/**
 * util math function none float
 * 
 * @author yifeizhang
 * @since 2006-12-1 
 * @version 1.0
 */
public class CMath extends CObject{

	final public static int sqrt(int i) {
		int l = 0;
		for (int k = 0x100000; k != 0; k >>= 2) {
			int j = l + k;
			l >>= 1;
			if (j <= i) {
				i -= j;
				l += k;
			}
		}
		return l;
	}
	
	/** Pi */
	final static public int PI256 = 804;


	/**
	 * sine*256 angle table 0~90
	 */
	private static final short[] SINES = { 0, 4, 9, 13, 18, 22, 27, 31, 36, 40,
			44, 49, 53, 58, 62, 66, 71, 75, 79, 83, 88, 92, 96, 100, 104, 108,
			112, 116, 120, 124, 129, 132, 136, 139, 143, 147, 150, 154, 158,
			161, 165, 168, 171, 175, 178, 181, 184, 187, 190, 193, 196, 199,
			202, 204, 207, 210, 212, 215, 217, 219, 222, 224, 226, 228, 230,
			232, 234, 236, 237, 239, 241, 242, 243, 245, 246, 247, 248, 249,
			250, 251, 252, 253, 254, 254, 255, 255, 255, 256, 256, 256, 256, };

	/**
	 * @param angle 0~360
	 * @return 256 point
	 */
	final public static int sinTimes256(int angle) {
		int d = angle < 0 ? -1 : 1;
		angle = Math.abs(angle % 360); // 360 degrees
		if (angle <= 90) {
			return SINES[angle] * d;
		} else if (angle <= 180) {
			return SINES[180 - angle] * d;
		} else if (angle <= 270) {
			return -SINES[angle - 180] * d;
		} else {
			return -SINES[360 - angle] * d;
		}
	}

	/**
	 * 
	 * 
	 * @param angle  0~360
	 * @return 256 
	 */
	final public static int cosTimes256(int angle) {
		return sinTimes256(angle + 90); // i.e. add 90 degrees
	}

	/**
	 *  
	 * 
	 * @param angle  0~360
	 * @return 256 
	 */
	final public static int tanTimes256(int angle) {
		int dx = cosTimes256(angle);
		if(dx==0)return Integer.MAX_VALUE;
		return sinTimes256(angle) * 256 / dx;
	}

	/**
	 *  
	 * 
	 * @param angle  0~360
	 * @return 256 
	 */
	final public static int cotTimes256(int angle) {
		int dy = sinTimes256(angle);
		if(dy==0)return Integer.MAX_VALUE;
		return cosTimes256(angle) * 256 / dy;
	}

	
	
//	--------------------------------------------------------------------------------------------------------
	//  10 degree atan array[angle/10] = x/y * 256
	private static final short[] AtanDivTable = new short[]{
		0,45,93,147,214,305,443,703,1451,Short.MAX_VALUE
	};

	final private static int div(int i, int j) {
		int tmp = ((int)i<<8) / j;
		if(tmp > Short.MAX_VALUE){
			return Short.MAX_VALUE;
		}
		if(tmp < Short.MIN_VALUE){
			return Short.MIN_VALUE;
		}
		return (int)(tmp);
	}
	
	final private static int atan(int n){
		boolean flag = n < 0;
		if(flag) {
			n = -n;
		}
		int f1 = 0, f2 = AtanDivTable.length - 1,ft = 0;
		while(f1 + 1 != f2) {
			ft = f1 + f2 >> 1;
			if(n < AtanDivTable[ft]) {
				f2 = ft;
			}else {
				f1 = ft;
			}
		}
		return (flag? -f1: f1) * 10 ;// 10 ��
	}
	
	final public static int atan2(int dy,int dx){
		if (dx > 0) {
			return atan(div(dy, dx)) ;
		} else if (dx < 0) {
			return (180) + atan(div(dy, dx)) ;
		} else {
			return (dy > 0 ? 90 : -90 );
		}
	}
	
	
// 	--------------------------------------------------------------------------------------------------------
	
	/**
	 * comput cyc number: (value+d) within 0~max scope
	 * @param value
	 * @param d
	 * @param max
	 * @return 
	 */
	final static public int cycNum(int value, int d, int max) {
		value += d;
		return (value>=0)?(value % max):((max + value % max) % max) ;
	}

	
	/**
	 * comput cyc mod: -1 mod 10 = -1
	 * @param value
	 * @param div
	 * @return 
	 */
	final static public int cycMod(int value, int div) {
		return (value/div) + (value<0?-1:0);
	}
	

	/**
	 * @param value 
	 * @return 1 or 0 or -1
	 */
	final static public int getDirect(int value) {
		return value==0?0:(value>0?1:-1);
	}
	
	
	/**
	 * comput round mod roundMode(33,8) = 5 => 33/8 + (33%8==0?:0:1)
	 * @param value
	 * @param div
	 * @return 
	 */
	final static public int roundMod(int value, int div) {
		return (value/div) + (value%div==0?0:(1*getDirect(value)));
	}
	
	
	/**
	 * 根据速度和时间段得到距离
	 * @param speed 速度 (距离/秒)
	 * @param interval_ms 毫秒
	 * @return
	 */
	final static public double getDistance(double speed, int interval_ms) {
		double rate = interval_ms / 1000d ;
		return speed * rate;
	}
	
//	-------------------------------------------------------------------------------------------------------------------

	/**
	 * 
	 * @param px
	 * @param py
	 * @param qx
	 * @param qy
	 * @return v[]
	 */
	final public static void vectorAdd(int[] out, int px, int py, int qx, int qy) {
		out[0] = px + qx;
		out[1] = py + qy;
		
	}
	
	/**
	 * 
	 * @param px
	 * @param py
	 * @param qx
	 * @param qy
	 * @return v[]
	 */
	final public static void vectorSub(int[] out, int px, int py, int qx, int qy) {
		out[0] = px - qx;
		out[1] = py - qy;
	}

	/**
	 * Vector Cross Product in Descartes reference frame 
	 * if P x Q > 0 , P above Q clockwise
	 * if P x Q < 0 , P above Q anticlockwise
	 * if P x Q = 0 , P equal Q at same line
	 * 
	 * @param p
	 * @param q
	 * @return +-
	 */
	final public static int vectorCrossProduct(int[] p, int[] q) {
		return (p[0] * q[1] - p[1] * q[0]);
	}


//	--------------------------------------------------------------------------------------------------
	
	/* croe */
	static int v1[] = new int[2];
	static int v2[] = new int[2];
	static int v3[] = new int[2];
	static int v4[] = new int[2];
	
	static int v5[] = new int[2];
	static int v6[] = new int[2];
	static int v7[] = new int[2];
	static int v8[] = new int[2];
	
	
	/**
	 * ((Q2-Q1)��(P1-Q1))*((P2-Q1)��(Q2-Q1)) >= 0 
	 * ((P2-P1)��(Q1-P1))*((Q2-P1)��(P2-P1)) >= 0
	 * 
	 * @param p1x line 1
	 * @param p1y
	 * @param p2x
	 * @param p2y
	 * @param q1x line 2
	 * @param q1y
	 * @param q2x
	 * @param q2y
	 * @return false:true
	 */
	final static public boolean intersectLine(
			int p1x, int p1y, int p2x, int p2y,
			int q1x, int q1y, int q2x, int q2y) 
	{
		synchronized (v1) 
		{
			CMath.vectorSub(v1, q2x, q2y, q1x, q1y);//1
			CMath.vectorSub(v2, p1x, p1y, q1x, q1y);//2
			CMath.vectorSub(v3, p2x, p2y, q1x, q1y);//3
//			CMath.vectorSub(v4, q2x, q2y, q1x, q1y);//4=1
			
			CMath.vectorSub(v5, p2x, p2y, p1x, p1y);//5
			CMath.vectorSub(v6, q1x, q1y, p1x, p1y);//6
			CMath.vectorSub(v7, q2x, q2y, p1x, p1y);//7
//			CMath.vectorSub(v8, p2x, p2y, p1x, p1y);//8=5
			
			if (CMath.vectorCrossProduct(v1, v2) * CMath.vectorCrossProduct(v3, v1) >= 0 && 
				CMath.vectorCrossProduct(v5, v6) * CMath.vectorCrossProduct(v7, v5) >= 0) {
				return true;
			}
			return false;
		}
	}

//	--------------------------------------------------------------------------------------------------
	
	final static public boolean intersectRound(
			int sx, int sy, int sr, 
			int dx, int dy, int dr) 
	{
		int w = sx - dx;
		int h = sy - dy;
		int r = sr + dr;
		
		if (w*w + h*h <= r*r) {
			return true;
		}
		
		return false;
	}

//	--------------------------------------------------------------------------------------------------
	
	final static public boolean includeRoundPoint(
			int sx, int sy, int sr, 
			int px, int py) 
	{
		int w = sx - px;
		int h = sy - py;
		
		if (w*w + h*h <= sr*sr) {
			return true;
		}

		return false;
	}
	
//	--------------------------------------------------------------------------------------------------
	
	final static public boolean intersectRect(
			int sx1, int sy1, int sx2, int sy2, 
			int dx1, int dy1, int dx2, int dy2) {
		if (sx2 < dx1)		return false;
		if (sx1 > dx2)		return false;
		if (sy2 < dy1)		return false;
		if (sy1 > dy2)		return false;
		return true;
	}
	final static public boolean intersectRect(
			double sx1, double sy1, double sx2, double sy2, 
			double dx1, double dy1, double dx2, double dy2) {
		if (sx2 < dx1)		return false;
		if (sx1 > dx2)		return false;
		if (sy2 < dy1)		return false;
		if (sy1 > dy2)		return false;
		return true;
	}

//	--------------------------------------------------------------------------------------------------
	
	final static public boolean intersectRect2(
			int sx1, int sy1, int sw, int sh, 
			int dx1, int dy1, int dw, int dh) {
		int sx2 = sx1 + sw - 1 ;
		int sy2 = sy1 + sh - 1 ;
		int dx2 = dx1 + dw - 1 ;
		int dy2 = dy1 + dh - 1 ;
		if (sx2 < dx1)		return false;
		if (sx1 > dx2)		return false;
		if (sy2 < dy1)		return false;
		if (sy1 > dy2)		return false;
		return true;
	}

	final static public boolean intersectRect2(
			double sx1, double sy1, double sw, double sh, 
			double dx1, double dy1, double dw, double dh) {
		double sx2 = sx1 + sw - 1 ;
		double sy2 = sy1 + sh - 1 ;
		double dx2 = dx1 + dw - 1 ;
		double dy2 = dy1 + dh - 1 ;
		if (sx2 < dx1)		return false;
		if (sx1 > dx2)		return false;
		if (sy2 < dy1)		return false;
		if (sy1 > dy2)		return false;
		return true;
	}

//	--------------------------------------------------------------------------------------------------
	
	final static public boolean includeRectPoint(
			int sx1, int sy1, int sx2, int sy2, 
			int dx, int dy) {
		if (sx2 < dx)		return false;
		if (sx1 > dx)		return false;
		if (sy2 < dy)		return false;
		if (sy1 > dy)		return false;
		return true;
	}
	
	final static public boolean includeRectPoint(
			double sx1, double sy1, double sx2, double sy2, 
			double dx, double dy) {
		if (sx2 < dx)		return false;
		if (sx1 > dx)		return false;
		if (sy2 < dy)		return false;
		if (sy1 > dy)		return false;
		return true;
	}

//	--------------------------------------------------------------------------------------------------
	
	final static public boolean includeRectPoint2(
			int sx1, int sy1, int sw, int sh, 
			int dx, int dy) {
		int sx2 = sx1 + sw - 1 ;
		int sy2 = sy1 + sh - 1 ;
		if (sx2 < dx)		return false;
		if (sx1 > dx)		return false;
		if (sy2 < dy)		return false;
		if (sy1 > dy)		return false;
		return true;
	}
	
	final static public boolean includeRectPoint2(
			double sx1, double sy1, double sw, double sh, 
			double dx, double dy) {
		double sx2 = sx1 + sw - 1 ;
		double sy2 = sy1 + sh - 1 ;
		if (sx2 < dx)		return false;
		if (sx1 > dx)		return false;
		if (sy2 < dy)		return false;
		if (sy1 > dy)		return false;
		return true;
	}

//	--------------------------------------------------------------------------------------------------
	
	/**
	 * if the value is in a~b or equal
	 * @param value
	 * @param a
	 * @param b
	 * @return
	 */
	public static final boolean isIncludeEqual(int value, int min, int max)
	{
		return max >= value && min <= value ;
	}

	/**
	 * if the value is in a~b not equal
	 * @param value
	 * @param a
	 * @param b
	 * @return
	 */
	public static final boolean isInclude(int value, int min, int max)
	{
		return max > value && min < value ;
	}
	
}