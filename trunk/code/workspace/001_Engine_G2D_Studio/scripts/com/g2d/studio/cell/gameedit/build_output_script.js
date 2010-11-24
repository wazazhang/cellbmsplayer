/**
getDir()
exec(String)
deleteIfExists(String)
getEntrys(String, String, String)
pakEntrys(Map<String, byte[]>, String)
pakFiles(String, String, String, String)
saveImageThumb(String, String, float)
convertImageMaskEntrys(Map<String, byte[]>)
*/

function output(p, dir, cpj_file_name)
{

	convertImage	(p, dir, cpj_file_name);
	
	createDirOutput	(p, dir, cpj_file_name);
	
	createPakOutput	(p, dir, cpj_file_name);
	
	clean(p);
}

/**压缩图片*/
function convertImage(p, dir, cpj_file_name)
{
	convert = java.io.File(
			dir.getParentFile().getParentFile().getParentFile(),
			"\\sign\\ImageMagick\\convertimg.exe").getCanonicalPath();
	
//	println(convert);
	
	if (cpj_file_name.getName().endsWith("scene.cpj")) 
	{
		p.saveImageThumb("jpg.jpg", "thumb.jpg", 0.1);
		
		p.exec(convert + " thumb.jpg -strip -quality 50 thumb.jpg", 60000);
		
		// 场景只需要压缩JPG地砖
		var jpgs = java.io.File(dir, "output\\set\\jpg").listFiles(); 
		for (i in jpgs) 
		{
			var file = jpgs[i];
			if (file.getName().endsWith(".jpg")) {
				p.exec(convert + " " + file + " -strip -quality 50 " + file, 60000);
			}
		}
	}
	// 主角
	else if (cpj_file_name.getName().endsWith("actor.cpj")) 
	{
		var pngs = java.io.File(dir, "output").listFiles(); 
		for (i in pngs) 
		{
			var file = pngs[i];
			
			if (file.getName().endsWith(".png"))
			{
				if (dir.getName().startsWith("actor_00")) 
				{
					// actor_00* 开头的单位，PNG 8 像素
					p.exec(convert + " " + file + " -strip -quality 95 PNG8:" + file, 60000);
				} 
				else 
				{
					// 其他 PNG 24 半透
					p.exec(convert + " " + file + " -strip " + file, 60000);
				}
			}
		}
	}
	// 道具全部 PNG 8 像素
	else if (cpj_file_name.getName().endsWith("item.cpj")) 
	{
		var pngs = java.io.File(dir, "output").listFiles(); 
		for (i in pngs) 
		{
			var file = pngs[i];
			if (file.getName().endsWith(".png")) {
				p.exec(convert + " " + file + " -strip -quality 95 PNG8:" + file, 60000);
			}
		}
	}
	// 其他 PNG 24 半透
	else 
	{
		var pngs = java.io.File(dir, "output").listFiles(); 
		for (i in pngs) 
		{
			var file = pngs[i];
			if (file.getName().endsWith(".png")) {
				p.exec(convert + " " + file + " -strip " + file, 60000);
			}
		}
	}
}

/**输出老版本资源*/
function createDirOutput(p, dir, cpj_file_name)
{
	if (cpj_file_name.getName().endsWith("scene.cpj")) 
	{
		p.pakFiles			("output\\set\\jpg", ".jpg", "output\\jpg.zip", "");
		
		p.pakImageMaskFiles	("output\\set\\png", ".png", "output\\png.zip", "");
	}
}

/**输出新版本资源*/
function createPakOutput(p, dir, cpj_file_name)
{
	var entrys = p.getEntrys("output", ".properties", "");
	
	if (cpj_file_name.getName().endsWith("scene.cpj")) 
	{
		p.getEntrys(".", "thumb.jpg", "", entrys);
		p.getEntrys("output\\set\\jpg", ".jpg", "jpg/", entrys);
		p.getImageMaskEntrys("output\\set\\png", ".png", "png/", entrys);
	}
	else 
	{
//		p.getEntrys(".", "icon_\\w+.png", "", entrys);
		p.getEntrys("output", ".png", "", entrys);
	}
	
	p.pakEntrys(entrys, "..\\"+dir.getName()+".pak");
}

/**清理临时文件*/
function clean(p)
{
	p.deleteIfExists("output\\set");
	p.deleteIfExists("output\\jpg.png");
	p.deleteIfExists("output\\png.png");
	p.deleteIfExists("output\\scene_graph.conf");
	p.deleteIfExists("output\\scene_jpg.conf");
	p.deleteIfExists("output\\scene_png.conf");

	p.deleteIfExists("_script");
	p.deleteIfExists("scene_jpg_thumb.conf");
	p.deleteIfExists("png.jpg");
	p.deleteIfExists("jpg.jpg");
}