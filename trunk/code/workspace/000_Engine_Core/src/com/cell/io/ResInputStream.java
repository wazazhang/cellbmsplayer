package com.cell.io;

import java.io.InputStream;

/**
 * eg: res://web/ws_tw/index.properties
 * @author WAZA
 *
 */
public abstract class ResInputStream extends InputStream
{
	/**
	 * 得到数据长度
	 * @return
	 */
	abstract public int getLength();
}

