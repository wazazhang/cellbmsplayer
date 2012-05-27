package com.fc.castle.ui
{
	import com.cell.ui.Anchor;
	import com.cell.ui.TextPan;
	import com.cell.ui.component.Lable;
	import com.cell.ui.component.Pan;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.layout.UILayoutManager;
	
	import flash.display.DisplayObjectContainer;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;

	public class BaseForm extends UIComponent
	{
		private var _title : TextPan;
		
		public function BaseForm(w:int=320, h:int=240)
		{
			super(UILayoutManager.getInstance().createUI("com.fc.castle.ui.BaseForm", this));
			
			_title = new TextPan("");
			_title.getTextField().defaultTextFormat = UILayoutManager.getInstance().createTextFormat("com.fc.castle.ui.BaseForm.title", this);
			_title.mouseEnabled = false;
			_title.y = 0;
			_title.x = 0;
			_title.resize(w, 24, Anchor.ANCHOR_CENTER);
			addChild(_title);
			
			this.mouseChildren = true;
			this.mouseEnabled  = true;
			resize(w, h, true);
			
		}
		
		public function setTitle(html:String):void
		{
			_title.setHTMLText(html);
			_title.resize(getBG().width, 24);
		}
		
		public function getTitle() : String
		{
			return _title.getTextField().text;
		}
		
		override protected function resize(w:int, h:int, flush:Boolean):Boolean
		{
			if (super.resize(w, h, flush)) {
				_title.resize(w, 24);
				return true;
			} else {
				return false;
			}
		}
		
	}
}