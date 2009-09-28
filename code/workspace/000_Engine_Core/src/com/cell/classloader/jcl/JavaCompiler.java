package com.cell.classloader.jcl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 * 提供java动态编译的方法，在程序运行时，可将java文件动态编译成类，装载进程序。
 * @author WAZA
 */
@SuppressWarnings("unchecked")
public class JavaCompiler extends ClassLoader
{
	static JavaCompiler instance_compiler = new JavaCompiler();
	
	
	/**
	 * @param name
	 * @param javaFile
	 * @return
	 * @throws Exception
	 */
	public static Class loadClass(String name, String javaFile) throws Exception
	{
		return instance_compiler.loadClass(name, javaFile, false);
	}
	
//	public static void unloadAll()
//	{
//		instance_compiler.clearLoadedClasses();
//	}
	
//	--------------------------------------------------------------------------------------------------------------------------
	
	Hashtable<String, Class> loaded_class = new Hashtable<String, Class>();
	
	private byte[] getBytes(String name, File file) throws IOException 
	{
		long len = file.length();
		byte raw[] = new byte[(int) len];
		FileInputStream fin = new FileInputStream(file);
		int r = fin.read(raw);
		if (r != len)
			throw new IOException("Can't read all, " + r + " != " + len);
		fin.close();
		return raw;
	}

	private boolean compile(String name, String javaFile) throws IOException
	{
		System.out.print("Compiling java file : \"" + javaFile + "\" ...");
		try{
//			com.sun.tools.javac.Main.compile(new String[]{"-help"});
			com.sun.tools.javac.Main.compile(new String[]{javaFile});
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println();
		}
		return false;
	}
	
//	synchronized private void clearLoadedClasses()
//	{
//		clearAssertionStatus();
//		loaded_class.clear();
//	}
	
	synchronized private Class loadClass(String name, String javaFilename, boolean resolve) throws ClassNotFoundException 
	{
		Class clas = loaded_class.get(name);

		if (clas != null)
		{
			return clas;
		}
		else 	
		{
			File javaFile = new File(javaFilename);
			
			if (javaFile.exists())
			{
				File classFile = new File(javaFilename.substring(0, javaFilename.lastIndexOf(".java"))+".class");
				
				try {
					if (compile(name, javaFilename)) {
						byte raw[] = getBytes(name, classFile);
						clas = defineClass(name, raw, 0, raw.length);
						if (clas != null) {
							if (resolve) {
								resolveClass(clas);
							}
							loaded_class.put(name, clas);
						}
						classFile.delete();
					}
				} catch (Exception ie) {
					ie.printStackTrace();
				}
			}
		}

		if (clas == null){
			throw new ClassNotFoundException(name + "\npath="+javaFilename);
		}

		return clas;
	}

	
	
	
	
}
