package com.fc.castle.ui.demo
{
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageBox;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castlecraft.LanguageManager;
	
	import flash.text.TextField;

	public class SkillInfo extends BaseInfo
	{
		var hdimg : ImageBox;
		
		var tname:TextField = new TextField();
				
		var discript:TextField = new TextField();
		
		
		
		public function SkillInfo(type:SkillTemplate)
		{
			tname.htmlText 		= LanguageManager.getText("handbook.name", type.name);
			discript.htmlText 	= LanguageManager.getText("handbook.desc", type.desc);
			discript.wordWrap 	= true;
			
			this.hdimg = new ImageBox(CResourceManager.getIcon(type.icon), 0);
			
			super(type);
			
			this.addChild(hdimg);
			this.addChild(tname);
			this.addChild(discript);
		}
		
		override public function resize(w:int, h:int):void
		{
				this.hdimg.x = 10;
				this.hdimg.y = 10;

				tname.width = w;
				tname.x = 10 + hdimg.width + 8;
				tname.y = 10;

				discript.y = 10 + hdimg.height + 8;
				discript.x = 10;
				discript.width = w - 20;
				
		}
	}
}