package com.fc.castle.gfx.battle.ai
{
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.net.client.DataManager;

	public class BuildPlane
	{
		
		public var build:SoldierData;
		public var threa:Number; //威胁值
		public var Y:int = 0;
		public var soilder:UnitTemplate;
		
		public function BuildPlane(build:SoldierData,threa:Number,Y:int)
		{
			this.build = build;
			this.threa = threa;
			this.Y = Y;
			soilder = DataManager.getUnitTemplate(build.unitType);
		}
	}
}