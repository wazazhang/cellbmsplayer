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

public class Builder 
{
	static HashMap<String, String> script_map;

	public static Process openCellGameEdit(File cpj_file) 
	{
		return CellGameEditWrap.openCellGameEdit(Config.CELL_GAME_EDIT_CMD, cpj_file);
	}
	
	public static Process buildSprite(File cpj_file_name)
	{
		System.out.println("build sprite : " + cpj_file_name);
		try {
			File output_properties = copyScript(cpj_file_name, "output.properties");
			Process process = CellGameEditWrap.openCellGameEdit(Config.CELL_GAME_EDIT_CMD, cpj_file_name, 
					output_properties.getPath());
			process.waitFor();
			cleanOutput(cpj_file_name);
			String cmd = CUtil.replaceString(Config.CELL_BUILD_SPRITE_CMD, "{file}", cpj_file_name.getName());
			cmd = CUtil.replaceString(cmd, "\\n", "\n");
			CFile.writeText(new File(cpj_file_name.getParentFile(), "build_sprite.bat"), cmd, "UTF-8");
			return process;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Process buildScene(File cpj_file_name)
	{
		System.out.println("build scene : " + cpj_file_name);
		try {
			File output_properties	= copyScript(cpj_file_name,	"output.properties");
			File scene_jpg_script	= copyScript(cpj_file_name,	"scene_jpg.script");
			File scene_png_script	= copyScript(cpj_file_name,	"scene_png.script");
			Process process = CellGameEditWrap.openCellGameEdit(Config.CELL_GAME_EDIT_CMD, cpj_file_name, 
					output_properties.getPath(), 
					scene_jpg_script.getPath(),
					scene_png_script.getPath()
					);
			process.waitFor();
			cleanOutput(cpj_file_name);
			String cmd = CUtil.replaceString(Config.CELL_BUILD_SCENE_CMD, "{file}", cpj_file_name.getName());
			cmd = CUtil.replaceString(cmd, "\\n", "\n");
			CFile.writeText(new File(cpj_file_name.getParentFile(), "build_scene.bat"), cmd, "UTF-8");
			return process;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static private void deleteIfExists(File file) {
		if (file != null && file.exists()) {
			if (file.isDirectory()) {
				for (File sub : file.listFiles()) {
					deleteIfExists(sub);
				}
			}
			file.delete();
		}
	}
	
	static private void cleanOutput(File cpj_file_name) 
	{
		try
		{
			deleteIfExists(new File(cpj_file_name.getParentFile(), "_script"));
			File output = new File(cpj_file_name.getParentFile(), "output");
			if (output.exists())
			{
				deleteIfExists(new File(output, "jpg.png"));
				deleteIfExists(new File(output, "png.png"));
				deleteIfExists(new File(output, "scene_graph.conf"));
				deleteIfExists(new File(output, "scene_jpg.conf"));
				deleteIfExists(new File(output, "scene_png.conf"));
				
				File set = new File(output, "set");
				if (set.exists())
				{
					{
						File jpg	= new File(set, "jpg");
						if (jpg.exists() && jpg.isDirectory()) {
							CFile.deleteFiles(jpg, ".png");
							pakFiles(jpg, ".jpg", new File(output, "jpg.zip"));
							CFile.deleteFiles(jpg, ".jpg");
						}
					} {
						File png	= new File(set, "png");
						if (png.exists() && png.isDirectory()) {
							CFile.deleteFiles(png, ".jpg");
							pakFiles(png, ".png", new File(output, "png.zip"));
							CFile.deleteFiles(png, ".png");
						}
					}
					deleteIfExists(set);
				}
			}
		}
		catch(Exception err){
			err.printStackTrace();
		}
	}
	
	static private File pakFiles(File dir, String suffix, File out)
	{
		try {
			ArrayList<File> packs = new ArrayList<File>();
			for (File file : dir.listFiles()) {
				if (file.getName().toLowerCase().endsWith(suffix)) {
					packs.add(file);
				}
			}
			if (!packs.isEmpty()) {
				ByteArrayOutputStream baos = ZipUtil.packFiles(packs);
				CFile.wirteData(out, baos.toByteArray());
				baos.close();
				return out;
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return out;
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
			script_map.put("scene_jpg.script",		CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_jpg.script"));
			script_map.put("scene_png.script",		CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_png.script"));
		}
	}
	
	
	public static void main(String[] args)
	{
		System.err.close();
		System.out.close();
//		build(null);
		CAppBridge.init();
		if (args != null && args.length > 1) {
			String arg_0 = args[0].toLowerCase().trim();
			String arg_1 = args[1].toLowerCase().trim();
			if (arg_1.equals("scene")) {
				buildScene(new File(arg_0));
			} else {
				buildSprite(new File(arg_0));
			}
		}
		
//		openCellGameEdit(new File("D:\\EatWorld\\trunk\\eatworld\\data\\edit\\resource\\scene\\scene_000000\\scene.cpj"));
//		buildScene(new File("D:\\EatWorld\\trunk\\eatworld\\data\\edit\\resource\\scene\\scene_000000\\scene.cpj"));
	}
}
