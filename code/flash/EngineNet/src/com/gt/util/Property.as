package com.gt.util
{
	import flash.utils.Dictionary;
	

public class Property 
{
	protected var Map : Dictionary = new Dictionary();
	
	public function Property(text : String = null, separator:String = null){
		loadText(text, separator);
	}
	
	public function loadText(text:String, separator:String) : void {
		if (text!=null)
		var lines : Array = text.split("\n");
		for(var i:int=0; i<lines.length; i++){
			try{
				if (lines[i].trim().startsWith("#")){
					continue;
				}
				var kv : Array = lines[i].split(separator, 2);
				put(kv[0].trim(), kv[1].trim());
			}catch(err:Error){
				//err.printStackTrace();
			}
		}
	}
	
	public function getKeys() : Array
	{
		return Map.keys();
	}
	
	public function getValues() : Array
	{
		return Map.elements();
	}
	
	public function put( key:String,  value:Object) : void {
		Map[key] = value;
	}

	public function getValue(key:String) : Object {
		return Map[key];
	}

	public function getString(key:String, defaultValue:String = null) : String {
		var v : Object = getValue(key);
		if (v==null) return defaultValue;
		return v.toString();
	}
	
	public function getBoolean(key:String, defaultValue:Boolean = false) : Boolean {
		var v : String = getString(key);
		if (v==null) return defaultValue;
		return Util.parseBoolean(v);
	}
	
	public function getInteger(key:String, defaultValue:int = 0) : int {
		var v : String = getString(key);
		if (v==null) return defaultValue;
		return Util.parseInt(v);
	}
	
	public function getNumber(key:String, defaultValue:Number = 0) : Number {
		var v : String = getString(key);
		if (v==null) return defaultValue;
		return Util.parseNumber(v);
	}
	
	public function getStringArray(key:String, separator:String=",") : Array
	{
		var v : String = getString(key);
		if (v == null) return null;
		return v.split(separator);
	}
	
	public function getIntegerArray(key:String, separator:String=",") : Array
	{
		var vs : Array = getStringArray(key, separator);
		if (vs != null) {
			var ret : Array = new int[vs.length];
			for (var i:int = 0; i < vs.length; i++) {
				ret[i] = Util.parseInt(vs[i].trim());
			}
			return ret;
		}
		return null;
	}
	

}

}
