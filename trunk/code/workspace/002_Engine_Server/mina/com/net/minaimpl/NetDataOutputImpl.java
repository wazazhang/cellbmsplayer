package com.net.minaimpl;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;

import com.cell.exception.NotImplementedException;
import com.cell.io.ExternalizableUtil;
import com.net.ExternalizableMessage;
import com.net.NetDataOutput;

public class NetDataOutputImpl implements NetDataOutput
{
	CharsetEncoder charset_encoder_utf8 = Charset.forName("UTF-8").newEncoder();
	
	final IoBuffer buffer ;
	
	public NetDataOutputImpl(IoBuffer buffer) {
		this.buffer = buffer;
	}
	
	synchronized
	public void writeExternal(ExternalizableMessage data) throws IOException {
		buffer.putInt(0);
		if (data != null) {
			int pos = buffer.position();
			data.writeExternal(this);
			buffer.putInt(pos-1, buffer.position()-pos);
		}
	}
	
	synchronized
	public void writeObject(Object data) throws IOException {
		buffer.putInt(0);
		if (data != null) {
			int pos = buffer.position();
			buffer.putObject(data);
			buffer.putInt(pos-1, buffer.position()-pos);
		}
	}
	
	synchronized
	public void write(byte[] b, int off, int len) throws IOException {
		buffer.put(b, off, len);
	}
	synchronized
	public void write(byte[] b) throws IOException {
		buffer.put(b);
	}
	synchronized
	public void write(int b) throws IOException {
		buffer.put((byte)(b));
	}
	synchronized
	public void writeBoolean(boolean v) throws IOException {
		buffer.put(v?(byte)1:0);
	}
	synchronized
	public void writeByte(int v) throws IOException {
		buffer.put((byte)v);
	}
	synchronized
	public void writeBytes(String s) throws IOException {
		throw new NotImplementedException();
	}
	synchronized
	public void writeChar(int v) throws IOException {
		buffer.putChar((char)v);
	}
	synchronized
	public void writeChars(String s) throws IOException {
		throw new NotImplementedException();
	}
	synchronized
	public void writeDouble(double v) throws IOException {
		buffer.putDouble(v);
	}
	synchronized
	public void writeFloat(float v) throws IOException {
		buffer.putFloat(v);
	}
	synchronized
	public void writeInt(int v) throws IOException {
		buffer.putInt(v);
	}
	synchronized
	public void writeLong(long v) throws IOException {
		buffer.putLong(v);
	}
	synchronized
	public void writeShort(int v) throws IOException {
		buffer.putShort((short)v);
	}
	
	synchronized
	public void writeUTF(String s) throws IOException {
		charset_encoder_utf8.reset();
		buffer.putString(s, charset_encoder_utf8);
	}

//	-----------------------------------------------------------------------------------------------------------
//	
	synchronized
	public void writeBooleanArray(boolean[] bools) throws IOException {
		ExternalizableUtil.writeBooleanArray(this, bools);
	}
	synchronized
	public void writeCharArray(char[] chars) throws IOException {
		ExternalizableUtil.writeCharArray(this, chars);
	}
	synchronized
	public void writeByteArray(byte[] bytes) throws IOException {
		ExternalizableUtil.writeByteArray(this, bytes);
	}
	synchronized
	public void writeShortArray(short[] shorts) throws IOException {
		ExternalizableUtil.writeShortArray(this, shorts);
	}
	synchronized
	public void writeIntArray(int[] ints) throws IOException {
		ExternalizableUtil.writeIntArray(this, ints);
	}
	synchronized
	public void writeLongArray(long[] longs) throws IOException {
		ExternalizableUtil.writeLongArray(this, longs);
	}
	synchronized
	public void writeFloatArray(float[] floats) throws IOException {
		ExternalizableUtil.writeFloatArray(this, floats);
	}
	synchronized
	public void writeDoubleArray(double[] doubles) throws IOException {
		ExternalizableUtil.writeDoubleArray(this, doubles);
	}
	
	
	
}
