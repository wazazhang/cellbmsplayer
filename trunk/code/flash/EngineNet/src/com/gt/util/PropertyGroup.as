package com.gt.util
{

	public class PropertyGroup extends Property
	{
		public function PropertyGroup(text : String = null, separator:String = null){
			loadText(text, separator);
		}
	
		override public function put(key:String, value:Object) : void {
			if (Map.containsKey(key)){
				getValue(key).addElement(value);
			}else{
				var group : Array = new Array();
				group.addElement(value);
			}
		}
		
		override public function getValue(key:String) : Object {
			var v:Object = Map[key];
			if (v is Array) {
				return (v as Array)[0];
			}
			return v;
		}
		
		public function getGroup(key:String) : Array {
			var v:Object = Map[key];
			if (v is Array) {
				return (v as Array);
			}
			return new Array();
		}
		
		
	}

}