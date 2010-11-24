package com.g2d.studio.cell.gameedit;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.gameedit.Output;
import com.cell.gameedit.StreamTiles;
import com.cell.gameedit.object.ImagesSet;
import com.cell.gameedit.object.MapSet;
import com.cell.gameedit.object.SpriteSet;
import com.cell.gameedit.object.TableSet;
import com.cell.gameedit.object.WorldSet;
import com.cell.gameedit.object.WorldSet.RegionObject;
import com.cell.gameedit.object.WorldSet.WaypointObject;
import com.cell.gameedit.output.OutputProperties;
import com.cell.gameedit.output.OutputPropertiesDir;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IImages;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CMap;
import com.cell.gfx.game.CSprite;
import com.cell.gfx.game.CWayPoint;
import com.cell.io.BigIODeserialize;
import com.cell.io.BigIOSerialize;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CImage;
import com.cell.script.js.JSManager;
import com.cell.util.Pair;
import com.cell.util.PropertyGroup;
import com.cell.util.zip.ZipUtil;
import com.g2d.Tools;
import com.g2d.cell.CellGameEditWrap;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.StudioResource;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.io.file.FileIO;


public class EatBuilder extends Builder
{
	final HashMap<String, String> script_map;

	JSManager external_script_manager = new JSManager();
	
	public EatBuilder() 
	{
		script_map = new HashMap<String, String>(5);
		script_map.put("output.properties", 		CIO.readAllText("/com/g2d/studio/cell/gameedit/output.properties"));
		script_map.put("scene_jpg.script",			CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_jpg.script"));
		script_map.put("scene_jpg_thumb.script",	CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_jpg_thumb.script"));
		script_map.put("scene_png.script",			CIO.readAllText("/com/g2d/studio/cell/gameedit/scene_png.script"));
	}
	
	private java.io.File getLocalFile(com.g2d.studio.io.File cpj_file) {
		try {
			return new File(cpj_file.getPath()).getCanonicalFile();
		} catch (Exception err) {
			err.printStackTrace();
			return new File(cpj_file.getPath());
		}
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

	public void openCellGameEdit(com.g2d.studio.io.File cpj_file) 
	{
		CellGameEditWrap.openCellGameEdit(
				Config.CELL_GAME_EDIT_CMD, 
				getLocalFile(cpj_file)
				);
	}
	
	public void buildSprite(com.g2d.studio.io.File cpj_file)
	{
		System.out.println("build sprite : " + cpj_file.getPath());
		File cpj_file_name = getLocalFile(cpj_file);
		try {
			File output_properties = copyScript(cpj_file_name, "output.properties");
			Process process = CellGameEditWrap.openCellGameEdit(Config.CELL_GAME_EDIT_CMD, cpj_file_name, 
					output_properties.getPath());
			process.waitFor();
			process.destroy();
			output(cpj_file_name, false);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			saveBuildSpriteBat(cpj_file_name);
		}
	}
	
	public void buildScene(com.g2d.studio.io.File cpj_file)
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
			process.destroy();
			output(cpj_file_name, true);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			saveBuildSceneBat(cpj_file_name);
		}
	}

	protected void saveBuildSpriteBat(File cpj_file_name) {
		String cmd = CUtil.replaceString(Config.CELL_BUILD_SPRITE_CMD, "{file}", cpj_file_name.getName());
		cmd = CUtil.replaceString(cmd, "\\n", "\n");
		CFile.writeText(new File(cpj_file_name.getParentFile(), "build_sprite.bat"), cmd, "UTF-8");
	}

	protected void saveBuildSceneBat(File cpj_file_name) {
		String cmd = CUtil.replaceString(Config.CELL_BUILD_SCENE_CMD, "{file}", cpj_file_name.getName());
		cmd = CUtil.replaceString(cmd, "\\n", "\n");
		CFile.writeText(new File(cpj_file_name.getParentFile(), "build_scene.bat"), cmd, "UTF-8");
	}
	
//	---------------------------------------------------------------------------------------------------------------------------
//
//	---------------------------------------------------------------------------------------------------------------------------
	
	protected void output(File cpj_file_name, boolean is_scene) 
	{
		try {
			File scfile = new File(
					Config.getRoot() + "/" +
					Config.CELL_BUILD_OUTPUT_SCRIPT_FILE).getCanonicalFile();
			
			if (scfile.exists()) 
			{
				BuildExternalScript script = external_script_manager.getInterface(
						scfile.getCanonicalPath(), 
						BuildExternalScript.class);
				if (script != null)
				{
					File dir = cpj_file_name.getParentFile().getCanonicalFile();
					BuildProcess bp = new BuildProcess(dir);
					script.output(bp, dir, cpj_file_name.getCanonicalFile());
				}
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		
//		try
//		{
//			File output = new File(cpj_file_name.getParentFile(), "output");
//
//			saveSceneThumb(cpj_file_name, "thumb.jpg");
//			
//			if (output.exists())
//			{
//				File set = new File(output, "set");
//				File jpg	= new File(set, "jpg");
//				File png	= new File(set, "png");
//				
//				try 
//				{
//					evalExternalScript(cpj_file_name);
//					
//					if (set.exists() && is_scene)
//					{
//						if (jpg.exists() && jpg.isDirectory()) {
//							pakFiles(jpg, ".jpg", new File(output, "jpg.zip"));
//						}
//						if (png.exists() && png.isDirectory()) {
//							pakPngMasks	(png, ".png", new File(output, "png.zip"));
//						}
//					}
//					
//					outputZipPack(cpj_file_name, is_scene);
//					
//				} finally {
////					deleteIfExists(set);
////					deleteIfExists(new File(output, "jpg.png"));
////					deleteIfExists(new File(output, "png.png"));
////					deleteIfExists(new File(output, "scene_graph.conf"));
////					deleteIfExists(new File(output, "scene_jpg.conf"));
////					deleteIfExists(new File(output, "scene_png.conf"));
//				}
//			}
//		} catch(Exception err){
//			err.printStackTrace();
//		} finally {
////			deleteIfExists(new File(cpj_file_name.getParentFile(), "_script"));
////			deleteIfExists(new File(cpj_file_name.getParentFile(), "scene_jpg_thumb.conf"));
////			deleteIfExists(new File(cpj_file_name.getParentFile(), "png.jpg"));
////			deleteIfExists(new File(cpj_file_name.getParentFile(), "jpg.jpg"));
//		}
	}

//	private void outputZipPack(File cpj_file_name, boolean is_scene)
//	{
//		File root = cpj_file_name.getParentFile();
//		LinkedHashMap<String, byte[]> entrys = new LinkedHashMap<String, byte[]>();
//		File outfile = new File(root, "output/"+cpj_file_name.getName().replace(".cpj", ".properties"));
//		entrys.put(outfile.getName(), CFile.readData(outfile));
//		if (is_scene) {
//			entrys.put("thumb.jpg", CFile.readData(new File(root, "thumb.jpg")));
//			getEntrys(new File(root, "output/set/jpg"), ".jpg",  "jpg/", entrys);
//			getEntrys(new File(root, "output/set/png"), ".mask", "png/", entrys);
//		} else {
//			getEntrys(root, "icon_\\w+.png",  "", entrys);
//			getEntrys(new File(root, "output"), ".png",  "", entrys);
//		}
//		ByteArrayOutputStream pak_baos = new ByteArrayOutputStream();
//		ZipOutputStream pak_zip = new ZipOutputStream(pak_baos);
//		try {
//			for (Entry<String, byte[]> e : entrys.entrySet()) {
//				ZipEntry entry = new ZipEntry(e.getKey());
//				try {
//					entry.setTime(0);
//					pak_zip.putNextEntry(entry);
//					pak_zip.write(e.getValue());
////					System.out.println("\tentry: " + entry.getName());
//				} catch(Exception err){
//					err.printStackTrace();
//				}
//			}
//		} catch (Exception err) {
//			err.printStackTrace();
//		} finally {
//			try {
//				pak_zip.close();
//			} catch (IOException e) {}
//		}
//		File pak_out = new File(
//				cpj_file_name.getParentFile().getParentFile(), 
//				cpj_file_name.getParentFile().getName()+".pak");
//		CFile.writeData(pak_out, pak_baos.toByteArray());
//	}
//
//	private void evalExternalScript(File cpj_file_name)
//	{
//		try {
//			File scfile = new File(
//					Config.getRoot() + "/" +
//					Config.CELL_BUILD_OUTPUT_SCRIPT_FILE).getCanonicalFile();
//			
//			if (scfile.exists()) 
//			{
//				BuildExternalScript script = external_script_manager.getInterface(
//						scfile.getCanonicalPath(), 
//						BuildExternalScript.class);
//				if (script != null)
//				{
//					File dir = cpj_file_name.getParentFile().getCanonicalFile();
//					BuildProcess bp = new BuildProcess(dir);
//					script.output(bp, dir, cpj_file_name.getCanonicalFile());
//				}
//			}
//		} catch (Throwable ex) {
//			ex.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 导出一份缩略图
//	 * @param cpj_file_name
//	 */
//	private byte[] saveSceneThumb(File cpj_file_name, String thumb_name) {
//		try {
//			File jpg = new File(cpj_file_name.getParentFile(), "jpg.jpg");
//			if (jpg.exists()) {
//				BufferedImage src = Tools.readImage(jpg.getPath());
//				BufferedImage tag = new BufferedImage(
//						(int)(src.getWidth() *Config.CELL_BUILD_SCENE_THUMB_SCALE), 
//						(int)(src.getHeight()*Config.CELL_BUILD_SCENE_THUMB_SCALE), 
//						BufferedImage.TYPE_INT_RGB);
//				tag.getGraphics().drawImage(
//						src, 0, 0, 
//						tag.getWidth(),
//						tag.getHeight(), null);
//				File thumb_file = new File(cpj_file_name.getParentFile(), thumb_name);
//				byte[] data = Tools.writeImage(
//						thumb_file.getPath(), 
//						"jpg", tag);
//				return data;
//			}
//		} catch (Exception err) {
//			err.printStackTrace();
//		}
//		return null;
//	}
//	
//	/**
//	 * 转换所有PNG图片为二进制点格式
//	 * @param dir
//	 * @param suffix
//	 * @param out
//	 * @return
//	 * @throws Exception
//	 */
//	private ArrayList<Pair<ByteArrayOutputStream, String>> converPngMasks(File dir, String suffix) throws Exception
//	{
//		ArrayList<Pair<ByteArrayOutputStream, String>> packs = new ArrayList<Pair<ByteArrayOutputStream, String>>();
//		for (File file : dir.listFiles()) {
//			if (file.getName().toLowerCase().endsWith(suffix)) {
//				try {
//					BufferedImage bi = Tools.readImage(file.getPath());
//					if (bi != null) {
//						int [] rgb = new int[bi.getWidth() * bi.getHeight()];
//						bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), rgb, 0, bi.getWidth());
//						ByteArrayOutputStream baos = new ByteArrayOutputStream(rgb.length / 8 + 10);
//						BigIOSerialize.putInt(baos, bi.getWidth());
//						BigIOSerialize.putInt(baos, bi.getHeight());
//						for (int i = 0; i < rgb.length; i+=8) {
//							byte state = 0;
//							for (int s = 0; s < 8; s ++) {
//								int index = i + s;
//								if (index < rgb.length && ((rgb[index] & 0xff000000) != 0)) {
//									state += (0x01 << s);
//								}
//							}
//							BigIOSerialize.putByte(baos, state);
//						}
//						packs.add(new Pair<ByteArrayOutputStream, String>(baos, file.getName()));
//						CFile.writeData(new File(dir, file.getName().replace(suffix, ".mask")), baos.toByteArray());
//					}
//				} catch (Throwable tx) {
//					tx.printStackTrace();
//				}
//			}
//		}
//		return packs;
//	}
//	
//	private File pakPngMasks(File dir, String suffix, File out) {
//		try {
//			ArrayList<Pair<ByteArrayOutputStream, String>> packs = converPngMasks(dir, suffix);
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ZipOutputStream zip_out = new ZipOutputStream(baos);
//			try {
//				for (Pair<ByteArrayOutputStream, String> pak : packs) {
//					ZipEntry entry = new ZipEntry(pak.getValue());
//					try {
//						entry.setTime(0);
//						zip_out.putNextEntry(entry);
//						zip_out.write(pak.getKey().toByteArray());
//					} catch(Exception err){
//						err.printStackTrace();
//					}
//				}
//			} finally {
//				try {
//					zip_out.close();
//				} catch (IOException e) {}
//				try {
//					baos.close();
//				} catch (IOException e) {}
//			}
//			CFile.writeData(out, baos.toByteArray());
//
//		} catch (Exception err) {
//			err.printStackTrace();
//		}
//		return out;
//	
//	}
//	
//	private File pakFiles(File dir, String regex, File out)
//	{
//		try 
//		{
//			Pattern pattern = Pattern.compile(regex);
//			
//			ArrayList<File> packs = new ArrayList<File>();
//			for (File file : dir.listFiles()) {
//				if (file.isFile()) {
//					if (pattern.matcher(file.getName()).find()) {
//						packs.add(file);
//					}
//				}
//			}
//			
//			if (!packs.isEmpty()) 
//			{
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				ZipOutputStream zip_out = new ZipOutputStream(baos);
//				try{
//					for (File file : packs) {
//						byte[] data = CFile.readData(file);
//						if (data != null) {
//							ZipEntry entry = new ZipEntry(file.getName());
//							entry.setTime(0);
//							try{
//								zip_out.putNextEntry(entry);
//								zip_out.write(data);
//							} catch(Exception err){
//								err.printStackTrace();
//							}
//						}
//					}
//				} finally {
//					try {
//						zip_out.close();
//					} catch (IOException e) {}
//				}
//				if (out != null) {
//					CFile.writeData(out, baos.toByteArray());
//				}
//				baos.close();
//				return out;
//			}
//		} catch (Exception err) {
//			err.printStackTrace();
//		}
//		return out;
//	}
//
//	
//	private void getEntrys(File dir, String regex, String prefix, Map<String, byte[]> entrys)
//	{
//		if (dir.exists() && dir.isDirectory()) {
//			try {
//				Pattern pattern = Pattern.compile(regex);
//				for (File file : dir.listFiles()) {
//					if (file.isFile()) {
//						if (pattern.matcher(file.getName()).find()) {
//							entrys.put(prefix+file.getName(), CFile.readData(file));
//						}
//					}
//				}
//			} catch (Exception err) {
//				err.printStackTrace();
//			}
//		}
//	}
	
//	-------------------------------------------------------------------------------------------------------------------

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
	
	@Override
	public StudioResource createResource(com.g2d.studio.io.File cpj_file) {
		try {
			Output out = getOutputFile(cpj_file);
			if (out != null) {
				return new EatResource(out, cpj_file.getPath());
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
//	-----------------------------------------------------------------------------------------------------------

	private static Output getOutputFile(com.g2d.studio.io.File cpj_file)
	{
		try{
			com.g2d.studio.io.File pak = cpj_file.getParentFile().getParentFile().getChildFile(
					cpj_file.getParentFile().getName() + ".pak");
//			System.out.println("check : " + pak.getPath());
			if (pak.exists()) {
				return new OutputPack(pak);
			}
		} catch(Exception err){}
		try {
			if (cpj_file.getParentFile().getChildFile("output").exists()) {
				String outname = cpj_file.getName().toLowerCase()
						.replace("cpj", "properties");
				com.g2d.studio.io.File o = cpj_file.getParentFile().getChildFile("output").getChildFile(outname);
//				System.out.println("check : " + o.getPath());
				return new OutputPropertiesDir(o.getPath());
			}
		} catch (Exception err) {
		}
		return null;
	}
	
	static class EatResource extends StudioResource
	{
		public EatResource(Output output, String path) throws Exception {
			super(output, path);
		}
		
		@Override
		protected StreamTiles getLocalImage(ImagesSet img) throws IOException {
			StreamTiles tiles = new OutputDirTiles(img, this);
			return tiles;
		}
		
		@Override
		protected StreamTiles getStreamImage(ImagesSet img) throws IOException {
			StreamTiles tiles = new OutputDirTiles(img, this);
			return tiles;
		}
	}
	
	static class OutputPack extends OutputProperties
	{
		HashMap<String, byte[]> resources;
		com.g2d.studio.io.File 	pak_file;
		
		public OutputPack(com.g2d.studio.io.File pak_file) throws Exception
		{
			super(pak_file.getPath());
			this.pak_file = pak_file;
		
			InputStream fis = pak_file.getInputStream();
			try {
				ZipInputStream zip_in = new ZipInputStream(fis);
				ZipEntry e = zip_in.getNextEntry();
				// 读入基础属性
				byte[] conf_data = ZipUtil.readBytes(zip_in);
				if (conf_data == null) {
					throw new FileNotFoundException(path);
				} else {
					e.getName();
//					System.out.println("unpak : " + e.getName());
				}
				String conf = new String(conf_data, CIO.ENCODING);
				PropertyGroup config = new PropertyGroup(conf, "=");
				super.init(config);
			} finally {
				fis.close();
			}
		}
		
		@Override
		public void dispose() {
			resources = null;
		}
		
		@Override
		public byte[] loadRes(String name) {
			if (resources == null) {
				initPakFiles();
			}
			name = name.replaceAll("\\\\", "/");
			return resources.get(name);
		}
		
		private void initPakFiles()
		{
			resources = new HashMap<String, byte[]>();
			try {
				InputStream fis = pak_file.getInputStream();
				try {
					ZipInputStream zip_in = new ZipInputStream(fis);
					zip_in.getNextEntry();
					try {
						for (ZipEntry e = zip_in.getNextEntry(); e != null; e = zip_in.getNextEntry()) {
							resources.put(e.getName(), ZipUtil.readBytes(zip_in));
						}
					} finally {
						zip_in.close();
					}
				} finally {
					fis.close();
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}


	/**
	 * 根据图片组名字确定读入jpg或png
	 * @author WAZA
	 */
	static class OutputDirTiles extends StreamTiles
	{
		public OutputDirTiles(ImagesSet img, StudioResource resource) {
			super(img, resource);
		}
		
		@Override
		protected void initImages() 
		{
			try {
				// 根据tile的类型来判断读取何种图片
				if (img.Name.equals("png") || img.Name.equals("jpg")) {
					if (set.getOutput() instanceof OutputPack) {
						if (loadPakImages()) {
							return;
						}
					} else {
						if (loadZipImages()) {
							return;
						}
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
		protected void drawLoading(IGraphics g, int x, int y, int w, int h) {}
		
		protected boolean loadPakImages() {
			boolean is_png_mask = img.Name.startsWith("png");
			for (int i = 0; i < images.length; i++) {
				if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
					try {
						if (is_png_mask) {
							images[i] = new CImage(Tools.decodeImageMask(
									set.getOutput().loadRes(img.Name+"/"+i+".png"), 0));
						} else {
							images[i] = new CImage(Tools.readImage(new ByteArrayInputStream(
									set.getOutput().loadRes(img.Name+"/"+i+"."+img.Name))));
						}
					} catch (Exception err) {
//						err.printStackTrace();
						System.err.println("loadPakImages \""+img.Name+"\" tile error : " + i);
					}
				}
			}
			return true;
		}
		
		protected boolean loadZipImages() {
			boolean is_png_mask = img.Name.startsWith("png");
			byte[] zipdata = set.getOutput().loadRes(img.Name+".zip");
			if (zipdata != null) {
				Map<String, ByteArrayInputStream> files = ZipUtil.unPackFile(new ByteArrayInputStream(zipdata));
				for (int i = 0; i < images.length; i++) {
					if (img.ClipsW[i] > 0 && img.ClipsH[i] > 0) {
						ByteArrayInputStream idata = files.get(i+"."+img.Name);
						try { 
							if (is_png_mask) {
								images[i] = new CImage(Tools.decodeImageMask(CIO.readStream(idata), 0));
							} else {
								images[i] = new CImage(Tools.readImage(idata));
							}
						} catch (Exception err) {
//							err.printStackTrace();
							System.err.println("loadZipImages \""+img.Name+"\" tile error : " + i);
						}
					}
				}
				return true;
			}
			return false;
		}
	}
	


	
	
//	-----------------------------------------------------------------------------------------------------------
	
	public static void main(String[] args) throws IOException
	{
//		System.err.close();
//		System.out.close();
		CAppBridge.init();
		Builder builder = Builder.setBuilder(EatBuilder.class.getName());
		if (args != null && args.length > 1) {
			String arg_0 = args[0].toLowerCase().trim();
			String arg_1 = args[1].toLowerCase().trim();
			String arg_2 = args[2].toLowerCase().trim();
			Config.load(arg_2);
			FileIO io = new FileIO();
			if (arg_1.equals("scene")) {
				builder.buildScene(io.createFile(new File(arg_0).getCanonicalPath()));
			} else {
				builder.buildSprite(io.createFile(new File(arg_0).getCanonicalPath()));
			}
		}
	}
}
