package com.net.mutual;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Map.Entry;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.reflect.Fields;
import com.cell.reflect.Parser;
import com.net.ExternalizableFactory;
import com.net.ExternalizableMessage;
import com.net.MessageHeader;
import com.net.NetDataInput;
import com.net.NetDataOutput;


public interface MutualMessageCodeGenerator
{
	public String genMutualMessageCodec(Map<Integer, Class<?>> regist_types);

	
	public static class MutualMessageCodeGeneratorJava
	{
		String template = CIO.readAllText("com/net/mutual/MutualMessageCodecJava.txt");
		
		public MutualMessageCodeGeneratorJava(String template) {
			if (template != null) {
				this.template = template;
			}
		}
		
		public String genCodec(Map<Integer, Class<?>> regist_types)
		{
			StringBuilder read_external		= new StringBuilder();
			StringBuilder write_external	= new StringBuilder();
			StringBuilder classes			= new StringBuilder();

			for (Entry<Integer, Class<?>> e : regist_types.entrySet()) 
			{
				String c_name = e.getValue().getCanonicalName();
				String s_name = e.getValue().getSimpleName();
				read_external.append(
				"		if (msg.getClass().equals(" + c_name + ".class)) {\n" +
				"			read_" + s_name + "((" + c_name + ")msg, in);\n" +
				"			return;\n" +
				"		}\n");
				write_external.append(
				"		if (msg.getClass().equals(" + c_name + ".class)) {\n" +
				"			write_" + s_name + "((" + c_name + ")msg, out);\n" +
				"			return;\n" +
				"		}\n");
				genMethod(e.getValue(), classes);
			}
			
			String ret = this.template;
			ret = CUtil.replaceString(ret, "//readExternal",  read_external.toString());
			ret = CUtil.replaceString(ret, "//writeExternal", write_external.toString());
			ret = CUtil.replaceString(ret, "//classes",       classes.toString());
			return ret;
		}
		
		/**
		 * 产生一个方法，用于读写消息。
		 * @param msg
		 * @param sb
		 */
		public void genMethod(Class<?> msg, StringBuilder sb)
		{
			String c_name = msg.getCanonicalName();
			String s_name = msg.getSimpleName();
			String m_name = c_name.replace('.', '_');
			
			StringBuilder read = new StringBuilder();
			StringBuilder write = new StringBuilder();
			for (Field f : msg.getFields()) {
				int modifiers = f.getModifiers();
				if (!Modifier.isStatic(modifiers)) {
					genField(msg, f, read, write);
				}
			}
			
			sb.append("//	----------------------------------------------------------------------------------------------------\n");
			sb.append("//	" + c_name + "\n");
			sb.append("//	----------------------------------------------------------------------------------------------------\n");
			sb.append("	void " + m_name + "(){}\n");
			sb.append("	public void read_" + s_name + "(" + c_name + " msg, NetDataInput in) throws IOException {\n");
			sb.append(read);
			sb.append("	}\n");
			sb.append("	public void write_" + s_name + "(" + c_name + " msg, NetDataOutput out) throws IOException {\n");
			sb.append(write);
			sb.append("	}\n\n");
		}
		
		/**
		 * 产生一行代码，用于读写一个消息内的字段
		 * @param msg
		 * @param f
		 * @param read
		 * @param write
		 */
		public void genField(Class<?> msg, Field f, StringBuilder read, StringBuilder write)
		{
			Class<?> 	f_type 		= f.getType();
			String 		f_name 		= "msg." + f.getName();
			if (ExternalizableMessage.class.isAssignableFrom(f_type)) {
				read.append("		" + f_name + " = in.readExternal(" + f_type.getCanonicalName() + ".class);\n");
				write.append("		out.writeExternal(" + f_name + ");\n");
			} 
			else if (f_type.equals(byte.class) || f_type.equals(Byte.class)) {
				read.append("		" + f_name + " = in.readByte();\n");
				write.append("		out.writeByte(" + f_name + ");\n");
			}
			else if (f_type.equals(short.class) || f_type.equals(Short.class)) {
				read.append("		" + f_name + " = in.readShort();\n");
				write.append("		out.writeShort(" + f_name + ");\n");
			}
			else if (f_type.equals(char.class) || f_type.equals(Character.class)) {
				read.append("		" + f_name + " = in.readChar();\n");
				write.append("		out.writeChar(" + f_name + ");\n");
			}
			else if (f_type.equals(int.class) || f_type.equals(Integer.class)) {
				read.append("		" + f_name + " = in.readInt();\n");
				write.append("		out.writeInt(" + f_name + ");\n");
			}
			else if (f_type.equals(long.class) || f_type.equals(Long.class)) {
				read.append("		" + f_name + " = in.readLong();\n");
				write.append("		out.writeLong(" + f_name + ");\n");
			}
			else if (f_type.equals(float.class) || f_type.equals(Float.class)) {
				read.append("		" + f_name + " = in.readFloat();\n");
				write.append("		out.writeFloat(" + f_name + ");\n");
			}
			else if (f_type.equals(double.class) || f_type.equals(Double.class)) {
				read.append("		" + f_name + " = in.readDouble();\n");
				write.append("		out.writeDouble(" + f_name + ");\n");
			}
			else if (f_type.equals(String.class)) {
				read.append("		" + f_name + " = in.readUTF();\n");
				write.append("		out.writeUTF(" + f_name + ");\n");
			}
			else if (f_type.isArray()) 
			{
				

			}
			else {
				read.append("		// Unsupported type : " + f_name + " " + f_type.getName() + "\n");
				write.append("		// Unsupported type : " + f_name + " " + f_type.getName() + "\n");
			}
		}
	}
}
