package com.g2d.studio.cell.gameedit;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.gfx.IGraphics;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CImage;
import com.cell.rpg.res.Resource;
import com.cell.util.zip.ZipUtil;
import com.g2d.Tools;
import com.g2d.cell.CellGameEditWrap;
import com.g2d.cell.CellSetResource.ImagesSet;
import com.g2d.cell.CellSetResource.StreamTiles;
import com.g2d.studio.Config;


public class EatBuilder extends Builder
{
	final HashMap<String, String> script_map;

	public EatBuilder() 
	{
		script_map = new HashMap<String, String>(5);
		script_map.put("output.properties", 		CIO.readAllText("/com/g2d/studio/cell/gameedit/output.properties"));
		script_map.put("scene_jpg.script",			CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_jpg.script"));
		script_map.put("scene_jpg_thumb.script",	CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_jpg_thumb.script"));
		script_map.put("scene_png.script",			CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_png.script"));
		
	}
	
	
	public Process openCellGameEdit(File cpj_file) 
	{
		return CellGameEditWrap.openCellGameEdit(Config.CELL_GAME_EDIT_CMD, cpj_file);
	}
	
	public Process buildSprite(File cpj_file_name)
	{
		System.out.println("build sprite : " + cpj_file_name);
		try {
			File output_properties = copyScript(cpj_file_name, "output.properties");
			Process process = CellGameEditWrap.openCellGameEdit(Config.CELL_GAME_EDIT_CMD, cpj_file_name, 
					output_properties.getPath());
			process.waitFor();
			cleanOutput(cpj_file_name);
			return process;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Process buildScene(File cpj_file_name)
	{
		System.out.println("build scene : " + cpj_file_name);
		try {
			File output_properties		= copyScript(cpj_file_name,	"output.properties");
			File scene_jpg_script		= copyScript(cpj_file_name,	"scene_jpg.script");
			File scene_jpg_thumb_script	= copyScript(cpj_file_name,	"scene_jpg_thumb.script");
			File scene_png_script		= copyScript(cpj_file_name,	"scene_png.script");
			
			Process process = CellGameEditWrap.openCellGameEdit(Config.CELL_GAME_EDIT_CMD, cpj_file_name, 
					output_properties.getPath(), 
					scene_jpg_script.getPath(),
					scene_jpg_thumb_script.getPath(),
					scene_png_script.getPath()
					);
			process.waitFor();
			saveSceneThumb(cpj_file_name) ;
			cleanOutput(cpj_file_name);
			return process;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveBuildSpriteBat(File cpj_file_name) {
		String cmd = CUtil.replaceString(Config.CELL_BUILD_SPRITE_CMD, "{file}", cpj_file_name.getName());
		cmd = CUtil.replaceString(cmd, "\\n", "\n");
		CFile.writeText(new File(cpj_file_name.getParentFile(), "build_sprite.bat"), cmd, "UTF-8");
	}

	public void saveBuildSceneBat(File cpj_file_name) {
		String cmd = CUtil.replaceString(Config.CELL_BUILD_SCENE_CMD, "{file}", cpj_file_name.getName());
		cmd = CUtil.replaceString(cmd, "\\n", "\n");
		CFile.writeText(new File(cpj_file_name.getParentFile(), "build_scene.bat"), cmd, "UTF-8");
	}
	
	private void deleteIfExists(File file) {
		if (file != null && file.exists()) {
			if (file.isDirectory()) {
				for (File sub : file.listFiles()) {
					deleteIfExists(sub);
				}
			}
			file.delete();
		}
	}
	
	private void saveSceneThumb(File cpj_file_name) {
		try {
			File jpg = new File(cpj_file_name.getParentFile(), "jpg.jpg");
			if (jpg.exists()) {
				BufferedImage src = Tools.readImage(jpg.getPath());
				BufferedImage tag = new BufferedImage(
						(int)(src.getWidth() *Config.CELL_BUILD_SCENE_THUMB_SCALE), 
						(int)(src.getHeight()*Config.CELL_BUILD_SCENE_THUMB_SCALE), 
						BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(
						src, 0, 0, 
						tag.getWidth(),
						tag.getHeight(), null);
				Tools.writeImage(
						new File(cpj_file_name.getParentFile(), "thumb.jpg").getPath(), 
						"jpg", tag);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	private void cleanOutput(File cpj_file_name) 
	{
		try
		{
			deleteIfExists(new File(cpj_file_name.getParentFile(), "_script"));
			deleteIfExists(new File(cpj_file_name.getParentFile(), "scene_jpg_thumb.conf"));
			deleteIfExists(new File(cpj_file_name.getParentFile(), "png.jpg"));
			deleteIfExists(new File(cpj_file_name.getParentFile(), "jpg.jpg"));
			
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
	
	private File pakFiles(File dir, String suffix, File out)
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
	
	private File copyScript(File cpj_file, String script_name)
	{
		File script_dir = new File(cpj_file.getParent(), "_script");
		if (!script_dir.exists()) {
			script_dir.mkdirs();
		}
		File script_file = new File(script_dir, script_name);
		CFile.writeText(script_file, script_map.get(script_name), "UTF-8");
		return script_file;
	}

	@Override
	public StreamTiles createResource(ImagesSet img, Resource resource) {
		return new StreamTypeTiles(img, resource);
	}

	/**
	 * 根据图片组名字确定读入jpg或png
	 * @author WAZA
	 */
	public static class StreamTypeTiles extends StreamTiles
	{
		public StreamTypeTiles(ImagesSet img, Resource resource) {
			super(img, resource);
		}
		
		@Override
		protected void initImages() 
		{
			try {
				// 根据tile的类型来判断读取何种图片
				if (img.Name.equals("png") || img.Name.equals("jpg")) {
					if (loadZipImages()) {
						return;
					}
					if (loadSetImages()) {
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				super.initImages();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		protected void drawLoading(IGraphics g, int x, int y, int w, int h) {
		}
		
		protected boolean loadSetImages() {
			try{
				for (int i=0; i<images.length; i++){
					if (img.ClipsW[i]>0 && img.ClipsH[i]>0){
						byte[] idata = set.loadRes("set/"+img.Name+"/"+i+"."+img.Name);
						images[i] = new CImage(new ByteArrayInputStream(idata));
					}
				}
				return true;
			} catch (Exception err) {
				err.printStackTrace();
				return false;
			}
		}
		
		protected boolean loadZipImages() {
			byte[] zipdata = set.loadRes(img.Name+".zip");
			if (zipdata != null) {
				Map<String, ByteArrayInputStream> files = ZipUtil.unPackFile(new ByteArrayInputStream(zipdata));
				for (int i = 0; i < images.length; i++) {
					if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
						ByteArrayInputStream idata = files.get(i+"."+img.Name);
						images[i] = new CImage(idata);
					}
				}
				return true;
			}
			return false;
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------
	
	public static void main(String[] args)
	{
		System.err.close();
		System.out.close();
		CAppBridge.init();
		Builder builder = Builder.setBuilder(EatBuilder.class.getName());
		if (args != null && args.length > 1) {
			String arg_0 = args[0].toLowerCase().trim();
			String arg_1 = args[1].toLowerCase().trim();
			String arg_2 = args[2].toLowerCase().trim();
			Config.load(Config.class, arg_2);
			if (arg_1.equals("scene")) {
				builder.buildScene(new File(arg_0));
			} else {
				builder.buildSprite(new File(arg_0));
			}
		}
	}
}
