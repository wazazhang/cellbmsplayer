package com.g2d.studio.cell.gameedit;

import java.io.File;
import java.util.HashMap;

import com.cell.CIO;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.g2d.cell.CellGameEditWrap;
import com.g2d.studio.Config;

public class Builder 
{
	static HashMap<String, String> script_map;
	static String build_scene_bat;
	static String build_sprite_bat;

	public static Process openCellGameEdit(File cpj_file) 
	{
		return CellGameEditWrap.openCellGameEdit(Config.CELL_GAME_EDIT_CMD, cpj_file);
	}
	
	public static Process buildSprite(File cpj_file_name)
	{
		try {
			File output_properties = copyScript(cpj_file_name, "output.properties");
			Process process = CellGameEditWrap.openCellGameEdit(Config.CELL_GAME_EDIT_CMD, cpj_file_name, 
					output_properties.getPath());
			process.waitFor();
			CFile.writeText(new File(cpj_file_name.getParentFile(), "build_sprite.bat"), build_sprite_bat, "UTF-8");
			return process;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Process buildScene(File cpj_file_name)
	{
		try {
			File output_properties	= copyScript(cpj_file_name,	"output.properties");
			File scene_graph_script	= copyScript(cpj_file_name,	"scene_graph.script");
			File scene_jpg_script	= copyScript(cpj_file_name,	"scene_jpg.script");
			File scene_png_script	= copyScript(cpj_file_name,	"scene_png.script");
			Process process = CellGameEditWrap.openCellGameEdit(Config.CELL_GAME_EDIT_CMD, cpj_file_name, 
					output_properties.getPath(), 
					scene_graph_script.getPath(),
					scene_jpg_script.getPath(),
					scene_png_script.getPath()
					);
			process.waitFor();
			cleanOutput(cpj_file_name);
			CFile.writeText(new File(cpj_file_name.getParentFile(), "build_scene.bat"), build_scene_bat, "UTF-8");
			return process;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static private void cleanOutput(File cpj_file_name) 
	{
		try{
			File output		= new File(cpj_file_name.getParentFile(), "output");
			{
				File jpg_png	= new File(output, "jpg.png");
				if (jpg_png.exists()) {
					jpg_png.delete();
				}
				File png_png	= new File(output, "png.png");
				if (png_png.exists()) {
					png_png.delete();
				}
				File set		= new File(output, "set");
				{
					File jpg	= new File(set, "jpg");
					for (File _png : jpg.listFiles()) {
						if (_png.getName().toLowerCase().endsWith(".png")) {
							_png.delete();
						}
					}
					File png	= new File(set, "png");
					for (File _jpg : png.listFiles()) {
						if (_jpg.getName().toLowerCase().endsWith(".jpg")) {
							_jpg.delete();
						}
					}
				}
			}
		}catch(Exception err){
			err.printStackTrace();
		}
	}

	static private File copyScript(File cpj_file, String script_name)
	{
		init();
		File script_dir = new File(cpj_file.getParent(), "_script");
		if (!script_dir.exists()) {
			script_dir.mkdirs();
		}
		File script_file = new File(script_dir, script_name);
		CFile.writeText(script_file, script_map.get(script_name), "UTF-8");
		return script_file;
	}

	static private void init() 
	{
		if (script_map == null) {
			script_map = new HashMap<String, String>(5);
			script_map.put("output.properties", 	CIO.readAllText("/com/g2d/studio/cell/gameedit/output.properties"));
			script_map.put("scene_graph.script",	CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_graph.script"));
			script_map.put("scene_jpg.script",		CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_jpg.script"));
			script_map.put("scene_png.script",		CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_png.script"));
			build_scene_bat		= CIO.readAllText("/com/g2d/studio/cell/gameedit/build_scene.bat");
			build_sprite_bat	= CIO.readAllText("/com/g2d/studio/cell/gameedit/build_sprite.bat");
		}
	}
	
	
	public static void main(String[] args)
	{
//		build(null);
		CAppBridge.init();
//		openCellGameEdit(new File("D:\\EatWorld\\trunk\\eatworld\\data\\edit\\resource\\scene\\scene_000000\\scene.cpj"));
		buildScene(new File("D:\\EatWorld\\trunk\\eatworld\\data\\edit\\resource\\scene\\scene_000000\\scene.cpj"));
	}
}
