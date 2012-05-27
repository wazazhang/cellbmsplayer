package com.fc.castle.gs.scene;

import com.cell.util.Properties;
import com.fc.castle.data.ExploreData;
import com.fc.castle.data.ExploreState;
import com.fc.castle.data.PlayerData;
import com.fc.castle.data.template.EventTemplate;
import com.fc.castle.service.DataManager;
import com.fc.castle.service.PersistanceManager;

public class ExploreSiteRandom extends ExploreSite {

	public int type;
	public static final int RANDOM_TYPE_GOLD	= 1;
	public static final int RANDOM_TYPE_BOX		= 2;
	public static final int RANDOM_TYPE_MONSTER	= 3;
	
	public ExploreSiteRandom(String unitName, Properties unitData)  throws Exception
	{
		super(unitName, unitData);
//		this.type = type;
//		EventTemplate et;
//		if (type == RANDOM_TYPE_GOLD){
//			et = DataManager.getInstance().getEventTemplate(3002);
//		}else if (type == RANDOM_TYPE_BOX){
//			et = DataManager.getInstance().getEventTemplate(3003);
//		}else{
//			et = DataManager.getInstance().getEventTemplate(3001);
//		}
//		setEventTemplate(et);
	}

//	@Override
//	public ExploreData getExploreData() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public boolean isVisible(PlayerData player){
		ExploreState es = player.exploreStates.get(UnitName);
		if (es==null){
//			es = new ExploreState();
//			es.UnitName = UnitName;
//			es.explore_count = 0;
//			player.exploreStates.put(UnitName, es);
//			PersistanceManager.getInstance().savePlayerFields(player, "exploreStates");
			return true;
		}
		if (es.last_time.getTime() + events[0].refreshTime*1000<System.currentTimeMillis()){
			return true;
		}else{
			return false;
		}
	}
}
