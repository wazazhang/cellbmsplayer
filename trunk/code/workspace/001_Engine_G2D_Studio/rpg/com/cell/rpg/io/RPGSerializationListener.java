package com.cell.rpg.io;

import java.io.File;

import com.cell.rpg.RPGObject;


public interface RPGSerializationListener {

	/**
	 * 最后添加的将被最后执行
	 * @param object
	 * @param xml_file
	 */
	public void onReadComplete(RPGObject object, String xml_file);

	/**
	 * 最后添加的将被最先执行
	 * @param object
	 * @param xml_file
	 */
	public void onWriteBefore(RPGObject object, String xml_file);

}
