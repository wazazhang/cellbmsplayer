package com.fc.castle.data.template
{
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.MessageFactory;
	import com.cell.net.io.NetDataTypes;
	import com.cell.util.Map;
	import com.fc.castle.data.*;
	import com.fc.castle.data.message.*;
	import com.fc.castle.data.message.Messages.*;
	import com.fc.castle.data.template.*;
	import com.fc.castle.data.template.Enums.*;


	/**
	 * 玩家道具模板数据<br>
	 * Java Class [80] [com.fc.castle.data.template.ItemTemplate]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class ItemTemplate extends com.fc.castle.data.message.AbstractTemplate implements MutualMessage
	{


		/** 道具类型<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var type : int;

		/** 图标<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var icon : String;

		/** 价格,对应PlayerData.coin<br>
		  * Java type is : <font color=#0000ff>int</font>*/
		public var priceCoin : int;

		/** 可以获取兵种<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>*/
		public var getUnits : com.fc.castle.data.SoldierDatas;

		/** 可以获取技能<br>
		  * Java type is : <font color=#0000ff>com.fc.castle.data.SkillDatas</font>*/
		public var getSkills : com.fc.castle.data.SkillDatas;

		/** 道具名字<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var name : String;

		/** 描述<br>
		  * Java type is : <font color=#0000ff>java.lang.String</font>*/
		public var desc : String;



		/**
		 * @param type as <font color=#0000ff>int</font>
		 * @param icon as <font color=#0000ff>java.lang.String</font>
		 * @param priceCoin as <font color=#0000ff>int</font>
		 * @param getUnits as <font color=#0000ff>com.fc.castle.data.SoldierDatas</font>
		 * @param getSkills as <font color=#0000ff>com.fc.castle.data.SkillDatas</font>
		 * @param name as <font color=#0000ff>java.lang.String</font>
		 * @param desc as <font color=#0000ff>java.lang.String</font>		 */
		public function ItemTemplate(
			type : int = 0,
			icon : String = null,
			priceCoin : int = 0,
			getUnits : com.fc.castle.data.SoldierDatas = null,
			getSkills : com.fc.castle.data.SkillDatas = null,
			name : String = null,
			desc : String = null) 
		{
			this.type = type;
			this.icon = icon;
			this.priceCoin = priceCoin;
			this.getUnits = getUnits;
			this.getSkills = getSkills;
			this.name = name;
			this.desc = desc;
		}
		
		override public function getName() : String {
			return name;
		}

		override public function getType() : int {
			return type;
		}



	}
}