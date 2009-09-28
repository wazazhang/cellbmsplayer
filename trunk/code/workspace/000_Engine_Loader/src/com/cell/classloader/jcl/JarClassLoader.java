package com.cell.classloader.jcl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;


import java.util.HashMap;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;


/**
 * 提供java动态加载的方法，在程序运行时，可将jar包动态加载进程序。
 * @author WAZA
 */
public class JarClassLoader  extends ClassLoader
{
	public static void callMethod(Class<?> clz, String methodName, String[] args)
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
	
	final static public void loadAllClass(byte[] jardata, boolean sign)
	{
		try
		{
			JarClassLoader classloader = new JarClassLoader();
			classloader.is_set_ProtectionDomain = sign;
			
			JarInputStream jar = new JarInputStream(new ByteArrayInputStream(jardata));
			
			JarEntry entry;
			while ((entry = jar.getNextJarEntry()) != null)
			{
				if (entry.getName().toLowerCase().endsWith(".class")) 
				{
					String name = entry.getName().substring(0, entry.getName().length() - ".class".length()).replace('/', '.');
					byte[] data = getResourceData(jar);
					classloader.Classes.put(name, data);
					classloader.findClass(name);
				} 
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	final static public JarClassLoader createJarClassLoader(Vector<byte[]> resources) throws IOException
	{
		return createJarClassLoader(resources, true);
	}
	
	final static public JarClassLoader createJarClassLoader(Vector<byte[]> resources, boolean is_sign_class) throws IOException
	{
		JarClassLoader classloader = new JarClassLoader();
		classloader.is_set_ProtectionDomain = is_sign_class;
			
		for (byte[] resource : resources)
		{
			// 将byte[]转为JarInputStream
			JarInputStream jar = new JarInputStream(new ByteArrayInputStream(resource));
			
			// 依次获得对应JAR文件中封装的各个被压缩文件的JarEntry
			JarEntry entry;
			while ((entry = jar.getNextJarEntry()) != null)
			{
				// 当找到的entry为class时
				if (entry.getName().toLowerCase().endsWith(".class")) 
				{
					// 将类路径转变为类全称
					String name = entry.getName().substring(0, entry.getName().length() - ".class".length()).replace('/', '.');
					// 加载该类
					byte[] data = getResourceData(jar);
					// 缓存类名及数据
					//System.out.println("unzip " + name);
					classloader.Classes.put(name, data);
				} 
				else
				{
					String name = entry.getName();
					byte[] data = getResourceData(jar);
					// 非class结尾但开头字符为'/'时
					if (name.charAt(0) != '/') {
						name = "/" + name;
					}
					//System.out.println("unzip " + name);
					classloader.Resources.put(name, data);
				}
			}
			
		}
		return classloader;
	}
	
	final static private byte[] getResourceData(JarInputStream jar) throws IOException {
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
	
//	----------------------------------------------------------------------------------------------------------------------------------
	// 
	private boolean is_set_ProtectionDomain	= true;
	
	//资源缓存
	private HashMap<String, byte[]> Resources = new HashMap<String, byte[]>();
	private HashMap<String, byte[]> Classes = new HashMap<String, byte[]>();
	
	//class资源及实体缓存
	//private ArrayList<String> classNames = new ArrayList<String>();
	
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
