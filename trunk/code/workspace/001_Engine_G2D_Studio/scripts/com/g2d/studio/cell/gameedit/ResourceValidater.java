package com.g2d.studio.cell.gameedit;

import com.cell.CObject;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.mysql.SQLTypeComparerMySQL;
import com.cell.rpg.res.ResourceManager;
import com.cell.sql.SQMTypeManager;
import com.cell.util.concurrent.ThreadPoolService;
import com.cell.xstream.XStreamAdapter;
import com.g2d.cell.CellSetResource;

public class ResourceValidater extends ResourceManager
{
	public ResourceValidater(String res_root) throws Exception
	{
		super(XStreamAdapter.class.getCanonicalName(), res_root, true, true, true, true);
	}
	
	
	@Override
	protected CellSetResource createSet(String path) throws Exception {
		return new CellSetResource(path);
	}
	
	@Override
	protected ThreadPoolService getLoadingService() {
		return null;
	}
	
	
	public static void main(String[] args) throws Exception
	{
		CAppBridge.init();
		// 初始化服务器配置文件
		SQMTypeManager.setTypeComparer(new SQLTypeComparerMySQL());
		ResourceValidater res = new ResourceValidater(args[0]);
		res.check();
		
		System.out.println("validate ok !");
	}
	
}