package com.unimas.tlm.bean;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;

@Table("hyd_rules")
public class HydRule extends JdbcBean {
	
	private String name;
	private String type;
	@Column(name="hyd", nullNumberValue=-1)
	private int hyd = -1;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getHyd() {
		return hyd;
	}
	public void setHyd(int hyd) {
		this.hyd = hyd;
	}

}
