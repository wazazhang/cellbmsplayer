package com.g2d.studio.gameedit.entity;


import java.util.Vector;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.rpg.NamedObject;
import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGObjectMap;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.io.File;



public abstract class ObjectGroup<T extends ObjectNode<D>, D extends RPGObject> extends G2DTreeNodeGroup<ObjectNode<D>>
{
	private static final long serialVersionUID = 1L;
	
	final static public String _XML	= ".xml";
	
	final public File		list_file;
	final public Class<D>	data_type;
	final public Class<T>	node_type;
	
	public ObjectGroup(String name, File list_file, Class<T> node_type, Class<D> data_type) {
		super(name);
		this.list_file = list_file;
		this.data_type = data_type;
		this.node_type = node_type;
	}

	abstract protected boolean createObjectNode(String key, D data);

	
//	----------------------------------------------------------------------------------------------------------
	
	@Override
	protected boolean pathAddLeafNode(String name, int index, int length) {
		if (name.toLowerCase().endsWith(_XML)) {
			try{
				String key = CUtil.replaceString(name, _XML, "");
				File file = Studio.getInstance().getIO().createFile(list_file.getParentFile(), name);
				D data = RPGObjectMap.readNode(file.getInputStream(), file.getPath(), data_type);
				return createObjectNode(key, data);
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		return false;
	}
	
	public void saveList()
	{
		File name_list_file = Studio.getInstance().getIO().createFile(
				list_file.getParentFile(), 
				"name_" + list_file.getName());
		
		StringBuffer all_objects = new StringBuffer();
		StringBuffer all_names = new StringBuffer();
		Vector<T> nodes = G2DTree.getNodesSubClass(this, node_type);
		for (T node : nodes) {
			try{
				File xml_file = Studio.getInstance().getIO().createFile(
						list_file.getParentFile(), node.getID()+_XML);
				String xml = RPGObjectMap.writeNode(xml_file.getPath(), node.getData());
				if (xml != null){
					xml_file.writeBytes(CIO.stringEncode(xml, CIO.ENCODING));
					all_objects.append(toPathString(node, "/") + node.getID() + _XML + "\n");
				}
				if (node.getData() instanceof NamedObject) {
					all_names.append("("+node.getData().id+")"+((NamedObject)node.getData()).getName()+"\n");
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		try {
			list_file.writeBytes(CIO.stringEncode(all_objects.toString(), CIO.ENCODING));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (NamedObject.class.isAssignableFrom(data_type)) {
			try {
				name_list_file.writeBytes(CIO.stringEncode(all_names.toString(), CIO.ENCODING));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadList()
	{
		try{
			if (list_file.exists()) {
				String[] lines = CIO.readAllLine(list_file.getPath(), "UTF-8");
				for (String line : lines) {
					try{
						loadPath(line.trim());
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}
