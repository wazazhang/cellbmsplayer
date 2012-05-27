package com.fc.castle.res
{
	import com.cell.gfx.game.Transform;
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Lable;
	import com.cell.ui.component.Panel;
	import com.cell.ui.component.ScrollBar;
	import com.cell.ui.component.TextBox;
	import com.cell.ui.component.TextButton;
	import com.cell.ui.component.TextInput;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;
	import com.cell.util.ImageUtil;
	import com.cell.util.Map;
	import com.cell.util.Util;
	import com.fc.castle.screens.Screens;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.text.TextFormat;
	
	import mx.controls.Image;

	public class CLayoutManager extends UILayoutManager
	{
		public static var SOLDIER_BTN_W	: int = 53;
		public static var SOLDIER_BTN_H	: int = 53;
		
		public static var SKILL_BTN_W	: int = 53;
		public static var SKILL_BTN_H	: int = 53;
		
		
		public static var ui_lable 			: UIRect;
		public static var ui_textfield 		: UIRect;
		public static var ui_form 			: UIRect;		
		public static var ui_pan_soldier 	: UIRect;
		
		public static var ui_scroll_vback 	: UIRect;
		public static var ui_scroll_vstrip 	: UIRect;
		public static var ui_scroll_hback 	: UIRect;
		public static var ui_scroll_hstrip 	: UIRect;
		
		public static var ui_btn_sel 		: UIRect;
		public static var ui_btn_unsel	 	: UIRect;
		
		public static var ui_lable_column	: UIRect;
		public static var ui_lable_mail		: UIRect;
		
		public static var ui_guage_bg		: UIRect;
		public static var ui_guage_strip	: UIRect;
		
		public static var ui_panel1			: UIRect;
		public static var ui_panel2			: UIRect;
		
		public static var ui_form1		:UIRect;
		public static var ui_blank1		:UIRect;
		
		public static var ui_btn2			:UIRect;
		public static var ui_btn3			:UIRect;
		public static var ui_btn4			:UIRect;
		public static var ui_btn5			:UIRect;
		public static var ui_btn6			:UIRect;
		public static var ui_btn7			:UIRect;
		
		public static var img_alert_ok 		: BitmapData;
		public static var img_alert_cancel	: BitmapData;
		
		public static var tf_default		: TextFormat = new TextFormat("Verdana", 12, 0);
		public static var tf_name			: TextFormat = new TextFormat("Verdana", 12, 0xffffff);
		public static var tf_header			: TextFormat = new TextFormat("Verdana", 12, 0xffffff);
		public static var tf_lablemail		: TextFormat = new TextFormat("Verdana", 12, 0xffffff);
		public static var tf_alert			: TextFormat = new TextFormat("Verdana", 20, 0xffffff);
		
//		-----------------------------------------------------------------------------------
		
		/**UIRect*/
		private var ui_map			: Map = new Map();
		/**TextFormat*/
		private var tf_map			: Map = new Map();
		/**ImageButton*/
		private var ib_map			: Map = new Map();
		
//		-----------------------------------------------------------------------------------
		
		public function CLayoutManager()
		{
			super(Screens.getRoot(), Screens.WIDTH, Screens.HEIGHT);
			
			// ui
			ui_lable			= Res.createUIRect(Res.ui_lable,			10).initBuffer(100, 24);
			ui_textfield		= Res.createUIRect(Res.ui_textfield, 		10).initBuffer(100, 24);
			ui_form				= Res.createUIRect(Res.ui_panel, 			16).initBuffer(100, 24);
			ui_pan_soldier		= Res.createUIRect(Res.ui_pan_soldier_hd, 	16).initBuffer(64, 64);
			
			// panel scroll bar
			ui_scroll_vback 	= new UIRect().setImagesClip036(Res.createBitmap(Res.ui_scroll_back).bitmapData, 10);
			ui_scroll_vstrip 	= new UIRect().setImagesClip036(Res.createBitmap(Res.ui_scroll_strip).bitmapData, 10);
			ui_scroll_hback 	= new UIRect().setImagesClip012(
									ImageUtil.transformImage(Res.createBitmap(Res.ui_scroll_back).bitmapData, Transform.TRANS_90), 10);
			ui_scroll_hstrip 	= new UIRect().setImagesClip012(
									ImageUtil.transformImage(Res.createBitmap(Res.ui_scroll_strip).bitmapData, Transform.TRANS_90), 10);
			
			// button
			ui_btn_unsel		= Res.createUIRect(Res.ui_btn_1u, 10).initBuffer(200, 24);
			ui_btn_sel			= Res.createUIRect(Res.ui_btn_1d, 10).initBuffer(200, 24);
			
			ui_btn2				= new UIRect().setImagesClipBorder(Res.createBitmap(Res.ui_btn2).bitmapData, UIRect.IMAGE_STYLE_BACK_4, 0, 0, 0, 0);
			ui_btn3				= new UIRect().setImagesClipBorder(Res.createBitmap(Res.ui_btn3).bitmapData, UIRect.IMAGE_STYLE_BACK_4, 0, 0, 0, 0);
			ui_btn4				= new UIRect().setImagesClipBorder(Res.createBitmap(Res.ui_btn4).bitmapData, UIRect.IMAGE_STYLE_BACK_4, 0, 0, 0, 0);
			ui_btn5				= new UIRect().setImagesClipBorder(Res.createBitmap(Res.ui_btn5).bitmapData, UIRect.IMAGE_STYLE_BACK_4, 0, 0, 0, 0);
			ui_btn6				= new UIRect().setImagesClipBorder(Res.createBitmap(Res.ui_btn6).bitmapData, UIRect.IMAGE_STYLE_BACK_4, 0, 0, 0, 0);
			ui_btn7				= new UIRect().setImagesClipBorder(Res.createBitmap(Res.ui_btn7).bitmapData, UIRect.IMAGE_STYLE_BACK_4, 0, 0, 0, 0);
			
			//
			ui_guage_bg			= Res.createUIRect(Res.ui_panel1,			32).initBuffer(100, 100);
			ui_guage_strip		= Res.createUIRect(Res.ui_panel2,			32).initBuffer(100, 100);
			
			//
			ui_panel1			: UIRect;
			ui_panel2			: UIRect;
			
			ui_form1	= new UIRect().setImagesClip9(Res.createBitmap(Res.ui_form1).bitmapData, 30);
			ui_blank1   = new UIRect().setImagesClip9(Res.createBitmap(Res.ui_blank1).bitmapData, 12);
			
			//
			ui_lable_column		= Res.createUIRect(Res.ui_lableColumn, 8).initBuffer(200, 26);
			ui_lable_mail		= Res.createUIRect(Res.ui_lableMail,  20).initBuffer(200, 26);
			
			// alert
			img_alert_ok		= ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_btn_menu, Res.ui_bth_ok).bitmapData;
			img_alert_cancel	= ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_btn_menu, Res.ui_bth_close).bitmapData;
		
			//////////////////////////////////////////////////////////////////////////////////
			tf_map.put("com.cell.ui.TextPan.text", 					tf_default);
			tf_map.put("com.cell.ui.TextLable.text", 				tf_default);
			
			
			ui_map.put("com.cell.ui.component.Alert", 				ui_form);
			tf_map.put("com.cell.ui.component.Alert.text", 			tf_alert);
			ib_map.put("com.cell.ui.component.Alert.ok", 			img_alert_ok);
			ib_map.put("com.cell.ui.component.Alert.cancel", 		img_alert_cancel);
			
			ui_map.put("com.cell.ui.component.Lable", 				ui_lable);
			tf_map.put("com.cell.ui.component.Lable.text", 			tf_default);
			
			ui_map.put("com.cell.ui.component.Pan", 				ui_form);
			ui_map.put("com.cell.ui.component.Panel", 				ui_pan_soldier);
			
			ui_map.put("com.cell.ui.component.ScrollBar.h", 		ui_scroll_hback);
			ui_map.put("com.cell.ui.component.ScrollBar.h.strip", 	ui_scroll_hstrip);
			ui_map.put("com.cell.ui.component.ScrollBar.v", 		ui_scroll_vback);
			ui_map.put("com.cell.ui.component.ScrollBar.v.strip", 	ui_scroll_vstrip);
			
			ui_map.put("com.cell.ui.component.TextBox", 			ui_textfield);
			tf_map.put("com.cell.ui.component.TextBox.text", 		tf_default);
			
			ui_map.put("com.cell.ui.component.TextButton.up", 		ui_btn_unsel);
			ui_map.put("com.cell.ui.component.TextButton.down", 	ui_btn_sel);
			tf_map.put("com.cell.ui.component.TextButton.text", 	tf_default);
			
			ui_map.put("com.cell.ui.component.TextInput", 			ui_textfield);
			tf_map.put("com.cell.ui.component.TextInput.text", 		tf_default);
			
			ui_map.put("com.cell.ui.component.Guage", 				ui_guage_bg);
			ui_map.put("com.cell.ui.component.Guage.strip", 		ui_guage_strip);
			
			
			ui_map.put("com.cell.ui.component.listview.ListView", 	ui_pan_soldier);
			ui_map.put("com.cell.ui.component.listview.ListViewHeader", ui_lable_column);
			tf_map.put("com.cell.ui.component.listview.ListViewHeader.text", tf_header);
			//////////////////////////////////////////////////////////////////////////////////
			
			ui_map.put("com.fc.castle.ui.BaseForm", 				ui_form);
			tf_map.put("com.fc.castle.ui.BaseForm.title", 			tf_default);
			
			ui_map.put("com.fc.castle.ui.form1", 				ui_form1);
			ui_map.put("com.fc.castle.ui.blank1"	,				ui_blank1);
			//
		}
		
//		-------------------------------------------------------------------------------------------------------------------
		
		override public function createUI(key:String, owner:*) : UIRect
		{
			var ret : UIRect = ui_map.get(key);
			if (ret != null) {
				return ret.copy();
			}
			trace("LayoutError : can not found UIRect ["+key+"] in [" + owner + "]");
			return null;
		}
		
		override public function createTextFormat(key:String, owner:*) : TextFormat
		{	
			var ret : TextFormat = tf_map.get(key);
			if (ret != null) {
				return ret;
			}
			trace("LayoutError : can not found TextFormat ["+key+"] in [" + owner + "]");
			return null;
		}
		
		override public function createButton(key:String, owner:*) : DisplayObject
		{
			var ret : BitmapData = ib_map.get(key);
			if (ret != null) {
				var btn : com.cell.ui.ImageButton = new ImageButton(
					new Bitmap(ret), 
					new Bitmap(ImageUtil.toTurngrey(ret, ImageUtil.CHANNEL_GREEN)));	
				btn.anchor = 0;
				return btn;
			}
			trace("LayoutError : can not found Image ["+key+"] in [" + owner + "]");
			return null;
		}
		
//		-------------------------------------------------------------------------------------------------------------------
		
		
		public static function alertCreateOK() : ImageButton
		{
			return UILayoutManager.getInstance().createButton(
				"com.cell.ui.component.Alert.ok", null) as ImageButton;
		}
		
		public static function alertCreateCancel() : ImageButton
		{
			return UILayoutManager.getInstance().createButton(
				"com.cell.ui.component.Alert.cancel", null) as ImageButton;
		}
	}
}