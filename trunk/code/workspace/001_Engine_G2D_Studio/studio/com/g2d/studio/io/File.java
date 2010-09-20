package com.g2d.studio.io;

import java.io.InputStream;
import java.io.OutputStream;


public interface File 
{
    public String 		getName();

    public String		getParent();

    public File 		getParentFile();

    public File 		getChildFile(String name);
    
    
    public String 		getPath();

    public boolean 		exists();

    public boolean 		isDirectory();

    public boolean 		isFile();

    public boolean 		createNewFile();

    public boolean 		delete();

    public String[] 	list();

    public File[] 		listFiles();

    public boolean 		mkdir() ;

    public boolean 		mkdirs();
    
    
    
    
    public InputStream 		getInputStream();

    public OutputStream		getOutputStream();
    
    public byte[] 			readBytes() throws Exception;
    
    public void 			writeBytes(byte[] data) throws Exception; 
    

}
