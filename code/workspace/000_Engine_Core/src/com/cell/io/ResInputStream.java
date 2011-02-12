package com.cell.io;

import java.io.InputStream;

/**
 * <pre>
 * 比如
 * res://www.xxx.com/web/ws_tw/index.properties
 * ftp://www.xxx.com/web/ws_tw/index.properties
 * </pre>
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

