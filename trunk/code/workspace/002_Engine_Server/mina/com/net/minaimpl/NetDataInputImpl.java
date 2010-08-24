package com.net.minaimpl;

import java.io.DataInput;
import java.io.Externalizable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;

import com.cell.exception.NotImplementedException;
import com.cell.io.ExternalizableUtil;
import com.net.NetDataInput;

public class NetDataInputImpl implements NetDataInput
{
	CharsetDecoder charset_decoder_utf8 = Charset.forName("UTF-8").newDecoder();
	
	final IoBuffer buffer ;
	
	public NetDataInputImpl(IoBuffer buffer) {
		this.buffer = buffer;
	}
	
//	-----------------------------------------------------------------------------------------------
	
	public boolean readBoolean() throws IOException {
		return buffer.get()!=0;
	}

	public byte readByte() throws IOException {
		return buffer.get();
	}

	public char readChar() throws IOException {
		return buffer.getChar();
	}

	public double readDouble() throws IOException {
		return buffer.getDouble();
	}

	public float readFloat() throws IOException {
		return buffer.getFloat();
	}

	public void readFully(byte[] b, int off, int len) throws IOException {
		buffer.get(b, off, len);
	}

	public void readFully(byte[] b) throws IOException {
		buffer.get(b);
	}

	public int readInt() throws IOException {
		return buffer.getInt();
	}

	public String readLine() throws IOException {
		throw new NotImplementedException();
	}

	public long readLong() throws IOException {
		return buffer.getLong();
	}

	public short readShort() throws IOException {
		return buffer.getShort();
	}

	public int readUnsignedByte() throws IOException {
		return buffer.getUnsigned();
	}

	public int readUnsignedShort() throws IOException {
		return buffer.getUnsignedShort();
	}

	synchronized
	public String readUTF() throws IOException {
		charset_decoder_utf8.reset();
		return buffer.getString(charset_decoder_utf8);
	}
	
	public int skipBytes(int n) throws IOException {
		buffer.skip(n);
		return n;
	}

//	--------------------------------------------------------------------------------------------------------------
	synchronized
	public boolean[] readBooleanArray() throws IOException {
		return ExternalizableUtil.readBooleanArray(this);
	}
	synchronized
	public char[] readCharArray() throws IOException {
		return ExternalizableUtil.readCharArray(this);
	}
	synchronized
	public byte[] readByteArray() throws IOException {
		return ExternalizableUtil.readByteArray(this);
	}
	synchronized
	public short[] readShortArray() throws IOException {
		return ExternalizableUtil.readShortArray(this);
	}
	synchronized
	public int[] readIntArray() throws IOException {
		return ExternalizableUtil.readIntArray(this);
	}
	synchronized
	public long[] readLongArray() throws IOException {
		return ExternalizableUtil.readLongArray(this);
	}
	synchronized
	public float[] readFloatArray() throws IOException {
		return ExternalizableUtil.readFloatArray(this);
	}
	synchronized
	public double[] readDoubleArray() throws IOException {
		return ExternalizableUtil.readDoubleArray(this);
	}
	
	
	
}