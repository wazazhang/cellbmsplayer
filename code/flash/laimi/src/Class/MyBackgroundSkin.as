package Class
{
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.Shape;
	import flash.geom.Matrix;
	
	import mx.core.EdgeMetrics;
	import mx.core.IChildList;
	import mx.core.IContainer;
	import mx.core.IRawChildrenContainer;
	import mx.core.mx_internal;
	import mx.skins.halo.HaloBorder;
	import mx.skins.halo.PanelSkin;
	
	public class MyBackgroundSkin extends HaloBorder  
	{  
		//position信息  
		private var position:Object;  
		//repeat信息  
		private var repeat:Object;  
		//背景图片的x坐标  
		private var bgX:int = 0;  
		//背景图片的y坐标  
		private var bgY:int = 0;  
		
		/** 
		 * 这个皮肤是专门用来做panel或titleWindow皮肤的 
		 * 如果做HBox，Canvas等容器这部分可以省略去，扩展类写HaloBorder 
		 **/  
		//panel边框信息  
		private var topH:Number=0;//panel顶部边框的高度  
		private var bottomH:Number=1;//panel底部边框的高度,如果等于0，则底部边框不显示
		private var leftW:Number=0;//panel左边边框的高度  
		private var rightW:Number=1;//panel右边边框的高度  
		
		public function MyBackgroundSkin()  
		{  
			super();  
		}  
		
		//覆盖updateDisplayList方法  
		override protected function updateDisplayList(w:Number, h:Number):void  
		{  
			super.updateDisplayList(w,h);  
			
			if(!hasBackgroundImage)  
			{  
				return;  
			}     
			
			//获取背景图片  
			var childrenList:IChildList = (parent is IRawChildrenContainer)?(IRawChildrenContainer(parent).rawChildren):IChildList(parent);  
			var backgroundImage:DisplayObject = childrenList.getChildAt(1);  
			
			//从样式表中获取有关背景平铺的信息  
			position = getStyle("backgroundImagePosition");  
			repeat = getStyle("backgroundImageRepeat");  
			
			//                //从样式表中获取panel有关信息  
			//                leftW = getStyle("borderThicknessLeft");  
			//                rightW = getStyle("borderThicknessRight");  
			//                topH = Object(parent).mx_internal::getHeaderHeightProxy() + getStyle("borderThicknessTop");//panel顶部的高度由两部分相加： headerHeigh 和 borderThiknesstop  
			//                if(getStyle("borderThicknessBottom") == null)  
			//                {  
			//                    //如果在css中并没有定义该属性的话该属性的默认值有一下两种情况：  
			//                    bottomH = leftW;//(1)当panel中没有向controlBar中添加任何组件  
			//                    //bottomH = topH;//(2)当panel的controlBar中有控件时候  
			//                }  
			//                else  
			//                {  
			//                    bottomH = getStyle("borderThicknessBottom");  
			//                }  
			
			//设置默认值  
			if(repeat == null)  
			{  
				repeat = "no-repeat";  
			}  
			if(position == null || !(position is Array))  
			{  
				position = ["left","top"];  
			}  
			
			setImagePosition(backgroundImage,w,h);  
			drawRepeat(backgroundImage,w,h);  
		}  
		
		//设置背景位置  
		private function setImagePosition(target:DisplayObject,w:Number,h:Number):void  
		{  
			if(position == null || !(position is Array))  
			{  
				return;  
			}  
			
			var posHorizontal:String = (position[0] as String).toLowerCase();  
			var posVertical:String = (position[1] as String).toLowerCase();  
			
			//取得边框  
			var bm:EdgeMetrics = (parent is IContainer)?IContainer(parent).viewMetrics:borderMetrics;  
			
			//设置背景在x轴的起始位置  
			switch(posHorizontal)  
			{  
				case "left":  
					bgX = bm.left;  
					break;  
				case "center":  
					//target 默认的位置为剧中  
					bgX = target.x;   
					break;  
				case "right":  
					bgX = parent.width - bm.right - target.width;  
					break;  
				default:  
					//当传值传传错时候：比如给它传了一个“righ”  
					bgX = leftW;  
					break;  
			}  
			
			//设置背景在y轴的起始位置  
			switch(posVertical)  
			{  
				case "top":  
					bgY = bm.top;  
					break;  
				case "middle":  
					//target 默认的位置为剧中  
					bgY = target.y;  
					break;  
				case "bottom":  
					bgY = parent.height - bm.bottom - target.height;  
					break;  
				default:  
					//当传值传传错时候：比如给它传了一个“righ”  
					bgY = topH;  
					break;  
			}  
			
			target.x = bgX;  
			target.y = bgY;  
			
			
			//设置遮罩位置  
			const backgroundMask:Shape = Shape(target.mask);  
			backgroundMask.x = bgX;  
			backgroundMask.y = bgY;   
		}  
		
		//绘制背景图片  
		private function drawRepeat(target:DisplayObject,w:Number,h:Number):void  
		{  
			if(!hasBackgroundImage)  
			{  
				return;  
			}  
			
			if(repeat == "no-repeat")  
			{  
				return;  
			}  
			
			var backgroundImageClass:Class = getStyle("backgroundImage");  
			var bgImage:DisplayObject = new backgroundImageClass();  
			
			//如果背景不是图片  
			if(!(bgImage as Bitmap))  
			{  
				return;  
			}  
			
			//取得背景图片的数据  
			var backgroundBitmapData:BitmapData = (bgImage as Bitmap).bitmapData;  
			
			//隐藏默认的背景图片  
			target.visible = false;  
			
			//使用矩阵设置背景图片的填充位置  
			var mtr:Matrix = new Matrix();  
			
			//graphics.clear();//如果是给canva，Hbox等皮肤可以去掉该行注释，这样Panel默认的边框就去掉了  
			
			//重绘背景图片  
			switch(repeat)  
			{  
				case "repeat-x":  
					mtr.tx = bgX;  
					mtr.ty = bgY;  
					graphics.beginBitmapFill(backgroundBitmapData,mtr);  
					graphics.drawRect(bgX,bgY,w - rightW - bgX,bgImage.height);  
					break;  
				
				case "repeat-y":  
					mtr.tx = bgX;  
					mtr.ty = bgY;  
					graphics.beginBitmapFill(backgroundBitmapData,mtr);  
					graphics.drawRect(bgX,bgY,bgImage.width,h - bottomH - bgY);  
					break;  
				
				case "repeat":  
					mtr.tx = bgX;  
					mtr.ty  = bgY;  
					graphics.beginBitmapFill(backgroundBitmapData,mtr);  
					graphics.drawRect(bgX,bgY,w - rightW - bgX,h - bottomH - bgY);  
					break;  
				
				default:  
					mtr.tx = leftW;  
					mtr.ty  = topH;  
					graphics.beginBitmapFill(backgroundBitmapData,mtr);  
					graphics.drawRect(bgX,bgY,w - rightW - bgX,h - bottomH - bgY);  
					break;  
			}  
			
			graphics.endFill();  
			
		}  
		
	}  
	

}