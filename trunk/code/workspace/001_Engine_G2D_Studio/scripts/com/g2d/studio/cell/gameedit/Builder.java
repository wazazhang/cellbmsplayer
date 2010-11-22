package com.g2d.studio.cell.gameedit;

import com.g2d.studio.StudioResource;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.io.File;

public abstract class Builder 
{
	public abstract Process openCellGameEdit(File cpj_file);
	
	public abstract Process buildSprite(File cpj_file_name);
	
	public abstract Process buildScene(File cpj_file_name);
	
	public abstract StudioResource createResource(com.g2d.studio.io.File cpj_file);
	
	public abstract com.g2d.studio.io.File getCPJFile(com.g2d.studio.io.File file, CPJResourceType res_type);
	
//	public abstract void saveBuildSpriteBat(File cpj_file_name);
//
//	public abstract void saveBuildSceneBat(File cpj_file_name);
	
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
