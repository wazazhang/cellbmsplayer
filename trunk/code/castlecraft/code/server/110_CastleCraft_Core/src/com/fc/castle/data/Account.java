package com.fc.castle.data;

import com.cell.net.io.Comment;
import com.cell.sql.SQLFieldGroup;
import com.cell.sql.SQLTableRow;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.cell.sql.anno.SQLTable;
import com.fc.castle.data.message.AbstractData;

@SQLTable(
		primary_key_name = "id",
		primary_key_type = SQLType.STRING, 
		comment = "用户帐号")
public class Account extends AbstractData implements SQLTableRow<String>
{
	private static final long serialVersionUID = 1L;
	
	@Comment("用户唯一id")
	@SQLField(type=SQLType.STRING, size=128, not_null=true)
	public String id;
	
	@Comment("用户验证串")
	@SQLField(type=SQLType.STRING, size=128)
	public String sign;

	@Comment("RMB")
	@SQLField(type=SQLType.INTEGER)
	public int gold;

	@Comment("所有玩家数据")
	@SQLField(type=SQLType.XML_STRUCT)
	public int[] players_id;
	

	
	@Override
	public String getPrimaryKey() {
		return id;
	}
	
	
}
