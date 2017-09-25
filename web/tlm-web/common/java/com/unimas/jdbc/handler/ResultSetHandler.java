package com.unimas.jdbc.handler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.unimas.common.date.TimeUtils;

/**
 * <p>描述: 处理ResultSet的工具类</p>
 * @author hxs
 * @date 2017年1月12日 上午10:56:11
 */
public class ResultSetHandler extends BaseHandler {
	
	/**
	 * 获取ResultSet第一行第一列并转换成long的便捷方法
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static long toLong(ResultSet rs) throws Exception {
		long value = -1;
		if(rs != null && rs.next()){
			value = rs.getLong(1);
		}
		return value;
	}
	
	/**
	 * 获取ResultSet第一行第一列并转换成Int的便捷方法
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static int toInt(ResultSet rs) throws Exception {
		int value = -1;
		if(rs != null && rs.next()){
			value = rs.getInt(1);
		}
		return value;
	}
	
	/**
	 * 获取ResultSet第一行第一列并转换成String的便捷方法
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static String toString(ResultSet rs) throws Exception {
		String value = null;
		if(rs != null && rs.next()){
			value = String.valueOf(rs.getObject(1));
		}
		return value;
	}
	
	/**
	 * 获取ResultSet第一行并转换成Bean的便捷方法
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static <T> T bean(ResultSet rs, Class<T> clazz) throws Exception {
		T t = null;
		if(rs != null && rs.next()){
			ResultSetMetaData md = rs.getMetaData();
			t = rsToBean(rs,md,clazz);
		}
		return t;
	}
	
	/**
	 * 将ResultSet转换成ListMap的便捷方法
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> listMap(ResultSet rs) throws Exception {
		return listMap(rs, null);
	}
	
	/**
	 * 将ResultSet转换成ListMap的便捷方法
	 * @param dateField 需要将时间格式转换为yyyy-MM-dd HH:mm:ss格式字符串的字段
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> listMap(ResultSet rs, String dateField) throws Exception {
		List<Map<String, Object>> list = null;
		if(rs != null){
			list = new ArrayList<Map<String, Object>>();
			ResultSetMetaData md = rs.getMetaData();
			while (rs.next()){
				list.add(rsToMap(rs, md, dateField));
			}
		}
		return list;
	}
	
	/**
	 * 将ResultSet转换成Map的便捷方法
	 * @param key   作为key的列名
	 * @param value 作为value的列名
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> map(ResultSet rs, String key, String value) throws Exception {
		Map<String, Object> map = null;
		if(rs != null){
			map = Maps.newHashMap();
			while (rs.next()){
				map.put(rs.getString(key), rs.getObject(value));
			}
		}
		return map;
	}
	
	/**
	 * 将ResultSet转换成Map的便捷方法, 默认第一列作为key, 第二列作为value
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> map(ResultSet rs) throws Exception {
		Map<String, Object> map = null;
		if(rs != null){
			ResultSetMetaData md = rs.getMetaData();
			if(md.getColumnCount() > 2){
				throw new Exception("结果集中至少包括俩列数据!");
			}
			String key = md.getColumnLabel(1);
			String value = md.getColumnLabel(2);
			map = map(rs, key, value);
		}
		return map;
	}
	
	public static final Map<String, Object> rsToMap(ResultSet rs,ResultSetMetaData md, String dateField) throws Exception {
		Map<String, Object> map = null;
		if(rs != null && md != null){
			map = new LinkedHashMap<String, Object>();
			for(int i=1;i<=md.getColumnCount();i++){
				String key = md.getColumnLabel(i);
				Object v = null;
				if(dateField != null && dateField.equals(key)){
					Timestamp ts = rs.getTimestamp(key);
					if(ts != null){
						v = TimeUtils.format(ts);
					}
				} else {
					v = rs.getObject(key);
				}
				v = v==null?"":v;
				if(key.contains(".")) key = key.replaceAll("\\.", "_");
				map.put(key, v);
			}
		}
		return map;
	}
	
	/**
	 * 将ResultSet转换成ListBean的便捷方法
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> listBean(ResultSet rs, Class<T> clazz) throws Exception {
		List<T> list = null;
		if(rs != null){
			list = new ArrayList<T>();
			ResultSetMetaData md = rs.getMetaData();
			while (rs.next()){
				list.add(rsToBean(rs,clazz,md));
			}
		}
		return list;
	}
	
	protected static final <T> T rsToBean(ResultSet rs,ResultSetMetaData md,Class<T> clazz) throws Exception {
		return rsToBean(rs, clazz, md);
	}
	
	/*public static final <T> T rsToBean1(ResultSet rs,ResultSetMetaData md,Class<T> clazz, String dateField) throws Exception {
		T t = clazz.newInstance();
		for (int i = 1; i <= md.getColumnCount(); i++) {
			String columnName = md.getColumnLabel(i);
			Object object = null;
			if(dateField != null && dateField.equals(columnName)){
				Timestamp ts = rs.getTimestamp(columnName);
				if(ts != null){
					object = TimeUtils.format(ts);
				}
			} else {
				object = rs.getObject(columnName);
			}
			setFieldValue(clazz, t, columnName, object);
		}
		return t;

	}*/
	
	private static boolean hasColumn(String fieldName, ResultSetMetaData md) {
		boolean has = false;
		try {
			for (int i = 1; i <= md.getColumnCount(); i++) {
				String columnName = md.getColumnLabel(i);
				if(fieldName.equals(columnName)){
					has = true;
					break;
				}
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage(), e);
		}
		return has;
	}
	
	public static final <T> T rsToBean(ResultSet rs, Class<T> clazz, ResultSetMetaData md) throws Exception {
		T t = clazz.newInstance();
		List<Field> fields = getFieldsFromClass(clazz);
		md = md==null?rs.getMetaData():md;
		for(Field field : fields){
			String fieldName = getFieldName(field);
			if(hasColumn(fieldName, md)){
				Object value = getValue(rs, fieldName, field);
				if(value != null){
					field.setAccessible(true);
					field.set(t, value);
					field.setAccessible(false);
				}
			}
		}
		return t;
	}
	
	public static final <T> T custom(ResultSet rs, IHandler<T> handler) throws Exception {
		return handler.handler(rs);
	}

}
