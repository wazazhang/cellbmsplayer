package com.fc.lami;

/**
 * 桌面上的牌
 * @author yagami0079
 *
 */
public class Desktop {
	/** 桌面牌矩阵 */
	CardData matrix[][] = new CardData[10][26];
	/** 玩家操作前备份 */
	CardData matrix_old[][] = new CardData[10][26];
}
