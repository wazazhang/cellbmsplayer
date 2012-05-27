package com.fc.castle.ui.card
{
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.Panel;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.layout.UIRect;
	import com.cell.util.Map;
	
	import flash.display.BitmapData;
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class CardSelectPanel extends UIComponent
	{
		private var cardSel : Panel;
		
		private var cardPan : CardPanel;
		
		private var cardSlots : Vector.<CardSlot> = new Vector.<CardSlot>();
		
		public function CardSelectPanel(w:int, h:int, cw:int, ch:int, datas:Array, slotCount:int)
		{
			super(new UIRect().setImagesClip9(new BitmapData(w, h, true, 0), 1));
			
			super.mouseChildren = true;
			
			cardSel = new Panel(w, 90);
			cardSel.setEnableScroll(true, false);
			cardSel.x = 0;
			cardSel.y = 0;
			var sx:int=0;
			var sy:int=0;
			for (var i:int=0; i<slotCount; i++) {
				var slot : CardSlot = new CardSlot(cw, ch);
				slot.addEventListener(MouseEvent.CLICK, onMouseDownDstCard);
				slot.x = sx;
				slot.y = sy;
				cardSlots.push(slot);
				cardSel.addPanelChild(slot);
				sx += slot.width + cardSel.getBorder();
			}
			addChild(cardSel);
			
			cardPan = new CardPanel(w, h-90-4, cw, ch, datas);
			for each (var card:Card in cardPan.getCards()) {
				card.addEventListener(MouseEvent.CLICK, onMouseDownSrcCard);
			}
			cardPan.setEnableScroll(false, true);
			cardPan.x = 0;
			cardPan.y = 94;
			addChild(cardPan);
		}
		
		//		------------------------------------------------------------------------------------------------------
		//		士兵/技能配给
		//		------------------------------------------------------------------------------------------------------
		
		
		/**获取空闲的士兵/技能携带槽*/
		private function getFreeSlot() : CardSlot
		{
			for each (var s:CardSlot in cardSlots) {
				if (s.getSrcCard() == null) {
					return s;
				}
			}
			return null;
		}
		
		/**判断该类型的士兵/技能是否已携带*/
		private function findDataSlot(templateType:int) : CardSlot
		{
			for each (var s:CardSlot in cardSlots) {
				if (s.getSrcCard() != null) {
					if (s.getSrcCard().template.type == templateType) {
						return s;
					}
				}
			}
			return null;
		}
		
		/**获取所有已选择士兵/技能*/
		public function getSlotCardsData() : Array
		{
			var soldiers : Array = new Array();
			for each (var s:CardSlot in cardSlots) {
				if (s.getSrcCard() != null) {
					soldiers.push(s.getSrcCard().data);
				}
			}
			return soldiers;
		}
		
		/**获取所有已选择士兵/技能数量*/
		public function getSlotCardsCount() : int
		{
			var count : int = 0;
			for each (var s:CardSlot in cardSlots) {
				if (s.getSrcCard() != null) {
					count ++;
				}
			}
			return count;
		}
		
		/** 获取所有可以选择的士兵/技能 */
		public function getAllCards():Array
		{
			return cardPan.getCards();
		}
		
		public function selectCardType(type:int) : void
		{
			var card : Card = cardPan.getCardType(type);
			selectCard(card);
		}
		
		public function selectCard(card:Card) : void
		{
			var free : CardSlot = getFreeSlot();
			if (card != null && free != null) {
				if (findDataSlot(card.template.type) == null) {
					free.setSrcCard(card);
					card.visible = false;
				}
			}
		}
		
		public function unselectCard(slot:CardSlot) : void
		{
			if (slot.getSrcCard() != null) {
				slot.getSrcCard().visible = true;
			}
			slot.setSrcCard(null);
		}
		
		/**携带士兵/技能*/
		private function onMouseDownSrcCard(e:MouseEvent) : void
		{
			if (!cardPan.isTouchMoved()) 
			{
				var card : Card = e.currentTarget as Card;
				selectCard(card);
			}
			
		}
		
		/**将已携带士兵/技能撤下*/
		private function onMouseDownDstCard(e:MouseEvent) : void
		{
			if (!cardSel.isTouchMoved()) 
			{
				var slot : CardSlot = e.currentTarget as CardSlot;
				unselectCard(slot);
			}
		}
		
	}
}