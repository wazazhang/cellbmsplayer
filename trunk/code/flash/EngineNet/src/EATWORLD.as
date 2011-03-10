package {

	import eatworld.scene.ScreenMain;
	
	import flash.display.Sprite;
	
	public class EATWORLD extends Sprite
	{
		private var MainScreen : ScreenMain;
		
		public function EATWORLD()
		{
			trace("wo cao ni ma !!!");
			MainScreen = new ScreenMain();
			addChild(MainScreen);
			
			
			
			//addChild(new LoaderExample());
		}
	}
	
	
	
}


    

