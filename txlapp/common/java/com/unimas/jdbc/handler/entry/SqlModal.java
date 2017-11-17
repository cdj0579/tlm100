package com.unimas.jdbc.handler.entry;

import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * <p>描述: 支持预编译的Sql模型</p>
 * @author hxs
 * @date 2017年1月12日 下午2:14:12
 */
public class SqlModal {
	
	protected String sql;
	protected List<Object> values = Lists.newArrayList();
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<Object> getValues() {
		return values;
	}
	public void addValue(Object value){
		this.values.add(value);
	}
	
	/**
	 * 解决字段名与保留字冲突的问题
	 * @param field
	 * @return
	 * @throws SQLException 
	 */
	public static String reservedWordConflict(String field, DbType type){
		if(DbType.POSTGRES.equals(type)){
			return "\""+field+"\"";
		} else if(DbType.MYSQL.equals(type)){
			return "`"+field+"`";
		} else {
			return field;
		}
	}

}
