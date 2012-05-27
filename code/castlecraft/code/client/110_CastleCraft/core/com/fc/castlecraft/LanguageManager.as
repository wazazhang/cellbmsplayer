package com.fc.castlecraft
{
	import com.cell.io.IOUtil;
	import com.cell.util.Map;
	import com.cell.util.StringUtil;
	import com.fc.castle.data.template.Enums.AttackType;
	import com.fc.castle.data.template.Enums.DefenseType;
	import com.fc.castle.data.template.Enums.FightType;
	import com.fc.castle.res.Res;
	
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.utils.ByteArray;
	
	import mx.core.ByteArrayAsset;

	public class LanguageManager
	{
		private static var language:Map;
		private static var language_embed:Map;
		
		private static var loader:URLLoader;
		
		public static function getText(key:String, ... args):String
		{		
			var ret : String = language[key];
			if (ret == null) {
				ret = language_embed[key];
				trace("language not contain key["+key+"], default is ["+ret+"]");
			}
			if (args.length > 0) {
				ret = StringUtil.substitute(ret, args);
			}
			return ret;
		}
		
		public static function loadLanguage():void
		{
			var bin:ByteArray = new Res.language;
			var txt:String = bin.readMultiByte(bin.length, 'utf-8');
			language_embed = Map.readFromProperties(txt);
			
			loader = IOUtil.loadURL("location/" + AutoLogin.LOCATION + "/language.properties", onComplete, onError);
		}
		
		public static function onComplete(e:Event) : void
		{
			language = Map.readFromProperties(loader.data);
		}
		
		public static function onError(e:Event) : void
		{

		}
		
		public static function getFightType(type:int):String
		{
			if(type == FightType.NORMAL)
				return getText("handbook.fightType.NORMAL");
			else if(type == FightType.MISSILE)
				return getText("handbook.fightType.MISSILE");
			else if(type == FightType.ARTILLERY)
				return getText("handbook.fightType.ARTILLERY");
			else if(type == FightType.INSTANT)
				return getText("handbook.fightType.INSTANT");
			else
				return "error";
		}
		
		public static function getAttackType(type:int):String
		{

			if(type == AttackType.NORMAL)
				return getText("handbook.attackType.NORMAL");
			else if(type == AttackType.CHAOS)
				return getText("handbook.attackType.CHAOS");
			else if(type == AttackType.HERO)
				return getText("handbook.attackType.HERO");
			else if(type == AttackType.MAGIC)
				return getText("handbook.attackType.MAGIC");
			else if(type == AttackType.PIERCE)
				return getText("handbook.attackType.PIERCE");
			else if(type == AttackType.SIEGE)
				return getText("handbook.attackType.SIEGE");
			else if(type == AttackType.SKILL)
				return getText("handbook.attackType.SKILL");
			else
				return "error";
		}
		public static function getDefenceType(type:int):String
		{
			if(type == DefenseType.FORT )
				return getText("handbook.defenceType.FORT");
			else if(type == DefenseType.HEAVY )
				return getText("handbook.defenceType.HEAVY");
			else if(type == DefenseType.HERO)
				return getText("handbook.defenceType.HERO");	
			else if(type == DefenseType.LIGHT)
				return getText("handbook.defenceType.LIGHT");		
			else if (type == DefenseType.MEDIUM )
				return getText("handbook.defenceType.MEDIUM");	
			else if (type == DefenseType.UNARMORED )
				return getText("handbook.defenceType.UNARMORED");	
			else	
				return "error";
		}
		
		
	}
}