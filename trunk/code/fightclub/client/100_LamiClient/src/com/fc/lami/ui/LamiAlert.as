package com.fc.lami.ui
{
	import flash.display.Sprite;
	
	import mx.controls.Alert;
	import mx.managers.ISystemManager;

	public class LamiAlert extends Alert
	{
		public static function show(text:String = "", title:String = "",
									flags:uint = 0x4 /* Alert.OK */, 
									parent:Sprite = null, 
									closeHandler:Function = null, 
									iconClass:Class = null, 
									defaultButtonFlag:uint = 0x4 /* Alert.OK */):Alert
		{
			
			var alert:Alert = Alert.show(
				text,
				title, 
				flags,
				parent,
				closeHandler, 
				iconClass, 
				defaultButtonFlag);
			alert.width = 226;
			alert.height = 199;
			return alert;
		}
	}
}