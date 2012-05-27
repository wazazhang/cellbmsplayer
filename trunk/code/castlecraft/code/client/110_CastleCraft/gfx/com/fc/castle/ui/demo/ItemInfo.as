package com.fc.castle.ui.demo
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageBox;
	import com.fc.castle.data.template.ItemTemplate;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castlecraft.LanguageManager;
	
	import flash.text.TextField;

	public class ItemInfo extends BaseInfo
	{
		var hdimg : ImageBox;
		
		var tname:TextField = new TextField();
		var price:TextField = new TextField();
		var discript:TextField = new TextField();
		
		public function ItemInfo(type:ItemTemplate, showPrice:Boolean=false)
		{
			tname.htmlText 		= LanguageManager.getText("handbook.name", 		type.name);
			price.htmlText 		= LanguageManager.getText("handbook.priceCoin", type.priceCoin);
			discript.htmlText 	= LanguageManager.getText("handbook.desc", 		type.desc);
			discript.wordWrap 	= true;
			
			this.hdimg = new ImageBox(CResourceManager.getIcon(type.icon), 0);
			
			super(type);
			
			this.addChild(hdimg);
			this.addChild(tname);
			this.addChild(price);
			this.addChild(discript);
			
			this.price.visible = showPrice;
		}
		
		override public function resize(w:int, h:int):void
		{
				
				this.hdimg.x = 10;
				this.hdimg.y = 10;

				tname.width = w - hdimg.width - 10;
				tname.x = 10 + hdimg.width + 8;
				tname.y = 10;

				price.width = w - hdimg.width - 10;
				price.x = 10 + hdimg.width + 8;
				price.y = 10 + hdimg.height/2;
				
				discript.y = 10 + hdimg.height + 8;
				discript.x = 10;
				discript.width = w - 20;
				
		}
	}
}