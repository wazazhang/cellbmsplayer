package Class
{
	public class Resource
	{
		
		[Embed(source='image/card/b1.png')]
		private static var b1:Class;
		
		[Embed(source='image/card/b2.png')]
		private static var b2:Class;
		
		[Embed(source='image/card/b3.png')]
		private static var b3:Class;
		
		[Embed(source='image/card/b4.png')]
		private static var b4:Class;
		
		[Embed(source='image/card/b5.png')]
		private static var b5:Class;
		
		[Embed(source='image/card/b6.png')]
		private static var b6:Class;
		
		[Embed(source='image/card/b7.png')]
		private static var b7:Class;
		
		[Embed(source='image/card/b8.png')]
		private static var b8:Class;
		
		[Embed(source='image/card/b9.png')]
		private static var b9:Class;
		
		[Embed(source='image/card/b10.png')]
		private static var b10:Class;
		
		[Embed(source='image/card/b11.png')]
		private static var b11:Class;
		
		[Embed(source='image/card/b12.png')]
		private static var b12:Class;
		
		[Embed(source='image/card/b13.png')]
		private static var b13:Class;
		

		
		[Embed(source='image/card/c1.png')]
		private static var c1:Class;
		
		[Embed(source='image/card/c2.png')]
		private static var c2:Class;
		
		[Embed(source='image/card/c3.png')]
		private static var c3:Class;
		
		[Embed(source='image/card/c4.png')]
		private static var c4:Class;
		
		[Embed(source='image/card/c5.png')]
		private static var c5:Class;
		
		[Embed(source='image/card/c6.png')]
		private static var c6:Class;
		
		[Embed(source='image/card/c7.png')]
		private static var c7:Class;
		
		[Embed(source='image/card/c8.png')]
		private static var c8:Class;
		
		[Embed(source='image/card/c9.png')]
		private static var c9:Class;
		
		[Embed(source='image/card/c10.png')]
		private static var c10:Class;
		
		[Embed(source='image/card/c11.png')]
		private static var c11:Class;
		
		[Embed(source='image/card/c12.png')]
		private static var c12:Class;
		
		[Embed(source='image/card/c13.png')]
		private static var c13:Class;
		
		
		
		[Embed(source='image/card/h1.png')]
		private static var h1:Class;
		
		[Embed(source='image/card/h2.png')]
		private static var h2:Class;
		
		[Embed(source='image/card/h3.png')]
		private static var h3:Class;
		
		[Embed(source='image/card/h4.png')]
		private static var h4:Class;
		
		[Embed(source='image/card/h5.png')]
		private static var h5:Class;
		
		[Embed(source='image/card/h6.png')]
		private static var h6:Class;
		
		[Embed(source='image/card/h7.png')]
		private static var h7:Class;
		
		[Embed(source='image/card/h8.png')]
		private static var h8:Class;
		
		[Embed(source='image/card/h9.png')]
		private static var h9:Class;
		
		[Embed(source='image/card/h10.png')]
		private static var h10:Class;
		
		[Embed(source='image/card/h11.png')]
		private static var h11:Class;
		
		[Embed(source='image/card/h12.png')]
		private static var h12:Class;
		
		[Embed(source='image/card/h13.png')]
		private static var h13:Class;
		
		
		
		[Embed(source='image/card/l1.png')]
		private static var l1:Class;
		
		[Embed(source='image/card/l2.png')]
		private static var l2:Class;
		
		[Embed(source='image/card/l3.png')]
		private static var l3:Class;
		
		[Embed(source='image/card/l4.png')]
		private static var l4:Class;
		
		[Embed(source='image/card/l5.png')]
		private static var l5:Class;
		
		[Embed(source='image/card/l6.png')]
		private static var l6:Class;
		
		[Embed(source='image/card/l7.png')]
		private static var l7:Class;
		
		[Embed(source='image/card/l8.png')]
		private static var l8:Class;
		
		[Embed(source='image/card/l9.png')]
		private static var l9:Class;
		
		[Embed(source='image/card/l10.png')]
		private static var l10:Class;
		
		[Embed(source='image/card/l11.png')]
		private static var l11:Class;
		
		[Embed(source='image/card/l12.png')]
		private static var l12:Class;
		
		[Embed(source='image/card/l13.png')]
		private static var l13:Class;

		[Embed(source='image/card/g.png')]
		private static var g:Class;

		
		public function Resource()
		{
			
		}
		
		public static function getCardImg(type:int,point:int):Class
		{
			if(type==1)
			{
				if(point==1)
					return b1;
				if(point==2)
					return b2;
				if(point==3)
					return b3;
				if(point==4)
					return b4;
				if(point==5)
					return b5;
				if(point==6)
					return b6;
				if(point==7)
					return b7;
				if(point==8)
					return b8;
				if(point==9)
					return b9;
				if(point==10)
					return b10;
				if(point==11)
					return b11;
				if(point==12)
					return b12;
				if(point==13)
					return b13;
			}
			else if(type == 2)
			{
				if(point==1)
					return c1;
				if(point==2)
					return c2;
				if(point==3)
					return c3;
				if(point==4)
					return c4;
				if(point==5)
					return c5;
				if(point==6)
					return c6;
				if(point==7)
					return c7;
				if(point==8)
					return c8;
				if(point==9)
					return c9;
				if(point==10)
					return c10;
				if(point==11)
					return c11;
				if(point==12)
					return c12;
				if(point==13)
					return c13;
			}
			else if(type == 3)
			{
				if(point==1)
					return h1;
				if(point==2)
					return h2;
				if(point==3)
					return h3;
				if(point==4)
					return h4;
				if(point==5)
					return h5;
				if(point==6)
					return h6;
				if(point==7)
					return h7;
				if(point==8)
					return h8;
				if(point==9)
					return h9;
				if(point==10)
					return h10;
				if(point==11)
					return h11;
				if(point==12)
					return h12;
				if(point==13)
					return h13;
			}
			else if(type == 4)
			{
				if(point==1)
					return l1;
				if(point==2)
					return l2;
				if(point==3)
					return l3;
				if(point==4)
					return l4;
				if(point==5)
					return l5;
				if(point==6)
					return l6;
				if(point==7)
					return l7;
				if(point==8)
					return l8;
				if(point==9)
					return l9;
				if(point==10)
					return l10;
				if(point==11)
					return l11;
				if(point==12)
					return l12;
				if(point==13)
					return l13;
			}
			else
			{
				return g;
			}
			return g;
		}
			
		
	}
}