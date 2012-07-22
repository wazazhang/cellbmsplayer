package com.fc.castle.data.message;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

import com.cell.j2se.CAppBridge;
import com.cell.net.io.ExternalizableFactory;
import com.cell.net.io.MutualMessageCodec;
import com.cell.net.io.flash.FlashMessageCodeGenerator;
import com.cell.net.io.java.MutualMessageCodeGeneratorJava;

import com.fc.castle.data.*;
import com.fc.castle.data.template.*;

public class MessageFactory extends ExternalizableFactory
{
	
	public MessageFactory() throws Exception
	{
		super(getMessageCodec(), getMessageCodec().getClasses());
	}
	
	public MessageFactory(File srcdir) throws Exception
	{
		super(getMessageCodec(), getDirClasses(srcdir));
	}
	
	public static MutualMessageCodec getMessageCodec() throws Exception
	{
		MutualMessageCodec codec = (MutualMessageCodec)Class.forName("com.fc.castle.data.message.MessageCodecJava").newInstance();
		
		return codec;
	}
	
	public static Class<?>[] getDirClasses(File srcdir) throws Exception
	{
		ArrayList<Class<?>> ret = new ArrayList<Class<?>>();
		
		ret.addAll(Arrays.asList(MutualMessageCodeGeneratorJava.findClasses(srcdir)));

//		ret.addAll(Arrays.asList(MutualMessageCodeGeneratorJava.findClasses(
//				new File(srcdir, "com/fc/castle/data/message"), 
//				"com.fc.castle.data.message")));
//		
//		ret.addAll(Arrays.asList(MutualMessageCodeGeneratorJava.findClasses(
//				new File(srcdir, "com/fc/castle/data/template"), 
//				"com.fc.castle.data.template")));

		System.out.println("find " + ret.size() + " class files");
		
		return ret.toArray(new Class<?>[ret.size()]);
	}
	
	public static void main(String[] args) throws Exception
	{
		CAppBridge.init();
		
		final File srcdir = new File("./src");
		
		final Date date = new Date();

		final MessageFactory factory = new MessageFactory(srcdir);
		
		// gen java code
		{
			MutualMessageCodeGeneratorJava gen_java = new MutualMessageCodeGeneratorJava(
					factory,
					"com.fc.castle.data.message",
					"",
					"MessageCodecJava"
					){
				@Override
				public String getVersion() {
					return date.toString();
				}
			};
			gen_java.genCodeFile(srcdir);
		}
		// gen as code
		{
			String simport = 
					"\timport com.fc.castle.data.*;\n" +
					"\timport com.fc.castle.data.message.*;\n" +
					"\timport com.fc.castle.data.message.Messages.*;\n" +
					"\timport com.fc.castle.data.template.*;\n" +
					"\timport com.fc.castle.data.template.Enums.*;\n" +
					"";
			FlashMessageCodeGenerator gen_as = new FlashMessageCodeGenerator(
					factory,
					"com.fc.castle.data.message",
					"MessageCodec",
					simport,
					simport
					){
				@Override
				public String getVersion() {
					return date.toString();
				}
			};
			gen_as.EnableConstruct = true;
			if (args.length > 0) {
				File flexsrc = new File(args[0], "/core");
				if (flexsrc.exists()) {
					gen_as.genCodeFile(flexsrc);
					System.out.println("nicely done!");
					return;
				}
			}
			System.err.println("未指定AS项目路径！");
		}
		
	}
}
