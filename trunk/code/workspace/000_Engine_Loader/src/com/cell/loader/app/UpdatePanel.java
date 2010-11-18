package com.cell.loader.app;

import java.io.File;
import java.util.HashMap;

import javax.swing.JPanel;

import com.cell.loader.LoadTask;


/**
 * file list<br>
 * md5 : size : path	<br>
aa58ea7fad8ee62a6dfc0e873abec53a : 652        : .\avatar\item_000100\icon_020100.png	<br>
54f4575f894775e32a35f5787c28f149 : 26809      : .\avatar\item_000100\output\actor.properties	<br>
1807c436ad4aa77de2579cd429c5b16f : 40584      : .\avatar\item_000100\output\item.properties	<br>
612ef854d496ad36292bf7f9d588428e : 9425       : .\avatar\item_000100\output\item_000100.png	<br>
caa6dabede83435d597bb220c0fe97b2 : 733        : .\avatar\item_000101\icon_000101.png	<br>
a43da58335f20affae6052411dd6b55a : 25011      : .\avatar\item_000101\output\item.properties	<br>
c95cd030ffbcc34968f6167bd6697049 : 23737      : .\avatar\item_000101\output\item_000101.png	<br>
eb909da48f90f2a4da711292d6e78ca6 : 722        : .\avatar\item_000102\icon_000102.png	<br>
f3d511974941211adf45b9af7b9c974f : 24604      : .\avatar\item_000102\output\item.properties	<br>
751a158842c6cfb9fa674bbda14f483b : 6949       : .\avatar\item_000102\output\item_000102.png	<br>
e9b82febaac8e64a2963f16e3de61f26 : 340        : .\avatar\item_000103\icon_000103.png	<br>
2e3e9775c8fb8ea0d63937eefcae65ff : 547        : .\avatar\item_000103\icon_020103.png	<br>
fec02be0d29b155679271230114b9867 : 36507      : .\avatar\item_000103\output\item.properties	<br>
c35f33c56d2934b98803197a828995ce : 2307       : .\avatar\item_000103\output\item_000103.png	<br>
 * @author WAZA
 */
public abstract class UpdatePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	String	remote_file;
	
	File	local_root;
	
	public UpdatePanel(String remote_val_file, File local_root)
	{
		
		
	}
	
	HashMap<String, RemoteValFile> getRemoteValList(String remote_file) throws Exception
	{
		return getValList(LoadTask.load(remote_file));
	}
	
	HashMap<String, RemoteValFile> getLocalValList(String local_file) throws Exception
	{
		return getValList(LoadTask.load(new File(local_file)));
	}
	
	HashMap<String, RemoteValFile> getValList(byte[] data) throws Exception
	{
		String text = new String(data, "UTF-8");
		String[] lines = text.split("\n");
		HashMap<String, RemoteValFile> ret = new HashMap<String, RemoteValFile>(lines.length);
		for (String line : lines) {
			try {
				String[] kv = line.split(":", 3);
				RemoteValFile vf = new RemoteValFile(kv[0].trim(), Integer.parseInt(kv[1].trim()), kv[2].trim());
				ret.put(vf.path, vf);
			} catch (Exception e) {
			}
		}
		return ret;
	}
	
	static class RemoteValFile
	{
		final String 	md5;
		final int		size;
		final String	path;

		public RemoteValFile(String md5, int size, String path) {
			this.md5 = md5;
			this.size = size;
			this.path = path;
		}
	}
}
