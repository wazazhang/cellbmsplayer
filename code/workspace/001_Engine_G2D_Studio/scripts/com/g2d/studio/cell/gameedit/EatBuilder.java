package com.g2d.studio.cell.gameedit;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.gameedit.StreamTiles;
import com.cell.gameedit.object.ImagesSet;
import com.cell.gameedit.output.OutputProperties;
import com.cell.gfx.IGraphics;
import com.cell.io.BigIODeserialize;
import com.cell.io.BigIOSerialize;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CImage;
import com.cell.util.Pair;
import com.cell.util.zip.ZipUtil;
import com.g2d.Tools;
import com.g2d.cell.CellGameEditWrap;
import com.g2d.studio.Config;
import com.g2d.studio.StudioResource;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.io.file.FileIO;


public class EatBuilder extends Builder
{
	final HashMap<String, String> script_map;

	public EatBuilder() 
	{
		script_map = new HashMap<String, String>(5);
		script_map.put("output.properties", 		CIO.readAllText("/com/g2d/cell/output.properties"));
		script_map.put("scene_jpg.script",			CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_jpg.script"));
		script_map.put("scene_jpg_thumb.script",	CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_jpg_thumb.script"));
		script_map.put("scene_png.script",			CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_png.script"));
	}
	
	private java.io.File getLocalFile(com.g2d.studio.io.File cpj_file) {
		return new File(cpj_file.getPath());
	}
	
	public Process openCellGameEdit(com.g2d.studio.io.File cpj_file) 
	{
		return CellGameEditWrap.openCellGameEdit(
				Config.CELL_GAME_EDIT_CMD, 
				getLocalFile(cpj_file)
				);
	}
	
	public Process buildSprite(com.g2d.studio.io.File cpj_file)
	{
		System.out.println("build sprite : " + cpj_file.getPath());
		File cpj_file_name = getLocalFile(cpj_file);
		try {
			File output_properties = copyScript(cpj_file_name, "output.properties");
			Process process = CellGameEditWrap.openCellGameEdit(Config.CELL_GAME_EDIT_CMD, cpj_file_name, 
					output_properties.getPath());
			process.waitFor();
			cleanOutput(cpj_file_name, false);
			return process;
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			saveBuildSpriteBat(cpj_file_name);
		}
		return null;
	}
	
	public Process buildScene(com.g2d.studio.io.File cpj_file)
	{
		System.out.println("build scene : " + cpj_file.getPath());
		File cpj_file_name = getLocalFile(cpj_file);
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
			cleanOutput(cpj_file_name, true);
			return process;
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			saveBuildSceneBat(cpj_file_name);
		}
		return null;
	}

	void saveBuildSpriteBat(File cpj_file_name) {
		String cmd = CUtil.replaceString(Config.CELL_BUILD_SPRITE_CMD, "{file}", cpj_file_name.getName());
		cmd = CUtil.replaceString(cmd, "\\n", "\n");
		CFile.writeText(new File(cpj_file_name.getParentFile(), "build_sprite.bat"), cmd, "UTF-8");
	}

	void saveBuildSceneBat(File cpj_file_name) {
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
	
	private void cleanOutput(File cpj_file_name, boolean is_scene) 
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
							if (is_scene) {
								pakPngMasks	(png, ".png", new File(output, "png.zip"));
							} else {
								pakFiles	(png, ".png", new File(output, "png.zip"));
							}
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
	
	private File pakPngMasks(File dir, String suffix, File out) {
		try {
			ArrayList<Pair<ByteArrayOutputStream, String>> packs = new ArrayList<Pair<ByteArrayOutputStream, String>>();
			for (File file : dir.listFiles()) {
				if (file.getName().toLowerCase().endsWith(suffix)) {
					try {
						BufferedImage bi = Tools.readImage(file.getPath());
						if (bi != null) {
							int [] rgb = new int[bi.getWidth() * bi.getHeight()];
							bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), rgb, 0, bi.getWidth());
							ByteArrayOutputStream baos = new ByteArrayOutputStream(rgb.length / 8 + 10);
							BigIOSerialize.putInt(baos, bi.getWidth());
							BigIOSerialize.putInt(baos, bi.getHeight());
							for (int i = 0; i < rgb.length; i+=8) {
								byte state = 0;
								for (int s = 0; s < 8; s ++) {
									int index = i + s;
									if (index < rgb.length && ((rgb[index] & 0xff000000) != 0)) {
										state += (0x01 << s);
									}
								}
								BigIOSerialize.putByte(baos, state);
							}
							packs.add(new Pair<ByteArrayOutputStream, String>(baos, file.getName()));
						}
					} catch (Throwable tx) {
						tx.printStackTrace();
					}
				}
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zip_out = new ZipOutputStream(baos);
			try{
				for (Pair<ByteArrayOutputStream, String> pak : packs) {
					ZipEntry entry = new ZipEntry(pak.getValue());
					try{
						entry.setTime(0);
						zip_out.putNextEntry(entry);
						zip_out.write(pak.getKey().toByteArray());
//						System.out.println(pak.getValue());
					} catch(Exception err){
						err.printStackTrace();
					}
				}
			} finally {
				try {
					zip_out.close();
				} catch (IOException e) {}
				try {
					baos.close();
				} catch (IOException e) {}
			}
			CFile.wirteData(out, baos.toByteArray());

		} catch (Exception err) {
			err.printStackTrace();
		}
		return out;
	
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
				ByteArrayOutputStream baos = ZipUtil.packFiles(packs, 0);
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

//	-------------------------------------------------------------------------------------------------------------------
	
	@Override
	public StudioResource createResource(com.g2d.studio.io.File cpj_file) {
		try {
			return new EatResource(getOutputFile(cpj_file));
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	@Override
	public com.g2d.studio.io.File getCPJFile(
			com.g2d.studio.io.File file,
			CPJResourceType resType) 
	{
		switch (resType) {
		case ACTOR:
			if (file.getName().startsWith("actor_")) {
				return file.getChildFile("actor.cpj");
			}
			break;
		case AVATAR:
			if (file.getName().startsWith("item_")) {
				return file.getChildFile("item.cpj");
			}
			break;
		case EFFECT:
			if (file.getName().startsWith("effect_")) {
				return file.getChildFile("effect.cpj");
			}
			break;
		case WORLD:
			if (file.getName().startsWith("scene_")) {
				return file.getChildFile("scene.cpj");
			}
			break;
		}
		return null;
	}
	
	private static com.g2d.studio.io.File getOutputFile(com.g2d.studio.io.File cpj_file)
	{
		try{
			String cpj_file_name = cpj_file.getName().toLowerCase().replace("cpj", "");
			com.g2d.studio.io.File o = cpj_file.getParentFile().getChildFile("output").getChildFile(
					cpj_file_name+"properties");
			return o;
		} catch(Exception err){}
		return null;
	}
	
//	-----------------------------------------------------------------------------------------------------------
	
	public class EatResource extends StudioResource
	{
		public EatResource(com.g2d.studio.io.File output) throws Exception {
			super(new OutputProperties(output.getPath()), output.getPath());
		}
		
		@Override
		protected StreamTiles getLocalImage(ImagesSet img) throws IOException {
			StreamTiles tiles = new StreamTypeTiles(img, this);
			return tiles;
		}
		
		@Override
		protected StreamTiles getStreamImage(ImagesSet img) throws IOException {
			StreamTiles tiles = new StreamTypeTiles(img, this);
			return tiles;
		}

		

	}


	/**
	 * 根据图片组名字确定读入jpg或png
	 * @author WAZA
	 */
	public static class StreamTypeTiles extends StreamTiles
	{
		public StreamTypeTiles(ImagesSet img, StudioResource resource) {
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
						byte[] idata = set.getOutput().loadRes("set/"+img.Name+"/"+i+"."+img.Name);
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
			boolean is_png_mask = ((OutputProperties)set.getOutput()).path.endsWith("scene.properties");
			if (is_png_mask) {
				is_png_mask = img.Name.startsWith("png");
			}
			byte[] zipdata = set.getOutput().loadRes(img.Name+".zip");
			if (zipdata != null) {
				Map<String, ByteArrayInputStream> files = ZipUtil.unPackFile(new ByteArrayInputStream(zipdata));
				for (int i = 0; i < images.length; i++) {
					if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
						ByteArrayInputStream idata = files.get(i+"."+img.Name);
						try { 
							if (is_png_mask) {
								images[i] = new CImage(createMaskImage(idata));
							} else {
								images[i] = new CImage(Tools.readImage(idata));
							}
						} catch (Exception err) {
							err.printStackTrace();
						}
					}
				}
				return true;
			}
			return false;
		}
	}
	
	public static BufferedImage createMaskImage(ByteArrayInputStream idata) 
	{
		try {
			int width	= BigIODeserialize.getInt(idata);
			int height	= BigIODeserialize.getInt(idata);
			BufferedImage buffer = Tools.createImage(width, height);
			int len = width * height;
			for (int i = 0; i < len; i++) {
				byte state = BigIODeserialize.getByte(idata);
				for (int s = 0; s < 8; s++) {
					int index = i * 8 + s;
					int x = index % width;
					int y = index / width;
					int b = ((0x00ff & state) >> s) & 0x01;
					if (b != 0) {
						if (x < width && y < height) {
							buffer.setRGB(x, y, 0xff000000);
						}
					}
				}
			}
			return buffer;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
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
			FileIO io = new FileIO();
			if (arg_1.equals("scene")) {
				builder.buildScene(io.createFile(arg_0));
			} else {
				builder.buildSprite(io.createFile(arg_0));
			}
		}
		
//		byte state = 0;
//		for (int i=0; i<100; i++) {
//			if (i%8==0) {
//				state = 0;
//			}
//			state = (byte)((state << 1) | 1);
//			System.out.println(Integer.toString((state & 0x00ff), 2));
//			
//		}
	}
}
