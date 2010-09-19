package com.g2d.studio.io;


public interface File 
{
    public String getName();

    public String getParent();

    public File getParentFile();

    public String getPath();

    public boolean exists();

    public boolean isDirectory() ;

    public boolean isFile();

    public boolean createNewFile();

    public boolean delete();

    public String[] list();

    public File[] listFiles();

    public boolean mkdir() ;

    public boolean mkdirs();

}
