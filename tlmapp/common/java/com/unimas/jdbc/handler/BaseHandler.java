package com.unimas.jdbc.handler;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.unimas.common.date.TimeUtils;
import com.unimas.common.util.StringUtils;
import com.unimas.common.utils.xml.ObjXml;
import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.jdbc.handler.entry.DataValue;
import com.unimas.jdbc.handler.entry.DbType;
import com.unimas.jdbc.handler.entry.SqlModal;
import com.unimas.jdbc.handler.entry.TableData;
import com.unimas.jdbc.handler.entry.TableData.Action;

public class BaseHandler {
	
	protected static final Logger logger = Logger.getLogger(BaseHandler.class);
	
	public static ToType getToType(Field field){
		ToType type = null;
		if(field.isAnnotationPresent(Column.class)){
			Column column = field.getAnnotation(Column.class);
			type = column.toType();
		}
		return type;
	}
	
	public static String getDatePattern(Field field){
		String pattern = TimeUtils.PATTERN_NORMAL;
		if(field.isAnnotationPresent(Column.class)){
			Column column = field.getAnnotation(Column.class);
			pattern = column.datePattern();
		}
		return pattern;
	}
	
	public static String getFieldName(Field field){
		String name = field.getName();
		if(field.isAnnotationPresent(Column.class)){
			Column column = field.getAnnotation(Column.class);
			String columnName = column.name();
			if(StringUtils.isNotEmpty(columnName)){
				name = columnName;
			}
		}
		return name;
	}
	
	public static String getTableName(Class<?> c){
		if(c.isAnnotationPresent(Table.class)){
			Table en = c.getAnnotation(Table.class);
			return en.value();
		} else if(List.class.isAssignableFrom(c) || Map.class.isAssignableFrom(c)){
			return null;
		} else {
			return getTableName(c.getName());
		}
	}

	protected static String getTableName(String classname) {
		return classname.substring(classname.lastIndexOf(".") + 1);
	}
	
	public static List<Field> getFieldsFromClass(Class<?> c){
		List<Field> fields = new ArrayList<Field>();
		if(c != null && !c.equals(ObjXml.class)){
			if (c.getGenericSuperclass() != null) {
				Class<?> superClass = c.getSuperclass();// 父类
				List<Field> superFields = getFieldsFromClass(superClass);
				fields.addAll(superFields);
			}
			for(Field f : c.getDeclaredFields()){
				fields.add(f);
			}
		}
		return fields;
	}
	
	/**
	 * 从表对象类中获取表字段列表
	 * @param c
	 * @return
	 */
	public static List<Field> getColumnsFromClass(Class<?> c){
		List<Field> columns = new ArrayList<Field>();
		List<Field> fields = getFieldsFromClass(c);
		if(fields != null && fields.size() > 0){
			for(Field field : fields){
				if(isChildren(field) || ignore(field)){
					continue;
				}
				columns.add(field);
			}
		}
		return columns;
	}
	
	@SuppressWarnings("rawtypes")
	protected static void setFieldValue(Class clazz, Object t, String columnName, Object value) throws IllegalArgumentException, IllegalAccessException {
		if(value == null) return;
		Field field = getFieldFromClass(clazz, columnName);
		if(field != null){
			field.setAccessible(true);
			field.set(t, value);
			field.setAccessible(false);
		}
	}
	
	public static Object getValue(ResultSet rs, String columnName, Field field) throws SQLException {
		Object value = null;
		ToType toType = getToType(field);
		if(ToType.DateToString.equals(toType)){
			String pattern = getDatePattern(field);
			Timestamp ts = rs.getTimestamp(columnName);
			if(ts != null){
				value = TimeUtils.format(ts, pattern);
			}
		} else if(ToType.toInt.equals(toType)){
			value = rs.getInt(columnName);
		} else if(ToType.toLong.equals(toType)){
			value = rs.getLong(columnName);
		} else if(ToType.toBoolean.equals(toType)){
			value = rs.getBoolean(columnName);
		} else {
			value = rs.getObject(columnName);
		}
		return value;
	} 
	
	public static String getFieldName(Class<?> c, String fieldName){
		Field field = getFieldFromClass(c, fieldName);
		if(field == null) throw new NullPointerException();
		return getFieldName(field);
	}
	
	public static Field getFieldFromClass(Class<?> c, String fieldName){
		Field field = null;
		if(c != null && !c.equals(Object.class)){
			try {
				field = c.getDeclaredField(fieldName);
			}catch (NoSuchFieldException e) {
				if (c.getGenericSuperclass() != null) {
					Class<?> superClass = c.getSuperclass();// 父类
					field = getFieldFromClass(superClass, fieldName);
				}
			}
		}
		return field;
	}
	
	protected static boolean isAuto(Field field) {
		boolean isAuto = false;
		if(field.isAnnotationPresent(Column.class)){
			Column column = field.getAnnotation(Column.class);
			isAuto = column.auto();
		}
		return isAuto;
	}
	
	protected static boolean isPk(Field field) {
		boolean isPk = false;
		if(field.isAnnotationPresent(Column.class)){
			Column column = field.getAnnotation(Column.class);
			isPk = column.isPk();
		}
		return isPk;
	}
	
	protected static boolean isChildren(Field field) {
		boolean isChildren = false;
		if(field.isAnnotationPresent(Column.class)){
			Column column = field.getAnnotation(Column.class);
			isChildren = column.children();
		}
		return isChildren;
	}
	
	protected static boolean ignore(Field field) {
		boolean ignore = false;
		if(field.isAnnotationPresent(Column.class)){
			Column column = field.getAnnotation(Column.class);
			ignore = column.ignore();
		}
		return ignore;
	}
	
	protected static DefaultValue getDefaultValue(Field field, Action action){
		DefaultValue vtype = DefaultValue.Null;
		if(field.isAnnotationPresent(Column.class)){
			Column column = field.getAnnotation(Column.class);
			if(TableData.Action.update.equals(action)){
				vtype = column.updateValue();
			} else if(TableData.Action.insert.equals(action)){
				vtype = column.insertValue();
			}
		}
		return vtype;
	}
	
	public static TableData toData(Class<?> c, TableData.Action action) {
		if(c == null || action == null) return null;
		TableData table = new TableData();
		table.setClasz(c);
		table.setName(getTableName(c));
		table.setAction(action);
		return table;
	}
	
	public static TableData toData(Object obj, TableData.Action action) throws Exception {
		if(obj == null || action == null) return null;
		Class<?> c = obj.getClass();
		TableData table = toData(c, action);
		List<DataValue> datas = getDatas(obj, action);
		table.setDatas(datas);
		return table;
	}
	
	protected static List<DataValue> getDatas(Object obj, TableData.Action action) throws Exception {
		List<DataValue> datas = new ArrayList<DataValue>();
		Class<?> c = obj.getClass();
		List<Field> fields = getFieldsFromClass(c);
		if(fields != null && fields.size() > 0){
			for(Field field : fields){
				if(isChildren(field) || ignore(field)){
					continue;
				}
				String fieldName = getFieldName(field);
				field.setAccessible(true);
				Object value = field.get(obj);
				field.setAccessible(false);
				DataValue data = new DataValue(field, fieldName, value);
				if(isAuto(field)){
					data.setAuto(true);
				}
				if(isPk(field)){
					data.setPk(true);
				}
				DefaultValue vtype = getDefaultValue(field, action);
				data.setDv(vtype);
				boolean isNull = data.isNull();
				if(!isNull || (!vtype.equals(DefaultValue.Null)/* && !TableData.Action.select.equals(action)*/)){
					if(!isNull){
						ToType toType = getToType(field);
						if(ToType.DateToString.equals(toType)){
							if(value instanceof String){
								String pattern = getDatePattern(field);
								data.setValue(new Timestamp(TimeUtils.parse((String)value, pattern).getTime()));
							}
						}
					}
					datas.add(data);
				}
			}
		}
		return datas;
	}
	
	protected static void setValues(PreparedStatement ppst, List<Object> values) throws SQLException {
		if (values != null && values.size() > 0) {
			for (int i = 0; i < values.size(); i++) {
				ppst.setObject(i + 1, values.get(i));
			}
		}
	}
	
	/**
	 * 获取数据库类型
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static DbType getDataBaseType(Connection conn) throws SQLException {  
		String driverName = conn.getMetaData().getDriverName();
        //通过driverName是否包含关键字判断  
        if (driverName.toUpperCase().indexOf("MYSQL") != -1) {  
            return DbType.MYSQL;  
        } else if (driverName.toUpperCase().indexOf("SQL SERVER") != -1) {  
            //sqljdbc与sqljdbc4不同，sqlserver中间有空格  
            //return DbType.SQLSERVER;  
        } else if (driverName.toUpperCase().indexOf("POSTGRESQL") != -1) {  
            return DbType.POSTGRES;
        }  
        return null;  
    }
	
	/**
	 * 解决字段名与保留字冲突的问题
	 * @param field
	 * @return
	 * @throws SQLException 
	 */
	protected static String reservedWordConflict(String field, Connection conn) throws SQLException{
		DbType type = getDataBaseType(conn);
		return SqlModal.reservedWordConflict(field, type);
	}

}
