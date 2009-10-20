package com.g2d.studio;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

import com.cell.CIO;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResourceManager;
import com.g2d.cell.CellSetResource.CellSetObject;
import com.g2d.cell.CellSetResource.WORLD;


/**
 * @author WAZA
 * 第三方程序用来读入G2D Studio对象的类
 */
public class ResourceManager extends CellSetResourceManager
{
	static ResourceManager instanceo;
	public static ResourceManager getInstance() {
		return instanceo;
	}
	
//	--------------------------------------------------------------------------------------------------------------------
	
	abstract static class ResourceSet<T extends CellSetObject> implements Serializable
	{
		private static final long serialVersionUID = 1L;
		

		final public String		set_name;
		
		final public int 		id;
		final public String		name;
		
		protected T				set_object;
		
		protected ResourceSet(String set_name, String name)
		{
			this.set_name	= set_name;
			this.id			= Integer.parseInt(name.substring(0, 6));
			this.name		= name;
		}
		
		public T getSetObject() throws Exception
		{
			if (set_object == null) {
				set_object = loadSetObject();
			}
			return set_object;
		}
		
		public CellSetResource getSetResource() throws Exception
		{
			return getInstance().getSet(getSetResourcePath());
		}
		
		abstract protected String getSetResourcePath();
		
		abstract protected T loadSetObject() throws Exception;
		
	}
	
//	--------------------------------------------------------------------------------------------------------------------
	
	/** 将编辑器导出的output文件里的场景信息的描述 */
	public static class SceneSet extends ResourceSet<CellSetResource.WORLD>
	{
		private static final long serialVersionUID = 1L;
		
		protected SceneSet(String set_name, String name){
			super(set_name, name);
		}
		
		@Override
		protected String getSetResourcePath() {
			return getInstance().RES_SCENE_ROOT + "/" + set_name + "/output/scene.properties";
		}
		
		@Override
		protected WORLD loadSetObject() throws Exception {
			return getSetResource().WorldTable.get(name);
		}
		
		
		public String getSceneG2dPath() {
			String path = getInstance().RES_SCENE_ROOT + "/" + set_name + "/" + name + ".xml";
			return path;
		}
		
		
	}

	
//	--------------------------------------------------------------------------------------------------------------------
	
	public static class ActorSet extends ResourceSet<CellSetResource.SPR>
	{
		private static final long serialVersionUID = 1L;
		
		protected ActorSet(String set, String name)
		{
			super(set, name);
		}
		
		@Override
		protected String getSetResourcePath() {
			return getInstance().RES_ACTOR_ROOT + "/" + set_name + "/output/actor.properties";
		}
		@Override
		protected CellSetResource.SPR loadSetObject() throws Exception {
			return getSetResource().SprTable.get(name);
		}
		
	}
	
//	--------------------------------------------------------------------------------------------------------------------
	
	public static class AvatarSet extends ResourceSet<CellSetResource.SPR>
	{
		private static final long serialVersionUID = 1L;
		
		protected AvatarSet(String set, String name)
		{
			super(set, name);
		}
		
		@Override
		protected String getSetResourcePath() {
			return getInstance().RES_AVATAR_ROOT + "/" + set_name + "/output/item.properties";
		}
		@Override
		protected CellSetResource.SPR loadSetObject() throws Exception {
			return getSetResource().SprTable.get(name);
		}
		
	}
	
//	--------------------------------------------------------------------------------------------------------------------
	
	final protected String RES_SCENE_ROOT;
	final protected String RES_ACTOR_ROOT;
	final protected String RES_AVATAR_ROOT;
	
	Hashtable<Integer, SceneSet>	all_scene_set	= new Hashtable<Integer, SceneSet>();
	Hashtable<Integer, ActorSet>	all_actor_set	= new Hashtable<Integer, ActorSet>();
	Hashtable<Integer, AvatarSet>	all_avatar_set	= new Hashtable<Integer, AvatarSet>();
	
	public ResourceManager(String scene_root, String actor_root, String avatar_root)
	{
//		if (instanceo!=null) {
//			throw new IllegalStateException("ResourceManager : can not create 2 or more instance !");
//		}
		this.RES_SCENE_ROOT		= scene_root;
		this.RES_ACTOR_ROOT		= actor_root;
		this.RES_AVATAR_ROOT	= avatar_root;
		instanceo = this;
//		RES_SCENE_ROOT	= "D:/EatWorld/trunk/eatworld/data/edit/scene";
//		RES_ACTOR_ROOT	= "D:/EatWorld/trunk/eatworld/data/edit/character";
//		RES_AVATAR_ROOT	= "D:/EatWorld/trunk/eatworld/data/edit/avatar";
	}

	protected CellSetResource createSet(String path) throws Exception
	{
		return new CellSetResource(path);
	}
	
	protected SceneSet createSceneSet(String set_name, String object_name)
	{
		return new SceneSet(set_name, object_name);
	}
	protected ActorSet createActorSet(String set_name, String object_name)
	{
		return new ActorSet(set_name, object_name);
	}
	protected AvatarSet createAvatarSet(String set_name, String object_name)
	{
		return new AvatarSet(set_name, object_name);
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends ResourceSet<?>> Vector<T> readSetList(String file, Hashtable<Integer, T> table, Class<T> type) throws Exception
	{
		System.out.println("list : " + file);
		
		table.clear();
		
		Vector<T>	ret			= new Vector<T>();
		String[]	res_list	= CIO.readAllLine(file);
		String		set_name	= "";
		
		for (int i=0; i<res_list.length; i++)
		{
			String line = res_list[i].trim();
			
			if (line.length()>0)
			{
				if (!line.contains("="))
				{
					set_name = line;
				}
				else
				{
					String object_name = line.split("=")[1].split(",")[1].trim();
					
					T set = null;
					
					if (type.equals(SceneSet.class)) {
						set = (T)(createSceneSet(set_name, object_name));
					}
					else if (type.equals(ActorSet.class)) {
						set = (T)(createActorSet(set_name, object_name));
					}
					else if (type.equals(AvatarSet.class)) {
						set = (T)(createAvatarSet(set_name, object_name));
					}
					
					System.out.println("\tget " + type.getSimpleName() + " : " + object_name + "(" + set_name + ")");
					
					ret.add(set);
					
					table.put(set.id, set);
				}
			}
		}
		
		return ret;
	}

//	--------------------------------------------------------------------------------------------------------------------
	

	
//	--------------------------------------------------------------------------------------------------------------------
	
	public SceneSet getSceneSet(int id) throws Exception
	{
		SceneSet set = all_scene_set.get(id);
		set.loadSetObject();
		return set;
	}

	public Vector<SceneSet> readSceneList() throws Exception
	{
		return readSetList(RES_SCENE_ROOT + "/scene_list.txt", all_scene_set, SceneSet.class);
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	public ActorSet getActorSet(int id) throws Exception
	{
		ActorSet set = all_actor_set.get(id);
		set.loadSetObject();
		return set;
	}
	
	public Vector<ActorSet> readActorList() throws Exception
	{
		return readSetList(RES_ACTOR_ROOT + "/actor_list.txt", all_actor_set, ActorSet.class);
	}

//	--------------------------------------------------------------------------------------------------------------------

	public AvatarSet getAvatarSet(int id) throws Exception
	{
		AvatarSet set = all_avatar_set.get(id);
		set.loadSetObject();
		return set;
	}
	
	public Vector<AvatarSet> readAvatarList() throws Exception
	{
		return readSetList(RES_AVATAR_ROOT + "/item_list.txt", all_avatar_set, AvatarSet.class);
	}
	
//	--------------------------------------------------------------------------------------------------------------------
	
	
	
	
}
