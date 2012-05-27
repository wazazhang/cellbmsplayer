package com.fc.castle.gs.scene;

import com.cell.CUtil;
import com.cell.gameedit.object.WorldSet;
import com.cell.gameedit.object.WorldSet.SpriteObject;
import com.cell.reflect.Parser;
import com.cell.util.Properties;
import com.cell.util.StringUtil;
import com.fc.castle.data.ExploreData;
import com.fc.castle.data.ExploreState;
import com.fc.castle.data.PlayerData;
import com.fc.castle.data.message.AbstractData;
import com.fc.castle.data.template.EventTemplate;
import com.fc.castle.service.DataManager;


public abstract class ExploreSite 
{
//	public final int id;
	
//	private EventTemplate event;
	
//	public String map_id;
//	// SpriteObject 
//	

//	public int type;
	
	final protected String 	UnitName;
	final protected String 	explore_type;
	final protected int[] 	event_types;
	final protected EventTemplate[] 	events;
	
//	protected ExploreData ed = new ExploreData();
	
	public ExploreSite(String unitName, Properties unitData) throws Exception
	{
		this.UnitName		= unitName;
		this.explore_type	= unitData.getString("explore_type");
		this.event_types	= StringUtil.getIntegerArray(unitData.getString("event_types"), ",");
		this.events			= DataManager.getInstance().getEventTemplates(event_types);
//		this.edit_obj 	= obj;
//		this.event		= pdata.getInteger(key);
//		this.UnitName 	= obj.UnitName;
//		ed.event_type 	= this.event.eventType;
//		ed.map_id 		= this.map_id;
		
		if (events == null || events.length == 0) {
			throw new Exception("There is no [event_types] in ["+unitName+"]");
		}
	}
	
	final public String getUnitName() {
		return UnitName;
	}
	
	final public String getExploreType() {
		return explore_type;
	}
	
	final public EventTemplate[] getEvents() {
		return events;
	}
	
	final public ExploreData getData() {
		ExploreData ed = new ExploreData();
		ed.UnitName = UnitName;
		return ed;
	}
	
	/**对于某玩家是否可见*/
	public boolean isVisible(PlayerData player)
	{
		return true;
	}
	
	/** 是否正在冷却 */
	public boolean isCoolDown(PlayerData player)
	{
		ExploreState es = player.exploreStates.get(UnitName);
		if (es==null){
			return false;
		}
		if (es.last_time.getTime() + events[0].refreshTime * 1000 < System.currentTimeMillis()) {
			return false;
		}else{
			return true;
		}
	}
}
