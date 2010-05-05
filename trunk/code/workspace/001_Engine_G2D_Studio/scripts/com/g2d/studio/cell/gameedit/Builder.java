package com.g2d.studio.cell.gameedit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.cell.util.zip.ZipUtil;
import com.g2d.cell.CellGameEditWrap;
import com.g2d.studio.Config;

public abstract class Builder 
{
	public abstract Process openCellGameEdit(File cpj_file);
	
	public abstract Process buildSprite(File cpj_file_name);
	
	public abstract Process buildScene(File cpj_file_name);
	

//	----------------------------------------------------------------------------------------------------------
	
	private static Builder instance;
	
	public static Builder setBuilder(String builder_class_name) {
		try {
			Class<?> type = Class.forName(builder_class_name);
			instance = (Builder)type.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	public static Builder getInstance() {
		return instance;
	}

//	----------------------------------------------------------------------------------------------------------
	
	

}
