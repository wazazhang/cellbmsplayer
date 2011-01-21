package com.net.anno;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 给ExternalizableMessage做类型签名的注释
 * @author WAZA
 */
@Documented
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface ExternalizableMessageType 
{
	public int value();
}