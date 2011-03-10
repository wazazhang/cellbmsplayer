package com.gt.game
{
	import flash.display.Graphics;
	
	public class CWayPoint
	{
		public var X : int = 0;
		public var Y : int = 0;
		
		protected var Link : Array = new Array();
		
		public var Data : Object;
		
		
		public function CWayPoint( x : int, y : int)
		{
			X = x;
			Y = y;
		}
		
		public function getNextCount() : int 
		{
			return Link.length;
		}
		
		public function getNextPoint(linkIndex : int) : CWayPoint 
		{
			return CWayPoint(Link[linkIndex]);
		}
		
		public function linkTo(point : CWayPoint) : void
		{
			if(point==null)return ;
			
			if(!this.Link.contains(point)){
				this.Link.addElement(point);
			}
	
		}
	
		public function linkFrom(point : CWayPoint) : void
		{
			if(point==null)return ;
	
			if(!point.Link.contains(this)){
				point.Link.addElement(this);
			}
	
		}
	
		public function link(point : CWayPoint) : void
		{
			if(point==null)return;
			
			if(!this.Link.contains(point)){
				this.Link.addElement(point);
			}
	
			
		}
		
		public function unlink(point : CWayPoint) : void
		{
			if(point==null)return ;
			
			if( this.Link.contains(point)){
				this.Link.removeElement(point);
			}
			if( point.Link.contains(this)){
				point.Link.removeElement(this);
			}
			
		}
		
		public function unlinkAll() : void
		{
			if(Link.size()==0)return ;
	
	        for (var i:int=0; i<Link.size(); i++)
	        {
	        	var p : CWayPoint = CWayPoint(Link[i]);
	        	p.unlink(this);    
	        }
	
	        this.Link.removeAllElements();
	        
		}
	
		public function render(g : Graphics) : void
		{
			/*
			g.setColor(0xff808080);
			
			for (var i:int=0; i<Link.size(); i++)
			{
				var p : CWayPoint = CWayPoint(Link.elementAt(i));
				g.drawLine(X+offsetX, Y+offsetY, p.X+offsetX, p.Y+offsetY);
			}

			g.fillRect(X-2+offsetX, Y-2+offsetY, 4, 4);
			*/
		}



	}
}