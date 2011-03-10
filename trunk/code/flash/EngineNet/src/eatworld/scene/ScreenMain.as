package eatworld.scene
{
	import com.gt.game.AScreen;
	import com.gt.game.CImage;
	import com.gt.game.CSprite;
	import com.gt.game.edit.SetResource;
	
	import eatworld.entity.Actor;
	
	import flash.display.Graphics;
	import flash.text.TextField;
	import flash.text.TextFormat;

	

	public class ScreenMain extends AScreen
	{
		private var InfoReader : TextField;
		
		private var setresource : SetResource;
		
		private var actor : Actor = new Actor();
		
		private var sprite : CSprite = new CSprite();
		
		private var image : CImage = new CImage("data/Actor.gif");
		
		public function ScreenMain()
		{
			trace(new int("1234"));
			trace(new int("0x80"));
			trace(new int("1234.6"));
			trace(new int("xx"));
			
			trace(new Number("1234.6"));
			trace(new Number("0xffff"));
			trace(new Number("xax"));
			
			trace(new Boolean("0"));
			trace(new Boolean("true"));
			trace(new Boolean("false"));
			
			x = y = 0;
			
			// text
			InfoReader = new TextField();
			InfoReader.width = 800;
			InfoReader.height = 500;
            InfoReader.background = true;
            InfoReader.border = true;
            var format:TextFormat = new TextFormat();
            format.font = "CourierNew";
            format.color = 0xFF000000;
            format.size = 10;
            InfoReader.defaultTextFormat = format;   
            addChild(InfoReader);
	        
			//new ResLoader();
			
			// image
			addChild(image);
			
			// sprite
			addChild(sprite);
		}
		
		
		override public function init() : void 
		{
			
		}
		
		
		override public function destory() : void 
		{
			
		}
		
		
		
		override public function update() : void 
		{
			//actor.getNetService().request_login(actor, "test", "testw");
		}
		
		override public function render(g:Graphics) : void 
		{
			
		}
		
		
		
	}
}