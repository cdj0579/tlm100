package com.unimas.jdbc.handler.entry;

import java.util.List;

public class TableData {
	
	public enum Action {
		update,insert,delete,select
	}
	
	private Class<?> clasz;
	private String name;
	private Action action;
	private List<DataValue> datas;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<DataValue> getDatas() {
		return datas;
	}
	public void setDatas(List<DataValue> datas) {
		this.datas = datas;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Class<?> getClasz() {
		return clasz;
	}
	public void setClasz(Class<?> clasz) {
		this.clasz = clasz;
	}

}
