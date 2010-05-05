package com.g2d.studio.cell.gameedit;

import java.io.File;

import com.cell.rpg.res.Resource;
import com.g2d.cell.CellSetResource.ImagesSet;
import com.g2d.cell.CellSetResource.StreamTiles;

public abstract class Builder 
{
	public abstract Process openCellGameEdit(File cpj_file);
	
	public abstract Process buildSprite(File cpj_file_name);
	
	public abstract Process buildScene(File cpj_file_name);
	
	public abstract StreamTiles createResource(ImagesSet img, Resource resource);
	
	public abstract void saveBuildSpriteBat(File cpj_file_name);

	public abstract void saveBuildSceneBat(File cpj_file_name);
	
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
