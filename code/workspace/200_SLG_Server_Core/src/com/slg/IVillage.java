package com.slg;

/**
 * 村寨实体
 * @author yagami0079
 *
 */
public interface IVillage {
	/**
	 * 获得村寨ID
	 * @return
	 */
	public int getVillageID();
	/**
	 * 获得村寨名
	 * @return
	 */
	public String getVillageName();
	/**
	 * 获得所属城市ID
	 * @return
	 */
	public int getCityID();
	/**
	 * 获得村寨粮食储备
	 * @return
	 */
	public int getFood();
}
