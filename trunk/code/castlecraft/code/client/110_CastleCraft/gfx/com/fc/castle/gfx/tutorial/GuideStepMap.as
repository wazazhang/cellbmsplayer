package com.fc.castle.gfx.tutorial
{
	import com.cell.io.IOUtil;
	import com.cell.util.Map;
	import com.cell.util.StringUtil;
	import com.fc.castlecraft.AutoLogin;
	
	import flash.events.Event;
	import flash.net.URLLoader;

	public class GuideStepMap
	{
		public var gss:Array;
		
		private var loader : URLLoader;
		
		public function GuideStepMap()
		{
			gss = new Array();
			var url:String = "location/" + AutoLogin.LOCATION + "/guide/"+"guide_data.properties";
			loader = IOUtil.loadURL(url, loaded);
		}
		
		private function loaded(e:Event) : void
		{
			readFromProperties(loader.data);
		}
		
		public function getGuideStep(step:int):GuideStep
		{
			return gss[step];
		}
		
		private function readFromProperties(properties:String, equalChar:String="=") : void
		{
			
			var lines : Array = StringUtil.splitString(properties, "\n");
			
			var line : String = null;
			
			for (var i : int = 0; i < lines.length; i++)
			{
				try
				{
					if (line == null) {
						line = StringUtil.trim(lines[i]);
					} else {
						line += StringUtil.trim(lines[i]);
					}
					// 如果是注释 #
					if (StringUtil.trim(line).charAt(0) == '#'||line.length==0){
						line = null;
						continue;
					}
					// 如果是尾部出现 \
					if (i < lines.length - 1 && line.charAt(line.length-1) == '\\') {
						line = line.substring(0, line.length-1);
						continue;
					}
					
					var data:Array = StringUtil.splitString(line, ",");
					var gd:GuideStep = new GuideStep();
					var step:int = int(data[0]);
					for (var j:int = 1; j<data.length; j++){
						var kv:Array = StringUtil.splitString(data[j],"=");
						if (kv.length==2){
							if (kv[0] == "level"){
								gd.level_condition = int(kv[1]);
							}else if (kv[0] == "type"){
								gd.type = int(kv[1]);
							}
						}
					}
					gss.push(gd);
//					var kv : Array = StringUtil.splitString(line, equalChar, 2, true);
//					if (kv.length == 2) {
//						map.put(kv[0], kv[1]);
//						//						trace(line);
//					}		
					line = null;
				}
				catch(err:Error){
					trace(err.getStackTrace());
					line = null;
				}
			}
		}
	}
}