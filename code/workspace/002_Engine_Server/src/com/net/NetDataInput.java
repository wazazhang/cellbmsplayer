package com.net;

import java.io.DataInput;
import java.io.IOException;

public interface NetDataInput extends DataInput
{
	public boolean[] readBooleanArray() throws IOException;
	
	public char[] readCharArray() throws IOException;
	
	public byte[] readByteArray() throws IOException;
	
	public short[] readShortArray() throws IOException;
	
	public int[] readIntArray() throws IOException;
	
	public long[] readLongArray() throws IOException;
	
	public float[] readFloatArray() throws IOException;
	
	public double[] readDoubleArray() throws IOException;
}
