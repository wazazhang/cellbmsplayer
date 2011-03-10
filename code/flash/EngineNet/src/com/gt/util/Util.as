package com.gt.util
{
	import flash.utils.getTimer;
	
	public class Util
	{
	
		static public function parseInt(value:String) : int
		{
			return new int(value);
		}
		
		static public function parseUInt(value:String) : uint
		{
			return new uint(value);
		}
		
		static public function parseNumber(value:String) : uint
		{
			return new Number(value);
		}
		
		static public function parseBoolean(value:String) : Boolean
		{
			if ("true" == value) return true;
			if ("false" == value) return false;
			return false;
		}
	
		
		
		
		
		
		
		
		
		static public function runTimeMillis() : uint {
			return flash.utils.getTimer();
		}
		
		
		

		static public function replaceString(text:String, s:String, d:String) : String
		{
			var sb : String = "";
			
			for (var i:int = 0; i < text.length; i++)
			{
				var dst : int = text.indexOf(s, i);
				
				if(dst>=0){
					sb += (text.substring(i,dst)+d);
					i = dst + s.length - 1;
				}else{
					sb += (text.substring(i));
					break;
				}
			}
			
			return sb;
		}
	
		static public function splitString( text:String,  separator:String) : Array
		{
			var lines : Array = new Array();
			
			for(var i:int=0; i<text.length; i++)
			{
				var dst:int = text.indexOf(separator, i);
				
				if(dst>=0)
				{
					lines.addElement(text.substring(i, dst));
					i = dst + separator.length - 1;
				}
				else
				{
					lines.push(text.substring(i,text.length));
					break;
				}
			}
			
			return lines;
		}
	
		
		static public function getRegion(src : String, r0 : int = -1, r1 : int = -1) : String
		{
			var dst : String = src;
			
			if (r0 >=0 && r1 >=0) {
				dst = src.substring(r0, r1);
			}
			else if (r0 >=0) {
				dst = src.substring(r0);
			}
			
			return dst;
		}
		
		static public function getRegionWithArray(src : String, region : Array) : String
		{
			var dst : String = src;
			if (region==null){
			}else if (region.length==1){
				dst = src.substring(region[0]);
			}else if (region.length==2){
				dst = src.substring(region[0], region[1]);
			}
			return dst;
		}
		
	}
}