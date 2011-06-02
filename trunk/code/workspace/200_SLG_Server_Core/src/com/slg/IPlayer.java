package com.slg;

/**
 * 玩家实体接口
 * @author yagami0079
 *
 */
public interface IPlayer {
	/**
	 * 获得玩家ID
	 * @return
	 */
	public int getPlayerId();
	/**
	 * 获得玩家名字
	 * @return
	 */
	public String getPlayerName();
	
	/**
	 * 获得当前经验值
	 * @return
	 */
	public int getExp();
	/**
	 * 获得经验值最大值
	 * @return
	 */
	public int getExpMax();
	/**
	 * 添加经验值
	 * @param exp
	 * @return
	 */
	public int addExp(int exp);
	/**
	 * 获得玩家当前行动值
	 * @return
	 */
	public int getAp();
	/**
	 * 获得玩家行动值的上限
	 * @return
	 */
	public int getApMax();
	/**
	 * 添加玩家行动值
	 * @param ap
	 * @return
	 */
	public int addAp(int ap);
	/**
	 * 获取官阶
	 * @return
	 */
	public int getOffice();
	/** 
	 * 官阶操作
	 * @param off
	 * @return
	 */
	public int addOffice(int off);
	/**
	 * 官阶操作
	 * @param office
	 * @return
	 */
	public int setOffice(int office);
}
