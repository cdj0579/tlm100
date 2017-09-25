package com.unimas.jdbc.handler.entry;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;

public class DataValue {
	
	private Field f;
	private String field;
	private Object value;
	private boolean isAuto;
	private boolean isPk;
	private DefaultValue dv;
	
	public DataValue(){};
	
	public DataValue(Field f, String field, Object value){
		this.f = f;
		this.field = field;
		this.value = value;
	}
	
	/**
	 * 若字段未赋值，可以设置默认值<br/>
	 *    目前新增或修改时有用
	 * @return
	 */
	public Object getRealValue(){
		Object value = this.value;
		if(isNull() && !dv.equals(DefaultValue.Null) && !isPk() && !isAuto()){
			if(dv.equals(DefaultValue.Now)){
				value = new Timestamp(System.currentTimeMillis());
			} else if(dv.equals(DefaultValue.Empty)){
				value = "";
			}
		}
		return value;
	}
	
	/**
	 * 判断字段是否赋值
	 * @return
	 */
	public boolean isNull(){
		boolean isNull = false;
		if(value == null){
			isNull = true;
		} else if(value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double){
			int nullValue = 0;
			if(f.isAnnotationPresent(Column.class)){
				Column column = f.getAnnotation(Column.class);
				nullValue = column.nullNumberValue(); 
			}
			if((value instanceof Integer && nullValue == (Integer)value) 
					|| (value instanceof Long && nullValue == (Long)value) 
					|| (value instanceof Float && nullValue == (Float)value) 
					|| (value instanceof Double && nullValue == (Double)value)){
				isNull = true;
			}
		}
		return isNull;
	}
	
	public String getField() {
		return field;
	}
	public Field getFieldC() {
		return f;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isAuto() {
		return isAuto;
	}

	public void setAuto(boolean isAuto) {
		this.isAuto = isAuto;
	}

	public boolean isPk() {
		return isPk;
	}

	public void setPk(boolean isPk) {
		this.isPk = isPk;
	}

	public DefaultValue getDv() {
		return dv;
	}

	public void setDv(DefaultValue dv) {
		this.dv = dv;
	}

}
