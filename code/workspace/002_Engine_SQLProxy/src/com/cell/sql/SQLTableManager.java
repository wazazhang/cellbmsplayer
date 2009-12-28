package com.cell.sql;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CUtil;
import com.cell.CUtil.ICompare;
import com.cell.CUtil.StringCompare;
import com.cell.reflect.Fields;
import com.cell.sql.anno.SQLField;
import com.cell.sql.anno.SQLTable;
import com.cell.sql.util.SQLUtil;


/**
 * loadAll 读入所有数据，在运行时，只写入数据，所有Table的PrimaryKey由该类管理。
 * @author WAZA
 *
 * @param <K> PrimaryKey 类型
 * @param <R> Row 类型
 */
public abstract class SQLTableManager<K, R extends SQLTableRow<K>> extends SQLColumnsManager<K, R>
{
	public SQLTableManager(Class<R> cls)
	{
		super(cls, new ConcurrentHashMap<K, R>(1024));
	}
	
	public SQLTableManager(Class<R> cls, int base_size)
	{
		super(cls, new ConcurrentHashMap<K, R>(base_size));
	}
	
}
