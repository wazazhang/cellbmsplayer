package com.fc.castle.gfx.world
{
	import com.cell.gameedit.object.worldset.SpriteObject;
	import com.cell.gfx.game.CCD;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.gfx.game.worldcraft.CellCSpriteBuffer;
	import com.cell.gfx.game.worldcraft.CellCSpriteGraphics;
	import com.cell.gfx.game.worldcraft.CellUnit;
	import com.cell.ui.Anchor;
	import com.cell.ui.TextLable;
	import com.cell.ui.component.Lable;
	import com.cell.ui.layout.UIRect;
	import com.cell.util.StringUtil;
	import com.fc.castle.res.CLayoutManager;

	public class WorldUnit extends CellCSpriteBuffer
	{
		protected var data		: SpriteObject;
		
		protected var head_text		: Lable;
		protected var bottom_text	: Lable;
		
		protected var bounds	: CCD;
		
		protected var scene		: WorldScene;
		
		public function WorldUnit(ws:WorldScene, wd:SpriteObject, csprite:CSpriteBuffer)
		{
			super(csprite);
			
			this.scene = ws;
			this.data = wd;
		
			this.x = wd.X;
			this.y = wd.Y;
			
			this.setCurrentAnimate(wd.Anim);
			this.setCurrentFrame(wd.Frame);
			
			this.bounds = getCSprite().getFrameImageBounds(getCSprite().getCurrentAnimate(), getCSprite().getCurrentFrame());
			
			this.head_text = new Lable("");
			this.head_text.mouseEnabled = false;
			this.head_text.textAnchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_BOTTOM;	
			
			this.bottom_text = new Lable("");
			this.bottom_text.mouseEnabled = false;
			this.bottom_text.textAnchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_BOTTOM;	

			this.cacheAsBitmap = true;
		}
		
		public function getUnitName() : String
		{
			return data.UnitName;
		}
		
		public function setHeadText(txt:String) : void
		{
			head_text.setHTMLText(txt);
			if (!contains(head_text)) {
				addChild(head_text);
			}
			head_text.setSize(
				head_text.getTextField().textWidth+8, 
				head_text.getTextField().textHeight+8);
			head_text.x = -head_text.width/2;
			head_text.y = bounds.Y1 - head_text.height;
		}
		
		public function setBottomText(txt:String) : void
		{
			bottom_text.setHTMLText(txt);
			if (!contains(bottom_text)) {
				addChild(bottom_text);
			}
			bottom_text.setSize(
				bottom_text.getTextField().textWidth+8, 
				bottom_text.getTextField().textHeight+8);
			bottom_text.x = -bottom_text.width/2;
			bottom_text.y = bounds.Y2;
		}
		
		
		override protected function onUpdate():void
		{
			this.nextCycFrame();
		}
		
		
		public static function isStaticUnit(wd:SpriteObject) : Boolean
		{
			return StringUtil.trim(wd.Data).length == 0;
		}
	}
}