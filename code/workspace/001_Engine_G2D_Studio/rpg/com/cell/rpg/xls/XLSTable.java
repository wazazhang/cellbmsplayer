package com.cell.rpg.xls;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.Sheet;
import jxl.Workbook;

import com.cell.CIO;
import com.cell.reflect.Parser;
import com.cell.sql.SQLColumn;
import com.cell.sql.SQLTableManager;
import com.cell.sql.SQLTableRow;

public class XLSTable<V extends SQLTableRow<?>>
{	
	ArrayList<V> values = new ArrayList<V>();
	
	public XLSTable(String file, Class<V> cls) throws Exception
	{
		InputStream is = CIO.loadStream(file);
		
		try
		{
			SQLColumn[]	sql_columns		= SQLTableManager.getSQLColumns(cls);
			Workbook	work_book		= Workbook.getWorkbook(is);

		    System.out.println("Init XLSTable : " + file + "\n" +
//		    		"\ttable size  : row="+row_count+" column="+column_count+"\n" +
		    		"\ttable class : " + cls.getSimpleName());
		 
			for (Sheet rs : work_book.getSheets()) 
			{
			    int			row_start		= 1;
			    int			column_start	= 1;
			    int			row_count		= rs.getRows();
			    int			column_count	= rs.getColumns();
			    
				for (int r = row_start+1; r < row_count; r++)
				{
					try
					{
						HashMap<String, String> row = new HashMap<String, String>(column_count);
						
						String primary_key = rs.getCell(column_start, r).getContents().trim();
						if (primary_key.length()<=0) {
							 System.out.println("\ttable eof at row " + r + " sheet " + rs.getName());
							break;
						}
						
						for (int c = column_start; c < column_count; c++) {
							String k = rs.getCell(c, row_start).getContents().trim();
							String v = rs.getCell(c, r).getContents().trim();
							row.put(k, v);
						}
						
						V instance = cls.newInstance();
						for (SQLColumn sql_column : sql_columns) 
						{
							if (row.containsKey(sql_column.name)) 
							{
								String v = row.get(sql_column.name);
								Object value = Parser.stringToObject(v, sql_column.leaf_field.getType());
								
								if (value != null) {
									sql_column.setObject(instance, value);
//									System.out.println(sql_column.name + "=" + value);
								}else{
									throw new NullPointerException(
											"format error with column ( " + sql_column.name + " = \""+ v +"\" ) at row " + r + " sheet " + rs.getName());
								}
							}
						}
						values.add(instance);
					}
					catch (Exception e) {
						System.err.println("read error at row " + r + " sheet " + rs.getName());
						throw e;
					}
				}
			
			}
		}
		finally{
			is.close();
		}
	}
	
	public ArrayList<V> getValues() 
	{
		return values;
	}
		
}


