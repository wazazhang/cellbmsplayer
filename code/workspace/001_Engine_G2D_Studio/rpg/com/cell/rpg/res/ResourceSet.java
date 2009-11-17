package com.cell.rpg.res;

import java.io.Serializable;

import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResource.CellSetObject;
import com.g2d.cell.CellSetResource.WorldSet;


public abstract class ResourceSet<T extends CellSetObject> implements Serializable
{
	private static final long serialVersionUID = 1L;

//	--------------------------------------------------------------------------------------------------------------------
	
	final public String		cpj_name;
	final public String		set_name;
	final public String		resource_path;
	
	transient private T		set_object;

//	--------------------------------------------------------------------------------------------------------------------
	
	ResourceSet(
			String cpj_name, 
			String set_name, 
			String res_root,
			String res_prefix, 
			String res_suffix) throws Exception
	{
		if (!cpj_name.startsWith(res_prefix)) {
			throw new Exception("resource not validate, \"" + cpj_name + "\" must prefix in \"" + res_prefix + "\"");
		}
		this.cpj_name		= cpj_name;
		this.set_name		= set_name;
		this.resource_path	= res_root + "/" + cpj_name + "/" + res_suffix;
	}
	
	final public T getSetObject(ResourceManager manager) throws Exception {
		if (set_object == null) {
			set_object = loadSetObject(manager);
		}
		return set_object;
	}
	
	final public CellSetResource getSetResource(ResourceManager manager) throws Exception {
		return manager.getSet(manager.res_root + "/" + resource_path);
	}

	final public String getID() {
		return toID(cpj_name, set_name);
	}
	
	abstract protected T loadSetObject(ResourceManager manager) throws Exception;
	
	
//	--------------------------------------------------------------------------------------------------------------------
	
	static public String toID(String cpj_name, String obj_name) {
		return cpj_name + "/" + obj_name;
	}
	
//	--------------------------------------------------------------------------------------------------------------------
	
	
	/** 将编辑器导出的output文件里的场景信息的描述 */
	public static class SceneSet extends ResourceSet<CellSetResource.WorldSet>
	{
		private static final long serialVersionUID = 1L;
		
		SceneSet(
				String cpj_name, 
				String set_name, 
				String res_root,
				String res_prefix, 
				String res_suffix) throws Exception {
			super(cpj_name, set_name, res_root, res_prefix, res_suffix);
		}
		
		@Override
		protected CellSetResource.WorldSet loadSetObject(ResourceManager manager) throws Exception {
			return getSetResource(manager).WorldTable.get(set_name);
		}
		
	}

	
//	--------------------------------------------------------------------------------------------------------------------
	
	public static class SpriteSet extends ResourceSet<CellSetResource.SpriteSet>
	{
		private static final long serialVersionUID = 1L;
		
		SpriteSet(
				String cpj_name, 
				String set_name, 
				String res_root,
				String res_prefix, 
				String res_suffix) throws Exception {
			super(cpj_name, set_name, res_root, res_prefix, res_suffix);
		}
		
		@Override
		protected CellSetResource.SpriteSet loadSetObject(ResourceManager manager) throws Exception {
			return getSetResource(manager).SprTable.get(set_name);
		}
		
	}
	
	
}

