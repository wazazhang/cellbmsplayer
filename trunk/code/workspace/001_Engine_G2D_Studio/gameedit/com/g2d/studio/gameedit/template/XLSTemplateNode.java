package com.g2d.studio.gameedit.template;

import javax.swing.ImageIcon;

import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.icon.IconFile;
import com.g2d.studio.res.Res;

public abstract class XLSTemplateNode<T extends TemplateNode> extends ObjectNode<T>
{
	final protected T	template_data;
	final XLSFile		xls_file;
	final XLSFullRow	xls_fullrow;

	transient protected IconFile	icon_file;
	
	@SuppressWarnings("unchecked")
	XLSTemplateNode(XLSFile xls_file, XLSFullRow xls_row, TemplateNode data) 
	{
		this.xls_file		= xls_file;
		this.xls_fullrow	= xls_row;
		this.template_data	= (data == null) ? newData(xls_file, xls_row) : (T)data;
//		System.out.println("read a xls row : " + xls_file.xls_file + " : " + xls_fullrow.id + " : " + xls_fullrow.desc);
		if (template_data.icon_index!=null) {
			icon_file = Studio.getInstance().getIconManager().getIcon(template_data.icon_index);
		}
	}
	
	/**
	 * 根据xls创建新的数据对象，当构造函数无法得到数据对象时自动创建
	 * @param xls_file
	 * @param xls_row
	 * @return
	 */
	abstract protected T newData(XLSFile xls_file, XLSFullRow xls_row) ;
	
	/**
	 * 获得当前实时数据对象
	 * @return
	 */
	public T getData() {
		return template_data;
	}
	
	final public XLSFile getXLSFile() {
		return xls_file;
	}
	
	final public XLSFullRow getXLSRow() {
		return xls_fullrow;
	}
	
	@Override
	final public String getName() {
		return getXLSRow().desc + "(" + getXLSRow().id + ")";
	}

	@Override
	final public String getID() {
		return getXLSRow().id;
	}
	
	final public int getIntID() {
		return Integer.parseInt(getXLSRow().id);
	}
	
//	public XLSObjectViewer<?> getEditComponent(){
//		if (edit_component==null) {
//			edit_component = new XLSObjectViewer<XLSTemplateNode<?>>(this);
//		}
//		return edit_component;
//	}

//	@Override
//	public ImageIcon createIcon() {
//		if (icon_file!=null) {
//			return Tools.createIcon(Tools.combianImage(20, 20, icon_file.image));
//		} else {
//			return Tools.createIcon(Tools.combianImage(20, 20, Res.icon_error));
//		}
//	}
	
//	final public IconFile getIconFile() {
//		return icon_file;	
//	}
//
//	final public void setIcon(IconFile icon) {
//		template_data.icon_index = icon.icon_file_name;
//		this.icon_file = icon;
//	}

	@Override
	public ImageIcon getIcon(boolean update) {
		if (icon_file==null || !icon_file.icon_file_name.equals(getData().icon_index)) {
			resetIcon();
		} 
		return super.getIcon(update);
	}
	
	@Override
	protected ImageIcon createIcon() {
		icon_file = Studio.getInstance().getIconManager().getIcon(getData().icon_index);
		if (icon_file!=null) {
			return new ImageIcon(Tools.combianImage(20, 20, icon_file.getImage()));
		}
		return Tools.createIcon(Tools.combianImage(20, 20, Res.icon_error));
	}
	
//	----------------------------------------------------------------------------------------------------------------------
	
	
}
