package com.fc.castle.gs.scene;

import com.cell.util.Properties;
import com.fc.castle.data.ExploreData;
import com.fc.castle.data.template.EventTemplate;
import com.fc.castle.service.DataManager;

public class ExploreSiteRace extends ExploreSite
{
	public int race;
	
	public final static int RACE_WAIXING	= 1;
	public final static int RACE_YESHOU		= 2;
	public final static int RACE_YUREN		= 3;
	public final static int RACE_YUANSU		= 4;
	public final static int RACE_JIXIE		= 5;
	public final static int RACE_HEIAN		= 6;
	
	public ExploreSiteRace(String unitName, Properties unitData)  throws Exception
	{
		super(unitName, unitData);
//		this.race = race;
//		int type = 2001;
//		switch(race){
//		case RACE_WAIXING:
//			type = 2001;
//			break;
//		case RACE_YESHOU:
//			type = 2002;
//			break;
//		case RACE_YUREN:
//			type = 2003;
//			break;
//		case RACE_YUANSU:
//			type = 2004;
//			break;
//		case RACE_JIXIE:
//			type = 2005;
//			break;
//		case RACE_HEIAN:
//			type = 2006;
//			break;
//		}
//		EventTemplate et = DataManager.getInstance().getEventTemplate(type);
//		setEventTemplate(et);
	}

//	@Override
//	public ExploreData getExploreData() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
