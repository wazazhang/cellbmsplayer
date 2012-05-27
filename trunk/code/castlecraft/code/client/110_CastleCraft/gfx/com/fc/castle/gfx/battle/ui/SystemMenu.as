package com.fc.castle.gfx.battle.ui
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.PopupMenu;
	import com.cell.ui.TouchToggleButton;
	import com.cell.ui.component.Alert;
	import com.cell.util.ImageUtil;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.Screens;
	
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class SystemMenu extends CellSprite
	{		
		private var battle			: StageBattle;
		
		private var popup_option 	: PopupMenu;
		private var btn_pause		: TouchToggleButton;
		private var op_return 		: ImageButton;
				
		public function SystemMenu(battle:StageBattle)
		{
			this.battle = battle;
			
			op_return = com.cell.ui.ImageButton.createImageButtonScaleBitmap(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_btn_nh, Res.ui_btn_op_return).bitmapData);
			op_return.anchor = Anchor.ANCHOR_CENTER;
			op_return.addEventListener(MouseEvent.CLICK, onMouseClick);
			
			btn_pause =  new TouchToggleButton(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_btn_menu, Res.ui_bth_pause),
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_btn_menu, Res.ui_bth_resume)
			);
			btn_pause.anchor = Anchor.ANCHOR_CENTER;
			
			popup_option = new PopupMenu(btn_pause, op_return);
			popup_option.mode = PopupMenu.MODE_DOWN;
			popup_option.x = 0;
			popup_option.y = 0;
			addChild(popup_option);
		}
		
		private function onMouseClick(e:MouseEvent) : void
		{
			// help
			if (e.currentTarget == op_return) 
			{
				var alert : Alert = Alert.showAlertText("中止当前战斗?","", true, true);
				alert.btnOK.addEventListener(MouseEvent.CLICK,
					function onReturn(e:Event) : void {
						Screens.getRoot().changeScreen(Screens.SCREEN_MAIN_MENU);
				});
				alert.btnCancel.addEventListener(MouseEvent.CLICK,
					function onReturn(e:Event) : void {
						if (isPause()) {
							btn_pause.setSelected(false);
							popup_option.close();
						}
				});
			}
		}

		public function isPause() : Boolean
		{
			return btn_pause.getSelected();
		}
		
	}
}