package com.g2d.studio.io.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.g2d.studio.Config;
import com.g2d.studio.io.File;
import com.g2d.studio.io.IO;

public class FileIO implements IO
{	
	public FileIO() throws Throwable
	{}
	
	
	
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
    	public InputStream getInputStream() {
    		try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return null;
    	}

    	@Override
    	public OutputStream getOutputStream() {
    		try {
				return new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return null;
    	}
    	
    	@Override
    	public byte[] readBytes() throws Exception {
    		return CIO.readStream(getInputStream());
    	}
    	@Override
    	public void writeBytes(byte[] data) throws Exception {
    		CFile.wirteData(file, data);
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
		public File getChildFile(String name) {
			return new FileImpl(new java.io.File(file, name));
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
