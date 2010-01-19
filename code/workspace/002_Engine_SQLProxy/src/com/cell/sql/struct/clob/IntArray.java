package com.cell.sql.struct.clob;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import com.cell.CUtil;
import com.cell.io.BigIODeserialize;
import com.cell.io.BigIOSerialize;
import com.cell.io.TextDeserialize;
import com.cell.io.TextSerialize;
import com.cell.sql.SQLStructCLOB;

public class IntArray extends Vector<Integer> implements SQLStructCLOB
{
	public IntArray() {}
	
	public IntArray(int len) {
		super(len);
	}
	
	public IntArray(Collection<Integer> another) {
		super(another);
	}
	
	public IntArray(int[] another) {
		super(another.length);
		for (int d : another) {
			super.add(d);
		}
	}
	
	
	synchronized public void decode(Reader in) throws IOException {
		int size = TextDeserialize.getInt(in);
		super.clear();
		super.ensureCapacity(size);
		for (int i=0; i<size; i++) {
			this.add(TextDeserialize.getInt(in));
		}
	}

	synchronized public void encode(Writer out) throws IOException {
		TextSerialize.putInt(out, super.size());
		for (Integer e : this) {
			TextSerialize.putInt(out, e);
		}
	}
	
	synchronized public int[] toIntArray() {
		int[] ret = new int[size()];
		int i=0;
		for (Integer d : this) {
			ret[i] = d;
			i++;
		}
		return ret;
	}
}
