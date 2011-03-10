package com.gt.net{

	
	import flash.errors.*;
	import flash.events.*;
	import flash.net.*;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;


	public class MessageHeader 
	{
		public var Type: int;
		public var PacketNumber: int;

		public function MessageHeader( type: int) : void {
			Type = type;
		}

		public function serialize( buffer: IDataOutput) : void {}

		public function deserialize( buffer: IDataInput) : void {}


		
	}
}