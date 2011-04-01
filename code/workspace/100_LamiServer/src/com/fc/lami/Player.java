package com.fc.lami;

/**
 * 一个玩家的数据，包含玩家手中的牌，该回合打出的牌
 * @author yagami0079
 *
 */
public class Player {
	public int player_id;
	/** 手中的牌 */
 	CardData card_list[];
 	/** 本回合打出的牌 */
	CardData send_list[];
}
