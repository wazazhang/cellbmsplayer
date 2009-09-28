package com.cell.sql.struct.blob;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

import com.cell.sql.SQLStructBLOB;

public class BlobArray<T> extends ArrayList<T> implements SQLStructBLOB
{
	private static final long serialVersionUID = 1L;
}
