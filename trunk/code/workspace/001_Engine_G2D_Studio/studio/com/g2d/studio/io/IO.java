package com.g2d.studio.io;


public interface IO 
{
	public void save(File file, byte[] data);

	public byte[] load(File file);
	
    public File createFile(String pathname) ;

    public File createFile(String parent, String child);
    
    public File createFile(File parent, String child);
}
