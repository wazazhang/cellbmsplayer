package com.fc.castle.gs.scene;

import com.cell.util.Properties;
import com.fc.castle.data.ExploreData;
import com.fc.castle.data.template.EventTemplate;
import com.fc.castle.service.DataManager;

public class ExploreSiteChallange extends ExploreSite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int type;
	public static final int CHALLANGE_TYPE_MOTA = 1;
	
	public ExploreSiteChallange(String unitName, Properties unitData)  throws Exception
	{
		super(unitName, unitData);
		
//		this.type = type;
//		EventTemplate et = DataManager.getInstance().getEventTemplate(5001);
//		setEventTemplate(et);
	}

//	@Override
//	public ExploreData getExploreData() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
