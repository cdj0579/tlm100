package com.unimas.jdbc.handler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unimas.common.date.TimeUtils;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	/**
	 * 字段名称或SQL时的别名
	 * @return
	 */
	public String name() default "";
	/**
	 * 返回信息时，转换的方式
	 * @return
	 */
	public ToType toType() default ToType.Default;
	
	/**
	 * 返回信息时，转换的方式
	 * @return
	 */
	public String datePattern() default TimeUtils.PATTERN_NORMAL;
	
	/**
	 * 为数字类型指定一个代表NULL的值，为NULL时不修改此字段
	 * @return
	 */
	public int nullNumberValue() default 0;
	/**
	 * 修改表：当字段字段值为Null时的处理方式
	 * @return
	 */
	public DefaultValue updateValue() default DefaultValue.Null;
	/**
	 * 插入表：当字段字段值为Null时的处理方式
	 * @return
	 */
	public DefaultValue insertValue() default DefaultValue.Null;
	/**
	 * 是否主键，或条件字段（修改表信息时有效）
	 * @return
	 */
	public boolean isPk() default false;
	/**
	 * 是否自增字段（插入表信息时有效）
	 * @return
	 */
	public boolean auto() default false;
	/**
	 * 是否忽略（插入或修改表信息时有效）
	 * @return
	 */
	public boolean ignore() default false;
	/**
	 * 子表
	 * @return
	 */
	public boolean children() default false;
}
