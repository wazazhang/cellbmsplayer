package com.fc.castle.ws;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.io.TextSerialize;
import com.cell.net.io.MutualMessage;
import com.cell.net.io.TextNetDataInput;
import com.cell.net.io.TextNetDataOutput;
import com.cell.util.StringUtil;
import com.fc.castle.data.message.AbstractData;
import com.fc.castle.data.message.MessageFactory;
import com.fc.castle.data.message.Request;
import com.fc.castle.data.message.Response;
import com.fc.castle.ws.config.WSConfig;
import com.fc.castle.ws.impl.game.WSGameManager;
import com.net.minaimpl.NetDataInputImpl;
import com.net.minaimpl.NetDataOutputImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

public abstract class WSHttpRequest extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(WSHttpRequest.class);
	
//	private static HierarchicalStreamDriver xdriver = null;
	
	private static MessageFactory message_factory;
	
	public static void initStatic() throws Exception
	{
//		try {
//			xdriver = new JettisonMappedXmlDriver();
//		} catch (Throwable e) {
//			xdriver = new XppDriver();
//		}
		 message_factory = new MessageFactory();
	}
	
	
//	------------------------------------------------------------------------------------------------------------------------
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
//		System.out.println(getSessionInfo(request.getSession()) );
		processMessage(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		processMessage(request, response);
	}
	
	protected void processMessage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Request req = decodeMessage(request);
			if (onReceivedMessage(req, request, response)) {
				Response res = WSGameManager.getInstance().onRequest(req);
				if (onSendMessage(res, request, response)) {
					encodeMessage(res, writer);
					return;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error(getRequestInfo(request));
			e.printStackTrace(writer);
		}
		writer.write("Aceess denied !");
	}
	
	/**
	 * 一个消息在被接收并解码后调用。
	 * 子类可用来做安全验证
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected boolean onReceivedMessage(Request data, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return data != null;
	}

	/**
	 * 一个消息在被发送之前调用。
	 * 子类可用来做安全验证
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected boolean onSendMessage(Response data, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return data != null;
	}
	
//	------------------------------------------------------------------------------------------------------
	
	protected Request decodeMessage(HttpServletRequest request) throws Exception
	{
		if (WSConfig.DEBUG)
		{
			System.out.println(
					"--------------------------------------------------------------------------------------\n" +
					"- Received :\n" +
					"--------------------------------------------------------------------------------------\n" +
					getRequestInfo(request));
		}
		String ptype = request.getParameter("type");
		String pdata = request.getParameter("data");
		if (ptype != null && pdata != null) 
		{
			StringReader sr = new StringReader(pdata);
			TextNetDataInput in2d = new TextNetDataInput(sr, message_factory);
			
//			byte[] bin = StringUtil.hex2bin(pdata);
//			NetDataInputImpl in2d = new NetDataInputImpl(IoBuffer.wrap(bin), message_factory);
			
			Object msg = in2d.readMutual(message_factory.getMessageClass(Integer.parseInt(ptype)));
			return (Request)msg;
		} else {
			throw new NullPointerException("bad http param!");
		}
	}
	
	protected void encodeMessage(AbstractData data, Writer writer) throws IOException 
	{  
		{
			TextNetDataOutput out2d = new TextNetDataOutput(
					new TextSerialize(writer),
					message_factory);
			out2d.writeMutual(data);
		}
		
		if (WSConfig.DEBUG)
		{
			StringWriter sw = new StringWriter();
			TextNetDataOutput outtest = new TextNetDataOutput(
					new TextSerialize(sw),
					message_factory);
			outtest.writeMutual(data);
			System.out.println(
					"--------------------------------------------------------------------------------------\n" +
					"- Send : " + data.getClass().getCanonicalName() + "\n" +
					"--------------------------------------------------------------------------------------\n" +
					sw.toString()+"\n");
		}
		
//		try 
//		{
//			IoBuffer buffer = IoBuffer.allocate(4096).setAutoExpand(true);
//			NetDataOutputImpl out = new NetDataOutputImpl(buffer, message_factory);
//			out.writeMutual(data);
//			buffer.shrink();
//			buffer.flip();
//			byte[] outbin = CIO.readStream(buffer.asInputStream());
//			String outstring = CUtil.bin2hex(outbin);
//
////			System.out.println(outstring);
//			
//			byte[] bin = CUtil.hex2bin(outstring);
//			NetDataInputImpl in2d = new NetDataInputImpl(IoBuffer.wrap(outbin), message_factory);
//			Object msg = in2d.readMutual(data.getClass());
////			System.out.println(msg);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
	}

//	------------------------------------------------------------------------------------------------------


//	------------------------------------------------------------------------------------------------------


//	------------------------------------------------------------------------------------------------------

	public static String getSessionInfo(HttpSession session) 
	{
		StringBuilder sb = new StringBuilder();
		
		CUtil.toStatusSeparator(sb);
		{
			Enumeration<String> anames = session.getAttributeNames();
			while (anames.hasMoreElements()) {
				String aname = anames.nextElement();
				Object attr = session.getAttribute(aname);
				CUtil.toStatusLine(aname, attr, sb);
			}
		}
		CUtil.toStatusSeparator(sb);
		return sb.toString();
	}
	
	public static String getRequestInfo(HttpServletRequest request)
	{
		StringBuilder sb = new StringBuilder();
		
		CUtil.toStatusSeparator(sb);
		{
			Enumeration<String> anames = request.getAttributeNames();
			while (anames.hasMoreElements()) {
				String aname = anames.nextElement();
				Object attr = request.getAttribute(aname);
				CUtil.toStatusLine(aname, attr, sb);
			}
		}
		CUtil.toStatusSeparator(sb);
		{
			Enumeration<String> anames = request.getHeaderNames();
			while (anames.hasMoreElements()) {
				String aname = anames.nextElement();
				Object attr = request.getHeader(aname);
				CUtil.toStatusLine(aname, attr, sb);
			}
		}
		CUtil.toStatusSeparator(sb);
		{
			Enumeration<String> pnames = request.getParameterNames();
			while (pnames.hasMoreElements()) {
				String pname = pnames.nextElement();
				Object param = request.getParameter(pname);
				CUtil.toStatusLine(pname, param, sb);
			}
		}
		return sb.toString();
	}
	
//	@SuppressWarnings("unchecked")
//	static public Object parseFrom(String str, Class<?> return_type) throws Exception {
//		NetDataInputText in = new NetDataInputText(str, message_factory);
//		return in.readMutual((Class<? extends MutualMessage>)return_type);
//	}

	
}
