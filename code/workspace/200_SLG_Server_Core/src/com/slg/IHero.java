package com.slg;

/**
 * 武将实体
 * @author yagami0079
 *
 */
public interface IHero {
	/**
	 * 获得武将ID
	 * @return
	 */
	public int getHeroID();
	/**
	 * 获得武将名字
	 * @return
	 */
	public String getHeroName();
	/**
	 * 获得武将性别
	 * @return
	 */
	public int getSex();
	/**
	 * 获得武将品阶
	 * @return
	 */
	public int getQuality();
	/**
	 * 获得当前经验值
	 * @return
	 */
	public int getHeroExp();
	/**
	 * 获得经验最大值
	 * @return
	 */
	public int getHeroExpMax();
	/**
	 * 获得经验操作
	 * @param exp
	 * @return
	 */
	public int addExp(int exp);
	/**
	 * 获得武将等级
	 * @return
	 */
	public int getHeroLevel();
	/**
	 * 获得武将体力值
	 * @return
	 */
	public int getHp();
	/**
	 * 获得武将体力值上限
	 * @return
	 */
	public int getHpMax();
	/**
	 * 体力值操作
	 * @return
	 */
	public int addHp();
	/**
	 * 获得武将攻击力
	 * @return
	 */
	public int getAttack();
	/**
	 * 获得武将内政值
	 * @return
	 */
	public int getPolitics();
	/**
	 * 获得武将统帅值
	 * @return
	 */
	public int getCommander();
	/**
	 * 获得武将忠诚度
	 * @return
	 */
	public int getLoyalty();
}
