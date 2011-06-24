package Class
{
	import flash.filters.GlowFilter;
	
	import mx.containers.Canvas;
	import mx.controls.Label;
	import mx.core.Application;

	public class MaskCtr
	{
		private static var canvas:Canvas;
		private static var label:Label;
		public function MaskCtr()
		{
			
		}
		
		public static function show(str:String):void
		{
			if(canvas == null)
			{
				canvas = new Canvas();
				label  =  new Label();
				
				label.setStyle('horizontalCenter',0);
				label.setStyle('verticalCenter',0);
				label.setStyle('fontSize',20);
				label.setStyle('fontWeight','bold')
				label.filters = new Array(new GlowFilter(0xffffff));
				canvas.percentHeight = 100;
				canvas.percentWidth = 100;
				canvas.setStyle('backgroundColor',0);
				canvas.setStyle('backgroundAlpha',0.05);
				
				canvas.addChild(label);
			}
			
			//canvas.addChild()
			label.text = str;
			Application.application.addChild(canvas);
			
		}	
		public static function close():void
		{
			Application.application.removeChild(canvas);
		}
	}
}