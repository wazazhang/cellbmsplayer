/**
 * 
 */
package com.cell.sql.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cell.sql.SQLType;

/**
 * 表示该字段是SQL类型，建议该类型申明为 protected 或者 public
 * @author WAZA
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLField 
{
	/**
	 * 得到该字段对应的SQL类型
	 * @return
	 */
	SQLType	type();

	/**
	 * 得到创建数据库时，该字段的约束，比如 NOT NULL
	 * @return
	 */
	String	constraint() default "";

	/**
	 * 该字段在创建数据库时的默认值
	 * @return
	 */
	String	defaultValue() default "";
}
