package com.cell.classloader.jcl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;


/**
 * 提供java动态加载的方法，在程序运行时，可将jar包动态加载进程序。
 * @author WAZA
 */
public class JarClassLoader extends ClassLoader
{
	final static String NATIVE_SUFFIX_WINDOWS	= ".dll";
	final static String NATIVE_SUFFIX_MAC_OS	= ".jnilib";
	final static String NATIVE_SUFFIX_LINUX		= ".so";
	final static String NATIVE_SUFFIX;
	static
	{
		String os = System.getProperty("os.name").toLowerCase();
		if (os.startsWith("windows")) {
			NATIVE_SUFFIX = NATIVE_SUFFIX_WINDOWS;
		} else if (os.startsWith("mac")) {
			NATIVE_SUFFIX = NATIVE_SUFFIX_MAC_OS;
		} else {
			NATIVE_SUFFIX = NATIVE_SUFFIX_LINUX;
		}
		System.out.println("current os : " + os);
		System.out.println("current native suffix : " + NATIVE_SUFFIX);
	}
	
	final static public JarClassLoader createJarClassLoader(
			Vector<byte[]> 	resources,
			String 			key,
			boolean 		is_sign_class) throws Exception
	{
		if (key != null) {
			Vector<byte[]> resources_enc = new Vector<byte[]>(resources.size());
			for (byte[] resource : resources) {
				CC enc = new CC(key);
				resources_enc.add(enc.dd(resource));
				System.out.println("decode jar : " + resource.length + " bytes");
			}
			resources = resources_enc;
		}
		
		JarClassLoader classloader = new JarClassLoader();
		
		classloader.is_set_ProtectionDomain	= is_sign_class;
		
		for (byte[] resource : resources)
		{
			// 将byte[]转为JarInputStream
			JarInputStream jar = new JarInputStream(new ByteArrayInputStream(resource));
			
			// 依次获得对应JAR文件中封装的各个被压缩文件的JarEntry
			JarEntry entry;
			while ((entry = jar.getNextJarEntry()) != null)
			{
				String name	= entry.getName();
				String ex	= name.toLowerCase();
				
				// put classes
				if (ex.endsWith(".class")) 
				{
					String class_name = getPrefix(name, ".class").replace('/', '.');
					byte[] data = getResourceData(jar);
					classloader.Classes.put(class_name, data);
				}
				// put windows native
				else if (ex.endsWith(NATIVE_SUFFIX)) 
				{
					String native_name = getPrefix(name, NATIVE_SUFFIX);
					byte[] data = getResourceData(jar);
					classloader.Natives.put(native_name, data);
					System.out.println("get native lib : " + name);
				}
				// other resources
				else
				{
					byte[] data = getResourceData(jar);
					if (name.charAt(0) != '/') {
						name = "/" + name;
					}
					classloader.Resources.put(name, data);
				}
			}
			
		}
		return classloader;
	}

	final static private String getPrefix(String name, String suffix) {
		if (name.endsWith(suffix)) {
			return name.substring(0, name.length() - suffix.length());
		}
		return name;
	}

	final static private byte[] getResourceData(JarInputStream jar) throws IOException 
	{
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int size;
		while (jar.available() > 0) {
			size = jar.read(buffer);
			if (size > 0) {
				data.write(buffer, 0, size);
			}
		}
		return data.toByteArray();
	}

	final static public void callMethod(Class<?> clz, String methodName, String[] args)
	{
		Class<?>[] arg = new Class<?>[1];
		arg[0] = args.getClass();
		try {
			Method method = clz.getMethod(methodName, arg);
			Object[] inArg = new Object[1];
			inArg[0] = args;
			method.invoke(clz, inArg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	final static public void loadNatives(JarClassLoader loader, ArrayList<String> native_libs)
	{
		for (String native_lib : native_libs) {
			try{
				String path = loader.findLibrary(native_lib);
				System.load(path);
				System.out.println("load native : " + native_lib);
			}catch(Throwable err){
				err.printStackTrace();
			}
		}
	}
//	----------------------------------------------------------------------------------------------------------------------------------
	// 
	private boolean		is_set_ProtectionDomain	= true;
	
	
	//资源缓存
	private HashMap<String, byte[]> Resources	= new HashMap<String, byte[]>();
	private HashMap<String, byte[]> Natives		= new HashMap<String, byte[]>();
	private HashMap<String, byte[]> Classes		= new HashMap<String, byte[]>();
	
	File					native_file_dir 	= null;
	HashMap<String, String>	native_paths		= new HashMap<String, String>();
	
	//class资源及实体缓存
	//private ArrayList<String> classNames = new ArrayList<String>();
	
	@SuppressWarnings("unchecked")
	public Class<?> findClass(String className) throws ClassNotFoundException 
	{
		try{
			Class clazz = this.findLoadedClass(className);
			if (null == clazz) {
				byte[] bytes = Classes.get(className);
				if (is_set_ProtectionDomain) {
					clazz = defineClass(className, bytes, 0, bytes.length, 
							this.getClass().getProtectionDomain());
				}else{
					clazz = defineClass(className, bytes, 0, bytes.length);
				}
			}
			return clazz;
		}
		catch (Exception e) {
			System.err.println("JarClassLoader : " + e.getMessage());
		}
		return super.findClass(className);
	}

	@Override
	protected String findLibrary(String libname)
	{
		try
		{
			String path = native_paths.get(libname);
			
			if (path!=null) {
				return path;
			} 
			else 
			{
				byte[] native_data = Natives.get(libname);
				if (native_data != null) 
				{
					if (native_file_dir == null) {
						File tmp_dir = File.createTempFile("jni_", "");
						native_file_dir = new File(tmp_dir+"_cache"+File.separatorChar+"natives");
						native_file_dir.mkdirs();
					}
					File file = new File(native_file_dir, libname + NATIVE_SUFFIX);
					FileOutputStream fos = new FileOutputStream(file);
					try{
						fos.write(native_data);
						fos.flush();
						file.deleteOnExit();
						path = file.getPath();
						native_paths.put(libname, path);
						System.out.println("find library : " + libname + " : " + path);
						return file.getPath();
					}finally{
						fos.close();
					}
				}
			}
		}
		catch(Throwable ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public InputStream getResourceAsStream(String path) {
		String name = path;
		if (name.charAt(0) != '/') {
			name = "/" + name;
		}
		byte[] data = Resources.get(name);
		if (data != null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			return bais;
		}
		return super.getResourceAsStream(path);
	}
	

//	----------------------------------------------------------------------------------------------------------------------------------

}
