package com.cell.mysql;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.sql.Types;

import com.cell.sql.SQLStructCLOB;
import com.cell.sql.SQLType;
import com.cell.sql.SQLTypeComparer;
import com.cell.sql.util.SQLUtil;
import com.cell.xstream.XStreamAdapter;

/**
 * @author WAZA

<html>
<body>
<table border="1">
<colgroup>
<col>
<col>
<col>
</colgroup>
<thead><tr>
<th>MySQL Type Name</th>
<th>Return value of <code class="literal">GetColumnClassName</code>
</th>
<th>Returned as Java Class</th>
</tr></thead>
<tbody>
<tr>
<td>
<span class="type">BIT(1)</span> (new in MySQL-5.0)</td>
<td><span class="type">BIT</span></td>
<td><code class="classname">java.lang.Boolean</code></td>
</tr>
<tr>
<td>
<span class="type">BIT( &gt; 1)</span> (new in MySQL-5.0)</td>
<td><span class="type">BIT</span></td>
<td><code class="classname">byte[]</code></td>
</tr>
<tr>
<td><span class="type">TINYINT</span></td>
<td><span class="type">TINYINT</span></td>
<td>
<code class="classname">java.lang.Boolean</code> if the configuration property
                    <code class="literal">tinyInt1isBit</code> is set to
                    <code class="literal">true</code> (the default) and the
                    storage size is 1, or
                    <code class="classname">java.lang.Integer</code> if not.</td>
</tr>
<tr>
<td>
<span class="type">BOOL</span>, <span class="type">BOOLEAN</span>
</td>
<td><span class="type">TINYINT</span></td>
<td>See <span class="type">TINYINT</span>, above as these are aliases for
                    <span class="type">TINYINT(1)</span>, currently.</td>
</tr>
<tr>
<td><span class="type">SMALLINT[(M)] [UNSIGNED]</span></td>
<td><span class="type">SMALLINT [UNSIGNED]</span></td>
<td>
<code class="classname">java.lang.Integer</code> (regardless if UNSIGNED or not)</td>
</tr>
<tr>
<td><span class="type">MEDIUMINT[(M)] [UNSIGNED]</span></td>
<td><span class="type">MEDIUMINT [UNSIGNED]</span></td>
<td>
<code class="classname">java.lang.Integer,</code> if UNSIGNED
                    <code class="classname">java.lang.Long</code> (C/J 3.1 and
                    earlier), or
                    <code class="classname">java.lang.Integer</code> for C/J 5.0
                    and later</td>
</tr>
<tr>
<td><span class="type">INT,INTEGER[(M)] [UNSIGNED]</span></td>
<td><span class="type">INTEGER [UNSIGNED]</span></td>
<td>
<code class="classname">java.lang.Integer</code>, if UNSIGNED
                    <code class="classname">java.lang.Long</code>
</td>
</tr>
<tr>
<td><span class="type">BIGINT[(M)] [UNSIGNED]</span></td>
<td><span class="type">BIGINT [UNSIGNED]</span></td>
<td>
<code class="classname">java.lang.Long</code>, if UNSIGNED
                    <code class="classname">java.math.BigInteger</code>
</td>
</tr>
<tr>
<td><span class="type">FLOAT[(M,D)]</span></td>
<td><span class="type">FLOAT</span></td>
<td><code class="classname">java.lang.Float</code></td>
</tr>
<tr>
<td><span class="type">DOUBLE[(M,B)]</span></td>
<td><span class="type">DOUBLE</span></td>
<td><code class="classname">java.lang.Double</code></td>
</tr>
<tr>
<td><span class="type">DECIMAL[(M[,D])]</span></td>
<td><span class="type">DECIMAL</span></td>
<td><code class="classname">java.math.BigDecimal</code></td>
</tr>
<tr>
<td><span class="type">DATE</span></td>
<td><span class="type">DATE</span></td>
<td><code class="classname">java.sql.Date</code></td>
</tr>
<tr>
<td><span class="type">DATETIME</span></td>
<td><span class="type">DATETIME</span></td>
<td><code class="classname">java.sql.Timestamp</code></td>
</tr>
<tr>
<td><span class="type">TIMESTAMP[(M)]</span></td>
<td><span class="type">TIMESTAMP</span></td>
<td><code class="classname">java.sql.Timestamp</code></td>
</tr>
<tr>
<td><span class="type">TIME</span></td>
<td><span class="type">TIME</span></td>
<td><code class="classname">java.sql.Time</code></td>
</tr>
<tr>
<td><span class="type">YEAR[(2|4)]</span></td>
<td><span class="type">YEAR</span></td>
<td>If <code class="literal">yearIsDateType</code> configuration property is set to
                    false, then the returned object type is
                    <code class="classname">java.sql.Short</code>. If set to
                    true (the default) then an object of type
                    <code class="classname">java.sql.Date</code> (with the date
                    set to January 1st, at midnight).</td>
</tr>
<tr>
<td><span class="type">CHAR(M)</span></td>
<td><span class="type">CHAR</span></td>
<td>
<code class="classname">java.lang.String</code> (unless the character set for
                    the column is <span class="type">BINARY</span>, then
                    <code class="classname">byte[]</code> is returned.</td>
</tr>
<tr>
<td><span class="type">VARCHAR(M) [BINARY]</span></td>
<td><span class="type">VARCHAR</span></td>
<td>
<code class="classname">java.lang.String</code> (unless the character set for
                    the column is <span class="type">BINARY</span>, then
                    <code class="classname">byte[]</code> is returned.</td>
</tr>
<tr>
<td><span class="type">BINARY(M)</span></td>
<td><span class="type">BINARY</span></td>
<td><code class="classname">byte[]</code></td>
</tr>
<tr>
<td><span class="type">VARBINARY(M)</span></td>
<td><span class="type">VARBINARY</span></td>
<td><code class="classname">byte[]</code></td>
</tr>
<tr>
<td><span class="type">TINYBLOB</span></td>
<td><span class="type">TINYBLOB</span></td>
<td><code class="classname">byte[]</code></td>
</tr>
<tr>
<td><span class="type">TINYTEXT</span></td>
<td><span class="type">VARCHAR</span></td>
<td><code class="classname">java.lang.String</code></td>
</tr>
<tr>
<td><span class="type">BLOB</span></td>
<td><span class="type">BLOB</span></td>
<td><code class="classname">byte[]</code></td>
</tr>
<tr>
<td><span class="type">TEXT</span></td>
<td><span class="type">VARCHAR</span></td>
<td><code class="classname">java.lang.String</code></td>
</tr>
<tr>
<td><span class="type">MEDIUMBLOB</span></td>
<td><span class="type">MEDIUMBLOB</span></td>
<td><code class="classname">byte[]</code></td>
</tr>
<tr>
<td><span class="type">MEDIUMTEXT</span></td>
<td><span class="type">VARCHAR</span></td>
<td><code class="classname">java.lang.String</code></td>
</tr>
<tr>
<td><span class="type">LONGBLOB</span></td>
<td><span class="type">LONGBLOB</span></td>
<td><code class="classname">byte[]</code></td>
</tr>
<tr>
<td><span class="type">LONGTEXT</span></td>
<td><span class="type">VARCHAR</span></td>
<td><code class="classname">java.lang.String</code></td>
</tr>
<tr>
<td><span class="type">ENUM('value1','value2',...)</span></td>
<td><span class="type">CHAR</span></td>
<td><code class="classname">java.lang.String</code></td>
</tr>
<tr>
<td><span class="type">SET('value1','value2',...)</span></td>
<td><span class="type">CHAR</span></td>
<td><code class="classname">java.lang.String</code></td>
</tr>
</tbody>
</table>
</body>
</html>
 */
public class SQLTypeComparerMySQL implements SQLTypeComparer 
{
	public boolean typeEquals(SQLType type, int jdbc_type) 
	{
		switch(type)
		{
		case STRUCT:
			switch(jdbc_type){
			case Types.BINARY: 			return true;
			case Types.VARBINARY: 		return true;
			case Types.LONGVARBINARY: 	return true;
			}
			break;
		case TEXT_STRUCT:
		case XML_STRUCT:
			switch(jdbc_type){
			case Types.VARCHAR: 		return true;
			case Types.LONGVARCHAR: 	return true;
			}
			break;
		case BOOLEAN:
			switch (jdbc_type) {
			case Types.TINYINT: 		return true;
			case Types.BIT:				return true;
			}
			break;
		}
		return type.getJdbcType() == jdbc_type;
	}

	public Object toJavaObject(SQLType type, Class<?> type_clazz, Object sqlObject) throws Exception 
	{
		switch(type)
		{
		case STRUCT:
			return SQLUtil.binToBlob((byte[])sqlObject);
		case TEXT_STRUCT:
			return SQLUtil.stringToClob((String)sqlObject, type_clazz);
		case XML_STRUCT:
			return SQLUtil.stringToXML((String)sqlObject);
		case BYTE:
			return ((Number)sqlObject).byteValue();
		case SHORT:
			return ((Number)sqlObject).shortValue();
		default:
			return sqlObject;
		}
	}
	
	public Object toSQLObject(SQLType type, Class<?> type_clazz, Object javaObject) throws Exception 
	{
		switch(type)
		{
		case STRUCT:
			return SQLUtil.blobToBin((Serializable)javaObject);
		case TEXT_STRUCT:
			return SQLUtil.clobToString((SQLStructCLOB)javaObject);
		case XML_STRUCT:
			return SQLUtil.xmlToString((Serializable)javaObject);
		default:
			return javaObject;
		}
	}
	
	@Override
	public ObjectInputStream getXMLInputStream(Reader reader) throws IOException {
		ObjectInputStream ois = XStreamAdapter.getInstance().createReadStream(reader);
		return ois;
	}
	
	@Override
	public ObjectOutputStream getXMLOutputStream(Writer writer) throws IOException {
		ObjectOutputStream oos = XStreamAdapter.getInstance().createWriteStream(writer);
		return oos;
	}
	
	public String getDirverTypeString(int jdbc_type) 
	{
		switch (jdbc_type) {
		case Types.BOOLEAN:			return "boolean";
		case Types.TINYINT: 		return "tinyint";
		case Types.SMALLINT:		return "smallint";
		case Types.INTEGER: 		return "integer";
		case Types.BIGINT: 			return "bigint";
		case Types.REAL: 			return "float";
		case Types.DOUBLE: 			return "double";
		case Types.VARCHAR: 		return "varchar";
		case Types.BLOB: 			return "blob";
		case Types.CLOB: 			return "text";
		case Types.TIME: 			return "time";
		case Types.TIMESTAMP:		return "timestamp";
		case Types.ARRAY:			return "blob";
		case Types.LONGVARBINARY:	return "longblob";
		}
		return null;
	}
}

