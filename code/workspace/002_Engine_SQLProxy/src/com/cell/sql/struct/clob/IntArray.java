package com.cell.sql.struct.clob;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import com.cell.CUtil;
import com.cell.io.BigIODeserialize;
import com.cell.io.BigIOSerialize;
import com.cell.io.TextDeserialize;
import com.cell.io.TextSerialize;
import com.cell.sql.SQLStructCLOB;

public class IntArray implements SQLStructCLOB 
{
	public int[] array;
	
	public IntArray() {
		array = new int[0];
	}
	
	public IntArray(int len) {
		array = new int[len];
	}
	
	public IntArray(int[] another) {
		array = another;
	}
	
	public void decode(Reader in) throws IOException {
		array = TextDeserialize.getInts(in);
	}

	public void encode(Writer out) throws IOException {
		TextSerialize.putInts(out, array);
	}
	
	@Override
	public String toString() {
		return array.toString();
	}
}
