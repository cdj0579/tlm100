package com.unimas.jdbc.handler.annotation;

public enum DefaultValue {
	
	/**
	 * 当字段值为Null时，不保存此字段
	 */
	Null,
	/**
	 * 当字段值为Null时，保存为字符串
	 */
	Empty,
	/**
	 * 当字段值为Null时，取当前系统时间
	 */
	Now;

}
