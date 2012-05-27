package com.fc.castle.ui.relation
{
	import com.cell.ui.Anchor;
	import com.cell.ui.TextPan;
	import com.cell.ui.component.Pan;
	import com.cell.ui.component.UIComponent;
	import com.cell.util.Util;
	import com.fc.castle.data.PlayerFriend;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.res.Res;
	
	import flash.display.Bitmap;
	import flash.geom.Rectangle;

	public class FriendCard extends UIComponent
	{
		private var friend:PlayerFriend;
		
		
		public function FriendCard(friend:PlayerFriend)
		{
			super(CLayoutManager.ui_pan_soldier.copy());
			super.setSize(80, 100);
			Util.setScrollRect(this, new Rectangle(0,0,80,100));
			
			this.friend = friend;
			
			var name : TextPan = new TextPan(friend.playerName);
			name.x = 10;
			name.y = 10;
			name.getTextField().setTextFormat(CLayoutManager.tf_name);
			name.resize(getBG().width-20, 24, Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_TOP);
			this.addChild(name);
			
			if (friend.headUrl!=null && friend.headUrl.length>0) {
				
			} else {
				var hd : Bitmap = new Res.gfx_defaultHead();
				hd.x = width/2 - hd.width/2;
				hd.y = 26;
				addChild(hd);
			}
			
			this.mouseEnabled  = true;
		}
		
		public function getData() : PlayerFriend
		{
			return friend;
		}
		
		
	}
}