
package com.cell.io 
{
	import flash.utils.IDataInput;
	

public class IODeserialize
{
	static public const STRING_MAX_LEN : int = 1024 * 100 + 1;
	
	public static function getString(istream : IDataInput) : String
	{
		var len : uint = istream.readUnsignedShort();

	    if (len >= 0 && len < STRING_MAX_LEN){
			return istream.readUTFBytes(len);
		}
		
		return "";    
	}
	
	
	public static function getByte(istream : IDataInput) : int
	{
		return istream.readByte();
	}
	
	public static function getUnsignedByte(istream : IDataInput) : uint
	{
		return istream.readUnsignedByte();
	}
	
	public static function getBoolean(istream : IDataInput) : Boolean
	{
		return istream.readByte() != 0;
	}
	
	public static function getShort(istream : IDataInput) : int
	{
		return istream.readShort();
	}
	
	public static function getUnsignedShort(istream : IDataInput) : uint
	{
		return istream.readUnsignedShort();
	}
	
	public static function getInt(istream : IDataInput) : int
	{
		return istream.readInt();
	}
	
	public static function getUnsignedInt(istream : IDataInput) : uint
	{
		return istream.readUnsignedInt();
	}
	
	public static function getLong(istream : IDataInput) : Number
	{
		return istream.readDouble();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// get array
	
	public static function getStrings(istream : IDataInput) : Array
	{
		var count : uint = IODeserialize.getUnsignedInt(istream);
		if (count > 0)
		{
			var values : Array = new Array(count);
			for (var i:int=0; i<count; ++i)
				values[i] = IODeserialize.getString(istream);
			return values;
		}
		
		return null;
	}
	
	public static function getBooleans(istream : IDataInput) : Array
	{
		var count : uint = IODeserialize.getUnsignedInt(istream);
		if (count > 0)
		{
			var values : Array = new Array(count);
			for (var i:int=0; i<count; ++i)
				values[i] = IODeserialize.getBoolean(istream);
			return values;
		}
		
		return null;
	}	
	
	public static function getBytes(istream : IDataInput) : Array
	{
		var count : uint = IODeserialize.getUnsignedInt(istream);
		if (count > 0)
		{
			var values : Array = new Array(count);
			for (var i:int=0; i<count; ++i)
				values[i] = IODeserialize.getByte(istream);
			return values;
		}
		
		return null;
	}
	
	public static function getUnsignedBytes(istream : IDataInput) : Array
	{
		var count : uint = IODeserialize.getUnsignedInt(istream);
		if (count > 0)
		{
			var values : Array = new Array(count);
			for (var i:int=0; i<count; ++i)
				values[i] = IODeserialize.getUnsignedByte(istream);
			return values;
		}
		
		return null;
	}
	
	public static function getShorts(istream : IDataInput) : Array
	{
		var count : uint = IODeserialize.getUnsignedInt(istream);
		if (count > 0)
		{
			var values : Array = new Array(count);
			for (var i:int=0; i<count; ++i)
				values[i] = IODeserialize.getShort(istream);
			
			return values;
		}
		
		return null;
	}
	
	public static function getUnsignedShorts(istream : IDataInput) : Array
	{
		var count : uint = IODeserialize.getUnsignedInt(istream);
		if (count > 0)
		{
			var values : Array = new Array(count);
			for (var i:int=0; i<count; ++i)
				values[i] = IODeserialize.getUnsignedShort(istream);
			
			return values;
		}
		
		return null;
	}	
	
	public static function getInts(istream : IDataInput) : Array
	{
		var count : uint = IODeserialize.getUnsignedInt(istream);
		if (count > 0)
		{
			var values : Array = new Array(count);
			for (var i:int=0; i<count; ++i)
				values[i] = IODeserialize.getInt(istream);
			
			return values;
		}
		
		return null;
	}
	
	public static function getUnsignedInts(istream : IDataInput) : Array
	{
		var count : uint = IODeserialize.getUnsignedInt(istream);
		if (count > 0)
		{
			var values : Array = new Array(count);
			for (var i:int=0; i<count; ++i)
				values[i] = IODeserialize.getUnsignedInt(istream);
			
			return values;
		}
		
		return null;
	}
	
	public static function getLongs(istream : IDataInput) : Array
	{
		var count : uint = IODeserialize.getUnsignedInt(istream);
		if (count > 0)
		{
			var values : Array = new Array(count);
			for (var i:int=0; i<count; ++i)
				values[i] = IODeserialize.getLong(istream);
			
			return values;
		}
		
		return null;
	}
	
//	---------------------------------------------------------------------------------------------------------------------------	
	
	
}

}

