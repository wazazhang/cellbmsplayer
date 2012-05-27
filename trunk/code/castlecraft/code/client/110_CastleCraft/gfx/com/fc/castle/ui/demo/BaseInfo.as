package com.fc.castle.ui.demo
{
	import com.cell.gfx.CellSprite;
	import com.fc.castle.data.message.AbstractTemplate;
	import com.fc.castle.data.template.ItemTemplate;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.data.template.UnitTemplate;

	public class BaseInfo extends CellSprite
	{
		private var _template : AbstractTemplate;
		
		public function BaseInfo(type:AbstractTemplate)
		{
			this._template = type;
			this.mouseEnabled  = false;
			this.mouseChildren = false;
		}
		
		public function resize(w:int, h:int):void
		{
			
		}
		
		static public function createCardInfoPan(type:AbstractTemplate) : BaseInfo
		{
			var ret : BaseInfo = null;
			if (type is UnitTemplate) {
				ret = new UnitInfo(type as UnitTemplate);
			}
			else if (type is SkillTemplate) {
				ret = new SkillInfo(type as SkillTemplate);
			}
			else if (type is ItemTemplate) {
				ret = new ItemInfo(type as ItemTemplate);
			}
			return ret;
		}
	}
}