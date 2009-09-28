package com.cell.sql.struct.blob;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;

import com.cell.sql.SQLStructBLOB;

public class BlobMap<K, V> extends HashMap<K, V> implements SQLStructBLOB
{
	private static final long serialVersionUID = 1L;
}
