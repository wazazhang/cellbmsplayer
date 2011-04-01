package com.fc.lami;

import com.net.flash.message.FlashMessage;

/**
 * 桌面上的牌
 * @author yagami0079
 *
 */
public class Desktop extends FlashMessage
{
	/** 桌面牌矩阵 */
	public CardData matrix[][] = new CardData[10][26];
	/** 玩家操作前备份 */
	public CardData matrix_old[][] = new CardData[10][26];
}
