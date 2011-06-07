package com.slg.net.impl;

import com.slg.IVillage;
import com.slg.entity.Village;

public class VillageImpl implements IVillage {

	final private Village village;
	
	public VillageImpl(Village village){
		this.village = village;
	}
	
	@Override
	public int getVillageID() {
		return village.id;
	}

	@Override
	public void setVillageID(int id) {
		village.id = id;
	}

	@Override
	public String getVillageName() {
		return village.name;
	}

	@Override
	public int getPlayerID(){
		return village.player_id;
	}
	
	@Override
	public int getCityID() {
		return village.city_id;
	}

	@Override
	public int getFood() {
		return village.food;
	}

	@Override
	public Village getVillageData() {
		return village;
	}

}
