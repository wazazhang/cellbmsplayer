package com.fc.castle.data;

import java.sql.Date;

import com.cell.net.io.Comment;
import com.cell.sql.SQLTableRow;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;
import com.cell.sql.anno.SQLTable;
import com.fc.castle.data.message.AbstractData;

@Comment("mail")
@SQLTable(
		primary_key_name = "id",
		primary_key_type = SQLType.INTEGER, 
		comment = "mail")
public class Mail extends AbstractData implements SQLTableRow<Integer>
{
	private static final long serialVersionUID = 1L;
	

	@SQLField(type=SQLType.INTEGER, auto_increment=true)
	public int id;
	
	@SQLField(type=SQLType.INTEGER)
	public int type;
	
	@SQLField(type=SQLType.TIMESTAMP)
	public Date sendTime;

	@SQLField(type=SQLType.INTEGER)
	public int cyccode;
	
	
	@SQLField(type=SQLType.STRING, size=128)
	public String senderName;

	@SQLField(type=SQLType.STRING, size=128)
	public String receiverName;
	
	
	@SQLField(type=SQLType.INTEGER)
	public int senderPlayerID;

	@SQLField(type=SQLType.INTEGER)
	public int receiverPlayerID;
	
	
	@SQLField(type=SQLType.STRING, size=128)
	public String title;

	@SQLField(type=SQLType.TEXT_STRUCT)
	public String content;


	@SQLField(type=SQLType.BOOLEAN)
	public boolean readed;
	
	
	public Mail() {
		readed = false;
	}
	
	@Override
	public Integer getPrimaryKey() {
		return id;
	}
}
