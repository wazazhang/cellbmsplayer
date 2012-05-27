package com.fc.castle.ui.card
{
	import com.cell.gfx.CellSprite;
	import com.cell.io.UrlManager;
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageBox;
	import com.cell.ui.TextPan;
	import com.cell.ui.component.UIComponent;
	import com.fc.castle.data.ItemData;
	import com.fc.castle.data.ShopItem;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.message.AbstractTemplate;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castlecraft.LanguageManager;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.filters.BitmapFilter;
	import flash.filters.BitmapFilterQuality;
	import flash.filters.GlowFilter;
	
	public class Card extends UIComponent
	{
		private var hdimg 	: ImageBox;
		private var tname 	: TextPan;
		
		private var sd		: *;
		private var tp 		: AbstractTemplate;
		private var index	: int;
		
		public function Card(sd:*, index:int, w:int, h:int)
		{
			super(CLayoutManager.ui_lable.copy().initBuffer(w, h));
			this.mouseEnabled  = true;
			
			this.index = index;
			this.sd = sd;
			this.tp = DataManager.getDataTemplate(sd);
			
			this.hdimg = new ImageBox(CResourceManager.getIcon(tp.icon),
				Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER);
			this.hdimg.mouseEnabled = false;
			this.hdimg.mouseChildren = false;
			this.hdimg.x = w/2;
			this.hdimg.y = h/2;
			addChild(hdimg);
			
//			var hfilter:BitmapFilter = new GlowFilter();
//			var hfilters:Array = new Array();
//			hfilters.push(hfilter);
//			this.hdimg.filters = hfilters;
			
			if (sd is SoldierData)
			{
				
			}
			else if (sd is SkillData) 
			{
			}
			else if (sd is ItemData)
			{
				this.tname = new TextPan(LanguageManager.getText("ui.card.name", "x"+sd.count));
				this.visible = sd.count > 0;
			}
			else if (sd is ShopItem)
			{
				this.tname = new TextPan(LanguageManager.getText("ui.card.name", tp.name));
			}
			
			if (tname != null) 
			{
				this.tname.resize(w, h, Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_BOTTOM);
				addChild(tname);
				
				var tfilter:BitmapFilter = new GlowFilter(0x000000);
				var tfilters:Array = new Array();
				tfilters.push(tfilter);
				
//				this.filters = myFilters;
				tname.getTextField().filters = tfilters;
			}
		}
		
		public function reset(sd:*) : void
		{
			this.sd = sd;
			if (sd is ItemData)
			{
				this.visible = sd.count > 0;
				this.tname.setHTMLText(LanguageManager.getText("ui.card.name", "x"+sd.count));
			}
		}
		
		public function get template() : AbstractTemplate
		{
			return tp;
		}
		
		public function get data() : *
		{
			return sd;
		}
		
		public function getIndex() : int
		{
			return index;
		}
		
		public function clone() : Card
		{
			return new Card(sd, index, width, height);
		}
	}
}