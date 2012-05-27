package com.fc.castle.ui.card
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.component.Panel;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.template.ItemTemplate;
	import com.fc.castle.gfx.world.res.UnitData;
	import com.fc.castle.ui.demo.ItemInfo;

	public class ItemDetailPan extends CellSprite
	{
		private var card_info : ItemInfo;
		
		private var content : SyncCardInfoPan;
		
		private var sit : ItemTemplate;
		private var w : int;
		private var h : int;
		
		public function ItemDetailPan(sit:ItemTemplate, w:int, h:int)
		{
			this.mouseChildren = false;
			this.mouseEnabled  = false;
			this.sit = sit;
			this.w = w;
			this.h = h;
			
			var card_pan : Panel = new Panel(w/2-4, h);
			this.card_info = new ItemInfo(sit, true);
			this.card_info.x = 0;
			this.card_info.y = 0;
			this.card_info.resize(w/2-4, h);
			card_pan.addChild(card_info);
			this.addChild(card_pan);
			
			if ((sit.getSkills!=null && sit.getSkills.datas.length>0) ||
				(sit.getUnits!=null  && sit.getUnits.datas.length>0)) 
			{
				var data : * = null;
				for each (var sd : * in sit.getSkills.datas) {
					data = sd;
				}
				for each (var ud : * in sit.getUnits.datas) {
					data = ud;
				}
				this.content = new SyncCardInfoPan(data, w/2-4, h);
				this.content.x = w/2+4;
				this.content.y = 0;
				this.addChild(content);
			}
			
		}
		
		
		
	}
}