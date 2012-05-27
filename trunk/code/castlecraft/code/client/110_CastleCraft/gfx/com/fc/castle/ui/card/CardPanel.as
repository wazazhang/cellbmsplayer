package com.fc.castle.ui.card
{
	import com.cell.ui.component.Panel;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.SkillDatas;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.SoldierDatas;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CLayoutManager;
	
	import flash.geom.Rectangle;

	/**
	 */
	public class CardPanel extends Panel
	{
		private var cards : Array = new Array();
		
		public function CardPanel(w:int, h:int, cw:int, ch:int, datas:Array)
		{
			super(w, h);
			
			var lb : Rectangle = getPanelBounds();
			var sx : int = 0;
			var sy : int = 0;
			var i : int = 0;
			for each (var sd : * in datas) {
				var btn : Card = new Card(sd, i, cw, ch);
				btn.x = sx;
				btn.y = sy;
				addPanelChild(btn);
				cards.push(btn);
				sx += cw + getBorder();
				if (sx + cw > lb.width) {
					sx = 0;
					sy += ch + getBorder();
				}
				i++;
			}
			
			resize(w, h, true);
		}
		
		public function getCards() : Array 
		{
			return cards;
		}
		
		public function getCard(index:int) : Card
		{
			return cards[index];
		}
		
		public function getCardType(type:int) : Card
		{
			for each (var cd : Card in cards) {
				if (cd.template.getType() == type) {
					return cd;
				}
			}
			return null;
		}
	};
	
	
}
