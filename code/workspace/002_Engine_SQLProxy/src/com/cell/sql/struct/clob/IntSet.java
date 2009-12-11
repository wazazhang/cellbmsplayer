package com.cell.sql.struct.clob;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;

import com.cell.io.TextDeserialize;
import com.cell.io.TextSerialize;
import com.cell.sql.SQLStructCLOB;

public class IntSet extends HashSet<Integer> implements SQLStructCLOB
{
	
	public IntSet() {}
	
	public IntSet(int len) {
		super(len);
	}
	
	public IntSet(Collection<Integer> another) {
		super(another);
	}
	
	synchronized public void decode(Reader in) throws IOException {
		int size = TextDeserialize.getInt(in);
		super.clear();
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

}
