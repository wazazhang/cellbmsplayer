package com.fc.castle.ui.card
{
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageBox;
	import com.cell.ui.component.UIComponent;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.res.CResourceManager;
	
	public class CardSlot extends UIComponent
	{
		private var src_card : Card;
		
		public function CardSlot(cw:int, ch:int)
		{
			super(CLayoutManager.ui_lable.copy().initBuffer(cw, ch));
			this.mouseEnabled  = true;
		}
		
		public function getSrcCard() : Card
		{
			return src_card;
		}
		
		public function setSrcCard(card:Card) : void
		{
			if (src_card != null) {
				this.removeChildAt(1);
			}
			this.src_card = card;
			if (src_card != null) {
				var hdimg : Card = card.clone();
				hdimg.mouseEnabled = false;
				hdimg.mouseChildren = false;
				Anchor.setAnchorRect(hdimg, Anchor.ANCHOR_CENTER, width, height);
				this.addChild(hdimg);
				
//				var hdimg : ImageBox = ImageBox.createImageBox(card.getImageURL(),
//					Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER);
//				hdimg.mouseEnabled = false;
//				hdimg.mouseChildren = false;
//				hdimg.x = width/2;
//				hdimg.y = height/2;
//				this.addChild(hdimg);
			}
		}
	};
}