package com.cell.rpg.io;

import java.io.File;

import com.cell.rpg.RPGObject;


public interface RPGSerializationListener {

	public void onReadComplete(RPGObject object, String xml_file);

	public void onWriteBefore(RPGObject object, String xml_file);

}
