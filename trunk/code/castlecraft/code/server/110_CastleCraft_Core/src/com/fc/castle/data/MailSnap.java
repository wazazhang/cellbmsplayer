package com.fc.castle.data;

import com.cell.net.io.Comment;
import com.fc.castle.data.message.AbstractData;


@Comment("邮件简介")
public class MailSnap extends AbstractData
{
	private static final long serialVersionUID = 1L;

	public int id;
	
	public String title;
			
	public String senderPlayerName;
	
	public int senderPlayerID;
	
	public boolean readed;
	
	public MailSnap() {}
	public MailSnap(Mail mail) {
		this.id = mail.id;
		this.title = mail.title;
		this.senderPlayerName = mail.senderName;
		this.senderPlayerID = mail.senderPlayerID;
		this.readed = mail.readed;
	}
}
