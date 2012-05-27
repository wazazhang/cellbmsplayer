package com.fc.castle.formual
{
	import com.cell.util.Map;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.BuffTemplate;
	import com.fc.castle.data.template.UnitTemplate;

	public interface FormualSoldier extends FormualObject
	{
		function getMaxHP() : Number;
		
		function getSoldierData() : SoldierData;
		
		function getUnitTemplate() : UnitTemplate;
		
		function getBuffState() : BuffTemplate;
		
		function addBuff(type:int) : void;
		
		function removeBuff(type:int) : void;
		
		
		function makeSpecialEffect(sender:FormualPlayer, sp:int) : void;
	}
}