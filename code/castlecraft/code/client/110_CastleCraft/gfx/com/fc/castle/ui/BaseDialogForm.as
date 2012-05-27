package com.fc.castle.ui
{
	import com.cell.ui.Anchor;
	import com.cell.ui.DialogGround;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.UIComponent;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.screens.Screens;
	
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class BaseDialogForm extends BaseForm
	{
		
		private var _ok 		: ImageButton;
		private var _cancel 	: ImageButton;
		
		
		public function BaseDialogForm(w:int=320, h:int=240, cancel:Boolean=true, ok:Boolean=false)
		{
			super(w, h);
			
			var sx : Number = w-10;
			var sy : Number = h-10;
			if (ok) {
				_ok = CLayoutManager.alertCreateOK();
				_ok.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
				_ok.addEventListener(MouseEvent.CLICK, onMouseClickOK);
				_ok.x = sx - _ok.width/2;
				_ok.y = sy - _ok.height/2;
				this.addChild(_ok);
				sx -= (_ok.width + 4);
			}
			
			if (cancel) {
				_cancel = CLayoutManager.alertCreateCancel();
				_cancel.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
				_cancel.addEventListener(MouseEvent.CLICK, onMouseClickCancel);
				_cancel.x = sx - _cancel.width/2;
				_cancel.y = sy - _cancel.height/2;
				this.addChild(_cancel);
			}
		}
		
		public function get btnOK() : ImageButton
		{
			return _ok;
		}
		
		public function get btnCancel() : ImageButton
		{
			return _cancel;
		}
		
		protected function onMouseClickOK(e:MouseEvent) : void
		{

		}
		
		protected function onMouseClickCancel(e:MouseEvent) : void
		{
			removeFromParent();
		}
		
		static public function show(form:BaseDialogForm) : BaseDialogForm
		{
			form.x = Screens.WIDTH/2 - form.width/2;
			form.y = Screens.HEIGHT/2 - form.height/2;
			DialogGround.showAsDialog(form, Screens.getRoot().getCurrentScreen());
			return form;
		}
	}
}