package com.slg.net.impl;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.slg.IVillage;
import com.slg.entity.Arms;
import com.slg.entity.Building;
import com.slg.entity.Soldiers;
import com.slg.entity.Village;

public class VillageImpl implements IVillage {

	final private Village village;
	
	final private ConcurrentHashMap<Integer, Building> building_list;
	final private ArrayList<Integer> hero_list;
	final private ConcurrentHashMap<Integer, Arms> arms_list;
	final private ConcurrentHashMap<Integer, Soldiers> solidiers_list;
	
	public VillageImpl(Village village){
		this.village = village;
		building_list = new ConcurrentHashMap<Integer, Building>();
		if (village.buildings!=null && village.buildings.length>0){
			for (Building b:village.buildings){
				building_list.put(b.id, b);
			}
		}
		hero_list = new ArrayList<Integer>();
		if (village.heros!=null && village.heros.length>0){
			for (int i : village.heros){
				hero_list.add(i);
			}
		}
		arms_list = new ConcurrentHashMap<Integer, Arms>();
		if (village.arms_list != null && village.arms_list.length>0){
			for (Arms a:village.arms_list){
				arms_list.put(a.type, a);
			}
		}
		solidiers_list = new ConcurrentHashMap<Integer, Soldiers>();
		if (village.soldiers_list!=null && village.soldiers_list.length>0){
			for (Soldiers s:village.soldiers_list){
				solidiers_list.put(s.type, s);
			}
		}
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
		village.buildings = new Building[building_list.size()];
		building_list.values().toArray(village.buildings);
		village.heros = new int[hero_list.size()];
		int i = 0;
		for (Integer I:hero_list){
			village.heros[i] = I;
			i++;
		}
		village.arms_list = new Arms[arms_list.size()];
		arms_list.values().toArray(village.arms_list);
		village.soldiers_list = new Soldiers[solidiers_list.size()];
		solidiers_list.values().toArray(village.soldiers_list);
		return village;
	}

}
