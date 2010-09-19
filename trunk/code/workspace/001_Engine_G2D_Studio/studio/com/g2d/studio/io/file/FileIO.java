package com.g2d.studio.io.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.cell.CObject;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.g2d.studio.Config;
import com.g2d.studio.io.File;
import com.g2d.studio.io.IO;

public class FileIO implements IO
{
	java.io.File root;
	
	public FileIO(String g2d_file) throws Throwable
	{
		this.root = new java.io.File(g2d_file);
		if (!root.exists() || !root.isFile()) {
			throw new FileNotFoundException(g2d_file);
		}
		this.root = this.root.getParentFile();
		CObject.initSystem(
				new CStorage("g2d_studio"), 
				new CAppBridge(
				this.getClass().getClassLoader(), 
				this.getClass()));

		System.out.println(System.setProperty("user.dir", this.root.getPath()));
		System.out.println(System.getProperty("user.dir"));
		Config.load(Config.class, g2d_file);
	}
	
	public void save(File url, byte[] data) {
		java.io.File file = ((FileImpl)url).file;
		if (!file.getParentFile().exists()) {
			file.mkdirs();
		}
		CFile.wirteData(file, data);
	}

	public byte[] load(File url) {
		java.io.File file = ((FileImpl)url).file;
		return CFile.readData(file);
	}
	
    public File createFile(String pathname) {
    	return new FileImpl(new java.io.File(pathname));
    }

    public File createFile(String parent, String child) {
    	return new FileImpl(new java.io.File(parent, child));
    }

    public File createFile(File parent, String child) {
    	return new FileImpl(new java.io.File(((FileImpl)parent).file, child));
    }
    
    
    private static class FileImpl implements File
    {
    	java.io.File file;
    	
    	private FileImpl(java.io.File file) {
			this.file = file;
		}

		@Override
		public boolean createNewFile() {
			try {
				return file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		public boolean delete() {
			return file.delete();
		}

		@Override
		public boolean exists() {
			return file.exists();
		}

		@Override
		public String getName() {
			return file.getName();
		}

		@Override
		public String getParent() {
			return file.getParent();
		}

		@Override
		public File getParentFile() {
			return new FileImpl(file.getParentFile());
		}

		@Override
		public String getPath() {
			return file.getPath();
		}

		@Override
		public boolean isDirectory() {
			return file.isDirectory();
		}

		@Override
		public boolean isFile() {
			return file.isFile();
		}

		@Override
		public String[] list() {
			return file.list();
		}

		@Override
		public File[] listFiles() {
			java.io.File[] rfs = file.listFiles();
			ArrayList<File> files = new ArrayList<File>(rfs.length);
			for (java.io.File sub : file.listFiles()) {
				files.add(new FileImpl(sub));
			}
			return files.toArray(new File[files.size()]);
		}
		
		@Override
		public boolean mkdir() {
			return file.mkdir();
		}

		@Override
		public boolean mkdirs() {
			return file.mkdirs();
		}
    	
    }
}
