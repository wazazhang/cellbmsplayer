package com.gt.game
{
	public class CCD
	{
		static public const CD_TYPE_RECT : int = 1;
		static public const CD_TYPE_LINE : int = 2;
		static public const CD_TYPE_POINT : int = 3;
		
		public var Type : int;
		public var Mask : int;
	
		/**Left,Top,Right,Bottom*/
		public var X1 : int;
		public var Y1 : int;
		public var X2 : int;
		public var Y2 : int;
		
		public var Data : Object;
		
		public function CCD() {}


		static public function createCDRect(mask:int, x:int, y:int, w:int, h:int) : CCD
		{
			var cd : CCD = new CCD();
			cd.Type = CD_TYPE_RECT;
			cd.Mask = mask;

			cd.X1 = x;
			cd.Y1 = y;
			cd.X2 = x + w-1;
			cd.Y2 = y + h-1;
			
			return cd;
		}
	}
}