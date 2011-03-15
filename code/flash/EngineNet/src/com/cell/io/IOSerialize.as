
package com.cell.io
{
	import flash.utils.IDataOutput;
	
	public class IOSerialize
	{
		public static function putString(ostream : IDataOutput, string : String) : void
		{
			ostream.writeUTF(string);
		}
		
		public static function putByte(ostream : IDataOutput,  value : int) : void
		{
			ostream.writeByte(value & 0x000000FF);
		}
		
		public static function putUnsignedByte(ostream : IDataOutput, value : uint) : void
		{
			ostream.writeByte(value & 0x000000FF);
		}
		
		public static function putBoolean(ostream : IDataOutput, value : Boolean) : void
		{
			ostream.writeByte(value? 1 : 0);
		}
		
		public static function putShort(ostream : IDataOutput,  value : int) : void
		{
			ostream.writeShort(value);		
		}
		
		public static function putUnsignedShort(ostream : IDataOutput, value : uint) : void
		{
			ostream.writeShort(value);
		}
		
		public static function putInt(ostream : IDataOutput, value : int) : void
		{
			ostream.writeInt(value);
		}
		
		public static function putUnsignedInt(ostream : IDataOutput, value : uint) : void
		{
			ostream.writeUnsignedInt(value);
		}
		
		public static function putLong(ostream : IDataOutput, value : Number) : void
		{
			ostream.writeDouble(value);
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		// put array
		
		public static function putStrings(ostream : IDataOutput, value : Array) : void
		{
			if (value != null)
			{
				IOSerialize.putUnsignedInt(ostream, value.length);
				for (var i:int=0; i<value.length; ++i )
					IOSerialize.putString(ostream, value[i]);
			}
			else
			{
				IOSerialize.putUnsignedInt(ostream, 0);
			}
		}
		
		public static function putBooleans(ostream : IDataOutput, value : Array) : void
		{
			if (value != null)
			{
				IOSerialize.putUnsignedInt(ostream, value.length);
				for (var i:int=0; i<value.length; ++i )
					IOSerialize.putBoolean(ostream, value[i]);
			}
			else
			{
				IOSerialize.putUnsignedInt(ostream, 0);
			}
		}	
		
		public static function putBytes(ostream : IDataOutput, value : Array) : void
		{
			if (value != null)
			{
				IOSerialize.putUnsignedInt(ostream, value.length);
				for (var i:int=0; i<value.length; ++i )
					IOSerialize.putByte(ostream, value[i]);
			}
			else
			{
				IOSerialize.putUnsignedInt(ostream, 0);
			}
		}	
		
		public static function putUnsignedBytes(ostream : IDataOutput, value : Array) : void
		{
			if (value != null)
			{
				IOSerialize.putUnsignedInt(ostream, value.length);
				for (var i:int=0; i<value.length; ++i )
					IOSerialize.putUnsignedByte(ostream, value[i]);
			}
			else
			{
				IOSerialize.putUnsignedInt(ostream, 0);
			}
		}
		
		public static function putShorts(ostream : IDataOutput, value : Array) : void
		{
			if (value != null)
			{
				IOSerialize.putUnsignedInt(ostream, value.length);
				for (var i:int=0; i<value.length; ++i )
					IOSerialize.putShort(ostream, value[i]);
			}
			else
			{
				IOSerialize.putUnsignedInt(ostream, 0);
			}		
		}	
		
		public static function putUnsignedShorts(ostream : IDataOutput, value : Array) : void
		{
			if (value != null)
			{
				IOSerialize.putUnsignedInt(ostream, value.length);
				for (var i:int=0; i<value.length; ++i )
					IOSerialize.putUnsignedShort(ostream, value[i]);
			}
			else
			{
				IOSerialize.putUnsignedInt(ostream, 0);
			}		
		}
		
		public static function putInts(ostream : IDataOutput, value : Array) : void
		{
			if (value != null)
			{
				IOSerialize.putUnsignedInt(ostream, value.length);
				for (var i:int=0; i<value.length; ++i )
					IOSerialize.putInt(ostream, value[i]);
			}
			else
			{
				IOSerialize.putUnsignedInt(ostream, 0);
			}		
		}	
		
		public static function putUnsignedInts(ostream : IDataOutput, value : Array) : void
		{
			if (value != null)
			{
				IOSerialize.putUnsignedInt(ostream, value.length);
				for (var i:int=0; i<value.length; ++i )
					IOSerialize.putUnsignedInt(ostream, value[i]);
			}
			else
			{
				IOSerialize.putUnsignedInt(ostream, 0);
			}		
		}
		
		public static function putLongs(ostream : IDataOutput, value : Array) : void
		{
			if (value != null)
			{
				IOSerialize.putUnsignedInt(ostream, value.length);
				for (var i:int=0; i<value.length; ++i )
					IOSerialize.putLong(ostream, value[i]);
			}
			else
			{
				IOSerialize.putUnsignedInt(ostream, 0);
			}		
		}	
		
	}

}

