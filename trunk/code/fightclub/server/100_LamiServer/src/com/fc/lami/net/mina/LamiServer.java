package com.fc.lami.net.mina;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.cell.CIO;
import com.cell.j2se.CAppBridge;
import com.fc.lami.LamiConfig;
import com.fc.lami.MessageFactory;
import com.fc.lami.LamiServerListener;
import com.fc.lami.RoomSet;
import com.net.flash.FlashCrossdomainService;
import com.net.minaimpl.server.ServerImpl;

public class LamiServer extends ServerImpl {

	public LamiServer(MessageFactory factory) 
	{
		super(CIO.getAppBridge().getClassLoader(), factory, 10, 600, 600, 0);	
	}

	public static void main(String[] args) throws IOException
	{
		try {
			CAppBridge.initNullStorage();
			FlashCrossdomainService.main(new String[]{});
			MessageFactory factory = new MessageFactory();
			int port = 19821;
			if (args.length > 0) {
				LamiConfig.load(args[0]);
				port = LamiConfig.MINA_SERVER_PORT;
			}
			LamiServer server = new LamiServer(factory);
			
			server.open(port, new LamiServerListener(
					CIO.getInputStream(LamiConfig.ROOM_SET_CONFIG_XLS)));
		} catch (Exception err) {
			err.printStackTrace();
			System.exit(1);
		}
	}
}
